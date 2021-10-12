-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 35.205.108.8    Database: prueba_logitravel
-- ------------------------------------------------------
-- Server version	5.7.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `preferencia` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Pepe Flores','+34666666661','pflores@gmail.com','email'),(2,'Miguel Perez','+34666666662','mperez@gmail.com','sms'),(3,'Toni Gomez','+34666666663','tgomez@gmail.com','email');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoteles`
--

DROP TABLE IF EXISTS `hoteles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoteles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idplantilla` int(11) DEFAULT NULL,
  `nombre` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ciudad` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idplantilla_idx` (`idplantilla`),
  CONSTRAINT `idplantilla` FOREIGN KEY (`idplantilla`) REFERENCES `plantillas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoteles`
--

LOCK TABLES `hoteles` WRITE;
/*!40000 ALTER TABLE `hoteles` DISABLE KEYS */;
INSERT INTO `hoteles` VALUES (1,1,'La Sirena','Palma'),(2,1,'Bellver','Palma'),(3,2,'Maricel','Palma'),(4,3,'Abelux','Palma'),(5,2,'La Bodega','Inca'),(6,3,'Son Vivot','Inca'),(7,1,'Sa Vinya','Manacor'),(8,2,'Can Guixa','Manacor');
/*!40000 ALTER TABLE `hoteles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plantillas`
--

DROP TABLE IF EXISTS `plantillas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plantillas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `asunto` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cuerpo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantillas`
--

LOCK TABLES `plantillas` WRITE;
/*!40000 ALTER TABLE `plantillas` DISABLE KEYS */;
INSERT INTO `plantillas` VALUES (1,'Plantilla 1','Promocion 1','Promocionando 1'),(2,'Plantilla 2','Promocion 2','Promocionando 2'),(3,'Plantilla 3','Promocion 3','Promocionando 3');
/*!40000 ALTER TABLE `plantillas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idcliente` int(11) DEFAULT NULL,
  `idhotel` int(11) DEFAULT NULL,
  `personas` int(11) DEFAULT NULL,
  `entrada` date DEFAULT NULL,
  `salida` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idcliente_idx` (`idcliente`),
  KEY `idhotel_idx` (`idhotel`),
  CONSTRAINT `idcliente` FOREIGN KEY (`idcliente`) REFERENCES `clientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idhotel` FOREIGN KEY (`idhotel`) REFERENCES `hoteles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,1,1,2,'2020-01-01','2020-01-05'),(2,2,5,4,'2020-02-15','2020-02-20'),(3,2,7,2,'2020-03-15','2020-03-20');
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-12 23:06:29
