import Exceptions.InvalidCommandException;

import java.util.*;

/**
 * O clasa care modeleaza magazinul din joc
 */
public class Shop implements CellElement {
    private List<Potion> potions;

    public Shop() {
        potions = new ArrayList<>();
        int cont;
        int value;
        SplittableRandom random = new SplittableRandom();
        potions.add(new HealthPotion(25, 15, 35));
        potions.add(new ManaPotion(15, 20, 45));
        if (random.nextInt(10) % 2 == 0) {
            cont = 2;
            for (int i = 0; i < cont; i++) {
                value = random.nextInt(2);
                switch (value) {
                    case 0:
                        potions.add(new HealthPotion(25, 15, 35));
                        break;
                    case 1:
                        potions.add(new ManaPotion(15, 20, 45));
                        break;
                }
            }
        } else {
            cont = 3;
            for (int i = 0; i < cont; i++) {
                value = random.nextInt(2);
                switch (value) {
                    case 0:
                        potions.add(new ManaPotion(15, 20, 45));
                        break;
                    case 1:
                        potions.add(new HealthPotion(25, 15, 35));
                        break;
                }
            }
        }
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }

    /**
     * O metoda ce returneaza o potiune din magazin
     * De asemenea,afiseaza lista de potiuni
     */
    public Potion getPotionShop(String hardcoded) throws InvalidCommandException {
        if (potions.isEmpty()) {
            System.out.println("\t\t\tThis shop does not have potions anymore (Sold out) !");
            return null;
        }
        System.out.println("\t\t\tIn this shop there are " + potions.size() + " potions");
        System.out.println("\t\t\tThese are:");
        int cnt = 0;
        for (Potion potion : potions) {
            if (potion.getPrice() == 25)
                System.out.println("\t\t\tOn the position: " + cnt + " there is a potion which regenerates your life: price " +
                        potion.getPrice() + " weight " + potion.getWeight());
            else
                System.out.println("\t\t\tOn the position: " + cnt + " there is a potion which regenerates your mana: price " +
                        potion.getPrice() + " weight " + potion.getWeight());
            cnt++;
        }
        cnt--;
        String index = "";
        Scanner input = new Scanner(System.in);
        if (!hardcoded.equals("1")) {
            System.out.println("\t\t\tChoosing in progress...");
            System.out.println("\t\t\tType a number from this set {0,.." + cnt + "}" + " to buy a potion" +
                    "(or exit if you do not want to buy)");
            System.out.println("\t\t\tYou will get an error if you do not type a number from the given set");
            index = input.nextLine();
            if (index.equals("exit"))
                return null;
            if (Integer.parseInt(index) < 0 || Integer.parseInt(index) > cnt)
                throw new InvalidCommandException();
            return potions.remove(Integer.parseInt(index));
        } else {
            System.out.println("\t\t\tHardcoded mode is on.Choosing the first potion...");
            System.out.println("\t\t\t------Press P to make an action!!!------");
            index=input.nextLine();
            if(!index.equals("P"))
                throw new InvalidCommandException();
            index = "0";
        }
        return potions.remove(Integer.parseInt(index));
    }

    @Override
    public char toCharacter() {
        return 'S';
    }

}
