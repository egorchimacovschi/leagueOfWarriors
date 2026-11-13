<div align="center">

# ⚔️ League of Warriors

**A terminal-based RPG engine in Java — object-oriented from the ground up.**

# League of Warriors

A command-line text-adventure game written in Java, built for the POO (OOP) homework "Tema - Partea 1". You log in to an account, pick one of its characters, and explore a randomly generated map, fighting enemies with melee attacks and elemental spells until you find the portal.

## Features

- Account login against a JSON file (`accounts.json`), with multiple characters per account
- Three character classes: Warrior, Mage, Rogue, each with a main attribute, two secondary attributes and one elemental immunity
- Randomly generated maps (3-10 cells per side) with enemies, sanctuaries, a portal and a start cell
- Turn-based combat: melee attacks and Fire / Ice / Earth spells that cost mana
- Sanctuaries that fully restore life and mana
- Experience and levelling
- Progress (maps completed, characters) saved back to `accounts.json` when the game ends
- Invalid commands and off-map moves are reported without crashing the game

## Requirements

- Java 21
- Maven 3.x

## Build

```
mvn package
```

This produces a self-contained jar (dependencies bundled with the Maven Shade plugin) at `target/league-of-warriors.jar`.

## Run

Run from the project root, because `accounts.json` is read from the current working directory:

```
java -jar target/league-of-warriors.jar
```

## How to play

Log in with an email and password from `accounts.json`, then choose a character by its number.

Map symbols:

| Symbol | Meaning |
|--------|---------|
| `P` | You |
| `.` | Empty cell |
| `E` | Enemy |
| `S` | Sanctuary |
| `O` | Portal (reach it to win) |

Commands on the map:

| Command | Effect |
|---------|--------|
| `north`, `south`, `west`, `east` | Move one cell |
| `map` | Redraw the map |
| `exit` | Save and quit |

Walking onto an enemy starts a battle. Each turn, choose one of:

| Action | Effect |
|--------|--------|
| `attack` | Melee attack |
| `fire`, `ice`, `earth` | Cast that spell (costs mana; blocked if the enemy is immune) |

Enemies can dodge melee attacks (50% chance) and each one is randomly immune to some elements, so a turn can have no effect. Defeating an enemy gives experience. If your life reaches zero the game ends.

## Character classes

| Class | Main attribute (raises damage) | Secondary attributes (reduce damage taken) | Immune to |
|-------|-------------------------------|--------------------------------------------|-----------|
| Warrior | Strength | Charisma, Dexterity | Fire |
| Mage | Charisma | Strength, Dexterity | Ice |
| Rogue | Dexterity | Strength, Charisma | Earth |

All classes have a 50% chance to double the damage they deal and a 50% chance to halve the damage they receive.

## accounts.json format

A JSON array of accounts:

```json
[
  {
    "credentials": { "email": "marcel@yahoo.com", "password": "6K7GUxjsAc" },
    "name": "Marcel",
    "country": "Romania",
    "favoriteGames": ["Metin", "4Story"],
    "mapsCompleted": 15,
    "characters": [
      {
        "class": "Warrior",
        "name": "Odysseus Prisco",
        "maxMana": 20,
        "maxLife": 100,
        "strength": 8,
        "charisma": 3,
        "dexterity": 4
      }
    ]
  }
]
```

`favoriteGames`, `mapsCompleted` and `characters` are optional. `class` must be `Warrior`, `Mage` or `Rogue`.

## Project structure

```
LeagueOfWarriors/
├── accounts.json
├── pom.xml
├── README.md
└── src/main/java/leagueofwarriors/
    ├── Test.java                  entry point
    ├── account/                   Account, Account.Information, Credentials
    ├── entities/                  Battle, Entity, Character, Warrior, Mage, Rogue, Enemy
    ├── game/                      Game, InvalidCommandException
    ├── io/                        JsonInput (reads accounts.json with org.json)
    ├── map/                       CellEntityType, Cell, Grid, ImpossibleMove
    └── spells/                    Spell, Fire, Ice, Earth
```

## Design notes

- `Entity` implements the `Battle` interface; `receiveDamage` and `getDamage` are left abstract so every class defines its own combat rules.
- Elemental immunity is checked with `instanceof` on the spell type, so each element is its own `Spell` subclass.
- `Grid` extends `ArrayList<ArrayList<Cell>>`, has a private constructor and is created through `Grid.generateRandom()`.
- `InvalidCommandException` and `ImpossibleMove` are checked exceptions, caught in `Game` so bad input never crashes the game.
- Game logic currently reads and writes the console directly. Part II of the assignment (adding a UI with design patterns) will need that pulled out behind an interface.
