package leagueofwarriors.entities;

public class Warrior extends Character {
    public Warrior(String name, int maxMana, int maxLife,
            int strength, int charisma, int dexterity) {
        super(name, maxMana, maxLife, true, false, false, strength, charisma, dexterity);
    }

    @Override
    public int getDamage() {
        return getStrength() + (Math.random() > 0.5 ? getStrength() : 0);
    }

    @Override
    public void receiveDamage(int damage) {
        int reduction = (getCharisma() + getDexterity()) / 4;
        int reduced = Math.max(0, damage - reduction);

        if (Math.random() > 0.5) {
            reduceLife(reduced / 2);
        } else {
            reduceLife(reduced);
        }
    }
}
