import java.util.*;

/**
 * O Clasa ce modeleaza inamicul din joc
 * Implementeaza toate metodele necesare
 */
public class Enemy extends Entity implements CellElement {

    public Enemy() {
        int value;
        SplittableRandom random = new SplittableRandom();
        setHitpoints(random.nextInt(66) + 55);
        setCurrMana(random.nextInt(56) + 60);
        setMaxHitpoints(getHitpoints());
        setMaxMana(getCurrMana());
        for (int i = 0; i < 3; i++) {
            value = random.nextInt(3);
            switch (value) {
                case 0:
                    setFireProtection(true);
                    break;
                case 1:
                    setIceProtection(true);
                    break;
                case 2:
                    setEarthProtection(true);
                    break;
            }
        }
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    /**
     * O metoda care modeleaza primirea daunelor
     */
    @Override
    public void receiveDamage(int damage) {
        SplittableRandom random = new SplittableRandom();
        boolean chance = random.nextInt(1, 101) <= 50;
        if (chance)
            setHitpoints(getHitpoints() - damage);
    }

    /**
     * O metoda care modeleaza oferirea daunelor
     */
    @Override
    public int getDamage() {
        SplittableRandom random = new SplittableRandom();
        int initialDamage = random.nextInt(1, 12);
        boolean chance = random.nextInt(1, 101) <= 50;
        if (chance) {
            return 2 * initialDamage;
        }
        return initialDamage;
    }

    /**
     * O metoda ce accepta 'vizita' unei abilitati
     */
    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}
