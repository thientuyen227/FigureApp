var express = require('express');
var router = express.Router();
const { authenticateToken, parseUserId } = require('./helper.js')

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.post('/checkout', authenticateToken, function(req, res, next){
  const userId = parseUserId(req);
  const sql = 'Insert into cart(userId) value(?)'
  connection.query(sql, [userId], (err,result)=>{
    if(err) throw err;
    res.json(result);
  })
})
module.exports = router;

