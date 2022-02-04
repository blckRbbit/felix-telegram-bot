CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `telegram_name` varchar(255) DEFAULT NULL,
  `telegram_id` bigint DEFAULT NULL,
  `details_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKmywwflpk22vos9qpigr3e9ar` (`details_id`),
  CONSTRAINT `FKmywwflpk22vos9qpigr3e9ar` FOREIGN KEY (`details_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `telegram_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `user_ideas` (
  `idea_id` bigint NOT NULL AUTO_INCREMENT,
  `additionally` varchar(255) DEFAULT NULL,
  `estimated_costs` int DEFAULT '0',
  `economic_effect` int DEFAULT '0',
  `idea` varchar(1222255) DEFAULT NULL,
  `idea_title` varchar(255) DEFAULT NULL,
  `kind_of_idea` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`idea_id`),
  KEY `FKgi38biqlg4y971lmi0na2uxnn` (`user_id`),
  CONSTRAINT `FKgi38biqlg4y971lmi0na2uxnn` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` bigint DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `TEST_DB`.`admin` (`telegram_name`, `password`) VALUES ('hypnoZzz', '1');
