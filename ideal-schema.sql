-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: ideal
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cod` varchar(32) DEFAULT NULL,
  `nome` varchar(32) DEFAULT NULL,
  `descricao` text,
  `qtd_estoque` int DEFAULT NULL,
  `estoque_minimo` int DEFAULT NULL,
  `estoque_maximo` int DEFAULT NULL,
  `preco_compra` decimal(12,2) NOT NULL DEFAULT '0.00',
  `preco_venda` decimal(12,2) NOT NULL DEFAULT '0.00',
  `bar_code` bigint DEFAULT NULL,
  `ncm` varchar(20) DEFAULT NULL,
  `fator` decimal(12,2) NOT NULL DEFAULT '0.00',
  `imagem` longblob,
  `data_cadastro` date DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'1234','Erick','',1,0,1,5.00,8.00,1234,'1234',37.50,_binary '../img/erixku.jpg',NULL,NULL),(2,'1233','teste','teste',2,1,12,12.00,34.00,12322123,'1231',64.71,NULL,NULL,NULL),(3,'23232','teste2','teste2',1,1,3,12.00,122.00,122321324,'123223',90.16,NULL,NULL,NULL),(4,'12312','Teste 3','Teste3 ',2,1,7,2.00,7.00,124512343,'124451425',71.43,NULL,'2024-12-01',NULL),(5,'45432','Teste 4','Teste 4',4,1,8,23.00,3467.00,235623,'2345',99.34,NULL,'2024-12-01',NULL),(6,'24234','asfgafg','svbsvbsdb',2,2,5,2.00,6.00,111111,'55',66.67,NULL,'2024-12-02','A - Ativo'),(7,'2213','teste 232','jfvbhjvb',32,2,4,2.00,87.00,435242,'3453',97.70,NULL,'2024-12-02','A - Ativo');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 19:41:55
