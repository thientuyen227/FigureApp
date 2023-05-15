var express = require('express');
var router = express.Router();
const { authenticateToken, parseUserId } = require('./helper.js')

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
module.exports = router;