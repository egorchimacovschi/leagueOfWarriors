package leagueofwarriors.game;

import leagueofwarriors.account.Account;
import leagueofwarriors.entities.Character;
import leagueofwarriors.entities.Enemy;
import leagueofwarriors.io.JsonInput;
import leagueofwarriors.map.CellEntityType;
import leagueofwarriors.map.Grid;
import leagueofwarriors.map.ImpossibleMove;
import leagueofwarriors.spells.Earth;
import leagueofwarriors.spells.Fire;
import leagueofwarriors.spells.Ice;
import leagueofwarriors.spells.Spell;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Account> accounts;
    private Account currentAccount;
    private Character currentCharacter;
    private Grid grid;
    private final Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        try {
            accounts = JsonInput.deserializeAccounts("accounts.json");
        } catch (IOException e) {
            System.out.println("Could not load accounts: " + e.getMessage());
            return;
        }

        login();
        selectCharacter();

        if (currentCharacter == null) {
            System.out.println("No character to play with. Exiting.");
            return;
        }

        grid = Grid.generateRandom();
        grid.display();

        boolean playing = true;
        while (playing) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            try {
                playing = executeCommand(input);
            } catch (InvalidCommandException | ImpossibleMove e) {
                System.out.println(e.getMessage());
            }

            if (!currentCharacter.isAlive()) {
                System.out.println("You died. Game over.");
                playing = false;
            }
        }

        saveAccounts();
        System.out.println("Goodbye, " + currentCharacter.getName() + "!");
    }

    private void login() {
        while (currentAccount == null) {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            for (Account account : accounts) {
                if (account.getInformation().getCredentials().matches(email, password)) {
                    currentAccount = account;
                    break;
                }
            }

            if (currentAccount == null) {
                System.out.println("Invalid credentials, try again.");
            }
        }
    }

    private void selectCharacter() {
        ArrayList<Character> characters = currentAccount.getCharacters();

        if (characters.isEmpty()) {
            System.out.println("This account has no characters.");
            return;
        }

        System.out.println("Choose a character:");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println(i + ") " + characters.get(i).getName());
        }

        int choice = -1;
        while (choice < 0 || choice >= characters.size()) {
            System.out.print("Enter a number: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < 0 || choice >= characters.size()) {
                    System.out.println("Out of range, try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number, try again.");
            }
        }

        currentCharacter = characters.get(choice);
    }

    private boolean executeCommand(String command) throws InvalidCommandException, ImpossibleMove {
        CellEntityType encountered = null;

        switch (command) {
            case "north" -> encountered = grid.goNorth();
            case "south" -> encountered = grid.goSouth();
            case "west" -> encountered = grid.goWest();
            case "east" -> encountered = grid.goEast();
            case "map" -> grid.display();
            case "exit" -> {
                return false;
            }
            default -> throw new InvalidCommandException("Unknown command: " + command);
        }

        if (encountered == CellEntityType.ENEMY) {
            battle(new Enemy());
        } else if (encountered == CellEntityType.SANCTUARY) {
            System.out.println("A sanctuary! Life and mana fully restored.");
            currentCharacter.regenLife(Integer.MAX_VALUE);
            currentCharacter.regenMana(Integer.MAX_VALUE);
        } else if (encountered == CellEntityType.PORTAL) {
            System.out.println("You found the portal! You win!");
            currentAccount.incrementMapsCompleted();
            return false;
        }

        grid.display();
        return true;
    }

    private void battle(Enemy enemy) {
        System.out.println("An enemy appears!");

        while (currentCharacter.isAlive() && enemy.isAlive()) {
            System.out.println("Your HP: " + currentCharacter.getLife()
                    + " (mana: " + currentCharacter.getMana() + ")"
                    + " | Enemy HP: " + enemy.getLife());
            System.out.print("Action (attack/fire/ice/earth): ");
            String action = scanner.nextLine().trim().toLowerCase();

            int enemyLifeBefore = enemy.getLife();

            switch (action) {
                case "attack" -> enemy.receiveDamage(currentCharacter.getDamage());
                case "fire" -> currentCharacter.useSpell(findOrLearnSpell(Fire.class, 15, 10), enemy);
                case "ice" -> currentCharacter.useSpell(findOrLearnSpell(Ice.class, 15, 10), enemy);
                case "earth" -> currentCharacter.useSpell(findOrLearnSpell(Earth.class, 15, 10), enemy);
                default -> {
                    System.out.println("Unknown action.");
                    continue;
                }
            }

            if (enemy.getLife() == enemyLifeBefore) {
                System.out.println("No effect this turn (dodged, immune, or out of mana).");
            }

            if (!enemy.isAlive()) {
                System.out.println("You defeated the enemy!");
                currentCharacter.gainExperience(20);
                break;
            }

            currentCharacter.receiveDamage(enemy.getDamage());
        }
    }

    private Spell findOrLearnSpell(Class<? extends Spell> spellClass, int damage, int manaCost) {
        for (Spell spell : currentCharacter.getSpells()) {
            if (spellClass.isInstance(spell)) {
                return spell;
            }
        }

        Spell spell;
        if (spellClass == Fire.class) {
            spell = new Fire(damage, manaCost);
        } else if (spellClass == Ice.class) {
            spell = new Ice(damage, manaCost);
        } else {
            spell = new Earth(damage, manaCost);
        }

        currentCharacter.learnSpell(spell);
        return spell;
    }

    private void saveAccounts() {
        JSONArray accountsJson = new JSONArray();

        for (Account account : accounts) {
            JSONObject accountJson = new JSONObject();

            JSONObject credentialsJson = new JSONObject();
            credentialsJson.put("email", account.getInformation().getCredentials().getEmail());
            credentialsJson.put("password", account.getInformation().getCredentials().getPassword());
            accountJson.put("credentials", credentialsJson);

            accountJson.put("name", account.getInformation().getName());
            accountJson.put("country", account.getInformation().getCountry());
            accountJson.put("favoriteGames", new JSONArray(account.getInformation().getFavouriteGames()));
            accountJson.put("mapsCompleted", account.getMapsCompleted());

            JSONArray charactersJson = new JSONArray();
            for (Character character : account.getCharacters()) {
                JSONObject characterJson = new JSONObject();
                characterJson.put("class", character.getClass().getSimpleName());
                characterJson.put("name", character.getName());
                characterJson.put("maxMana", character.getMaxMana());
                characterJson.put("maxLife", character.getMaxLife());
                characterJson.put("strength", character.getStrength());
                characterJson.put("charisma", character.getCharisma());
                characterJson.put("dexterity", character.getDexterity());
                charactersJson.put(characterJson);
            }
            accountJson.put("characters", charactersJson);

            accountsJson.put(accountJson);
        }

        try {
            Files.writeString(Path.of("accounts.json"), accountsJson.toString(2));
        } catch (IOException e) {
            System.out.println("Could not save accounts: " + e.getMessage());
        }
    }
}
