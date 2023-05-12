var express = require('express');
var jwt = require('jsonwebtoken');

var router = express.Router();

const { authenticateToken, parseUserId } = require('./helper.js')
//Connection MySQL
const connection = require('./connectionMySQL');
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
router.put('/updateProfile', authenticateToken, function (req, res, next) {
  const userId = parseUserId(req);
  const name = data.name;
  const email = data.email;
  const avatar = data.avatar;
  const idCard = data.idCard;
  const eWallet = data.eWallet;
  const sql = 'Update user where id = ? set name = ?, email=?, avatar = ? , idCard=?, eWallet =?'
  connection.query(sql, [userId, name, email, avatar, idCard, eWallet], (err, result) => {
    if (err) throw err;
    res.json(result);
  });
})

// Login
router.post('/login', function (req, res, next) {
  const { username, password } = req.body;

  // Query MySQL database to get user with matching username?
  const sql = 'SELECT * FROM user WHERE username = ? AND password = ?';

  connection.query(sql, [username, password], async (error, results) => {
    if (error) throw error;
    const user = results[0]
    const { password, role, ...others } = user

    if (results.length > 0) {
      // Nếu đúng, trả về mã thông  báo (token) đểsử dụng cho các yêu cầu khác
      const token = jwt.sign({ userId: user.id }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '3d' });
      res.json({ success: true, token: token});
    } else {
      // Nếu sai, trả về thông báo lỗi
      res.status(401).json({ success: false, token: 'Invalid username or password' });
    }
  });
});


// Signup
router.post('/signup', function (req, res, next) {
  const { username, email, password } = req.body;
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
      connection.query('INSERT INTO user (userName,email,password) VALUES (?, ?, ?)', [username, email, password], (err, result) => {
        if (err) throw err;

        // Trả về thông tin user vừa tạo
        const newUser = { id: result.insertId, username, email, password };
        return res.status(201).json(newUser);
      });
    }
  });
});
// router.post('/changeAvatar', upload)

module.exports = router;
