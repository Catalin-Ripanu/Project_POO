 /**
 * O clasa ce extinde clasa abstracta 'Spell'
 * Aceasta clasa defineste una din cele 3 abilitati
 */
public class Fire extends Spell{
    public Fire()
    {
        setSpellName("Fire");
        setSpellDamage(50);
        setManaCost(20);
    }
     /**
      *  O metoda ce 'viziteaza' o entitate
      */
    @Override
    public void visit(Entity entity) {
        possibleDamage(entity.isFireProtection(),entity);
    }
}
