-- Données de test pour la table `utilisateur`

INSERT INTO `utilisateur` (`email`, `password`) VALUES
('user1@example.com', '$2y$10$8rZa.lHZIEXAMPLESltZX/y'), -- Mot de passe "password1" hashé en bcrypt
('user2@example.com', '$2y$10$8rZa.lHZIEXAMPLESltZX/y'), -- Mot de passe "password2" hashé en bcrypt
('user3@example.com', '$2y$10$8rZa.lHZIEXAMPLESltZX/y'), -- Mot de passe "password3" hashé en bcrypt
('admin@example.com', '$2y$10$8rZa.lHZIEXAMPLESltZX/y'); -- Mot de passe "admin123" hashé en bcrypt

-- Données de test pour la table `entreprise`

INSERT INTO `entreprise` (`utilisateur_id`, `nom`) VALUES
(1, 'Entreprise Alpha'),
(2, 'Entreprise Beta'),
(3, 'Entreprise Gamma'),
(4, 'Entreprise Admin');

-- Données de test pour la table `convention`

INSERT INTO `convention` (`entreprise_id`, `salarie_maximum`, `subvention`, `nom`) VALUES
(1, 50, 10000.00, 'Convention Alpha'),
(2, 100, 20000.00, 'Convention Beta'),
(3, 200, 30000.00, 'Convention Gamma'),
(4, 500, 50000.00, 'Convention Admin');

-- Données de test pour la table `salarie`

INSERT INTO `salarie` (`convention_id`, `matricule`, `code_barre`) VALUES
(1, 'MAT001', 'CB001'),
(1, 'MAT002', 'CB002'),
(2, 'MAT003', 'CB003'),
(2, 'MAT004', 'CB004'),
(3, 'MAT005', 'CB005'),
(4, 'MAT006', 'CB006');
