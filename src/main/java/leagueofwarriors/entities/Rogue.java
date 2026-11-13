package leagueofwarriors.entities;

public class Rogue extends Character {
    public Rogue(String name, int maxMana, int maxLife,
            int strength, int charisma, int dexterity) {
        super(name, maxMana, maxLife, false, false, true, strength, charisma, dexterity);
    }

    @Override
    public int getDamage() {
        return getDexterity() + (Math.random() > 0.5 ? getDexterity() : 0);
    }

    @Override
    public void receiveDamage(int damage) {
        int reduction = (getStrength() + getCharisma()) / 4;
        int reduced = Math.max(0, damage - reduction);

        if (Math.random() > 0.5) {
            reduceLife(reduced / 2);
        } else {
            reduceLife(reduced);
        }
    }
}
