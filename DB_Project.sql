Create Database FigureApp;
-- drop database FigureApp;
use FigureApp;

create table User
	(
		id int      	auto_increment,
		userName        varchar(20) not null,
		name        	nvarchar(70) not null,
		password 		varchar(50) not null default '123456',
		role            varchar(50) not null default 'user',
		email           varchar(50) unique,
		idCard         varchar(9) unique,
		avatar          varchar(250),
		eWallet        	double  default 0,
		createdAt       timestamp default now(),
		updatedAt       timestamp default now() on update now(),
		constraint pk_user primary key (id),
        constraint check_user_idCard check (length(idCard) = 9),
        constraint check_user_ewallet check (eWallet >= 0)
	);

create table Category
	(
		idCategory int auto_increment,
		nameCategory       nvarchar(32) not null unique,
		createdAt  timestamp default now(),
		updatedAt   timestamp default now() on update now(),
		constraint pk_category primary key (idCategory)
	);
    
create table Product
	(
		id int       auto_increment,
		name             varchar(100) not null unique,
		description      varchar(1000) not null,
		price            double not null,    
		quantity         int     not null,
		idCategory       int  not null,
		rating           int     default 3,
		createdAt        timestamp default now(),
		updatedAt        timestamp default now() on update now(),
				
		constraint pk_product primary key (id),
		constraint fk_product_category foreign key (idCategory) references Category (idCategory),
		constraint check_product_rating check (0 <= rating <= 5),
		constraint check_product_quantity check (quantity > 0),
		constraint check_product_price check (price > 0),
		constraint check_product_name check (length(name) <= 100)
	);

create table Cart
	(
		id 		  int not null  	auto_increment,
		userId    int not null,
		createdAt timestamp default now(),
		updatedAt timestamp default now() on update now(),
		constraint pk_cart primary key (id),
        constraint pk_cart_user foreign key  (userId) references User (id)		
	);

create table ItemCart
	(
		id 		  int not null 		auto_increment,
		cartId    int not null,
		productId int not null,
		count     int not null,
		createdAt timestamp default now(),
		updatedAt timestamp default now() on update now(),

		constraint pk_cartItem primary key (id),		
		constraint fk_cartItem_card foreign key (cartId) references Cart (id),
		constraint fk_cartItem_product foreign key (productId) references Product (id),
		constraint check_cartItem_count check (count >= 1)
        
	);

create table FollowingProduct
	(
		id 		  int not null  	auto_increment,
		idProduct    int not null,
		userId   int not null,
		constraint pk_followingproduct primary key (id),
        constraint fk_followingproduc_product foreign key (idProduct) references Product (id),
        constraint fk_followingproduc_user foreign key (userId) references User (id)
	);

create table Orders
	(
		id int      auto_increment,
		userId          int  not null,
		
		createdAt       timestamp default now(),
		updatedAt       timestamp default now() on update now(),
		constraint pk_orders primary key (id),
		constraint fk_orders_user foreign key (userId) references User (id)
	);

create table OrdersItem
	(
		odersItemId int        not null auto_increment,
		ordersId  int  not null,
		productId int  not null,
        count     int not null,
		createdAt timestamp default now(),
		updatedAt timestamp default now() on update now(),

		constraint pk_ordersItem primary key (odersItemId),		
		constraint fk_ordersItem_orders foreign key (ordersId) references Orders (id),
        constraint fk_ordersItem_product foreign key (productId) references Product (id),
        constraint check_ordersitem_count check (count >= 1)
	);
    
create table Image_Product
	(
		id int auto_increment,
		address varchar(255),
		productId int,
		constraint pk_image_product primary key(id),
		constraint fk_image_product foreign key(productId) references product(id)
	);
    
create table Image_Category
	(
		id int auto_increment,
		address varchar(255),
		categoryId int,
		constraint pk_image_category primary key(id),
		constraint fk_image_category foreign key(categoryId) references Category (idCategory)
	);

