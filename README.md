<div align="center">

# ⚔️ League of Warriors

**A terminal-based RPG engine in Java — object-oriented from the ground up.**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![org.json](https://img.shields.io/badge/JSON-org.json-blue)](https://mvnrepository.com/artifact/org.json/json)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/status-in%20development-yellow)]()

</div>

---

Players authenticate into an account, choose a character — **Warrior**,
**Mage**, or **Rogue**, each with distinct stats and an elemental immunity —
and explore a randomly generated grid map. Every cell hides an encounter: a
turn-based battle, a sanctuary, or a portal to the next level. Built without
a framework, so every design decision — the class hierarchy, the interfaces,
the exception handling — is deliberate and visible.

## 📖 Contents

- [Demo](#-demo)
- [Design highlights](#-design-highlights)
- [Tech stack](#-tech-stack)
- [Getting started](#-getting-started)
- [Project structure](#-project-structure)
- [Status](#-status)
- [Roadmap](#-roadmap)

## 🎮 Demo

```
Comanda (NORTH, SOUTH, WEST, EAST, QUIT): EAST
Ai intalnit un inamic! Inamic: viata 80, mana 70

--- Tura 1 ---  Tu: viata 100, mana 100
1. Atac obisnuit   2. Abilitate
Alege: 2
  1. Fire  - damage 20, mana 15
  2. Ice   - damage 15, mana 10
Alege abilitatea: 1
Folosesti Fire: 10 (atacul tau) + 20 (abilitatea) = 30 damage.
```

## 🧩 Design highlights

| Concept | Where it shows up |
|---|---|
| **Abstraction & polymorphism** | `Battle` interface, implemented identically by every `Character` subclass and `Enemy` — the combat loop never checks *what* it's fighting |
| **Inheritance** | `Entity` → `Character` → `Warrior` / `Mage` / `Rogue`; `Spell` → `Fire` / `Ice` / `Earth` |
| **Encapsulation** | Account credentials are private, exposed only through accessors |
| **Defensive programming** | Every user input is validated; malformed commands and illegal moves raise `InvalidCommandException` / `ImpossibleMove` and are recovered from, never crash the session |
| **Collections** | `ArrayList` for characters & spell inventories, `SortedSet` for alphabetized favorite games, `Grid extends ArrayList<ArrayList<Cell>>` for the map |
| **Serialization** | Accounts and characters (de)serialized from `accounts.json` via `org.json` |

## 🛠 Tech stack

<div align="center">

![Java](https://img.shields.io/badge/-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/-Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

</div>

## 🚀 Getting started

```bash
mvn compile
mvn exec:java
```

Or build a standalone runnable jar:

```bash
mvn package
java -jar target/league-of-warriors.jar
```

## 📂 Project structure

```
src/main/java/leagueofwarriors/
├── account/    # Account, Credentials
├── entities/   # Battle, Entity, Character, Warrior, Mage, Rogue, Enemy
├── game/       # Game, InvalidCommandException
├── io/         # JsonInput
├── map/        # CellEntityType, Cell, Grid, ImpossibleMove
├── spells/     # Spell, Fire, Ice, Earth
└── Test.java   # entry point
```

## ✅ Status

- [ ] Spells — `Spell`, `Fire`, `Ice`, `Earth`
- [ ] Entities — `Battle`, `Entity`, `Character`, `Warrior`, `Mage`, `Rogue`, `Enemy`
- [ ] Accounts — `Credentials`, `Account`, `Account.Information`, JSON reading
- [ ] Map — `CellEntityType`, `Cell`, `Grid`, `ImpossibleMove`
- [ ] Game loop — `Game`, exception handling, `Test`

## 🗺 Roadmap

- Persist game progress to JSON (save/resume)
- Inventory & potion system
- Multiple enemy types + boss fight
- Difficulty levels
- Leaderboard (`Comparable`/`Comparator`, streams)
- Phase 2: GUI built on design patterns (MVC/Observer)

---

<div align="center">
<sub>Built by <a href="https://github.com/egorchimacovschi">Egor Chimacovschi</a></sub>
</div>
