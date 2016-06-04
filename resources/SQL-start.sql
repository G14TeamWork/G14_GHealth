CREATE DATABASE  IF NOT EXISTS `g10mygas` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `g10mygas`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: g10mygas
-- ------------------------------------------------------
-- Server version	5.7.9

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
-- Table structure for table `analiticsystem`
--

DROP TABLE IF EXISTS `analiticsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `analiticsystem` (
  `customerid` int(11) NOT NULL,
  `customerRate` int(11) DEFAULT NULL,
  `fuelingTime` time DEFAULT NULL,
  `fuelType` int(11) DEFAULT NULL,
  PRIMARY KEY (`customerid`),
  KEY `fk_fuelType_idx` (`fuelType`),
  CONSTRAINT `fk_customerid` FOREIGN KEY (`customerid`) REFERENCES `customers` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fuelType` FOREIGN KEY (`fuelType`) REFERENCES `fuel` (`fuelid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `analiticsystem`
--

LOCK TABLES `analiticsystem` WRITE;
/*!40000 ALTER TABLE `analiticsystem` DISABLE KEYS */;
INSERT INTO `analiticsystem` VALUES (1,1,'15:13:01',4),(5,2,'14:36:50',1);
/*!40000 ALTER TABLE `analiticsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customercars`
--

DROP TABLE IF EXISTS `customercars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customercars` (
  `carID` varchar(45) NOT NULL,
  `cusername` int(11) NOT NULL,
  `carfuel` int(11) DEFAULT NULL,
  PRIMARY KEY (`carID`,`cusername`),
  KEY `cusername_idx` (`cusername`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customercars`
--

LOCK TABLES `customercars` WRITE;
/*!40000 ALTER TABLE `customercars` DISABLE KEYS */;
INSERT INTO `customercars` VALUES ('11-111-11',1,1),('11-111-11',4,2),('12-312-31',4,3),('44-444-44',4,2),('55-555-55',5,3);
/*!40000 ALTER TABLE `customercars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customernetworks`
--

DROP TABLE IF EXISTS `customernetworks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customernetworks` (
  `netusername` int(11) NOT NULL,
  `networkidnum` int(11) NOT NULL,
  PRIMARY KEY (`netusername`,`networkidnum`),
  KEY `networkidnum_idx` (`networkidnum`),
  CONSTRAINT `netusername` FOREIGN KEY (`netusername`) REFERENCES `customers` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `networkidnum` FOREIGN KEY (`networkidnum`) REFERENCES `networks` (`networkid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customernetworks`
--

LOCK TABLES `customernetworks` WRITE;
/*!40000 ALTER TABLE `customernetworks` DISABLE KEYS */;
INSERT INTO `customernetworks` VALUES (1,1),(5,1),(2,2),(4,3),(5,3);
/*!40000 ALTER TABLE `customernetworks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `username` int(11) NOT NULL,
  `creditcard` varchar(45) DEFAULT NULL,
  `ProgramId` int(1) DEFAULT NULL,
  `isExclusive` int(1) DEFAULT NULL,
  `isPrivate` int(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk1_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'4580-4444-1111-2222',2,1,0),(2,'1234-5678-1234-5678',3,1,1),(4,'1234-5555-1111-8901',3,1,1),(5,'2590-1272-8778-0012',4,0,1);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuel`
--

DROP TABLE IF EXISTS `fuel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuel` (
  `fuelid` int(11) NOT NULL,
  `fuelname` varchar(45) DEFAULT NULL,
  `fuelprice` float DEFAULT NULL,
  PRIMARY KEY (`fuelid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuel`
--

LOCK TABLES `fuel` WRITE;
/*!40000 ALTER TABLE `fuel` DISABLE KEYS */;
INSERT INTO `fuel` VALUES (1,'Diesel',7.2),(2,'Benzin',6.4),(3,'ScooterFuel',5.12),(4,'HomeHeat',4);
/*!40000 ALTER TABLE `fuel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fueling`
--

DROP TABLE IF EXISTS `fueling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fueling` (
  `idFueling` int(11) NOT NULL AUTO_INCREMENT,
  `Fueldate` date DEFAULT NULL,
  `Fueltime` time DEFAULT NULL,
  `Fueltype` int(11) DEFAULT NULL,
  `ammount` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `stationID` int(11) DEFAULT NULL,
  `carID` varchar(45) DEFAULT NULL,
  `Fusername` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFueling`),
  KEY `fk_fuelId_idx` (`Fueltype`),
  KEY `fk_stationid_idx` (`stationID`),
  KEY `fk1_username_idx` (`Fusername`),
  CONSTRAINT `fk1_fuelId` FOREIGN KEY (`Fueltype`) REFERENCES `fuel` (`fuelid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk1_fusername` FOREIGN KEY (`Fusername`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk1_stationid` FOREIGN KEY (`stationID`) REFERENCES `station` (`idStation`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fueling`
--

LOCK TABLES `fueling` WRITE;
/*!40000 ALTER TABLE `fueling` DISABLE KEYS */;
INSERT INTO `fueling` VALUES (1,'2016-01-01','13:00:00',2,20,300,1,'24-456-12',1),(2,'2015-12-20','11:00:00',3,40,320,1,'17-615-89',1),(3,'2016-01-01','12:30:00',1,30,450,1,'23-770-45',5),(4,'2016-01-10','18:30:00',2,30,100,1,'17-615-89',1),(5,'2016-01-05','16:43:00',1,27.3,121,1,'23-770-45',5),(6,'2016-01-17','18:22:05',1,10,25.9,1,'11-111-11',1),(7,'2016-01-17','20:31:58',1,19000,49248,1,'11-111-11',1);
/*!40000 ALTER TABLE `fueling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homeheatreserves`
--

DROP TABLE IF EXISTS `homeheatreserves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homeheatreserves` (
  `idhomeheatreserves` int(11) NOT NULL AUTO_INCREMENT,
  `supplydate` date DEFAULT NULL,
  `price` float DEFAULT NULL,
  `username` int(11) DEFAULT NULL,
  `gasstationid` int(11) DEFAULT NULL,
  `AddressToSupply` varchar(45) DEFAULT NULL,
  `dateoforder` date DEFAULT NULL,
  `timeoforder` time NOT NULL,
  `ammount` float DEFAULT NULL,
  PRIMARY KEY (`idhomeheatreserves`),
  KEY `fk2_username_idx` (`username`),
  KEY `gasstationid_idx` (`gasstationid`),
  CONSTRAINT `fk2_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gasstationid` FOREIGN KEY (`gasstationid`) REFERENCES `station` (`idStation`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homeheatreserves`
--

LOCK TABLES `homeheatreserves` WRITE;
/*!40000 ALTER TABLE `homeheatreserves` DISABLE KEYS */;
INSERT INTO `homeheatreserves` VALUES (11,'2016-01-10',22.95,1,1,'asdjas','2016-01-10','17:39:00',10),(12,'2016-01-16',96.6,1,1,'dada','2016-01-15','04:30:00',20),(13,'2016-01-16',423,1,1,'Fasil 3','2016-01-15','03:53:00',100);
/*!40000 ALTER TABLE `homeheatreserves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `username` int(11) NOT NULL,
  `message` varchar(200) DEFAULT NULL,
  `read` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT '0 = null\n1 = awaiting confermation\n2 = aprroved\n3 = declined\n4 = canceled',
  `fromWhoID` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (7,'',0,0,2),(29,NULL,0,0,40);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networks`
--

DROP TABLE IF EXISTS `networks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `networks` (
  `networkid` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `disel` double DEFAULT NULL,
  `benzin` double DEFAULT NULL,
  `scooter` double DEFAULT NULL,
  `homeHeat` double DEFAULT NULL,
  PRIMARY KEY (`networkid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networks`
--

LOCK TABLES `networks` WRITE;
/*!40000 ALTER TABLE `networks` DISABLE KEYS */;
INSERT INTO `networks` VALUES (1,'Sonol',4.5,6.3,1.23,3.4),(2,'Paz',2,2,2,2),(3,'Yaad',3,3,3,3);
/*!40000 ALTER TABLE `networks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salepatterns`
--

DROP TABLE IF EXISTS `salepatterns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salepatterns` (
  `idsales` int(11) NOT NULL AUTO_INCREMENT,
  `salename` varchar(45) DEFAULT NULL,
  `disscount` int(11) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `timeFrom` time(4) DEFAULT NULL,
  `timeTo` time(4) DEFAULT NULL,
  `isOnNow` int(11) DEFAULT NULL,
  PRIMARY KEY (`idsales`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salepatterns`
--

LOCK TABLES `salepatterns` WRITE;
/*!40000 ALTER TABLE `salepatterns` DISABLE KEYS */;
INSERT INTO `salepatterns` VALUES (1,'HanukaSale',16,'2016-12-10','2016-12-17','10:00:00.0000','22:00:00.0000',1),(2,'ShavuotSale',10,'2016-06-13','2016-06-14','09:30:00.0000','21:30:00.0000',1),(3,'CrazySale',75,'2017-06-13','2017-07-13','11:20:32.0000','12:00:00.0000',1),(4,'NoWay!',95,'2020-01-01','2020-01-01','00:00:00.0000','12:00:00.0000',1),(5,'DontInterrupt',5,'2018-02-03','2018-02-05','14:00:00.0000','16:00:00.0000',1),(6,'GoNutz',50,'2010-09-08','2010-10-07','11:11:11.0000','13:12:11.0000',1),(7,'RightNow',10,'2016-01-17','2016-01-25','07:00:00.0000','20:00:00.0000',1);
/*!40000 ALTER TABLE `salepatterns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales` (
  `idsales` int(11) NOT NULL AUTO_INCREMENT,
  `salename` varchar(45) DEFAULT NULL,
  `disscount` int(11) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `timeFrom` time(4) DEFAULT NULL,
  `timeTo` time(4) DEFAULT NULL,
  `isOnNow` int(11) DEFAULT NULL,
  PRIMARY KEY (`idsales`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'HanukaSale',16,'2017-04-14','2015-12-23','13:00:00.0000','23:00:00.0000',1),(2,'ShavuotSale',10,'2016-06-13','2016-06-13','13:00:21.0000','14:00:00.0000',0),(3,'CrazySale',75,'2017-06-13','2017-06-13','11:20:32.0000','12:00:00.0000',0),(4,'NoWay!',95,'2016-01-01','2017-01-01','00:00:00.0000','12:00:00.0000',0),(5,'HanukaSale',1,'2013-04-02','2017-12-08','08:56:00.0000','22:57:00.0000',0),(6,'GoNutz',50,'2015-03-17','2020-02-20','11:11:11.0000','13:12:11.0000',1),(7,'DontInterrupt',5,'2018-02-03','2018-05-05','14:00:00.0000','16:00:00.0000',1),(8,'HanukaSale',1,'2014-04-02','2015-12-23','06:00:00.0000','10:00:00.0000',0),(9,'RightNow',10,'2016-01-17','2016-01-25','07:00:00.0000','20:00:00.0000',1),(10,'NoWay!',95,'2020-01-01','2020-01-01','00:00:00.0000','12:00:00.0000',1),(11,'ShavuotSale',10,'2016-06-13','2016-06-14','09:30:00.0000','21:30:00.0000',1),(12,'ShavuotSale',10,'2016-06-13','2016-06-14','09:30:00.0000','21:30:00.0000',1);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `idStation` int(11) NOT NULL,
  `stationName` varchar(45) DEFAULT NULL,
  `DieselStock` int(11) DEFAULT NULL,
  `BenzinStock` int(11) DEFAULT NULL,
  `ScooterStock` int(11) DEFAULT NULL,
  `HomeHeatingStock` int(11) DEFAULT NULL,
  `DieselMinLevel` int(11) DEFAULT NULL,
  `BenzinMinLevel` int(11) DEFAULT NULL,
  `ScooterMinLevel` int(11) DEFAULT NULL,
  `HomeHeatinglMinLevel` int(11) DEFAULT NULL,
  `sNetworkID` int(11) DEFAULT NULL,
  PRIMARY KEY (`idStation`),
  KEY `sNetworkID_idx` (`sNetworkID`),
  CONSTRAINT `sNetworkID` FOREIGN KEY (`sNetworkID`) REFERENCES `networks` (`networkid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES (1,'Hagalil',990,20000,20000,19880,1200,1050,2081,2000,1),(2,'Haifa',15000,15000,15000,15000,750,1040,849,700,2),(3,'Carmiel',30000,30000,30000,30000,550,700,1000,2340,3),(4,'Ten Akko',25000,25000,25000,25000,670,1420,1100,990,3),(5,'Sonol Coca Cola',22500,22500,22500,22500,1000,1100,1250,1617,2);
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(45) NOT NULL,
  `usertype` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `loginatmps` int(10) DEFAULT '0',
  `pictureUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'zaf','user','evyatar','zafir','evyatarzafir@Gmail.com',0,0,'http://www.nrg.co.il/images/archive/465x349/1/297/853.jpg'),(2,'herr','marketingmanger','gil','herr','gilherr@walla.koala',0,2,'http://forumsgallery.tapuz.co.il/ForumsGallery/galleryimages/932_1801200546594.jpg'),(3,'seb','manager','matan','sebton','matan_s08@walla.co.il',0,0,'http://www.ht1.co.il/_Uploads/dbsArticles/moskuna_kredit_daniel_kaminsky_450(1).jpg'),(4,'ba','user','roy','ba','roy@ba.com',0,0,'http://biglal.info/images/m_bar-yahalom.jpg'),(5,'hanuka','user','hacham','hanuka','hagigabasnooker@gmail.com',0,0,'https://pbs.twimg.com/profile_images/965192038/HaHanuka_400x400.jpg'),(6,'ba','marketingagent','james','bond','jamesbond@007.com',0,2,'https://s-media-cache-ak0.pinimg.com/236x/6e/58/0d/6e580da9f69be4a271df3db00cccc9ab.jpg'),(7,'7','networkmanager','stephen','hawking','milky@way.galaxy',0,0,'http://media1.s-nbcnews.com/i/MSNBC/Components/Photo/_new/a_3k_brown_hawking_130501.jpg'),(8,'faruk123','manager','faruk','faruk','farul@alex.com',0,0,'http://www.tlvtimes.co.il/wp-content/uploads/2016/01/%D7%90%D7%9C%D7%9B%D7%A1-%D7%97%D7%95%D7%9C%D7%'),(9,'helga','manager','helga','model','helgamodel@sportwaw.com',0,0,'https://pbs.twimg.com/media/B7gifSlCYAA1Nj3.png'),(10,'david','manager','david','beckham','davidbekham@wallaw.com',0,0,'http://media1.popsugar-assets.com/files/2015/03/20/719/n/2589278/da1b3b88_edit_img_cover_file_401219'),(11,'charlie','manager','charlie','sheen','2manandhalf@jake.com',1,0,'http://img2-2.timeinc.net/people/i/2011/news/110314/charlie-sheen-5240.jpg'),(12,'ez7','marketingmanger','eran','zehavi','eranzehavi7@sport5.co.il',0,0,'https://pbs.twimg.com/profile_images/1524797773/903_400x400.jpg'),(13,'foad','marketingmanger','foad','ben eliezer','foadfoad@sheamum.com',0,0,'http://www.knesset.gov.il/mk/images/members/beneliezer_binyamin.jpg'),(14,'obama','marketingagent','barack','obama','bo@bbb.com',0,0,'http://i.huffpost.com/gen/2156672/images/s-BARACK-OBAMA-INAUGURATION-2009-large300.jpg'),(15,'fridman','marketingagent','tal','fridman','eretznehederet@gamail.com',0,0,'http://msc.wcdn.co.il/w/w-218/101604-14.jpg'),(16,'shauli','networkmanager','shauli','shelanu','shauli@wawaw.com',1,0,'http://www.icellcom.co.il/SIP_STORAGE/files/4/2604654.gif'),(17,'irena','networkmanager','irena','shelanu','irena@wawaw.com',0,0,'http://www.maariv.co.il/download/pictures/%D7%9C%D7%99%D7%90%D7%AA%20%D7%94%D7%A8%D7%9C%D7%91%20%D7%');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workers` (
  `job` varchar(45) DEFAULT NULL,
  `username` int(11) NOT NULL,
  `stationID` int(11) DEFAULT NULL,
  `networkID` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_stationid_idx` (`stationID`),
  KEY `fk_networkID_idx` (`networkID`),
  CONSTRAINT `fk_networkID` FOREIGN KEY (`networkID`) REFERENCES `networks` (`networkid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stationid` FOREIGN KEY (`stationID`) REFERENCES `station` (`idStation`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers`
--

LOCK TABLES `workers` WRITE;
/*!40000 ALTER TABLE `workers` DISABLE KEYS */;
INSERT INTO `workers` VALUES ('marketingmanger',2,NULL,1),('manager',3,1,1),('marketingagent',6,NULL,1),('networkmanager',7,NULL,1),('manager',8,2,2),('manager',9,3,3),('manager',10,4,3),('manager',11,5,2),('marketingmanger',12,NULL,2),('marketingmanger',13,NULL,3),('marketingagent',14,NULL,2),('marketingagent',15,NULL,3),('networkmanager',16,NULL,2),('networkmanager',17,NULL,3);
/*!40000 ALTER TABLE `workers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-17 22:17:24
