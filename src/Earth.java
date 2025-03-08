 /**
  * O clasa ce extinde clasa abstracta 'Spell'
  * Aceasta clasa defineste una din cele 3 abilitati
 */
public class Earth extends Spell{
    public Earth()
    {
        setSpellName("Earth");
        setSpellDamage(60);
        setManaCost(30);
    }
     /**
      *  O metoda ce 'viziteaza' o entitate
     */
    @Override
    public void visit(Entity entity) {
        possibleDamage(entity.isEarthProtection(),entity);
    }
}
