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
    const sql = 'select category.nameCategory as name, image_category.address as images from category inner join Image_Category on category.idcategory = Image_Category.categoryid';
    connection.query(sql, (err, result) => {
        if (err) throw err;
        res.json(result);
      });
});

module.exports = router;