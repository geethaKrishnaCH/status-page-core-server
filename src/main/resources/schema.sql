CREATE TABLE `organizations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `organization_id` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p9pbw3flq9hkay8hdx3ypsldy` (`name`),
  UNIQUE KEY `UK_773n3h2qlfni9xh5hkb6ekttp` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_6efs5vmce86ymf5q7lmvn2uuf` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `organization_id` int NOT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqjj9a6xa11cu9ch24cjo4a7lc` (`organization_id`),
  CONSTRAINT `FKqjj9a6xa11cu9ch24cjo4a7lc` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` enum('DEGRADED','MAINTENANCE','MAJOR_OUTAGE','OPERATIONAL','PARTIAL_OUTAGE') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `organization_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKey68t00vkou73y8eu5u4bibcj` (`organization_id`),
  CONSTRAINT `FKey68t00vkou73y8eu5u4bibcj` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `service_status_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `status` enum('DEGRADED','MAINTENANCE','MAJOR_OUTAGE','OPERATIONAL','PARTIAL_OUTAGE') DEFAULT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsdsgg0n0rrsk9wrb5l2s7ysnh` (`service_id`),
  CONSTRAINT `FKsdsgg0n0rrsk9wrb5l2s7ysnh` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `incidents` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` enum('IDENTIFIED','INVESTIGATING','MONITORING','RESOLVED') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `assignee_id` int DEFAULT NULL,
  `organization_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhmiq51fx3iuwlvn28o2vyq4du` (`assignee_id`),
  KEY `FK9ai9ck5f687jsonfqd906w9b0` (`organization_id`),
  CONSTRAINT `FK9ai9ck5f687jsonfqd906w9b0` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `FKhmiq51fx3iuwlvn28o2vyq4du` FOREIGN KEY (`assignee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `incident_services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_status` enum('DEGRADED','MAINTENANCE','MAJOR_OUTAGE','OPERATIONAL','PARTIAL_OUTAGE') DEFAULT NULL,
  `incident_id` int DEFAULT NULL,
  `service_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7w8mhpqcciu31518nrb4ela9a` (`incident_id`),
  KEY `FKskkbb2c7ttiuossad6vqitj8u` (`service_id`),
  CONSTRAINT `FK7w8mhpqcciu31518nrb4ela9a` FOREIGN KEY (`incident_id`) REFERENCES `incidents` (`id`),
  CONSTRAINT `FKskkbb2c7ttiuossad6vqitj8u` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `incident_updates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` enum('IDENTIFIED','INVESTIGATING','MONITORING','RESOLVED') DEFAULT NULL,
  `incident_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfa95guv5b0lbylpkc784omvhw` (`incident_id`),
  KEY `FK3qxeoenyixb7pcskrm47kqx38` (`user_id`),
  CONSTRAINT `FK3qxeoenyixb7pcskrm47kqx38` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKfa95guv5b0lbylpkc784omvhw` FOREIGN KEY (`incident_id`) REFERENCES `incidents` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `invitations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accepted` bit(1) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `connection_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `invitation_url` varchar(255) DEFAULT NULL,
  `invite_id` varchar(255) NOT NULL,
  `invitee` varchar(255) DEFAULT NULL,
  `inviter` varchar(255) DEFAULT NULL,
  `ticket_id` varchar(255) DEFAULT NULL,
  `organization_id` int NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_83m1wcrkgryowe2t2h2ksrr9s` (`invite_id`),
  KEY `FKq0jssk151g1kt9cx4vnomojc9` (`organization_id`),
  KEY `FK11do2m26r7o1doyjilyj1fsgm` (`role_id`),
  CONSTRAINT `FK11do2m26r7o1doyjilyj1fsgm` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKq0jssk151g1kt9cx4vnomojc9` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `maintenance_events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `organization_id` int NOT NULL,
  `service_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkkmyakakjkyg48iy77nka7bvb` (`organization_id`),
  KEY `FK985wimmdfskbr3il3bcgxrdv1` (`service_id`),
  CONSTRAINT `FK985wimmdfskbr3il3bcgxrdv1` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  CONSTRAINT `FKkkmyakakjkyg48iy77nka7bvb` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;