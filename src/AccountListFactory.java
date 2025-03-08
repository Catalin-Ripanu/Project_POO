 /**
 * O clasa ce poseda o metoda 'getCharacter' care foloseste sablonul de proiectare 'Factory'
 */
public class AccountListFactory {
    public static Character getCharacter(String type,String hero_name,int exp,int level)
    {
        switch (type)
        {
            case "Warrior":
                return new Warrior(hero_name,exp,level);
            case "Mage":
                return new Mage(hero_name,exp,level);
            case "Rogue":
                return new Rogue(hero_name,exp,level);
            default:
                return null;
        }
    }
}
