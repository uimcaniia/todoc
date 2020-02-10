<<<<<<< HEAD
# Todoc
=======
# todoc
>>>>>>> origin/master

----


> Cleanup a financé le développement d’une application de gestion de tâches intitulée Todoc.
Nous souhaitons que nos collaborateurs puissent avoir une vision précise des tâches àaccomplir lorsqu'ils se rendent dans des locaux à nettoyer.
Elle est développé sur Android Studio sous Java 8 à compter de android 5.0 (API 21)..

----
## Fonctions principales
1. Lister les tâches​ pour chacun des projets en cours.
1.1 Pouvoir reconnaître à quel projet appartient une tâche, grâce à unecouleur unique qui sera attribuée manuellement à chaque projet.
1.2 Si aucune tâche n’est présente, une illustration et un message (“Tu n’asaucune tâche à traiter”) doivent apparaître à l’écran.
2. Ajouter une tâche​ à réaliser.
3. Supprimer une tâche​ à réaliser.
4. Trier les tâches​, par nom de projet OU par date de création.

----
## Installation

Récupérer le programme via le bouton Download ou en utilisant git clone via ce lien : [links](https://github.com/uimcaniia/todoc).

Lancer Android Studio, Open/File et choisir le fichier téléchargé. Compiler et exécuté en choisissant un émulateur réel ou virtuel. Cliquez sur Run/Run.

----
## Explications

Application responsive sur toutes les tailles de téléphones et tablettes Android enmodes portrait et paysage.
Tests unitaires pour chaque fonctionnalité.
Tests instrumentalisés.
Persistance des données de l’application avec implémentation d'une base de données SQLite.

Dans la première activité, ajoutez une tâche avec le bouton "ajouter" en bas de l'écran d'accueil.
Sélection le nom du projet ainsi que la désignation de la tâche à accomplir.
Valider.

Vous pouvez supprimer les tâche s'affichant dans le RecyclerView avec le bouton "supprimer" (poubelle).

Pour pouver filter et trier les tâches.
