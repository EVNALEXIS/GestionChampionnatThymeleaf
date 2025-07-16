# GestionChampionnatThymeleaf

TP CPIL

# Gestion de Championnat avec Thymeleaf
## Description
Ce projet est une application web de gestion de championnat de football utilisant le framework Spring Boot et le moteur de templates Thymeleaf. Il permet de gérer les équipes, les matchs et les classements d'un championnat.
L'application est fonctionnelle.


## Fonctionnalités
- Authentification des utilisateurs avec Spring Security
- Gestion des équipes (ajout, modification, suppression)
- Gestion des matchs (ajout, modification, suppression)
- Affichage des classements par journée
- Affichage du classement global par championnat
- Affichage des détails d'une équipe

## TODO
- Refactoriser le code pour améliorer la lisibilité et la maintenabilité

## Installation
Il y a docker compose pour instancier un conteneur MySql: docker compose up -d
Lors du Run de l'app, hibernate va instancier la base de données et y insérer les données de test (cf: DataInitilizer.java dans le package com.cpil.gestionchampionnatthymeleaf.config)
Le compte de base pour se connecter est : admin@admin.fr:admin

Attention, il faut commenter le DataInitializer dans le fichier com.cpil.gestionchampionnatthymeleaf.config pour éviter de réinserer les données de test à chaque lancement de l'application.
