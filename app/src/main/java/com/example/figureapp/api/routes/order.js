var express = require('express');
var router = express.Router();
const { authenticateToken, parseUserId } = require('./helper.js')
//Connection MySQL
const connection = require('./connectionMySQL');
/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.post('/orders', function(req, res, next){
  const userId = parseUserId(req);
  if(!userId){
    return res.status(401).json({message : 'Bạn chưa đăng nhập'})
  }
  else{
    const query = 'SELECT * FROM orders WHERE userId = ?'
    connection.query(query, [userId], function(err, results, fields){
      if(err) throw err;
      res.json(results);
    })
  }
});
router.get('/listorders', authenticateToken, function(req,res, next){
  const userId = parseUserId(req);
  const sql = 'select A.userId, A.orderId, A.count, B.name, B.price, B.description, B.idcategory, B.rating, B.imageproduct  from'
  const sql1 ='(select product.id, product.name, product.price, product.description, product.quantity, product.idCategory, product.rating, image_product.address as imageProduct from product inner join image_product on product.id = image_product.productid) as B';
  const sql2 = 'Select * from (' + sql +sql1 +' inner join (SELECT orders.userId, orders.id as orderId, ordersitem.count, ordersitem.productId FROM figureapp.orders INNER JOIN figureapp.ordersitem on orders.id = ordersitem.ordersId) As A on B.id = A.productid ) As C where userId=?';
  connection.query(sql2, [userId], function(err, result, fields){
    if(err) throw err;
    res.json(result);
  })
})
router.post('/checkout', authenticateToken, (req, res) => {
 
  const userId = parseUserId(req);
  // Lấy danh sách id sản phẩm từ yêu cầu POST
  const idProduct = req.body.idProduct.map(idProduct=> parseInt(idProduct));
  const count = req.body.count.map(count => parseInt(count));

  
  sql = 'INSERT INTO orders (userid) VALUES (?)' 
  // Thực hiện thêm bản ghi mới vào bảng `orders`
  connection.query(sql, [userId], (error, results) => {
    if (error) throw error;

    // Lấy id của bản ghi mới được thêm vào bảng `orders`
    const orderId = results.insertId;
    const itemOrders = 'INSERT INTO ordersitem (productId, ordersid, count) VALUES ?';
    // Thêm các bản ghi mới vào bảng `itemorder`
    const values = idProduct.map((idProduct, index) => [idProduct, orderId,count ? count[index] : 1]);
    connection.query(itemOrders, [values], (error, results) => {
      if (error) throw error;
      // Trả về thông báo thành công cho trang checkout
      res.json({ success: true})
    });
  });
});
module.exports = router;