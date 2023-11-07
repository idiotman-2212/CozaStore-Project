CREATE DATABASE cozastore;
USE cozastore;

CREATE TABLE user
(
    id          int auto_increment,
    username    varchar(30),
    password    varchar(30),
    email       varchar(30),
    id_role     int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE role
(
    id          int auto_increment,
    name        varchar(30),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE blog
(
    id          int auto_increment,
    title       varchar(255),
    image       varchar(225),
    content     text,
    id_user     int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE blog_tag
(
    id_blog     int,
    id_tag      int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id_blog, id_tag)
);
CREATE TABLE comment
(
    id          int auto_increment,
    name        nvarchar(50),
    email       varchar(225),
    content     text,
    id_blog     int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE tag
(
    id          int auto_increment,
    name        varchar(30),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE carousel
(
    id          int auto_increment,
    title       varchar(255),
    image       varchar(225),
    content     text,
    id_category int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE category
(
    id          int auto_increment,
    name        varchar(50),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE product
(
    id          int auto_increment,
    name        nvarchar(50),
    image       varchar(225),
    price       decimal(12, 3),
    description text,
    quanity     int,
    id_category int,
    id_size     int,
    id_color    int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE size
(
    id          int auto_increment,
    name        nvarchar(50),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE color
(
    id          int auto_increment,
    name        nvarchar(50),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE cart
(
    id          int auto_increment,
    id_product  int,
    quanity     int,
    id_user     int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE product_order
(
    id_product  int,
    id_order    int,
    quanity     int,
    price       decimal(12, 3),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id_order, id_product)
);
CREATE TABLE orders
(
    id          int auto_increment,
    id_user     int,
    id_status   int,
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE status
(
    id int auto_increment,
    name varchar(50),
    create_date datetime DEFAULT CURRENT_TIMESTAMP ON
UPDATE
	CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
);
CREATE TABLE file (
	id int auto_increment,
	name varchar(255),
	type varchar(255),
	data longblob,

	primary key (id)
);
ALTER TABLE carousel ADD CONSTRAINT fk_carousel_category_id FOREIGN KEY (id_category)REFERENCES category (id) ON DELETE CASCADE;

ALTER TABLE user ADD CONSTRAINT fk_user_id_role FOREIGN KEY (id_role)REFERENCES role(id) ON DELETE CASCADE;

ALTER TABLE product ADD CONSTRAINT fk_product_category_id FOREIGN KEY (id_category)REFERENCES category (id) ON DELETE CASCADE;
ALTER TABLE product ADD CONSTRAINT fk_product_id_size FOREIGN KEY (id_size)REFERENCES size (id) ON DELETE CASCADE;
ALTER TABLE product ADD CONSTRAINT fk_product_id_color FOREIGN KEY (id_color)REFERENCES color (id) ON DELETE CASCADE;

ALTER TABLE cart ADD CONSTRAINT fk_cart_id_product  FOREIGN KEY (id_product) REFERENCES product (id)  ON DELETE CASCADE;
ALTER TABLE cart ADD CONSTRAINT fk_cart_id_user  FOREIGN KEY (id_user) REFERENCES user (id)  ON DELETE CASCADE;

ALTER TABLE product_order ADD CONSTRAINT fk_product_order_id_product  FOREIGN KEY (id_product) REFERENCES product (id) ON DELETE CASCADE;
ALTER TABLE product_order ADD CONSTRAINT fk_product_order_id_order  FOREIGN KEY (id_order) REFERENCES orders (id) ON DELETE CASCADE;

ALTER TABLE orders ADD CONSTRAINT fk_orders_id_user  FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE;
ALTER TABLE orders ADD CONSTRAINT fk_orders_id_status  FOREIGN KEY (id_status)  REFERENCES status (id) ON DELETE CASCADE;

ALTER TABLE comment ADD CONSTRAINT fk_comment_id_blog FOREIGN KEY (id_blog) REFERENCES blog (id) ON DELETE CASCADE;

ALTER TABLE blog ADD CONSTRAINT fk_blog_id_user FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE;

ALTER TABLE blog_tag ADD CONSTRAINT fk_blog_tag_id_blog FOREIGN KEY (id_blog) REFERENCES blog (id) ON DELETE CASCADE;
ALTER TABLE blog_tag ADD CONSTRAINT fk_blog_tag_id_tag FOREIGN KEY (id_tag)REFERENCES tag (id) ON DELETE CASCADE;




SELECT * FROM `user` u ;
select * from product p ;
SELECT * FROM file f ;
SELECT * FROM `size` s ;
SELECT * FROM category c ;
SELECT * FROM color c ;
SELECT * FROM `role` r ;
SELECT * FROM cart c ;


DELETE  FROM cart c WHERE c.id_user = 1;