import javax.swing.*;
import java.util.*;
  /**
   *  O clasa abstracta ce modeleaza entitatile acestui joc
   *  Implementeaza interfata 'Element'
  */
public abstract class Entity implements Element<Entity> {
    private List<Spell> abilities;
    private int hitpoints;
    private int maxHitpoints;
    private int currMana;
    private int maxMana;
    private boolean fireProtection;
    private boolean iceProtection;
    private boolean earthProtection;

    public Entity() {
        abilities = new ArrayList<>();
        putRandomAbilities(abilities);
    }

    public List<Spell> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Spell> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Spell spell) {
        if (abilities == null)
            return;
        abilities.add(spell);
    }
    /**
     *  O metoda ce elimina o abilitate din lista de abilitati
     *  Aceasta metoda este folosita de metoda 'useAbility'
    */
    public Spell removeAbility(int index, String mode) {
        String message;
        if (abilities == null || abilities.isEmpty()) {
            message = "\t\t\t" + getClass().getSimpleName() + " tried to use an ability,but failed\n" +
                    "\t\t\t" + getClass().getSimpleName() + " does not have abilities!";
            if (mode.equals("1"))
                System.out.println(message);
            else
                JOptionPane.showMessageDialog(null, message);
            return null;
        }

        if (currMana >= abilities.get((index)).getManaCost())
            return abilities.remove((index));
        message = "\t\t\t" + getClass().getSimpleName() + " tried to use an ability,but failed\n" +
                "\t\t\t" + getClass().getSimpleName() + " does not have enough mana!";
        if (mode.equals("1"))
            System.out.println(message);
        else
            JOptionPane.showMessageDialog(null, message);
        return null;
    }
    /**
     *  O metoda ce afiseaza lista de abilitati
    */
    public String showAbility(String mode) {
        StringBuilder message = new StringBuilder();
        if (abilities == null || abilities.isEmpty()) {
            message = new StringBuilder("\t\t\t" + getClass().getSimpleName() + " does not have abilities!");
            if (mode.equals("2"))
                return message.toString();
            else {
                System.out.println(message);
                return "";
            }
        }
        int cnt = 0;
        for (Spell spell : abilities) {
            if (mode.equals("2"))
                message.append("\t\t\tOn the position ").append(cnt).append(" ")
                        .append(getClass().getSimpleName())
                        .append(" has this ability: ").append(spell.getSpellName()).append("\n");
            else
                System.out.println("\t\t\tOn the position " + cnt + " " +
                        getClass().getSimpleName() + " has this ability: " + spell.getSpellName());
            cnt++;
        }
        return message.toString();
    }
    /**
     *  O metoda ce populeaza lista de abilitati
    */
    public void putRandomAbilities(List<Spell> abilities) {
        int value;
        int cont;
        SplittableRandom random = new SplittableRandom();
        if (random.nextInt(10) % 2 == 0) {
            cont = 4;
            for (int i = 0; i < cont; i++) {
                value = random.nextInt(3);
                switch (value) {
                    case 0:
                        addAbility(new Fire());
                        break;
                    case 1:
                        addAbility(new Ice());
                        break;
                    case 2:
                        addAbility(new Earth());
                        break;
                }
            }
        } else {
            cont = 3;
            for (int i = 0; i < cont; i++) {
                value = random.nextInt(3);
                switch (value) {
                    case 0:
                        addAbility(new Ice());
                        break;
                    case 1:
                        addAbility(new Earth());
                        break;
                    case 2:
                        addAbility(new Fire());
                        break;
                }
            }
        }
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public int getCurrMana() {
        return currMana;
    }

    public void setCurrMana(int currMana) {
        this.currMana = currMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isFireProtection() {
        return fireProtection;
    }

    public void setFireProtection(boolean fireProtection) {
        this.fireProtection = fireProtection;
    }

    public boolean isIceProtection() {
        return iceProtection;
    }

    public void setIceProtection(boolean iceProtection) {
        this.iceProtection = iceProtection;
    }

    public boolean isEarthProtection() {
        return earthProtection;
    }

    public void setEarthProtection(boolean earthProtection) {
        this.earthProtection = earthProtection;
    }

    public void recoverHitpoints(int hp) {
        while (hp > 0) {
            if (hitpoints == maxHitpoints)
                break;
            else {
                hitpoints += 1;
                hp--;
            }
        }
    }

    public void recoverMana(int mana) {
        while (mana > 0) {
            if (currMana == maxMana)
                break;
            else {
                currMana += 1;
                mana--;
            }
        }
    }
    /**
     * O metoda ce foloseste o abilitate 'spell' asupra acestei tinte: 'enemy'
     * Metoda 'removeAbility' verifica daca sunt indeplinite toate conditiile
    */
    public void useAbility(Spell spell, Entity enemy) {
        if (spell == null) {
            return;
        }
        enemy.accept(spell);
        currMana = currMana - spell.getManaCost();
    }

    public abstract void receiveDamage(int damage);

    public abstract int getDamage();
}
