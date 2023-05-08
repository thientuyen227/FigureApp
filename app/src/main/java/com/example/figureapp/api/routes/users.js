var express = require('express');
var jwt = require('jsonwebtoken');

var router = express.Router();

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});


  // Login
  router.post('/login', function(req, res, next)  {
    const { username, password } = req.body;
  
    // Query MySQL database to get user with matching username
    const sql = `SELECT * FROM user WHERE email = '${username}' AND password = '${password}'`;

    connection.query(sql, (error, results) => {
      if (error) throw error;
      console.log(results);
  
      if (results.length > 0) {
        // Nếu đúng, trả về mã thông báo (token) để sử dụng cho các yêu cầu khác
        const token = jwt.sign({userid: results.id}, 'nm9aZv1wGP1r2qE6RoVqSQihv3f0BQOU', {expiresIn: '3d'});
        res.json({ success: true, token });
      } else {
        // Nếu sai, trả về thông báo lỗi
        res.status(401).json({ success: false, message: 'Invalid username or password' });
      }
    });
  });
  

// Signup
  router.post('/signup', function(req, res, next){
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
      connection.query('INSERT INTO user (username, email, password) VALUES (?, ?, ?)', [username, email, password], (err, result) => {
        if (err) throw err;

        // Trả về thông tin user vừa tạo
        const newUser = { id: result.insertId, username, email, password };
        return res.status(201).json(newUser);
      });
    }
  });
});


module.exports = router;
