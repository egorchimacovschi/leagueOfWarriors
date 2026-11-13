package leagueofwarriors.entities;

public abstract class Character extends Entity {
    private final String name;
    private int experience;
    private int level;
    private int strength;
    private int charisma;
    private int dexterity;

    protected Character(String name, int maxMana, int maxLife,
            boolean immuneToFire, boolean immuneToIce, boolean immuneToEarth,
            int strength, int charisma, int dexterity) {
        super(maxMana, maxLife, immuneToFire, immuneToIce, immuneToEarth);
        this.name = name;
        this.experience = 0;
        this.level = 1;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void gainExperience(int amount) {
        experience += amount;

        int threshold = level * 100;

        while (experience >= threshold) {
            experience -= threshold;
            level++;
            threshold = level * 100;
        }
    }
}
