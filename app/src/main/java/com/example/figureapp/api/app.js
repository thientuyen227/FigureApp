var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');
require("dotenv").config();

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var categoryRouter = require('./routes/category');
var productRouter = require('./routes/product');
var cartRouter = require('./routes/cart');
var itemcartRouter = require('./routes/itemcart');
var orderRouter = require('./routes/order');
var itemorderRouter = require('./routes/itemorder');
var followingproductRouter = require('./routes/followingproduct');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/category', categoryRouter);
app.use('/product', productRouter);
app.use('/cart', cartRouter);
app.use('/itemcart', itemcartRouter);
app.use('/order', orderRouter);
app.use('/itemorder', itemorderRouter);
app.use('/follow', followingproductRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});


// admin.initializeApp({
//   credential: admin.credential.cert(serviceAccount),
//   databaseURL: 'https://<DATABASE_NAME>.firebaseio.com'
// });
// // Khởi tạo multer để xử lý tệp tin đầu vào (avatar)
// const upload = multer({
//   storage: multer.memoryStorage(),
//   limits: {
//     fileSize: 5 * 1024 * 1024, // Giới hạn dung lượng file avatar là 5MB
//   },
// });

module.exports = app;
