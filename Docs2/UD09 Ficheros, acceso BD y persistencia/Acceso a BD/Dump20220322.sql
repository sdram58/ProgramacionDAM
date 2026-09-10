-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pizzeria
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientCod` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`clientCod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Alvaro'),(2,'Lorena'),(3,'Abdon'),(4,'Fulano'),(5,'Felipe'),(6,'Josep'),(7,'Ana'),(8,'Mengano');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizzas`
--

DROP TABLE IF EXISTS `pizzas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pizzas` (
  `NPizza` varchar(20) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  PRIMARY KEY (`NPizza`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizzas`
--

LOCK TABLES `pizzas` WRITE;
/*!40000 ALTER TABLE `pizzas` DISABLE KEYS */;
INSERT INTO `pizzas` VALUES ('Besubio',13.00),('Bolognesa',12.50),('Hawaiana',13.00),('Margarita',15.00),('Napolitana',18.00),('Simple',1.50),('Vegetariana',10.50);
/*!40000 ALTER TABLE `pizzas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preparation`
--

DROP TABLE IF EXISTS `preparation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preparation` (
  `NPizza` varchar(20) NOT NULL,
  `ingredient` varchar(20) NOT NULL,
  `quantity` decimal(4,0) NOT NULL,
  PRIMARY KEY (`NPizza`,`ingredient`),
  CONSTRAINT `preparation_ibfk_1` FOREIGN KEY (`NPizza`) REFERENCES `pizzas` (`NPizza`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preparation`
--

LOCK TABLES `preparation` WRITE;
/*!40000 ALTER TABLE `preparation` DISABLE KEYS */;
INSERT INTO `preparation` VALUES ('Besubio','Huevos',2),('Besubio','Oregano',3),('Besubio','Pasta',10),('Besubio','Peperoni',46),('Besubio','Queso',10),('Besubio','Sal',34),('Besubio','Tomate',23),('Bolognesa','Tomate',15),('Hawaiana','Ajo',15),('Hawaiana','Piña',35),('Hawaiana','Tomate',15),('Margarita','Jamon York',23),('Margarita','Oregano',5),('Margarita','Pasta',76),('Margarita','Queso',50),('Margarita','Tomate',20),('Napolitana','Botifarra',15),('Napolitana','Jamón York',34),('Napolitana','Oregano',15),('Napolitana','Queso',10),('Napolitana','Tomate',34),('Simple','Tomate',30),('Vegetariana','Alcachofas',45),('Vegetariana','Judias',4),('Vegetariana','Oregano',5),('Vegetariana','Queso',24),('Vegetariana','Tomate',15);
/*!40000 ALTER TABLE `preparation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `NPizza` varchar(20) NOT NULL,
  `clientCod` int NOT NULL,
  `orderCod` int NOT NULL,
  `date2` date NOT NULL,
  PRIMARY KEY (`orderCod`),
  KEY `NPizza` (`NPizza`),
  KEY `clientCod` (`clientCod`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`NPizza`) REFERENCES `pizzas` (`NPizza`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`clientCod`) REFERENCES `client` (`clientCod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES ('Vegetariana',1,1,'2019-05-03');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-22 19:47:12
