Guide de lancement de l'application Ressources Relationnelles
-------------------------------------------------------------
Bienvenue sur le projet Ressources Relationnelles,
une application web Java développée avec Spring Boot, Thymeleaf, et MySQL.

Ce document vous explique comment installer, configurer et lancer l'application.

Avant de lancer l'application, assurez-vous d’avoir les éléments suivants installés :

Java 17+

Maven

MySQL (ou un outil comme DBeaver pour la gestion de la base)

Un IDE (IntelliJ ou Eclipse recommandé)

🗄️ Configuration de la Base de Données
Créez une base de données MySQL nommée :


ressources_relationnelles

Lancez le script SQL pour créer les tables nécessaires après le premier lancement de l'application et avant toute manipulation.

Par défaut, l’application se connecte à la base avec les identifiants suivants :


utilisateur : root
mot de passe : root
Ces identifiants peuvent être modifiés dans le fichier src/main/resources/application.properties.

Pensez à ajouter des données minimales pour faire fonctionner l'application :

Des rôles (role)

Des utilisateurs (user)

Des adresses (address) si besoin

Lancer l’Application Spring Boot grâce à votre IDE

L'application sera disponible à l'adresse suivante :

👉 http://localhost:8080

📦 Structure du Projet
controllers → gestion des routes

entities → entités JPA

repositories → accès aux données

services → logique métier

config → configuration de sécurité / web

templates → vues Thymeleaf

static → fichiers CSS / JS


Seules certaines pages sont accessibles sans être connecté.
-------------------------------------------------------------

Guide de l'Utilisateur :

Lorsque vous arrivez sur l'application, vous êtes considéré comme utilisateur non connecté.
Dans ce cas, seules certaines pages publiques sont accessibles.

Pour débloquer toutes les fonctionnalités, connectez-vous avec l'un des comptes suivants :

Rôle	Email	Mot de passe
Utilisateur	user@user.com	P@ssword00
Modérateur	modo@modo.com	P@ssword00
Administrateur	admin@admin.com	P@ssword00
Super-Administrateur	superAdmin@superAdmin.com	P@ssword00
Astuce : Pour tester toutes les fonctionnalités, connectez-vous en tant que Super-Administrateur.

Fonctionnalités accessibles
Ressources : Liste publique des ressources disponibles.

Créer une ressource : Accessible après connexion.

Espace personnel : Une fois connecté, accédez à votre profil.

🔧 Espaces d'administration (après connexion) :
Bo Modérateur : Gestion des ressources à valider et des commentaires signalés.

Bo Administrateur : Statistiques détaillées sur l’utilisation de la plateforme.

Bo Super-Administrateur : Gestion complète des utilisateurs.

N’hésitez pas à explorer librement l’application.
📩 En cas de problème, contactez-nous sans hésiter !