insert into user (userName,name,email,idCard,avatar) 
	values (N'tlmtien',N'Trần Lê Minh Tiến','Tientlm@gmail.com',N'000000001',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FTranLeMinhTien.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'nttuyen',N'Nguyễn Thiện Tuyến','Tuyennt@gmail.com',N'000000002',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FNguyenThienTuyen.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'nhlap',N'Nguyễn Hữu Lập','Lapnh@gmail.com',N'000000003',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FNguyenHuuLap.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'tthnhung',N'Từ Thị Hải Nhung','Nhungtth@gmail.com',N'000000004',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FTuThiHaiNhung.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'nhkhang',N'Nguyễn Hưng Khang','Khangnh@gmail.com',N'000000005',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FNguyenHungKhang.jpg?alt=mediar');
insert into user (userName,name,email,idCard,avatar) 
	values (N'nhphuc',N'Nguyễn Hoàng Phúc','Phucnh@gmail.com',N'000000006',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FNguyenHoangPhuc.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'phphuc',N'Phan Hoàng Phúc','Phucph@gmail.com',N'000000007',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FPhanHoangPhuc.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'ndthanh',N'Nguyễn Duy Thanh','Thanhnd@gmail.com',N'000000008',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FNguyenDuyThanh.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'dtthuy',N'Đoàn Thị Thùy','Thuydt@gmail.com',N'000000009',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FDoanThiThuy.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'huyen',N'Hoàng Uyên','Uyenh@gmail.com',N'000000010',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FHoangUyen.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'ttathu',N'Trần Thị Anh Thư','Thwtta@gmail.com',N'000000011',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FTranThiAnhThu.jpg?alt=media');
insert into user (userName,name,email,idCard,avatar) 
	values (N'lmtam',N'Lê Minh Tâm','Tamlm@gmail.com',N'000000012',N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_user%2FLeMinhTam.jpg?alt=media');

insert into category (nameCategory) value (N'Naruto');
insert into category (nameCategory) value (N'Boruto');
insert into category (nameCategory) value (N'Fairy Tail');
insert into category (nameCategory) value (N'Doraemon');
insert into category (nameCategory) value (N'Kirito');
insert into category (nameCategory) value (N'One Piece');
insert into category (nameCategory) value (N'Attack on Titan');
insert into category (nameCategory) value (N'Demon Slayer');
insert into category (nameCategory) value (N'One Punch Man');
insert into category (nameCategory) value (N'Conan Detective');
insert into category (nameCategory) value (N'Hunter x Hunter');
insert into category (nameCategory) value (N'Yugioh');
insert into category (nameCategory) value (N'Dragon Balls');
insert into category (nameCategory) value (N'Bleach');
insert into category (nameCategory) value (N'Dr.STONE');
insert into category (nameCategory) value (N'Death Note');
insert into category (nameCategory) value (N'Spirited Away');
insert into category (nameCategory) value (N'Tonari no Totoro');
insert into category (nameCategory) value (N'Pokemon');
insert into category (nameCategory) value (N'Inuyasha');

insert into product (name,description,price,quantity,idCategory) 
	values (N'Naruto Vĩ Thú - Size S',N'20x30x50 (cm)',500000,20,1);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Naruto Vĩ Thú - Size L',N'40x60x90 (cm)',1000000,10,1);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Itachi Susanoo',N'20x30x50 (cm)',750000,30,1);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Kaito Kid - Size S',N'20x40x30 (cm)',700000,25,10);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Boruto - Size S',N'20x40x30 (cm)',400000,20,2);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Natsu hóa rồng',N'30x50x40 (cm)',800000,40,3);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Doraemon - Siêu anh hùng',N'70x110x90 (cm)',1500000,15,4);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Kirito - Chibi',N'70x100x90 (cm)',1500000,15,5);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Luffy - Gear 5',N'80x120x90 (cm)',2000000,25,6);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Titan - Size XL',N'100x140x110 (cm)',3000000,15,7);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Demon Slayer - Movie 1',N'70x100x90 (cm)',2500000,10,8);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Luffy - Gear 4',N'80x110x70 (cm)',1500000,35,6);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Zoro Asura - Phiên bản giới hạn',N'90x110x70 (cm)',2550000,5,6);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Attack on Titan - Phiên bản giới hạn',N'120x140x110 (cm)',3000000,15,7);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Demon Slayer - Movie 2',N'70x100x90 (cm)',2500000,10,8);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Saitama vs Zenos',N'70x100x90 (cm)',3500000,10,9);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Zenos vs 10',N'120x150x140 (cm)',3500000,15,9);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Conan - Shinichi',N'40x70x50 (cm)',900000,15,10);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Hunter x Hunter - Movie 1',N'40x70x50 (cm)',900000,25,11);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Rồng trắng mắt xanh',N'60x80x70 (cm)',1200000,50,12);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Rồng trắng mắt xanh 3 đầu',N'120x180x270 (cm)',4000000,20,12);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Goku - Super Saiyan',N'120x180x270 (cm)',4500000,25,13);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Goku - Ultra Instinct',N'120x180x270 (cm)',4550000,15,13);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Goku vs Vegeta',N'150x150x200 (cm)',5000000,10,13);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Ichigo',N'50x60x40 (cm)',650000,30,14);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Dr.STONE - Size S',N'50x60x40 (cm)',450000,35,15);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Yagami Raito - Size S',N'60x90x50 (cm)',550000,35,16);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Spirited Away - Size S',N'60x90x50 (cm)',650000,45,17);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Tonari no Totoro - Size S',N'60x90x50 (cm)',650000,45,18);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Charizard',N'60x90x50 (cm)',650000,45,19);
