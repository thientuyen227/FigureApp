var express = require('express');
var router = express.Router();
const uuid = require('uuid');
const admin = require('firebase-admin');
const serviceAccount = require('../serviceAccountKey.json');


// Initialize Firebase Admin SDK
const multer = require('multer');
const upload = multer({ dest: 'uploads/' });

//Connection MySQL
const connection = require('./connectionMySQL');
const { authenticateToken, parseUserId, parseRole } = require('./helper');
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

router.post('/deleteProduct', authenticateToken, function(req,res){
  const role = parseRole(req);
  
  if(role !="admin"){
    res.status(404).json({ success: false});
  }
  else{
    const productId = req.body.productId;
    const sql = 'Delete from image_product where productId=?';
    connection.query(sql,productId, (err, result)=>{
      if(err) throw err;
      else{
        const deleteProduct='delete from product where Id=?';
        connection.query(deleteProduct,productId,(err, result)=>{
          if(err) throw err;
          else res.json({success: true});
        })
      }
    })
  }
})

router.put('/editProduct', authenticateToken, function(req,res, next){
  const role = parseRole(req);
  console.log(role);
  const productId = req.body.productId;
  
  if(role!='admin'){
    res.status(404).json({ success: false});
  }
  else{
    // const image_product = req.file;
    // const productFilename = `product-${uuid.v4()}.jpg`;
    // const destination = `resources/image_product/${productFilename}`;
    try {
      // Upload Avatar to Firebase Storage
      // const bucket = admin.storage().bucket();
      // await bucket.upload(image_product.path, {
      //   destination: destination,
      //   contentType: 'image/jpeg'
      // });
      // //Get signed URL for Avatar file
      // const productUrl = `https://firebasestorage.googleapis.com/v0/b/${bucket.name}/o/resources%2Fimage_product%2F${productFilename}?alt=media`;
  
      // Update user profile in MySQL database
      const { productName, description, price, quantity, idCategory } = req.body;
      const sql = 'update product set name =?, description= ?,price= ?,quantity= ?,idCategory= ? where id=?';
      const params = [productName, description, price, quantity, idCategory, productId];
      connection.query(sql, params, (err, result) => {
        if (err) throw err;
        console.log(result);
        // const insertImageProduct = 'update Image_Product set address= ? where productId=?'
        // connection.query(insertImageProduct,[productUrl, productId], (err,result)=>{
        //   if(err) throw err;
        //   res.json({success: true})
        // })
        res.json({success: true})
      });
    } catch (error) {
      console.log(error);
      res.status(500).send(error);
    }
  }
})

router.get('/getProduct', function(req,res, next){
  const productId=req.body.productId;
  const sql = 'select product.id, product.name, product.description, product.quantity, product.idCategory, product.name, product.price, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid';
  const sql1 = 'select * from ('+sql+') as A where id=? '
  connection.query(sql1,[productId], (err, result) => {
      if (err) throw err;
      res.json(result);
    });
});

router.post('/addProduct',  authenticateToken, function(req,res, next){
  const role = parseRole(req);
  if(role!='admin'){
    res.status(404).json({ success: false});
  }
  else{
    // const image_product = req.file;
    // const productFilename = `product-${uuid.v4()}.jpg`;
    // const destination = `resources/image_product/${productFilename}`;
     try {
    //   // Upload Avatar to Firebase Storage
    //   const bucket = admin.storage().bucket();
    //   await bucket.upload(image_product.path, {
    //     destination: destination,
    //     contentType: 'image/jpeg'
    //   });
    //   //Get signed URL for Avatar file
    //   const productUrl = `https://firebasestorage.googleapis.com/v0/b/${bucket.name}/o/resources%2Fimage_product%2F${productFilename}?alt=media`;
  
      // Update user profile in MySQL database
      const { productName, description, price, quantity, idCategory } = req.body;
      console.log(description);
      const sql = 'insert into product (name,description,price,quantity,idCategory) values (?,?,?,?,?)';
      const params = [productName, description, price, quantity, idCategory];
      connection.query(sql, params, (err, result) => {
        if (err) throw err;
        // const productId = result.insertId;
        // const insertImageProduct = 'insert into Image_Product(address,productId) values (?,?)'
        // connection.query(insertImageProduct,[productUrl, productId], (err,result)=>{
        //   if(err) throw err;
          res.json({success: true})
        //})
      });
    } catch (error) {
      console.log(error);
      res.status(500).send(error);
    }
  }
})

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