/**
 * O interfata ce modeleaza potiunile din joc
 */
public interface Potion {
    String utilisePotion(Entity entity,String mode);
    int getPrice();
    int getRecoverValue();
    int getWeight();
}
