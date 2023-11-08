-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 05, 2023 at 05:44 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ticketsystemdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `body` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `ticket_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  KEY `FKsyf8wt2qb7rhcau6v3p4axrba` (`ticket_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_persian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `title` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `supporter_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yswhrin7x6gmllo347hy95ev` (`customer_id`),
  KEY `FK8b7d0a0ci1dk2uryebigimeyf` (`supporter_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_persian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `token` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf16_persian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_persian_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
