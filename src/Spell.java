/**
 * O clasa abstracta ce modeleaza abilitatile din joc
 * Implementeaza interfata 'Visitor' pentru a se putea folosi sablonul 'Visitor'
 */
public abstract class Spell implements Visitor<Entity> {
    private String spellName;
    private int spellDamage;
    private int manaCost;

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public int getSpellDamage() {
        return spellDamage;
    }

    public void setSpellDamage(int spellDamage) {
        this.spellDamage = spellDamage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    @Override
    public String toString() {
        return "On this position I have this ability: " + spellName + " that has this damage: " + spellDamage
                + " and mana cost: " + manaCost;
    }

    /**
     * O metoda auxiliara care verifica protectia entitatii 'entity'
     */
    public void possibleDamage(boolean protection, Entity entity) {
        if (!protection)
            entity.receiveDamage(spellDamage);
    }

}