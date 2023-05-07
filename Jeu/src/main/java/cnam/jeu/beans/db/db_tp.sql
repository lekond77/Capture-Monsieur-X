
CREATE database IF NOT EXISTS  jeu;

use jeu;

CREATE TABLE IF NOT EXISTS `comptes` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `Nom` varchar(50) NOT NULL,
 `Prenom` varchar(50) NOT NULL,
 `Age` int(11) NOT NULL,
 `Login` varchar(50) NOT NULL,
 `Motdepasse` varchar(50) NOT NULL,
 PRIMARY KEY (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `comptes` (`id`, `Nom`, `Prenom`, `Age`, `Login`, `Motdepasse`) 
VALUES
(1, 'Meyer', 'Luc', 30, 'meyer', '1111'),
(2, 'Dupont', 'René', 40, 'dupont', '2222'),
(3, 'Legrand', 'Lisa', 16, 'legrand', '3333');