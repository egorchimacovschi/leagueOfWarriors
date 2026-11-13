package leagueofwarriors.entities;

public class Enemy extends Entity {
    private final int baseDamage;

    public Enemy() {
        super((int) (Math.random() * 26) + 10,
                (int) (Math.random() * 81) + 40,
                ((int) (Math.random() * 2)) == 1 ? true : false,
                ((int) (Math.random() * 2)) == 1 ? true : false,
                ((int) (Math.random() * 2)) == 1 ? true : false);
        baseDamage = (int) (Math.random() * 11) + 1;
    }

    @Override
    public int getDamage() {
        return ((int) (Math.random() * 2)) == 1 ? baseDamage * 2 : baseDamage;
    }

    @Override
    public void receiveDamage(int damage) {
        if ((int) (Math.random() * 2) == 1) {
            reduceLife(damage);
        }
    }
}
