const mysql = require('mysql');
const connection = mysql.createConnection({
  host: 'localhost', // tên host của database
  user: 'root', // tên đăng nhập của database
  password: 'thientuyen', // mật khẩu của database
  database: 'figureapp' // tên database
});
// Kết nối đến database
connection.connect((err) => {
  if (err) throw err;
  console.log('Kết nối thành công!');
});
module.exports = connection;
  
  