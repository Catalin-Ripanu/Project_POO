import javax.swing.*;
import java.awt.*;
import java.util.List;

 /**
 * O clasa auxiliara ce ajuta la 'aerisirea' altor clase
 * Aceasta clasa retine mesaje utile jucatorului
 * Constructorul este privat intrucat nu este necesara instantierea
 */
public class HelperClass {
    private HelperClass() {
    }

    /**
     * O metoda care arata situatia inventarului
     */
    public static String showGoldWeight(Grid table, String mode) {
        String message = "\t\t\tYour gold: " + table.getReference().getInventory().getGold()
                + " Your remaining weight(inventory): " + table.getReference().getInventory().calculateWeight();
        if (mode.equals("1")) {
            System.out.println(message);
            return "";
        } else
            return message;
    }

    /**
     * O metoda care arata clasa personajului
     */
    public static String profession(Grid table, String mode) {
        String message = "\t\t\tYour profession is: " + table.getReference().getClass().getSimpleName();
        if (mode.equals("1")) {
            System.out.println(message);
            return "";
        } else
            return message;
    }

    /**
     * O metoda care arata viata,mana,potiunile personajului
     */
    public static String importantStats(Grid table, String mode) {
        String message = "\t\t\tYour HP is: " + table.getReference().getHitpoints() + " Your mana is: " +
                table.getReference().getCurrMana();
        String help = table.getReference().getInventory().showPotions(mode);
        if (mode.equals("1")) {
            System.out.println(help);
            System.out.println(message);
            return "";
        } else {
            help += "\n";
            return help + message;
        }
    }

    /**
     * O metoda care prezinta optiunile jucatorului atunci cand se intalneste cu un inamic (modul test/'hardcoded' dezactivat)
     */
    public static void dangerEnemy() {
        System.out.println("\t\t\t------The enemy is approaching!.He/She wants your money------");
        System.out.println("""
                \t\t\tPress 1 to use basic attack
                \t\t\tPress 2 to use a potion
                \t\t\tPress 3 to use an ability
                \t\t\tPress U to show your abilities
                \t\t\tPress I to show your potions""");
    }

    /**
     * O metoda care arata atributele personajului intr-un anumit moment
     */
    public static String showStats(Grid table, String mode) {
        String message = "\t\t\tYour current level: " + table.getReference().getCurrLevel() +
                "\n" + "\t\t\tYour experience: " + table.getReference().getCurrExp() +
                "\n" + "\t\t\tYour strength: " + table.getReference().getStrength() + "\n" +
                "\t\t\tYour dexterity: " + table.getReference().getDexterity() + "\n" +
                "\t\t\tYour charisma: " + table.getReference().getCharisma() + "\n" +
                "\t\t\tNumber of defeated enemies: " + table.getReference().getDefeatedEnemy() +
                "\n" + "\t\t\tYour gold: " + table.getReference().getInventory().getGold();
        if (mode.equals("2"))
            return message;
        else {
            System.out.println(message);
            return "";
        }
    }

    /**
     * Aceste metode prezinta instructiunile jocului
     */
    public static void showInformation(int counter, List<Account> accounts, String hardcoded) {
        System.out.println("------Displaying the account with the number " + counter + "------");
        System.out.println(accounts.get(counter));
        if (!hardcoded.equals("1"))
            System.out.println("""
                                   
                                   
                    \t\t\tPress 1 to choose the account.
                    \t\t\tPress 2 to move to the next account.
                    \t\t\tPress 3 to move to the previous account.
                    \t\t\tPress H to exit the game.
                                   
                                   
                    """);
        else
            System.out.println("\n\n\t\t\tHardcoded mode on\n\t\t\tYou cannot choose!\n\n");
    }

    public static String showInstructions(String mode) {
        String message = """
                \t\t\tPress R to show these instructions again (if you do not remember them).
                \t\t\tPress U to show your current abilities.
                \t\t\tPress I to show your potions and your stats (HP,mana,current level,experience).
                \t\t\tPress O to show your profession(Warrior/Rogue/Mage)
                \t\t\tPress Y to show your gold/weight(inventory).
                \t\t\tPress H to exit the game.You cannot exit while you are fighting or visiting.
                """;
        if (mode.equals("1")) {
            System.out.println("""
                                      
                                      
                    \t\t\tPress W to move to the north;
                    \t\t\tPress S to move to the south.
                    \t\t\tPress A to move to the west.
                    \t\t\tPress D to move to the east.
                    \t\t\tPress T to show the map.
                       """ + message);
            return "";
        } else
            return message;
    }

    /**
     * O metoda care ajuta la crearea butoanelor
     */
    public static void createButtons(JButton button, int size) {
        button.setBackground(Color.black);
        button.setForeground(Color.RED);
        button.setFont(new Font("Times New Roman", Font.PLAIN, size));
    }

    /**
     * O metoda care ajuta la crearea etichetelor
     */
    public static void createLabels(JLabel label, int size, Color color) {
        label.setForeground(color);
        label.setFont(new Font("Times New Roman", Font.PLAIN, size));
    }

    /**
     * Aceste metode prezinta alegerea inamicului
     */
    public static void enemyDecision(int damage) {
        System.out.println("\t\t\tEnemy wants to use basic attack " +
                "because he does not have enough mana or " +
                "does not have abilities.He is dealing " + damage + " damage");
    }

    public static void enemyDecision(Grid table, Spell spell) {
        System.out.println("\t\t\t" + table.getReference().getClass().getSimpleName() + " has protection" +
                " against: " + spell.getSpellName() + "\n\t\t\tYour hero does not take any damage!");
    }

    /**
     * O metoda ce avertizeaza jucatorul daca modul test/'hardcoded' este activat
     */
    public static String hardMessage() {
        return """
                Hardcoded mode is on!
                You cannot make decisions!
                You cannot choose an account or character!
                Just type P in terminal and watch your hero!
                """;
    }

    /**
     * O metoda auxiliara folosita in lupte
     * Aceasta verifica protectia entitatii 'entity' impotriva abilitatii 'spell'
     */
    public static boolean testFight(Entity entity, Spell spell) {
        if (spell == null)
            return false;
        String test = entity.getClass().getSimpleName();
        switch (test) {
            case "Warrior": {
                if (spell.getSpellName().equals("Fire"))
                    return true;
            }
            break;
            case "Rogue": {
                if (spell.getSpellName().equals("Earth"))
                    return true;
            }
            break;
            case "Mage": {
                if (spell.getSpellName().equals("Ice"))
                    return true;
            }
            break;
        }
        return false;
    }
    /**
    * O metoda care verifica daca parametrul 'string' este un numar intreg si pozitiv
    */
     public static boolean isNumber(String string) {
         boolean test=string != null && string.matches("^[+-]?\\d+$");
         if(test)
             return Integer.parseInt(string)>0;
         return false;
     }
}