insert into product (name,description,price,quantity,idCategory) 
	values (N'Inuyasha - Size L',N'60x90x50 (cm)',650000,45,20);

insert into Cart(userId) value(1);
insert into Cart(userId) value(2);
insert into Cart(userId) value(3);
insert into Cart(userId) value(4);
insert into Cart(userId) value(5);
insert into Cart(userId) value(6);
insert into Cart(userId) value(7);
insert into Cart(userId) value(8);
insert into Cart(userId) value(9);
insert into Cart(userId) value(10);
insert into Cart(userId) value(11);
insert into Cart(userId) value(12);

insert into ItemCart(cartId,productId,count) value (1,1,1);
insert into ItemCart(cartId,productId,count) value (1,2,1);
insert into ItemCart(cartId,productId,count) value (1,3,1);
insert into ItemCart(cartId,productId,count) value (1,5,1);
insert into ItemCart(cartId,productId,count) value (1,6,1);
insert into ItemCart(cartId,productId,count) value (2,9,1);
insert into ItemCart(cartId,productId,count) value (2,2,1);
insert into ItemCart(cartId,productId,count) value (2,8,1);
insert into ItemCart(cartId,productId,count) value (2,10,1);
insert into ItemCart(cartId,productId,count) value (3,3,1);
insert into ItemCart(cartId,productId,count) value (3,7,1);
insert into ItemCart(cartId,productId,count) value (3,11,1);
insert into ItemCart(cartId,productId,count) value (3,19,1);
insert into ItemCart(cartId,productId,count) value (3,8,1);
insert into ItemCart(cartId,productId,count) value (3,9,1);
insert into ItemCart(cartId,productId,count) value (4,7,1);
insert into ItemCart(cartId,productId,count) value (4,6,1);
insert into ItemCart(cartId,productId,count) value (4,4,1);
insert into ItemCart(cartId,productId,count) value (4,1,1);

insert into ItemCart(cartId,productId,count) value (5,12,1);
insert into ItemCart(cartId,productId,count) value (5,14,2);
insert into ItemCart(cartId,productId,count) value (5,16,1);
insert into ItemCart(cartId,productId,count) value (5,12,2);
insert into ItemCart(cartId,productId,count) value (5,10,1);
insert into ItemCart(cartId,productId,count) value (6,17,2);
insert into ItemCart(cartId,productId,count) value (6,12,1);
insert into ItemCart(cartId,productId,count) value (6,24,1);
insert into ItemCart(cartId,productId,count) value (6,13,3);
insert into ItemCart(cartId,productId,count) value (7,15,1);
insert into ItemCart(cartId,productId,count) value (7,23,1);
insert into ItemCart(cartId,productId,count) value (7,19,1);
insert into ItemCart(cartId,productId,count) value (7,16,2);
insert into ItemCart(cartId,productId,count) value (8,18,1);
insert into ItemCart(cartId,productId,count) value (8,23,1);
insert into ItemCart(cartId,productId,count) value (8,22,2);
insert into ItemCart(cartId,productId,count) value (8,21,2);

