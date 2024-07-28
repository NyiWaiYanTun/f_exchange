-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 28, 2024 at 08:31 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `f_exchange`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `balance` decimal(15,2) NOT NULL DEFAULT 0.00,
  `total_credit` decimal(15,2) NOT NULL DEFAULT 0.00,
  `total_debit` decimal(15,2) NOT NULL DEFAULT 0.00,
  `total_transaction` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `user_id`, `name`, `currency_id`, `balance`, `total_credit`, `total_debit`, `total_transaction`) VALUES
(2, 2, 'THB ACCOUNT', 5, 0.00, 0.00, 0.00, 0),
(4, 4, 'THB ACCOUNT', 5, 0.00, 0.00, 0.00, 0),
(5, 5, 'THB ACCOUNT', 5, 0.00, 0.00, 0.00, 0),
(6, 6, 'THB ACCOUNT', 5, 11700.00, 12200.00, 500.00, 3),
(7, 6, 'USD ACCOUNT', 2, 300.00, 500.00, 200.00, 2),
(8, 1, 'THB ACCOUNT', 5, 42800.00, 0.00, 7200.00, 1),
(9, 1, 'USD ACCOUNT', 2, 50200.00, 200.00, 0.00, 1),
(10, 4, 'Euro ACCOUNT', 1, 500.00, 500.00, 0.00, 1),
(11, 2, 'MMK ACCOUNT', 3, 500000.00, 500000.00, 0.00, 1),
(12, 1, 'SGD ACCOUNT', 4, 1000.00, 0.00, 0.00, 0),
(13, 1, 'MMK Account', 3, 5000000.00, 0.00, 0.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `currency`
--

CREATE TABLE `currency` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sell_rate` decimal(10,2) NOT NULL,
  `buy_rate` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `currency`
--

INSERT INTO `currency` (`id`, `name`, `sell_rate`, `buy_rate`) VALUES
(1, 'Euro', 40.00, 1.00),
(2, 'USD', 36.00, 32.00),
(3, 'MMK', 0.02, 0.01),
(4, 'SGD', 28.00, 26.76),
(5, 'THB', 40.00, 39.02);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_fxc_acc_id` int(11) NOT NULL,
  `user_thb_acc_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `transaction_desc` varchar(255) DEFAULT NULL,
  `transaction_type` varchar(50) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `rate` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `user_id`, `user_fxc_acc_id`, `user_thb_acc_id`, `currency_id`, `transaction_desc`, `transaction_type`, `amount`, `rate`, `created_at`) VALUES
(1, 6, 6, 6, 5, 'Deposited', 'deposit', 5000.00, 1.00, '2024-07-28 17:53:59'),
(2, 6, 6, 6, 5, 'Withdrawed', 'withdraw', 500.00, 1.00, '2024-07-28 17:55:31'),
(3, 6, 7, 7, 2, 'Deposited', 'deposit', 500.00, 1.00, '2024-07-28 18:01:57'),
(4, 6, 7, 6, 2, 'Phoebe Sells to F_Exchange', 'Exchange Sell', 200.00, 36.00, '2024-07-28 18:16:27'),
(5, 4, 10, 10, 1, 'Deposited', 'deposit', 500.00, 1.00, '2024-07-28 18:20:22'),
(6, 2, 11, 11, 3, 'Deposited', 'deposit', 500000.00, 1.00, '2024-07-28 18:22:07');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `isAdmin`) VALUES
(1, 'Joey', '2222', 1),
(2, 'Ross', '2222', 0),
(4, 'Chandler', '2222', 0),
(5, 'Monica', '2222', 0),
(6, 'Phoebe', '2222', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `currency_id` (`currency_id`);

--
-- Indexes for table `currency`
--
ALTER TABLE `currency`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `user_fxc_acc_id` (`user_fxc_acc_id`),
  ADD KEY `user_thb_acc_id` (`user_thb_acc_id`),
  ADD KEY `currency_id` (`currency_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `currency`
--
ALTER TABLE `currency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `account_ibfk_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`user_fxc_acc_id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`user_thb_acc_id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `transaction_ibfk_4` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
