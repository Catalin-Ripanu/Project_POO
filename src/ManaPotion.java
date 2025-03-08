/**
 * O clasa care modeleaza potiunea de mana
 */
public class ManaPotion implements Potion {
    private int price;
    private int weight;
    private int recoverValue;

    public ManaPotion(int price, int weight, int recoverValue) {
        this.price = price;
        this.weight = weight;
        this.recoverValue = recoverValue;
    }

    /**
     * O metoda folosita pentru a consuma o potiune
     */
    @Override
    public String utilisePotion(Entity entity, String mode) {
        String message = "\t\t\tYour mana is increasing with " + recoverValue + " points";
        entity.recoverMana(recoverValue);
        if (mode.equals("1")) {
            System.out.println(message);
            return "";
        } else
            return message;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getRecoverValue() {
        return recoverValue;
    }

    public void setRecoverValue(int recoverValue) {
        this.recoverValue = recoverValue;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "On this position there is a Mana Potion with the price: " +
                price + " and weight: " + weight + " that recovers " + recoverValue + " mana";
    }
}
