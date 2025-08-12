/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - smart_parking
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smart_parking` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `smart_parking`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `vehicle_no` varchar(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `book` */

insert  into `book`(`book_id`,`user_id`,`slot_id`,`vehicle_no`,`date`) values 
(1,8,1,'OVO3POJ','2024-4-1'),
(2,8,2,'OVO3POJ','2024-4-1'),
(5,8,1,'kl38gg7373','2024-4-4'),
(4,8,2,'OVO3POJ','2024-4-4'),
(6,8,2,'OVO3POJ','2024-4-6');

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `starting_date` varchar(45) DEFAULT NULL,
  `starting_time` varchar(45) DEFAULT NULL,
  `ending_date` varchar(45) DEFAULT NULL,
  `ending_time` varchar(45) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `path` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

insert  into `booking`(`book_id`,`user_id`,`starting_date`,`starting_time`,`ending_date`,`ending_time`,`slot_id`,`status`,`path`) values 
(1,1,'2024-02-25 00:00:00.000','8:00 AM','2024-02-25 00:00:00.000','12:21 AM',1,'Free','static/c70aa52c-33df-47b3-a7d8-0e91d3931a1c.png'),
(2,2,'2024-02-25 00:00:00.000','9.00 AM','2024-02-25 00:00:00.000','12:21 AM',1,'Free','jhjhj');

/*Table structure for table `checkin_checkout` */

DROP TABLE IF EXISTS `checkin_checkout`;

CREATE TABLE `checkin_checkout` (
  `checkin_checkout_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `number_plate` varchar(100) DEFAULT NULL,
  `starting_date` varchar(100) DEFAULT NULL,
  `starting_time` varchar(100) DEFAULT NULL,
  `ending_date` varchar(100) DEFAULT NULL,
  `ending_time` varchar(100) DEFAULT NULL,
  `slot_id` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`checkin_checkout_id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

/*Data for the table `checkin_checkout` */

insert  into `checkin_checkout`(`checkin_checkout_id`,`user_id`,`number_plate`,`starting_date`,`starting_time`,`ending_date`,`ending_time`,`slot_id`,`status`) values 
(21,8,'OVO3POJ','2024-04-01','09:56:49','2024-04-01','09:57:33','1','paid'),
(22,8,'OVO3POJ','2024-04-04','09:26:06','2024-04-04','09:27:09','2','paid'),
(24,8,'OVO3POJ','2024-04-06','10:32:25','2024-04-06','10:42:39','2','paid');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `reply` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`user_id`,`description`,`date`,`reply`) values 
(1,1,'issue','2024-01-21','ok'),
(2,8,'hii','2024-03-21','ok'),
(3,8,'hii','2024-03-21','pending'),
(4,8,'hii','2024-03-21','ok'),
(5,8,'hii','2024-03-21','pending'),
(6,8,'hii','2024-03-21','pending'),
(7,8,'hii','2024-03-21','pending'),
(8,8,'hii','2024-03-21','pending'),
(9,8,'hhh','2024-03-21','pending'),
(10,8,'tvtvfv','2024-03-21','pending'),
(11,8,'tvtvfv','2024-03-21','pending'),
(12,8,'hh','2024-03-21','pending');

/*Table structure for table `law_details` */

DROP TABLE IF EXISTS `law_details`;

CREATE TABLE `law_details` (
  `law_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `law_details` varchar(45) DEFAULT NULL,
  `fine_amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`law_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `law_details` */

insert  into `law_details`(`law_id`,`title`,`law_details`,`fine_amount`) values 
(1,'Accident','illegal case','2000');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values 
(1,'liya','liya1234','user'),
(2,'lijo','lijo12','Parking_Owner'),
(3,'admin','admin1','admin'),
(4,'liya','liya12','user'),
(5,'','','user'),
(6,'','','user'),
(7,'aaa','aaa','user'),
(8,'aa','aa','user'),
(9,'aa','aa','user'),
(10,'','','user'),
(11,'anu','123','user'),
(12,'amal','amaljames','Parking_Owner'),
(13,'','','user');

/*Table structure for table `owner` */

DROP TABLE IF EXISTS `owner`;

CREATE TABLE `owner` (
  `owner_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `house_name` varchar(45) DEFAULT NULL,
  `place` varchar(45) DEFAULT NULL,
  `pincode` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`owner_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `owner` */

insert  into `owner`(`owner_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`latitude`,`longitude`,`phone`,`email`) values 
(1,2,'lijo','antony','pellissery','thrissur','680563','10.522149656091836','76.2583487870439','4567892345','lijo@gmail.com'),
(2,12,'amal','james','arakkal house peramangalam','peramangalam','680605','9.988802472584254','76.28006357932709','09876543201','amaljames@gmail.com');

/*Table structure for table `parking_locations` */

DROP TABLE IF EXISTS `parking_locations`;

CREATE TABLE `parking_locations` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(45) DEFAULT NULL,
  `place` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `loc_desc` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `owner_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `parking_locations` */

insert  into `parking_locations`(`location_id`,`location_name`,`place`,`latitude`,`longitude`,`loc_desc`,`type`,`owner_id`) values 
(1,'Thrissur','Sakthan','10.500529897366267','76.20833921795305','parking','free','0'),
(2,'velur','velur','9.980873044036262','76.27663668576724','parking for cars only','paid','2');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `slot_id` int(11) DEFAULT NULL,
  `amount` varchar(45) DEFAULT NULL,
  `mode_of_payment` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`pay_id`,`slot_id`,`amount`,`mode_of_payment`,`date`,`status`) values 
(1,1,'56000','Debit Card','2024-01-21','Reserved');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `checkin_checkout_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

insert  into `payments`(`payment_id`,`checkin_checkout_id`,`amount`,`date`) values 
(2,20,'100','2024-03-31'),
(3,21,'100','2024-04-01'),
(16,24,'200','2024-04-06'),
(15,23,'200','2024-04-06'),
(14,22,'200','2024-04-04');

/*Table structure for table `slots` */

DROP TABLE IF EXISTS `slots`;

CREATE TABLE `slots` (
  `slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`slot_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `slots` */

insert  into `slots`(`slot_id`,`name`,`status`,`location_id`,`amount`) values 
(1,'slot one','reserved',1,'100'),
(2,'slot one','free',2,'200');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `house_name` varchar(45) DEFAULT NULL,
  `place` varchar(45) DEFAULT NULL,
  `pincode` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`latitude`,`longitude`,`phone`,`email`) values 
(1,1,'liya','antony','pellissery','thrissur','680563','10.515606356174404','76.20788257475448','567890876','liyaantonypellissery@gmail.com'),
(2,4,'liya','lazar','hjg','trichur','654378','10.515606356174404','76.20788257475448','9562345168','anliya9007@gmail.com'),
(4,7,'aa','aa','aa','aaa','aaa','','','aaa','aaa'),
(5,8,'aa','aa','aa','aa','aa','','','aa','aa'),
(6,9,'aa','aa','aa','aa','aa','10.5155514','76.218046','aa','aa'),
(8,11,'Anupriy','KS','kunnath','Thrissur','680519','10.5155411','76.2180352','8472827282','anu@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
