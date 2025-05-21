# LookingDuplicateMobs

**LookingDuplicateMobs** est un plugin Spigot (API 1.16) qui duplique automatiquement les créatures vivantes qu’un joueur fixe du regard. Il permet d’ajouter un effet amusant sur un serveur Minecraft : plus vous observez une entité, plus elle se multiplie.

## Fonctionnement

- Un `BukkitRunnable` (`Task.java`) s’exécute à chaque tick.
- Pour chaque joueur dans un mode de jeu autorisé par la configuration, le plugin parcourt les entités vivantes voisines (30 blocs).
- Si le joueur regarde directement l’entité (aucun bloc solide entre eux) et que le délai défini est écoulé, l’entité est clonée via `Main.cloneEntity`.
- Les clones conservent de nombreux attributs (nom personnalisé, santé, effets, propriétés spécifiques selon le type, etc.).

## Fichiers de configuration

### `config.yml`

| Option                | Type     | Description                                                                   |
|-----------------------|----------|-------------------------------------------------------------------------------|
| `delay`               | entier   | Délai en ticks entre deux duplicata pour une même entité (20 ticks = 1 seconde). |
| `gamemodes.adventure` | booléen  | Active la duplication pour les joueurs en mode **aventure**.                  |
| `gamemodes.survival`  | booléen  | Active la duplication pour les joueurs en mode **survie**.                    |
| `gamemodes.creative`  | booléen  | Active la duplication pour les joueurs en mode **créatif**.                   |
| `gamemodes.spectator` | booléen  | Active la duplication pour les joueurs en mode **spectateur**.                |

Exemple de configuration par défaut :
```yml
delay: 4 #(in ticks, so 20 = 1 second)
gamemodes:
  adventure: true
  survival: true
  creative: false
  spectator: false
```

### `plugin.yml`

Déclare les métadonnées du plugin et la commande principale.

```yml
name: LookingDuplicateMobs
author: DarkBow_
version: 1.1
main: fr.darkbow_.lookingatmobsduplicatethem.Main
api-version: 1.16
commands:
  lookduplimobs:
    description: Dont look at living entities OR You will be surrounded by Creepers!
    permission:
    aliases:
      - ldm
      - lookingduplimobs
      - lookdmobs
      - ldmobs
```

## Commandes

Commande principale : **`/lookduplimobs`** (alias : `/ldm`, `/lookingduplimobs`, `/lookdmobs`, `/ldmobs`).

- `/lookduplimobs show` : affiche le délai actuel.
- `/lookduplimobs set <nombre>` : définit un nouveau délai (en ticks). La valeur doit être numérique.

Aucune permission spécifique n’est définie dans `plugin.yml`. La commande est donc accessible à tous par défaut.

## Compilation / Installation

Ce projet utilise **Maven**.

```bash
mvn package
```

Le JAR généré se trouve dans le dossier `target/` (par exemple `LookingDuplicateMobs-1.2.jar`). Copiez-le dans le dossier `plugins` de votre serveur Spigot puis redémarrez celui‑ci.

## Fichiers importants

- `Main.java` – Classe principale : gestion de la config, enregistrement de la commande et lancement du `Task`.
- `Command.java` – Traite la commande `/lookduplimobs`.
- `Events.java` – Initialisation des structures liées aux joueurs à la connexion/déconnexion.
- `Task.java` – Boucle qui détecte le regard des joueurs et clone les entités.
- `config.yml` – Paramètres modifiables (délai, modes de jeu).
- `plugin.yml` – Déclaration du plugin et de sa commande.

## Utilisation rapide

1. Compilez (ou récupérez) le JAR dans `target/`.
2. Placez-le dans `plugins/` sur votre serveur Spigot.
3. (Re)démarrez le serveur pour générer `config.yml`.
4. Adaptez le délai et les modes de jeu dans `config.yml` si nécessaire puis rechargez le serveur.
5. Utilisez `/ldm show` ou `/ldm set <ticks>` pour consulter ou modifier la valeur en jeu.

<!-- Pas de section licence : aucun fichier LICENSE présent dans le dépôt. -->
