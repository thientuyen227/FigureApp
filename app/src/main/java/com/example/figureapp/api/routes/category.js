var express = require('express');
var router = express.Router();
const {authenticateToken, parseRole} = require('./helper.js');
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
router.get('/getAllCategory', function(req, res, next){
  const sql = 'select idcategory as id, nameCategory as name from category';
  connection.query(sql, (err, result) => {
      if (err) throw err;
      res.json(result);
    });
});
router.post('/addCategory', authenticateToken, function(req, res, next){
  const role = parseRole(req);
  if(role!= "admin"){
    res.status(404).json({success: false})
  }
  else{
    const nameCategory= req.body.categoryName;
    console.log(nameCategory);
    const insertCategory='Insert into category (nameCategory) value (?)'
    connection.query(insertCategory, nameCategory, (err, result)=>{
      if(err) throw err;
      else res.json({success: true})
    })
  }
})
router.put('/editCategory', authenticateToken, function(req, res, next){
  const role = parseRole(req);
  if(role!= "admin"){
    res.status(404).json({success: false})
  }
  else{
    const name= req.body.name;
    const id = req.body.id;
    console.log(name);
    const editCategory='Update category set nameCategory= ? where idCategory=?'
    const params=[name, id];
    connection.query(editCategory, params, (err, result)=>{
      if(err) throw err;
      else res.json({success: true})
    })
  }
})
router.post('/deleteCategory', authenticateToken, function(req, res, next){
  const role = parseRole(req);
  if(role!= "admin"){
    res.status(404).json({success: false})
  }
  else{
    const idCategory = req.body.idCategory;
    const deleteCategory='delete from category where idCategory=?'
    connection.query(deleteCategory, idCategory, (err, result)=>{
      if(err) throw err;
      else res.json({success: true})
    })
  }
})
module.exports = router;