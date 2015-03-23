SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = TRADITIONAL;


CREATE SCHEMA IF NOT EXISTS affablebean
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_unicode_ci;
USE affablebean;

-- -----------------------------------------------------
-- Table affablebean.customer
-- -----------------------------------------------------
DROP TABLE IF EXISTS affablebean.customer;

CREATE TABLE IF NOT EXISTS affablebean.customer (
  id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name        VARCHAR(45)  NOT NULL,
  email       VARCHAR(45)  NOT NULL,
  phone       VARCHAR(45)  NOT NULL,
  address     VARCHAR(45)  NOT NULL,
  city_region VARCHAR(2)   NOT NULL,
  cc_number   VARCHAR(19)  NOT NULL,
  PRIMARY KEY (id))
  ENGINE = InnoDB
  COMMENT = 'maintains customer details';


-- -----------------------------------------------------
-- Table affablebean.customer_order
-- -----------------------------------------------------
DROP TABLE IF EXISTS affablebean.customer_order;

CREATE TABLE IF NOT EXISTS affablebean.customer_order (
  id                  INT UNSIGNED  NOT NULL AUTO_INCREMENT,
  amount              DECIMAL(6, 2) NOT NULL,
  date_created        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  confirmation_number INT UNSIGNED  NOT NULL,
  customer_id         INT UNSIGNED  NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_customer_order_customer (customer_id ASC),
  CONSTRAINT fk_customer_order_customer
  FOREIGN KEY (customer_id)
  REFERENCES affablebean.customer (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = 'maintains customer order details';


-- -----------------------------------------------------
-- Table affablebean.category
-- -----------------------------------------------------
DROP TABLE IF EXISTS affablebean.category;

CREATE TABLE IF NOT EXISTS affablebean.category (
  id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(45)      NOT NULL,
  PRIMARY KEY (id))
  ENGINE = InnoDB
  COMMENT = 'contains product categories, e.g., dairy, meats, etc.';


-- -----------------------------------------------------
-- Table affablebean.product
-- -----------------------------------------------------
DROP TABLE IF EXISTS affablebean.product;

CREATE TABLE IF NOT EXISTS affablebean.product (
  id          INT UNSIGNED     NOT NULL AUTO_INCREMENT,
  name        VARCHAR(45)      NOT NULL,
  price       DECIMAL(5, 2)    NOT NULL,
  description TINYTEXT         NULL,
  last_update TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  category_id TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_product_category (category_id ASC),
  CONSTRAINT fk_product_category
  FOREIGN KEY (category_id)
  REFERENCES affablebean.category (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = 'contains product details';


-- -----------------------------------------------------
-- Table affablebean.ordered_product
-- -----------------------------------------------------
DROP TABLE IF EXISTS affablebean.ordered_product;

CREATE TABLE IF NOT EXISTS affablebean.ordered_product (
  customer_order_id INT UNSIGNED      NOT NULL,
  product_id        INT UNSIGNED      NOT NULL,
  quantity          SMALLINT UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (customer_order_id, product_id),
  INDEX fk_ordered_product_customer_order (customer_order_id ASC),
  INDEX fk_ordered_product_product (product_id ASC),
  CONSTRAINT fk_ordered_product_customer_order
  FOREIGN KEY (customer_order_id)
  REFERENCES affablebean.customer_order (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ordered_product_product
  FOREIGN KEY (product_id)
  REFERENCES affablebean.product (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


INSERT INTO category (name) VALUES ('dairy'), ('meats'), ('bakery'), ('fruit & veg');

INSERT INTO product (name, price, description, category_id) VALUES ('milk', 1.70, 'semi skimmed (1L)', 1);
INSERT INTO product (name, price, description, category_id) VALUES ('cheese', 2.39, 'mild cheddar (330g)', 1);
INSERT INTO product (name, price, description, category_id) VALUES ('butter', 1.09, 'unsalted (250g)', 1);
INSERT INTO product (name, price, description, category_id)
VALUES ('free range eggs', 1.76, 'medium-sized (6 eggs)', 1);

INSERT INTO product (name, price, description, category_id)
VALUES ('organic meat patties', 2.29, 'rolled in fresh herbs<br>2 patties (250g)', 2);
INSERT INTO product (name, price, description, category_id) VALUES ('parma ham', 3.49, 'matured, organic (70g)', 2);
INSERT INTO product (name, price, description, category_id) VALUES ('chicken leg', 2.59, 'free range (250g)', 2);
INSERT INTO product (name, price, description, category_id)
VALUES ('sausages', 3.55, 'reduced fat, pork<br>3 sausages (350g)', 2);

INSERT INTO product (name, price, description, category_id) VALUES ('sunflower seed loaf', 1.89, '600g', 3);
INSERT INTO product (name, price, description, category_id) VALUES ('sesame seed bagel', 1.19, '4 bagels', 3);
INSERT INTO product (name, price, description, category_id) VALUES ('pumpkin seed bun', 1.15, '4 buns', 3);
INSERT INTO product (name, price, description, category_id)
VALUES ('chocolate cookies', 2.39, 'contain peanuts<br>(3 cookies)', 3);

INSERT INTO product (name, price, description, category_id) VALUES ('corn on the cob', 1.59, '2 pieces', 4);
INSERT INTO product (name, price, description, category_id) VALUES ('red currants', 2.49, '150g', 4);
INSERT INTO product (name, price, description, category_id) VALUES ('broccoli', 1.29, '500g', 4);
INSERT INTO product (name, price, description, category_id) VALUES ('seedless watermelon', 1.49, '250g', 4);