var express = require('express');
var router = express.Router();
const { authenticateToken, parseUserId } = require('./helper.js')

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/itemcarts', authenticateToken, function (req, res, next) {
  const userId = parseUserId(req);
  const sql = '(select cart.userid, itemcart.cartId, itemcart.productId, itemcart.count from cart inner join itemcart where cart.id = itemcart.cartid) as A';
  const sql1 = '(select * from ' + sql + ' inner join (select product.id, product.name, product.description, product.quantity, product.idCategory, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as B where B.id = A.productid) as C';
  const sql2 = 'select * from ' + sql1 + ' where userid = ?'
  connection.query(sql2, [userId], (err, result) => {
    if (err) throw err;
    res.json(result);
  });
});
module.exports = router;