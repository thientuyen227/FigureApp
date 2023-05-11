var express = require('express');
var router = express.Router();
const { authenticateToken, parseUserId } = require('./helper.js');

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/followingproducts', authenticateToken, function(req, res, next) {
  const userId = parseUserId(req);
  console.log(userId);
  const sql = '(select product.id, product.name, product.description, product.quantity, product.idCategory, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as A';
  const sql1 = '(select followingproduct.userid, A.id, A.name, A.description, A.quantity, A.idCategory, A.rating, A.imageProduct from ' + sql + ' inner join followingproduct where A.id = followingproduct.idproduct) As B';
  const sql2 = 'select * from ' + sql1 + ' where userid = ?'
  connection.query(sql2, [userId], (err, result) => {
    if (err) throw err;
    res.json(result);
  });
});
module.exports = router;