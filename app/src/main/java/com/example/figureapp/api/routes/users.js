var express = require('express');
var jwt = require('jsonwebtoken');

var router = express.Router();
const uuid = require('uuid');
const admin = require('firebase-admin');
const serviceAccount = require('../serviceAccountKey.json');


// Initialize Firebase Admin SDK
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  storageBucket: 'figureapp-b8eb4.appspot.com'
});
const multer = require('multer');
const upload = multer({ dest: 'uploads/' });

const { authenticateToken, parseUserId, parseRole } = require('./helper.js')
//Connection MySQL
const connection = require('./connectionMySQL');
const { route } = require('./product');
/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});
//get profile
router.get('/getProfile', authenticateToken, function (req, res, next) {
  const userId = parseUserId(req);
  const sql = 'select * from user where id = ? limit 1';
  connection.query(sql, [userId], (err, result) => {
    if (err) throw err;
    res.json(result[0]);
  });
})

router.put('/changeAvatar', upload.single("avatar") ,authenticateToken, async function (req, res, next) {
  const userId = parseUserId(req);
  const avatar = req.file;
  const avatarFilename = `avatar-${uuid.v4()}.jpg`;
  const destination = `resources/image_user/${avatarFilename}`;

  try {
    // Upload Avatar to Firebase Storage
    const bucket = admin.storage().bucket();
    await bucket.upload(avatar.path, {
      destination: destination,
      contentType: 'image/jpeg'
    });
    // Get signed URL for Avatar file
    const avatarUrl = `https://firebasestorage.googleapis.com/v0/b/${bucket.name}/o/resources%2Fimage_user%2F${avatarFilename}?alt=media`;

    // Update user profile in MySQL database
    const sql = 'UPDATE user SET avatar = ? WHERE id = ?';
    const params = [avatarUrl, userId];
    connection.query(sql, params, (err, result) => {
      if (err) throw err;
      res.json(result);
    });
  } catch (error) {
    console.log(error);
    res.status(500).send(error);
  }
});


// Login
router.post('/login', function (req, res, next) {
  const { username, password } = req.body;

  // Query MySQL database to get user with matching username?
  const sql = 'SELECT * FROM user WHERE username = ? AND password = ?';

  connection.query(sql, [username, password], async (error, results) => {
    if (error) throw error;
    const user = results[0]

    if (results.length > 0) {
      // Nếu đúng, trả về mã thông  báo (token) đểsử dụng cho các yêu cầu khác
      const token = jwt.sign({ userId: user.id, role: user.role }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '3d' });
      res.json({ success: true, token: token});
    } else {
      // Nếu sai, trả về thông báo lỗi
      res.status(401).json({ success: false, token: 'Invalid username or password' });
    }
  });
});


// Signup
router.post('/signup', function (req, res, next) {
  const username = req.body.username;
  console.log(username);
  const email = req.body.email;
  const password = req.body.password;
  const name = req.body.name
  // Kiểm tra nếu username hoặc email đã tồn tại trong database
  connection.query('SELECT * FROM user WHERE username = ? OR email = ?', [username, email], (err, results) => {
    if (err) throw err;

    if (results.length > 0) {
      const existingUser = results[0];
      if (existingUser.username === username) {
        return res.status(409).json({ message: 'Username đã tồn tại' });
      } else {
        return res.status(409).json({ message: 'Email đã tồn tại' });
      }
    } else {
      // Thêm mới user vào bảng user
      connection.query('INSERT INTO user (userName,email,password,name) VALUES (?, ?, ?,?)', [username, email, password, name], (err, result) => {
        if (err) throw err;

        // Trả về thông tin user vừa tạo
        const newUser = { id: result.insertId, username, email, password };
        return res.status(201).json(newUser);
      });
    }
  });
});
// router.post('/changeAvatar', upload)


//admin
//
router.get('/getAllUser', authenticateToken, function(req, res, next){
  const role= parseRole(req);
  if(role!="admin"){
    res.status(404).json({success: false})
  }
  else{
    sql = 'select * from user'
    connection.query(sql, (err, result)=>{
      if(err) throw err;
      else res.json(result)
    })
  }
})
//
router.post('/getUser', function(req,res, next){
  const userId = req.body.userId;
  const sql = 'select * from user where id = ?';
  connection.query(sql, [userId], (err, result) => {
    if (err) throw err;
    res.json(result[0]);
  });
})
//
router.post('/deleteUser', authenticateToken, function(req, res, next){
  const role = parseRole(req)
  if(role != 'admin'){
    res.status(404).json({success: false})
  }
  else{
    const userId=req.body.userId;
    console.log(userId);
    const deleteUser = 'delete from user where id=?'
    connection.query(deleteUser, userId, (err, result)=>{
      if(err) throw err;
      else res.json(result);
    })
  }
})
//editUser
router.put('/editUser', authenticateToken, function(req, res, next){
  const roles = parseRole(req);
  if(roles!= "admin"){
    res.status(404).json({success: false})
  }
  else{
    const userId= req.body.userId;
    const name =req.body.name;
    const email = req.body.email;
    const password = req.body.password;
    const role = req.body.role;
    const idCard = req.body.idCard;
    const eWallet = req.body.eWallet;
    console.log(userId);
    const editCategory='Update user set name=?, email=?, password=?, role=?, idCard=?, eWallet=? where id=?'
    const params=[name, email, password, role, idCard, eWallet, userId];
    connection.query(editCategory, params, (err, result)=>{
      if(err) throw err;
      else res.json({success: true})
    })
  }
})
module.exports = router;
