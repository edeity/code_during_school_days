CREATE SCHEMA `hotel` ;

CREATE  TABLE `hotel`.`bill_info` (
  `id` VARCHAR(45) NOT NULL ,
  `kind` VARCHAR(45) NOT NULL ,
  `day` VARCHAR(45) NOT NULL ,
  `customer_id` VARCHAR(45) NOT NULL ,
  `room_id` VARCHAR(45) NOT NULL ,
  `price` VARCHAR(45) NOT NULL ,
  `pay_mode` VARCHAR(45) NOT NULL ,
  `bill_infocol` VARCHAR(45) NOT NULL ,
  `waiter_id` VARCHAR(45) NOT NULL ,
  `index_num` INT(11) NOT NULL AUTO_INCREMENT ,
  `last_day` INT(11) NOT NULL ,
  PRIMARY KEY (`index_num`, `id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `index_num_UNIQUE` (`index_num` ASC) );

CREATE  TABLE `hotel`.`customer_info` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `sex` VARCHAR(2) NOT NULL ,
  `address` VARCHAR(45) NULL DEFAULT NULL ,
  `phone` VARCHAR(45) NULL DEFAULT NULL ,
  `level` INT(11) NULL DEFAULT 0 ,
  `storage` INT(11) NULL DEFAULT 0 ,
  `index_id` INT(11) NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`index_id`, `id`) ,
  UNIQUE INDEX `index_id_UNIQUE` (`index_id` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

CREATE  TABLE `hotel`.`delay_info` (
  `bill_id` VARCHAR(45) NOT NULL ,
  `isPay` VARCHAR(45) NOT NULL DEFAULT 0 ,
  `index_num` VARCHAR(45) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`bill_id`, `index_num`) ,
  UNIQUE INDEX `bill_id_UNIQUE` (`bill_id` ASC) ,
  UNIQUE INDEX `index_num_UNIQUE` (`index_num` ASC) );
  
  CREATE  TABLE `hotel`.`room_arrange` (
  `room_id` VARCHAR(3) NOT NULL ,
  `20140918` INT(11) NOT NULL DEFAULT 0 ,
  `20140919` INT(11) NOT NULL DEFAULT 0 ,
  `20140920` INT(11) NOT NULL DEFAULT 0 ,
  `20140921` INT(11) NOT NULL DEFAULT 0 ,
  `20140922` INT(11) NOT NULL DEFAULT 0 ,
  `20140923` INT(11) NOT NULL DEFAULT 0 ,
  `20140924` INT(11) NOT NULL DEFAULT 0 ,
  `20140925` INT(11) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`room_id`) );

CREATE  TABLE `hotel`.`room_info` (
  `id` VARCHAR(3) NOT NULL ,
  `bed_num` INT NOT NULL ,
  `level` INT NOT NULL ,
  `price` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

CREATE  TABLE `hotel`.`waiter_info` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NULL DEFAULT NULL ,
  `rank` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );
