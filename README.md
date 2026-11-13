# League of Warriors

A command-line text-adventure game in Java, made for the POO homework "Tema - Partea 1".

Log in, pick a character, explore a random map, fight enemies with attacks and spells, and reach the portal to win.

## Features

- Login from `accounts.json`
- Several characters per account
- Three classes: Warrior, Mage, Rogue
- Random maps, 3 to 10 cells per side
- Turn-based combat with Fire, Ice and Earth spells
- Sanctuaries that restore life and mana
- Experience and levels
- Progress saved to `accounts.json` on exit
- Bad input never crashes the game

## Requirements

- Java 21
- Maven 3

## Build

```
mvn package
```

## Run

From the project root:

```
java -jar target/league-of-warriors.jar
```

## How to play

Log in with an email and password from `accounts.json`, then choose a character by number.

Map symbols:

- `P` you
- `.` empty
- `E` enemy
- `S` sanctuary
- `O` portal (reach it to win)

Map commands:

- `north`, `south`, `west`, `east` to move
- `map` to redraw the map
- `exit` to save and quit

Battle actions:

- `attack` for a melee hit
- `fire`, `ice`, `earth` to cast a spell

Spells cost mana and do nothing if the enemy is immune to that element. Enemies also dodge melee 50% of the time, so a turn can have no effect.

## Classes

Warrior
- Damage from Strength
- Defense from Charisma and Dexterity
- Immune to Fire

Mage
- Damage from Charisma
- Defense from Strength and Dexterity
- Immune to Ice

Rogue
- Damage from Dexterity
- Defense from Strength and Charisma
- Immune to Earth

Every class has a 50% chance to double the damage it deals and a 50% chance to halve the damage it takes.

## accounts.json

A JSON array of accounts:

```json
[
  {
    "credentials": {
      "email": "marcel@yahoo.com",
      "password": "6K7GUxjsAc"
    },
    "name": "Marcel",
    "country": "Romania",
    "favoriteGames": ["Metin"],
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

`favoriteGames`, `mapsCompleted` and `characters` are optional.
`class` is `Warrior`, `Mage` or `Rogue`.

## Project structure

Source is in `src/main/java/leagueofwarriors/`:

- `Test.java`: entry point
- `account/`: Account, Credentials
- `entities/`: Battle, Entity, Character, Warrior, Mage, Rogue, Enemy
- `game/`: Game, InvalidCommandException
- `io/`: JsonInput
- `map/`: CellEntityType, Cell, Grid, ImpossibleMove
- `spells/`: Spell, Fire, Ice, Earth

## Design notes

- `Entity` implements `Battle`. `receiveDamage` and `getDamage` are abstract, so each class sets its own combat rules.
- Each element is its own `Spell` subclass, and immunity is checked with `instanceof`.
- `Grid` extends `ArrayList<ArrayList<Cell>>` and is built with `Grid.generateRandom()`.
- `InvalidCommandException` and `ImpossibleMove` are checked exceptions caught in `Game`.
- `Game` reads and writes the console directly. Part II (adding a UI) will need that moved behind an interface.
