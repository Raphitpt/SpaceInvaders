# Space Invaders

Un clone classique de Space Invaders développé en Java avec Swing. Le jeu reprend les mécaniques du jeu d'arcade original avec quelques ajouts modernes, notamment un système de boss.

## Fonctionnalités

- Déplacement du vaisseau avec les flèches directionnelles
- Tir de projectiles (barre espace)
- Vagues d'ennemis qui descendent progressivement
- Boss de fin avec des capacités spéciales
- Système de score
- Détection de collisions
- Conditions de victoire et de défaite

## Prérequis

- Java 17 ou supérieur
- Maven 3.6+

## Installation et lancement

### Compilation

```bash
mvn clean install
```

### Exécution

```bash
mvn exec:java -Dexec.mainClass="spaceinvaders.SpaceInvadersApp"
```

Ou après compilation, directement depuis le JAR :

```bash
java -jar target/spaceinvaders-2.0-SNAPSHOT.jar
```

## Architecture du projet

```
src/
├── main/java/spaceinvaders/
│   ├── SpaceInvadersApp.java      # Point d'entrée
│   ├── config/
│   │   └── GameConfig.java        # Configuration du jeu
│   ├── game/
│   │   ├── Boss.java              # Logique du boss
│   │   ├── Enemy.java             # Entités ennemies
│   │   ├── GameEngine.java        # Moteur principal
│   │   ├── GameState.java         # État global du jeu
│   │   └── GameTimer.java         # Gestion du temps
│   ├── input/
│   │   └── InputHandler.java     # Gestion des entrées clavier
│   ├── ui/
│   │   ├── GameFrame.java         # Fenêtre principale
│   │   ├── GamePanel.java         # Panel de rendu
│   │   └── Renderer.java          # Logique de rendu
│   └── utils/
│       └── functions/
│           └── CollisionUtils.java # Détection de collisions
└── test/
    └── java/spaceinvaders/         # Tests unitaires
```

## Tests

Le projet utilise JUnit 5 et Mockito pour les tests unitaires.

### Lancer les tests

```bash
mvn test
```

### Rapport de couverture

JaCoCo génère automatiquement un rapport de couverture lors des tests :

```bash
mvn test
# Le rapport est disponible dans target/site/jacoco/index.html
```

## CI/CD

Le projet utilise GitHub Actions pour l'intégration continue. À chaque pull request :
- Compilation du projet
- Exécution des tests avec Xvfb (pour les tests GUI)
- Publication des rapports de tests

## Mécaniques de jeu

### Ennemis

Les ennemis se déplacent en formation et descendent progressivement vers le joueur. Chaque ennemi éliminé rapporte 10 points.

### Boss

Lorsque tous les ennemis normaux sont éliminés, un boss apparaît avec :
- 20 points de vie
- Capacité de tir vers le joueur
- Déplacement horizontal
- 500 points à la destruction

### Game Over

La partie se termine si :
- Un ennemi atteint la position du vaisseau
- Le boss atteint le vaisseau
- Un projectile du boss touche le vaisseau

### Victoire

La victoire est obtenue en éliminant tous les ennemis et le boss.

## Technologies

- **Java 17** : Langage principal
- **Swing** : Interface graphique
- **Maven** : Gestion des dépendances et build
- **JUnit 5** : Framework de tests
- **Mockito** : Mocking pour les tests
- **JaCoCo** : Couverture de code

## Développement

### Structure du code

Le projet suit une architecture MVC simplifiée :
- **Model** : `GameState`, `Enemy`, `Boss`
- **View** : `GamePanel`, `Renderer`, `GameFrame`
- **Controller** : `GameEngine`, `InputHandler`

### Conventions

- Code en français pour les commentaires
- Indentation : 4 espaces
- Tests pour les classes métier et utilitaires

## Licence

Projet académique - 2025
