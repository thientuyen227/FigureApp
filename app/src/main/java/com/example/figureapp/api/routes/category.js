var express = require('express');
var router = express.Router();

//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

//List category
router.get('/listcategories', function(req, res, next){
    const sql = 'select category.idcategory as id, category.nameCategory as name, image_category.address as images from category inner join Image_Category on category.idcategory = Image_Category.categoryid';
    connection.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result);
      });
});
router.post('/products', function(req,res, next){
  const idCategory = req.body.idCategory;
  const sql = '(select product.id, product.name, product.description, product.quantity, product.idCategory, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as A';
  const sql1 = 'select * from ' + sql + ' where idCategory = ?';
  connection.query(sql1,[idCategory], (err, result) => {
    if (err) throw err;
    res.json(result);
  });
})
module.exports = router;