import java.util.SplittableRandom;

/**
 * O clasa care modeleaza profesia unui personaj
 * Aceasta extinde clasa abstracta 'Character'
 */
public class Mage extends Character {
    public Mage(String heroName, int exp, int level) {
        super(heroName, exp, level);
        setIncreaseStrength(1);
        setIncreaseDexterity(1);
        setIncreaseCharisma(2);
        setStrength(1);
        setDexterity(1);
        setCharisma(5);
        while (level != 0) {
            increaseAttributes();
            level--;
        }
        setIceProtection(true);
        getInventory().setMaxWeight(45);
        setHeroClass("Mage");
    }

    /**
     * O metoda care modeleaza primirea daunelor
     */
    @Override
    public void receiveDamage(int damage) {
        SplittableRandom random = new SplittableRandom();
        boolean chance = random.nextInt(1, 151 - (getDexterity() + getStrength())) <= 20;
        if (chance) {
            if (damage % 2 == 0) {
                setHitpoints(getHitpoints() - damage / 2);
                return;
            }
            setHitpoints(getHitpoints() - (damage / 2 + 1));
        } else
            setHitpoints(getHitpoints() - damage);
    }

    /**
     * O metoda care modeleaza oferirea daunelor
     */
    @Override
    public int getDamage() {
        SplittableRandom random = new SplittableRandom();
        int damage = 25 + getCharisma()/2 + getCurrLevel() + random.nextInt(1, getCharisma()) / 5;
        boolean chance = random.nextInt(1, 135 - getCharisma()) <= 10;
        if (chance) {
            return 2 * damage;
        }
        return damage;
    }

    /**
     * O metoda ce accepta 'vizita' unei abilitati
     */
    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}
