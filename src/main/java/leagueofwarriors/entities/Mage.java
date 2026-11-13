package leagueofwarriors.entities;

public class Mage extends Character {
    public Mage(String name, int maxMana, int maxLife,
            int strength, int charisma, int dexterity) {
        super(name, maxMana, maxLife, false, true, false, strength, charisma, dexterity);
    }

    @Override
    public int getDamage() {
        return getCharisma() + (Math.random() > 0.5 ? getCharisma() : 0);
    }

    @Override
    public void receiveDamage(int damage) {
        int reduction = (getStrength() + getDexterity()) / 4;
        int reduced = Math.max(0, damage - reduction);

        if (Math.random() > 0.5) {
            reduceLife(reduced / 2);
        } else {
            reduceLife(reduced);
        }
    }
}
