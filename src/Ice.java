/**
 * O clasa ce extinde clasa abstracta 'Spell'
 * Aceasta clasa defineste una din cele 3 abilitati
 */
public class Ice extends Spell {
    public Ice() {
        setSpellName("Ice");
        setSpellDamage(25);
        setManaCost(10);
    }

    @Override
    public void visit(Entity entity) {
        possibleDamage(entity.isIceProtection(), entity);
    }
}
