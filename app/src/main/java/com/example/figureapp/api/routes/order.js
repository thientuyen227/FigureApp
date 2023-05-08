var express = require('express');
var router = express.Router();

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.post('/orders', function(req, res, next){
  if(!req.user){
    return res.status(401).json({message : 'Bạn chưa đăng nhập'})
  }
  else{
    const userId= req.user.userId;
    const query = 'SELECT * FROM orders WHERE userId = ?'
    connection.query(query, [userId], function(err, results, fields){
      if(err) throw err;
      res.json(results);
    })
  }
})
module.exports = router;