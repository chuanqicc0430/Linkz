/*
SQLyog Trial v10.5 
MySQL - 5.6.24-log : Database - resourcedb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`resourcedb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `resourcedb`;

/*Table structure for table `res_configtables` */

DROP TABLE IF EXISTS `res_configtables`;

CREATE TABLE `res_configtables` (
  `TableName` varchar(32) NOT NULL,
  `DatabaseName` varchar(16) DEFAULT NULL,
  `Version` datetime DEFAULT NULL,
  PRIMARY KEY (`TableName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_configtexts` */

DROP TABLE IF EXISTS `res_configtexts`;

CREATE TABLE `res_configtexts` (
  `ConfigKey` varchar(32) DEFAULT NULL,
  `ConfigParams` varchar(32) DEFAULT NULL,
  `ConfigText` text,
  `Version` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_resource` */

DROP TABLE IF EXISTS `res_resource`;

CREATE TABLE `res_resource` (
  `ResourceName` varchar(16) DEFAULT NULL,
  `ResourceType` varchar(8) DEFAULT NULL,
  `ResourceIndex` tinyint(4) DEFAULT NULL,
  `Config` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_resourcepolicy` */

DROP TABLE IF EXISTS `res_resourcepolicy`;

CREATE TABLE `res_resourcepolicy` (
  `ResourceName` varchar(16) NOT NULL,
  `ResourceType` varchar(8) DEFAULT NULL,
  `Protocol` varchar(8) DEFAULT NULL,
  `Locator` varchar(16) DEFAULT NULL,
  `LocatorParams` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ResourceName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_sensitivewords` */

DROP TABLE IF EXISTS `res_sensitivewords`;

CREATE TABLE `res_sensitivewords` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `FilterType` int(11) DEFAULT NULL COMMENT '业务类型',
  `Word` varchar(128) DEFAULT NULL COMMENT '敏感词',
  `FilterMode` tinyint(4) DEFAULT NULL COMMENT '拦截类型',
  `Enabled` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `RelateWords` varchar(128) DEFAULT NULL COMMENT '关联词',
  `Treatment` tinyint(4) DEFAULT NULL COMMENT '拦截级别',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
