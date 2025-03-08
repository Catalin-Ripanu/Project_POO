 /**
  * Clasa ce modeleaza personajele
 */
public abstract class Character extends Entity {
    private String heroName;
    private int positionOx;
    private int positionOy;
    private String heroClass;
    private Inventory inventory;
    private int currExp;
    private int currLevel;
    private int strength;
    private int dexterity;
    private int charisma;
    private int increaseStrength;
    private int increaseDexterity;
    private int increaseCharisma;
    private int defeatedEnemy;

    public int getDefeatedEnemy() {
        return defeatedEnemy;
    }

    public void setDefeatedEnemy(int defeatedEnemy) {
        this.defeatedEnemy = defeatedEnemy;
    }

    public Character(String heroName, int exp, int level)
    {
        this.heroName =heroName;
        this.currLevel =level;
        this.currExp =exp;
        setHitpoints(150);
        setCurrMana(100);
        setMaxHitpoints(getHitpoints());
        setMaxMana(getCurrMana());
        inventory=new Inventory();
        inventory.setGold(50);
    }
    public int getIncreaseStrength() {
        return increaseStrength;
    }

    public void setIncreaseStrength(int increaseStrength) {
        this.increaseStrength = increaseStrength;
    }

    public int getIncreaseDexterity() {
        return increaseDexterity;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public void setIncreaseDexterity(int increaseDexterity) {
        this.increaseDexterity = increaseDexterity;
    }

    public int getIncreaseCharisma() {
        return increaseCharisma;
    }

    public void setIncreaseCharisma(int increaseCharisma) {
        this.increaseCharisma = increaseCharisma;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getPositionOx() {
        return positionOx;
    }

    public void setPositionOx(int positionOx) {
        this.positionOx = positionOx;
    }

    public int getPositionOy() {
        return positionOy;
    }

    public void setPositionOy(int positionOy) {
        this.positionOy = positionOy;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getCurrExp() {
        return currExp;
    }

    public void setCurrExp(int currExp) {
        this.currExp = currExp;
    }

    public int getCurrLevel() {
        return currLevel;
    }

    public void setCurrLevel(int currLevel) {
        this.currLevel = currLevel;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    /**
     * O metoda ce mareste atributele in cazul in care personajul a trecut la nivelul urmator
    */
    public void increaseAttributes() {
        strength = strength/4 + currLevel + increaseStrength;
        dexterity = dexterity/4 + currLevel + increaseDexterity;
        charisma = charisma/4 + currLevel + increaseCharisma;
    }
    /**
     *  O metoda ce mareste experienta personajului atunci cand omoara un inamic
     *  S-a ales acest prag: 75
    */
    public String increaseExp(int exp,String mode) {
        String test="\t\t\tThe character got experience: " + exp+"\n";
        String message;
        if(mode.equals("1"))
        System.out.print(test);
        int value = exp + currExp;
        if (value < 75)
            currExp = value;
        else {
            /*
            * Se incrementeaza nivelul atunci cand s-a depasit pragul de 75 experienta
            */
            currLevel++;
            increaseAttributes();
            message= "\t\t\tThe character moved to the next level.The attributes are increasing!\n"+
                    "\t\t\tThe power of your hero becomes: " + strength+"\n"+
                    "\t\t\tThe dexterity of your hero becomes: " + dexterity+"\n"+
                    "\t\t\tThe charisma of your hero becomes : " + charisma+"\n"+
                    "\t\t\tThe current level becomes: " + currLevel+"\n";
            if(mode.equals("1"))
                System.out.println(message);
            else
                return test+message;
            currExp = value - 75;
        }
        return test;
    }
   /**
   * O metoda ce se ocupa cu achizitionarea unei potiuni din magazin
   */
    public void buyPotion(Potion potion) {
        if (potion == null) {
            System.out.println("\t\t\tTry to find another shop!");
            return;
        }
        if (inventory.getGold() >= potion.getPrice()) {
            /*
            * Aceasta metoda 'addPotion' verifica daca personajul are loc in inventar
            */
            inventory.addPotion(potion);
            return;
        }
        System.out.println("\t\t\tYour hero has " + inventory.getGold() + " gold and " + inventory.calculateWeight() +
                " current weight (inventory)");
        System.out.println("\t\t\tNot enough gold!." +
                "\n\t\t\t And..someone took the desired potion from the shop..sad");
    }

    @Override
    public String toString() {
        return "Name: " + heroName +"|" + "\nClass: " + heroClass +"|" + "\nLevel: "
                + currLevel +"|"+"\nExperience: " + currExp +"|";
    }
}
