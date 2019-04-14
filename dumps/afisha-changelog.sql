-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: afisha
-- ------------------------------------------------------
-- Server version	5.7.25-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('2018-11-23-data-generate-1','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',1,'EXECUTED','8:1613708460f1593834b5169114c5f610','createTable tableName=authorities','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-2','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',2,'EXECUTED','8:404c9f7098d54a2e473211527094ac54','createTable tableName=be_paid_request','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-3','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',3,'EXECUTED','8:c7aca52cdb1d99f81994038661274b30','createTable tableName=club_event','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-4','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',4,'EXECUTED','8:d0ac5d1e71605bb14442b891a11593fe','createTable tableName=club_event_ticket_price','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-5','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',5,'EXECUTED','8:d1d7c553a9239f238f1487a53554b64d','createTable tableName=event_concert_access','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-6','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',6,'EXECUTED','8:2f65071d7f18ed107b5ddb72fe9a2dc3','createTable tableName=event_report','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-7','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',7,'EXECUTED','8:1b748c1bac1cf913bad0d33660a58626','createTable tableName=event_report_image','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-8','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',8,'EXECUTED','8:0c3bc149539fe1366945c3e3e917000b','createTable tableName=feedback','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-9','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:29',9,'EXECUTED','8:8afd25b1ccc3f3940118219808a7e9d7','createTable tableName=files_users','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-10','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',10,'EXECUTED','8:a7d5c1af99606617ddebf6dd33c1476b','createTable tableName=menu_category','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-11','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',11,'EXECUTED','8:243f607cf54562a841e959b170a51a4e','createTable tableName=menu_item','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-12','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',12,'EXECUTED','8:f86629fd85e5ff580f54b5cdaf5350ab','createTable tableName=menu_item_price','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-13','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',13,'EXECUTED','8:ffd4d7f4fb69055dcfffb471bf2883d9','createTable tableName=menu_item_price_has_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-14','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',14,'EXECUTED','8:56685cf17511b81e9fb2da62fd98adf1','createTable tableName=menu_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-15','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',15,'EXECUTED','8:cba381f956dbfdfd770cf79f7a099c85','createTable tableName=news','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-16','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',16,'EXECUTED','8:b867dbf69d3eb9744906dc7b93cc48e8','createTable tableName=site_settings','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-17','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',17,'EXECUTED','8:092643f475d287daa28ccd8f31c40b20','createTable tableName=ticket_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-18','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',18,'EXECUTED','8:e4f422406694968b7ceaebf54334b949','createTable tableName=ticket_order_item','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-19','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',19,'EXECUTED','8:f07c6303bc3f7d1909f4fa7c02233a46','createTable tableName=user_data_statistic','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-20','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',20,'EXECUTED','8:24182dcbe33bcd73e879332e349f8689','createTable tableName=users','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-21','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',21,'EXECUTED','8:35ae986ffb128fc7d0c57762f64ba670','createIndex indexName=FKae3egx7nh40736uipvf4v02u5, tableName=event_concert_access','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-22','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',22,'EXECUTED','8:1e3493b6cbbdabbedeee83c44ffb31c9','createIndex indexName=FKaxaescdgxv56ipvf8m5ohdrwl, tableName=menu_item','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-23','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',23,'EXECUTED','8:540722a6c79e7ce935cc120e0e15ff4b','createIndex indexName=FKbf0dd84s990vewt058jfdalqi, tableName=event_report_image','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-24','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',24,'EXECUTED','8:9e092e2580a11686cbfbc42d4e0192d1','createIndex indexName=FKcok5692damkh4wtxh8gfjnovf, tableName=ticket_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-25','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',25,'EXECUTED','8:340769e4ef5214fef82abcf8d23e56e1','createIndex indexName=FKgu4ei6hck47jy0lybf5dabibb, tableName=menu_category','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-26','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',26,'EXECUTED','8:fb9323c2b4577e45dbef30da67f760e0','createIndex indexName=FKhegrw1l8wvs5xai9r5p07u807, tableName=menu_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-27','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',27,'EXECUTED','8:8de5a148c9955afacd920ff65645722a','createIndex indexName=FKi9vasyddh37dd975wh958j8qv, tableName=menu_item_price','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-28','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',28,'EXECUTED','8:7cede4c542213a97ec1a44e2d57905f9','createIndex indexName=FKjpymf2kwwueuu3pythsuweqae, tableName=menu_item_price_has_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-29','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',29,'EXECUTED','8:a622ae571bb99b8b688e645afa04b6b8','createIndex indexName=FKk91upmbueyim93v469wj7b2qh, tableName=authorities','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-30','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',30,'EXECUTED','8:4ffa77d3d93edcd5ac24112e2d6b972f','createIndex indexName=FKkohcfmlrg2f6jyj1vrxxqyfpg, tableName=news','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-31','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',31,'EXECUTED','8:cb12b96c0728894fd445c2770897cfae','createIndex indexName=FKme8mnwn5orgboqrj6wv3q2wth, tableName=event_concert_access','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-32','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',32,'EXECUTED','8:296bcf958b9defb168a1e2113864b9b7','createIndex indexName=FKnqj7cjv44ruwyor6xrgrihe7t, tableName=ticket_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-33','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',33,'EXECUTED','8:df4285d8d38b848459dfa7a85e886593','createIndex indexName=FKpkwuequm17x4eyq8t72c2fs1r, tableName=menu_item_price_has_order','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-34','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',34,'EXECUTED','8:971e6accd73904c4e3dcddd7d35b4b13','createIndex indexName=FKrineex3cm5rqk3gr91rt4uqdp, tableName=files_users','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2018-11-23-data-generate-35','Vasyan','/db/changelog/db.changelog.generated.yaml','2019-03-12 00:48:30',35,'EXECUTED','8:c80d5e3dc2ece60b6af51bf425f7f17f','createIndex indexName=FKsshcbjwam0en84yjquob9wdvw, tableName=ticket_order_item','',NULL,'3.6.2',NULL,NULL,'2340909765'),('2019-03-12-remove-club_event-alias-field','Andrei Ladyka','/db/changelog/db.changelog.1.yaml','2019-03-12 00:48:30',36,'EXECUTED','8:b9574d0a749e1c896fdfb5219c1f3e7a','dropColumn columnName=alias, tableName=club_event','',NULL,'3.6.2',NULL,NULL,'2340909765');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-12  0:49:07
