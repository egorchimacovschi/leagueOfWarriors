package leagueofwarriors.io;

import leagueofwarriors.account.Account;
import leagueofwarriors.account.Credentials;
import leagueofwarriors.entities.Character;
import leagueofwarriors.entities.Mage;
import leagueofwarriors.entities.Rogue;
import leagueofwarriors.entities.Warrior;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts(String path) throws IOException {
        String content = Files.readString(Path.of(path));
        JSONArray accountsJson = new JSONArray(content);
        ArrayList<Account> accounts = new ArrayList<>();

        for (int i = 0; i < accountsJson.length(); i++) {
            accounts.add(parseAccount(accountsJson.getJSONObject(i)));
        }

        return accounts;
    }

    private static Account parseAccount(JSONObject accountJson) {
        JSONObject credentialsJson = accountJson.getJSONObject("credentials");
        Credentials credentials = new Credentials(
                credentialsJson.getString("email"),
                credentialsJson.getString("password"));

        Account.Information information = new Account.Information(
                credentials,
                accountJson.getString("name"),
                accountJson.getString("country"));

        JSONArray favoriteGamesJson = accountJson.optJSONArray("favoriteGames");
        if (favoriteGamesJson != null) {
            for (int i = 0; i < favoriteGamesJson.length(); i++) {
                information.addFavouriteGame(favoriteGamesJson.getString(i));
            }
        }

        Account account = new Account(information);
        account.setMapsCompleted(accountJson.optInt("mapsCompleted", 0));

        JSONArray charactersJson = accountJson.optJSONArray("characters");
        if (charactersJson != null) {
            for (int i = 0; i < charactersJson.length(); i++) {
                account.addCharacter(parseCharacter(charactersJson.getJSONObject(i)));
            }
        }

        return account;
    }

    private static Character parseCharacter(JSONObject characterJson) {
        String name = characterJson.getString("name");
        int maxMana = characterJson.getInt("maxMana");
        int maxLife = characterJson.getInt("maxLife");
        int strength = characterJson.getInt("strength");
        int charisma = characterJson.getInt("charisma");
        int dexterity = characterJson.getInt("dexterity");
        String type = characterJson.getString("class");

        return switch (type) {
            case "Warrior" -> new Warrior(name, maxMana, maxLife, strength, charisma, dexterity);
            case "Mage" -> new Mage(name, maxMana, maxLife, strength, charisma, dexterity);
            case "Rogue" -> new Rogue(name, maxMana, maxLife, strength, charisma, dexterity);
            default -> throw new IllegalArgumentException("Unknown character class: " + type);
        };
    }
}
