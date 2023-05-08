var express = require('express');
var router = express.Router();

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.get('/listproducts', function(req,res, next){
    const sql = 'select product.id, product.name, product.description, product.quantity, product.idCategory, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid';
    connection.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result);
      });
});
module.exports = router;