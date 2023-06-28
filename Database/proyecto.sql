CREATE DATABASE  IF NOT EXISTS `proyectodb2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `proyectodb2`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: proyectodb2
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `codigo` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Sports and Outdoors'),(3,'Electronics'),(4,'Home and Garden');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallefactura`
--

DROP TABLE IF EXISTS `detallefactura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallefactura` (
  `id_detalle` int unsigned NOT NULL AUTO_INCREMENT,
  `idproducto` int unsigned NOT NULL,
  `idfactura` int unsigned NOT NULL,
  `cantidad` int unsigned DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`id_detalle`),
  KEY `DetalleFactura_FKIndex1` (`idfactura`),
  KEY `DetalleFactura_FKIndex2` (`idproducto`),
  CONSTRAINT `detallefactura_ibfk_1` FOREIGN KEY (`idfactura`) REFERENCES `factura` (`id_factura`),
  CONSTRAINT `detallefactura_ibfk_2` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallefactura`
--

LOCK TABLES `detallefactura` WRITE;
/*!40000 ALTER TABLE `detallefactura` DISABLE KEYS */;
INSERT INTO `detallefactura` VALUES (2,16,1,20,529493),(3,17,1,12,419775),(4,18,1,12,310966),(5,18,7,2,310966),(6,19,7,1,426052),(7,16,8,3,120000),(8,16,9,3,120000),(9,16,10,3,120000),(10,35,10,1,2100000),(11,16,11,2,120000),(12,16,12,2,120000),(13,18,13,3,310966),(14,16,14,7,120000);
/*!40000 ALTER TABLE `detallefactura` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `deshabilitarmenor0` BEFORE INSERT ON `detallefactura` FOR EACH ROW BEGIN
	DECLARE stock INT;
	SET stock = (SELECT en_stock FROM producto WHERE id_producto = new.idproducto);
    IF stock < new.cantidad THEN
		SET new.cantidad = stock;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `actualizarstock` AFTER INSERT ON `detallefactura` FOR EACH ROW BEGIN
	DECLARE stock, totalPago INT;
	SET stock = (SELECT en_stock FROM producto WHERE id_producto = new.idproducto);
    UPDATE producto SET en_stock = en_stock - new.cantidad WHERE id_producto = new.idproducto;
    SET totalPago = new.cantidad * new.total;
    UPDATE factura SET total = total + totalPago WHERE id_factura = new.idfactura;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id_factura` int unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `fecha_emision` date DEFAULT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`id_factura`),
  KEY `cliente_idx` (`id_cliente`),
  CONSTRAINT `cliente` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`identificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,100320101,'2023-06-26',19358752),(2,101010,'2023-06-26',0),(6,1,'2023-06-26',0),(7,2,'2023-06-26',1047984),(8,3,'2023-06-26',360000),(9,2,'2023-06-26',360000),(10,1003,'2023-06-26',2460000),(11,2,'2023-06-26',240000),(12,2,'2023-06-26',240000),(13,2,'2023-06-26',932898),(14,2,'2023-06-26',840000);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `factura_BEFORE_INSERT` BEFORE INSERT ON `factura` FOR EACH ROW BEGIN
	SET new.fecha_emision = CURRENT_DATE();
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int unsigned NOT NULL AUTO_INCREMENT,
  `codigo_cat` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `valor_unitario` double NOT NULL,
  `en_stock` double NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `Producto_FKIndex1` (`codigo_cat`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (16,1,'Hiking Boots',120000,1),(17,1,'Jump Rope',419775,0),(18,1,'Tennis Racket',310966,26),(19,1,'Weightlifting Gloves',426052,19),(20,1,'Bicycle',95476,11),(21,3,'router',282269,10),(22,3,'gaming console',252048,36),(23,3,'tablet',821343,29),(24,3,'speaker',377417,46),(25,3,'smart home device',110841,23),(27,4,'watering can',525205,44),(28,4,'garden tool set',364369,3),(29,4,'wind chimes',787404,7),(30,4,'garden gnome',708572,15),(31,4,'watering can',394726,46),(32,1,'Producto Nuevo',12,12),(33,1,'Río de la plata',12,12),(34,1,'Producto Nuevo',21000,2),(35,4,'garden',2100000,0),(36,1,'Río de la plata',1,0),(37,1,'Balón de Futbol',21000,40);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `identificacion` int NOT NULL,
  `tipo_cedula` varchar(8) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `ciudad` varchar(100) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `correo` varchar(150) NOT NULL,
  PRIMARY KEY (`identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'C.C.','1','1','1','1','1','1@gmail.com'),(2,'C.C.','2','2','2','2','2','2@gmail.com'),(3,'C.C.','3','3','3','3','3','3@gmail.com'),(1003,'C.C.','Óscar','Alean','Montería','Calle 101#102-1','31029102910','oalean@gmail.com'),(101010,'C.C.','Jorge','Zanabria','Montería','Calle 1','301020101','asodij@gmail.com'),(100320101,'C.C.','Juan','Cabrales','Montería','Calle 10-1010','3000000000','nonexistentemail@gmail.com');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'proyectodb2'
--

--
-- Dumping routines for database 'proyectodb2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27 21:25:41
