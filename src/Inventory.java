import java.util.*;

 /**
 * O clasa ce modeleaza inventarul unui personaj
 */
public class Inventory {
    private List<Potion> potions;
    private int maxWeight;
    private int gold;

    public Inventory() {
        potions = new ArrayList<>();
    }

    public Inventory(List<Potion> potions, int maxWeight, int gold) {
        this.potions = potions;
        this.maxWeight = maxWeight;
        this.gold = gold;
    }

    public Inventory(int maxWeight, int gold) {
        this.maxWeight = maxWeight;
        this.gold = gold;
    }

    /**
    * O metoda care arata potiunile din inventar
    */
    public String showPotions(String mode) {
        StringBuilder message = new StringBuilder();
        if (potions.isEmpty()) {
            message = new StringBuilder("\t\t\tYour inventory is empty!");
            if (mode.equals("1")) {
                System.out.println(message);
                return "";
            } else
                return message.toString();
        }
        int cnt = 0;
        for (Potion potion : potions) {
            if (mode.equals("2"))
                message.append("\t\t\tOn the position ").append(cnt)
                        .append(" you have this potion: ").append(potion.getClass().getSimpleName()).append("\n");
            else
                System.out.println("\t\t\tOn the position " + cnt + " you have this potion: " + potion.getClass().getSimpleName());
            cnt++;
        }
        return message.toString();
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
      /**
      * O metoda care calculeaza greutatea ramasa
      */
    public int calculateWeight() {
        int currWeight = 0;
        if (potions.size() == 0)
            return maxWeight;
        for (Potion potion : potions)
            currWeight += potion.getWeight();
        return maxWeight-currWeight;
    }

      /**
      * O metoda care adauga potiunea cumparata in inventar
      */
    public void addPotion(Potion potion) {
        if (potion.getWeight()<=calculateWeight()) {
            potions.add(potion);
            System.out.println("\t\t\tThis potion: " + potion.getClass().getSimpleName() + " was added in your inventory!");
            gold = gold - potion.getPrice();
            return;
        }
        System.out.println("\t\t\tNot enough space!." +
                "\n\t\t\t And..someone took the desired potion from the shop..sad");
    }

    /**
    * O metoda care arunca o potiune din inventar
    */
    public Potion removePotion(int index) {
        if (potions.isEmpty())
            return null;
        return potions.remove(index);
    }

}
