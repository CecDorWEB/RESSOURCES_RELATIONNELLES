BIEN LE BONJOUR,

Pour que l'application fonctionne bien il vous faudra :
- créer une base de données nommée "ressources_relationnelles" en MySQL ( vous pouvez utiliser DBEAVER pour une manipulation plus facile )
-> l'utilisateur est root et le mot de passe est root ( la sécurité avant tout ;D ) Hésitez pas à me demander @GaëtanLemeunier si vous avez un problème durant le lancement de l'application
- L'application démarre à cette url -> http://localhost:8080/

- Si vous souhaitez créer un dossier en plus et qu'il soit reconnu par l'application,
    -> Créer le dossier et ajouter le dans RessourcesRelationnellesApplication dans @ComponentScan
- Par pitié définissez bien vos entités, répertoire, vue et service dans les dossiers prévus à cet effet :)
Le changement des dépendances se fait dans le fichier pom.xml
Bon développement à vous !


A FAIRE :

-RGAA
-RGPD ( demande de suppression des données )
