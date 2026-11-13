package leagueofwarriors.entities;

import leagueofwarriors.spells.Spell;
import leagueofwarriors.spells.Earth;
import leagueofwarriors.spells.Fire;
import leagueofwarriors.spells.Ice;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Battle {

    private final List<Spell> spells = new ArrayList<>();
    private int life;
    private int maxLife;
    private int mana;
    private int maxMana;
    private boolean immuneToFire;
    private boolean immuneToIce;
    private boolean immuneToEarth;

    protected Entity(int maxMana, int maxLife, boolean immuneToFire, boolean immuneToIce, boolean immuneToEarth) {
        this.maxLife = maxLife;
        life = maxLife;

        this.maxMana = maxMana;
        mana = maxMana;

        this.immuneToEarth = immuneToEarth;
        this.immuneToIce = immuneToIce;
        this.immuneToFire = immuneToFire;
    }

    public int getMana() {
        return mana;
    }

    public int getLife() {
        return life;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public boolean isImmuneToFire() {
        return immuneToFire;
    }

    public boolean isImmuneToIce() {
        return immuneToIce;
    }

    public boolean isImmuneToEarth() {
        return immuneToEarth;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void learnSpell(Spell spell) {
        spells.add(spell);
    }

    public void regenLife(int amount) {
        life = Math.min(maxLife, life + amount);
    }

    public void regenMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    protected void reduceLife(int amount) {
        life -= amount;
    }

    protected boolean consumeMana(int amount) {
        if (mana - amount < 0) {
            return false;
        } else {
            mana -= amount;
            return true;
        }
    }

    public boolean isImmuneToSpell(Spell spell, Entity adversary) {
        if (spell instanceof Ice) {
            return adversary.isImmuneToIce();
        }

        if (spell instanceof Earth) {
            return adversary.isImmuneToEarth();
        }

        if (spell instanceof Fire) {
            return adversary.isImmuneToFire();
        }

        return false;
    }

    // public void useSpell(Spell spell, Entity adversary) {
    // if (spell.getClass().getSimpleName() == "Ice") {
    // if (adversary.isImmuneToIce()) {
    // this.reduceMana(spell.getManaCost());
    // return;
    // } else {
    // adversary.reduceLife(spell.getDamage());
    // this.reduceMana(spell.getManaCost());
    // return;
    // }
    // } else if (spell.getClass().getSimpleName() == "Earth") {
    // if (adversary.immuneToEarth()) {
    // this.reduceMana(spell.getManaCost());
    // return;
    // } else {
    // adversary.reduceLife(spell.getDamage());
    // this.reduceMana(spell.getManaCost());
    // return;
    // }
    // } else {
    // if (adversary.isImmuneToFire()) {
    // this.reduceMana(spell.getManaCost());
    // return;
    // } else {
    // adversary.reduceLife(spell.getDamage());
    // this.reduceMana(spell.getManaCost());
    // return;
    // }
    // }
    // }

    public void useSpell(Spell spell, Entity adversary) {
        if (!consumeMana(spell.getManaCost())) {
            return;
        }
        if (isImmuneToSpell(spell, adversary)) {
            return;
        }

        adversary.receiveDamage(spell.getDamage());
    }
}
