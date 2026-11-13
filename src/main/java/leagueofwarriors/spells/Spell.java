package leagueofwarriors.spells;

public abstract class Spell {
    private final int damage;
    private final int manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;

    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(damage=" + damage + ", manaCost=" + manaCost + ")";
    }
}
