package leagueofwarriors.account;

import leagueofwarriors.entities.Character;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {
    private final Information information;
    private final ArrayList<Character> characters;
    private int mapsCompleted;

    public Account(Information information) {
        this.information = information;
        characters = new ArrayList<>();
        mapsCompleted = 0;
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public int getMapsCompleted() {
        return mapsCompleted;
    }

    public void incrementMapsCompleted() {
        mapsCompleted += 1;
    }

    public static class Information {
        private final Credentials credentials;
        private final SortedSet<String> favouriteGames;
        private String name;
        private String country;

        public Information(Credentials credentials, String name, String country) {
            this.credentials = credentials;
            this.name = name;
            this.country = country;
            this.favouriteGames = new TreeSet<>();
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public SortedSet<String> getFavouriteGames() {
            return favouriteGames;
        }

        public void addFavouriteGame(String game) {
            favouriteGames.add(game);
        }
    }
}
