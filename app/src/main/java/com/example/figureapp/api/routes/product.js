var express = require('express');
var router = express.Router();

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.get('/listproducts', function(req,res, next){
    const sql = 'select product.id, product.name, product.description, product.quantity, product.idCategory, product.name, product.price, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid';
    connection.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result);
      });
});
router.post('/detailproduct',function(req,res, next){
  const id = req.body.id;
  const sql = '(select product.id, product.name, product.description, product.quantity, product.idCategory,product.price, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as A';
  const sql1 = 'select * from ' + sql + ' where id = ?';
  console.log(sql1);
  connection.query(sql1, [id], (err, result) => {
    console.log(result);
        if (err) throw err;
        res.json(result);
      });
});
router.get('/searchProduct',function(req, res, next){
  const keyWord = req.query.keyWord;
  const sql = '(select product.id, product.name, product.description, product.quantity, product.idCategory,product.price, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as A';
  const sql1 = 'select * from ' + sql + ' where  A.name like ?';
  const value = [keyWord + "%"];
  connection.query(sql1, [value], (err, result)=>{
    if(err) throw err;
    res.json(result)
  })
});
module.exports = router;