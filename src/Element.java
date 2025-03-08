 /**
  * O interfata implementata de clasa abstracta 'Entity' cu scopul de a folosi sablonul 'Visitor'
 */
public interface Element <T extends Entity>{
    void accept(Visitor<T> visitor);
}
