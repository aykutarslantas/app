-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1:3306
-- Üretim Zamanı: 11 Ağu 2023, 17:21:03
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `app`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `answer`
--

DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content_id` int NOT NULL,
  `time` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `attributes`
--

DROP TABLE IF EXISTS `attributes`;
CREATE TABLE IF NOT EXISTS `attributes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `eser_id` int NOT NULL,
  `category_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `attributes`
--

INSERT INTO `attributes` (`id`, `title`, `eser_id`, `category_id`, `created_at`) VALUES
(1, 'yellow', 1, 1, '2023-08-09 14:23:17');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `attribute_categories`
--

DROP TABLE IF EXISTS `attribute_categories`;
CREATE TABLE IF NOT EXISTS `attribute_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `attribute_categories`
--

INSERT INTO `attribute_categories` (`id`, `title`, `created_at`) VALUES
(1, 'ders adı', '2023-08-09 14:19:19'),
(2, 'yaş grubu', '2023-08-09 14:19:19'),
(3, '+18', '2023-08-09 14:20:10'),
(4, '+13', '2023-08-09 14:20:10'),
(5, 'aile', '2023-08-09 14:21:26'),
(6, '+7', '2023-08-09 14:21:26'),
(7, 'cinsellik', '2023-08-09 14:21:26'),
(8, 'ingilizce', '2023-08-09 14:21:26');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `eser`
--

DROP TABLE IF EXISTS `eser`;
CREATE TABLE IF NOT EXISTS `eser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` int NOT NULL,
  `created_at` timestamp NOT NULL,
  `cover` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `forgot_password_service`
--

DROP TABLE IF EXISTS `forgot_password_service`;
CREATE TABLE IF NOT EXISTS `forgot_password_service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `hash` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `expired_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `interactive`
--

DROP TABLE IF EXISTS `interactive`;
CREATE TABLE IF NOT EXISTS `interactive` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eser_id` int NOT NULL,
  `interactive` int NOT NULL,
  `created_at` timestamp NOT NULL,
  `end_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `options`
--

DROP TABLE IF EXISTS `options`;
CREATE TABLE IF NOT EXISTS `options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content_id` int UNSIGNED NOT NULL,
  `type_id` int NOT NULL,
  `text` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `option_types`
--

DROP TABLE IF EXISTS `option_types`;
CREATE TABLE IF NOT EXISTS `option_types` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `feature` text COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int NOT NULL,
  `amount` double NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_user_id` int NOT NULL,
  `content_type_id` int NOT NULL,
  `img` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `question_attributes`
--

DROP TABLE IF EXISTS `question_attributes`;
CREATE TABLE IF NOT EXISTS `question_attributes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `attribute_id` int NOT NULL,
  `question_id` int NOT NULL,
  `title` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `access` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `roles`
--

INSERT INTO `roles` (`id`, `name`, `access`) VALUES
(1, 'Öğrenci', ''),
(2, 'Öğretmen', ''),
(3, 'Kitapcı', ''),
(4, 'Eğlence', '');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE IF NOT EXISTS `subscriptions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment_id` int NOT NULL,
  `status` int NOT NULL,
  `plan` int NOT NULL,
  `plan_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int NOT NULL,
  `start_date_time` timestamp NOT NULL,
  `end_date_time` timestamp NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tokens`
--

DROP TABLE IF EXISTS `tokens`;
CREATE TABLE IF NOT EXISTS `tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `secret_key` text COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `login_type` varchar(23) COLLATE utf8mb4_general_ci NOT NULL,
  `headers` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `tokens`
--

INSERT INTO `tokens` (`id`, `created_at`, `secret_key`, `user_id`, `is_active`, `login_type`, `headers`) VALUES
(48, '2023-08-11 16:21:43', '8XTQuHqvCMfrn8KXj9/zC9l4rzm9OsIifbkPAyCz15E=', 9, 1, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: b7a787f9-d024-4e59-9c8d-519a47f5e144\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(46, '2023-08-09 19:12:51', 'gwUSo0dXsj+2chL46j3+dZx45Z3dnpGsIPnt7wPwFa0=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: b053e5b3-d2f1-49b5-ae00-3bee7715e364\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(47, '2023-08-09 19:17:10', 'kjumpII2InkBEvGm+kJYgcuOgQEkhPnVeCQzMgruu54=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 8a417040-e731-4d58-8c02-54d1fd27f937\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(45, '2023-08-09 17:25:25', 'ZQqCwGAiXpr56xzEQVuBczTjpEwwcNkteF0oNRsGrsg=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 80afc444-3807-4942-9b4c-c451f04b83f7\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(44, '2023-08-09 17:21:21', '7VkBvzq0W03Wd7sKv12hkJIlfoJEtdmWYz3gAo4KxL8=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 41095834-b40c-4644-a997-1ae81471499d\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(43, '2023-08-09 16:51:35', 'Jm2xjWAbJtOvBZySMJq5goSnaT7C9tR0/koyTLLsiRo=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 93f475b9-f9e7-47d3-9399-9845523160dc\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(42, '2023-08-09 16:22:26', 'Hj/OUEZ9J3926GpwaNC4f9N25O8CEj7G+J4HYv6mzIc=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: a494ca47-6274-428a-9895-deb15d98c774\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(41, '2023-08-09 16:16:31', 'lHM7foYR0EgKjM5CoJwixCfSHx8k152iyAUJ1FCLlY8=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: d262d659-89ad-4c4a-9682-fa547fec6a65\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(40, '2023-08-09 16:09:18', '7pZQTCJ7qzyxjFFnUPQzzKdUqnK9Ie8UOhu/rmP+xec=', 9, 0, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 7688714b-dae1-4318-880f-f4339bf24a1a\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n'),
(39, '2023-08-09 16:08:45', '7pZQTCJ7qzyxjFFnUPQzzKdUqnK9Ie8UOhu/rmP+xec=', 9, 1, 'web', 'user-agent: PostmanRuntime/7.32.3\naccept: */*\ncache-control: no-cache\npostman-token: 44e23bd4-f641-4736-8414-85b83ede351f\nhost: localhost:8080\naccept-encoding: gzip, deflate, br\nconnection: keep-alive\n');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `mail` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `country_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `facebook` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `instagram` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `twitter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tiktok` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `google` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `apple` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` tinyint(1) DEFAULT '0',
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`id`, `role_id`, `name`, `surname`, `birthday`, `mail`, `password`, `country_code`, `phone`, `facebook`, `instagram`, `twitter`, `tiktok`, `google`, `apple`, `created_at`, `is_active`, `hash`) VALUES
(1, 0, 'Aykut', 'ARSLANTAŞ', '1993-03-05', 'ay1kutarslantas@gmail.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '+90', '5059455369', '', '', '', '', '', '', '2023-07-25 11:25:05', 0, ''),
(2, 1, 'John', 'Doe', '1990-01-01', 'aykutarslantas@bulutklinik.com', '2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 1, 'John', 'Doe', '1993-03-05', 'JohnDoe@test.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 1, 'John', 'Doe', '1993-03-05', 'JohnDoe@test2.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 1, 'John', 'Doe', '1993-03-05', 'JohnDoe@t3est2.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 1, 'Ay3kut', 'ARSLANTAŞ', NULL, 'aykutarslantas@gmail.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', '', '', NULL, NULL, NULL, NULL, NULL, NULL, '2023-08-07 18:49:51', 0, '3af0cc326fa3d87ea820366aa9bcf819e1f7a62e45fe39d5f7ebac33d99afd75');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