insert into ItemCart(cartId,productId,count) value (9,26,1);
insert into ItemCart(cartId,productId,count) value (9,28,1);
insert into ItemCart(cartId,productId,count) value (9,30,1);
insert into ItemCart(cartId,productId,count) value (9,20,1);
insert into ItemCart(cartId,productId,count) value (9,29,1);
insert into ItemCart(cartId,productId,count) value (10,26,1);
insert into ItemCart(cartId,productId,count) value (10,27,1);
insert into ItemCart(cartId,productId,count) value (10,31,1);
insert into ItemCart(cartId,productId,count) value (10,25,1);
insert into ItemCart(cartId,productId,count) value (10,22,1);
insert into ItemCart(cartId,productId,count) value (11,28,1);
insert into ItemCart(cartId,productId,count) value (11,31,2);
insert into ItemCart(cartId,productId,count) value (11,30,1);
insert into ItemCart(cartId,productId,count) value (11,29,1);
insert into ItemCart(cartId,productId,count) value (12,27,1);
insert into ItemCart(cartId,productId,count) value (12,30,2);
insert into ItemCart(cartId,productId,count) value (12,28,1);
insert into ItemCart(cartId,productId,count) value (12,29,1);

insert into FollowingProduct(idProduct,userId) values(1,1);
insert into FollowingProduct(idProduct,userId) values(8,1);
insert into FollowingProduct(idProduct,userId) values(7,1);
insert into FollowingProduct(idProduct,userId) values(19,1);
insert into FollowingProduct(idProduct,userId) values(19,2);
insert into FollowingProduct(idProduct,userId) values(13,2);
insert into FollowingProduct(idProduct,userId) values(31,2);
insert into FollowingProduct(idProduct,userId) values(21,3);
insert into FollowingProduct(idProduct,userId) values(25,3);
insert into FollowingProduct(idProduct,userId) values(29,4);
insert into FollowingProduct(idProduct,userId) values(30,4);
insert into FollowingProduct(idProduct,userId) values(23,5);
insert into FollowingProduct(idProduct,userId) values(24,6);
insert into FollowingProduct(idProduct,userId) values(20,7);
insert into FollowingProduct(idProduct,userId) values(31,8);
insert into FollowingProduct(idProduct,userId) values(30,9);
insert into FollowingProduct(idProduct,userId) values(26,10);
insert into FollowingProduct(idProduct,userId) values(25,11);
insert into FollowingProduct(idProduct,userId) values(24,12);

insert into Orders(userId) Values(1);
insert into Orders(userId) Values(2);
insert into Orders(userId) Values(3);
insert into Orders(userId) Values(4);
insert into Orders(userId) Values(5);
insert into Orders(userId) Values(6);
insert into Orders(userId) Values(7);
insert into Orders(userId) Values(8);
insert into Orders(userId) Values(9);
insert into Orders(userId) Values(12);
insert into Orders(userId) Values(11);
insert into Orders(userId) Values(10);

insert into OrdersItem(ordersId,productId,count) values(1,1,2);
insert into OrdersItem(ordersId,productId,count) values(2,2,4);
insert into OrdersItem(ordersId,productId,count) values(3,21,3);
insert into OrdersItem(ordersId,productId,count) values(4,22,2);
insert into OrdersItem(ordersId,productId,count) values(5,8,2);
insert into OrdersItem(ordersId,productId,count) values(6,9,1);
insert into OrdersItem(ordersId,productId,count) values(7,31,1);
insert into OrdersItem(ordersId,productId,count) values(8,25,1);
insert into OrdersItem(ordersId,productId,count) values(9,21,2);
insert into OrdersItem(ordersId,productId,count) values(10,22,3);
insert into OrdersItem(ordersId,productId,count) values(11,8,1);
insert into OrdersItem(ordersId,productId,count) values(12,9,2);

insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FNaruto_Vythu_SizeS.jpg?alt=media',1);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FNaruto_Vythu_SizeL.png?alt=media',2);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FItachi_Susanoo.jpg?alt=media',3);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FKaitoKid.jpg?alt=media',4);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FBoruto.jpg?alt=media',5);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FNatsu_HoaRong.png?alt=media',6);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FDoraemon_SieuAnhHung.jpg?alt=media',7);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FKirito_Chibi.jpg?alt=media',8);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FLuffy_Gear5.jpg?alt=media',9);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FTitan_SizeXL.jpg?alt=media',10);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FDemonSlayer_Movie1.jpg?alt=media',11);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FLuffy_Gear4.jpg?alt=media',12);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FZoro_Asura.jpg?alt=media',13);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FAttack_on_titan_Phienbangioihan.jpg?alt=media',14);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FDemonSlayer_Movie2.jpg?alt=media',15);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FSaitama_Zenos.jpg?alt=media',16);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FZenosvs10.jpg?alt=media',17);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FConan_Shinichi.png?alt=media',18);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FHunter_x_Hunter_Movie1.jpg?alt=media',19);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FRong_Trang_Mat_Xanh.jpg?alt=media',20);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FRong_Trang_Mat_xanh_3dau.jpg?alt=media',21);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FGoku_SuperSaiyan.jpg?alt=media',22);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FGoku_Ultra_Instinct.jpg?alt=media',23);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FGoku_Vegeta.jpg?alt=media',24);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FIchigo.jpg?alt=media',25);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FDrSTONE_SizeS.jpg?alt=media',26);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FYagami_Raito.jpg?alt=media',27);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FSpirited_Away_Sizes.jpg?alt=media',28);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FTonari_no_Totoro_Sizes.jpg?alt=media',29);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FCharizard.jpg?alt=media',30);
insert into Image_Product (address,productId) 
	values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_product%2FInuyasha_Sizel.jpg?alt=media',31);
    
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FNaruto.jpg?alt=media',1);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FBoruto.jpg?alt=media',2);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FFairytail.jpg?alt=media',3);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FDoraemon.jpg?alt=media',4);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FKirito.jpg?alt=media',5);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FOne_Piece.png?alt=media',6);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FAttack_on_Titan.jpg?alt=media',7);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FDemon_Slayer.jpg?alt=media',8);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FOne_Punch_Man.jpg?alt=media',9);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FConan_Detective.jpg?alt=media',10);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FHunter_x_Hunter.jpg?alt=media',11);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FYugioh.jpg?alt=media',12);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FDragon_Balls.jpg?alt=media',13);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FBleach.png?alt=media',14);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FDrSTONE.png?alt=media',15);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FDeath_Note.jpg?alt=media',16);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FSpirited_Away.jpg?alt=media',17);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FTonari_no_Totoro.jpg?alt=media',18);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FPokemon.jpg?alt=media',19);
insert into Image_Category (address,categoryId) values (N'https://firebasestorage.googleapis.com/v0/b/figureapp-b8eb4.appspot.com/o/resources%2Fimage_category%2FInuyasha.jpg?alt=media',20);    
    
/*use FigureShop;


select E.storeName, E.userId, E.id, cartitem.productId, cartitem.count from
(select D.storeName, D.userId, D.storeId, D.id from (select store.name as storeName, A.userId, A.id, A.storeId from( SELECT cart.storeId, cart.id, cart.userId from cart) As A inner join store on store.id=A.storeId) As D where D.userId=1 ) E
inner join cartitem on E.id=cartitem.cartId;



select C.count, C.name, C.promotionalprice, A.storeName, A.userId from
(select * from( SELECT store.name as storeName, cart.id, cart.userId from cart inner join store on store.id=cart.storeId) D where D.userId=1 )As A
inner join
(select B.id, B.count, product.name, product.promotionalprice from
(select cart.id, cartitem.productId, cartitem.count from cartitem inner join cart on cart.id=cartitem.cartId) B inner join product on product.id= B.productId) C on A.id=C.id;*/
