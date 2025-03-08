import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SplittableRandom;

/**
 * O clasa ce modeleaza lupta cu inamicul prin intermediul interfetei grafice
 * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class EnemyBoard {
    private final JFrame enemy;
    private final JLabel health;
    private final JLabel mana;
    private final JLabel enemyHealth;
    private static EnemyBoard instance = null;
    private JList<Spell> abilities;
    private JList<Potion> potions;
    private final DefaultListModel<Spell> modelAbilities;
    private final DefaultListModel<Potion> modelPotion;
    private final JButton attack;
    private final JButton ability;
    private final JButton potion;

    private EnemyBoard() {
        enemy = new JFrame("Dangerous Enemy");
        health = new JLabel();
        mana = new JLabel();
        enemyHealth = new JLabel();
        attack = new JButton("Basic Attack");
        potion = new JButton("Use a potion");
        ability = new JButton("Use an ability");
        modelPotion = new DefaultListModel<>();
        modelAbilities = new DefaultListModel<>();
    }

    public static EnemyBoard getInstance() {
        if (instance == null)
            return new EnemyBoard();
        return instance;
    }

    /**
     * O metoda care se ocupa de toata logica luptei
     */
    public void fightEnemy(Grid table, JFrame board, String mode) {
        JScrollPane panelMyPotion = new JScrollPane();
        panelMyPotion.setBackground(Color.black);
        JScrollPane panelAbilities = new JScrollPane();
        panelAbilities.setBackground(Color.black);
        JLabel infoPotion = new JLabel("Select your potion =>");
        JLabel infoAbility = new JLabel("Select your ability =>");
        Enemy opponent = (Enemy) table.getCurrCell().getCellElement();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        enemy.setLocation(350, 85);
        HelperClass.createLabels(health, 17, Color.blue);
        HelperClass.createLabels(mana, 17, Color.blue);
        HelperClass.createLabels(enemyHealth, 17, Color.red);
        HelperClass.createLabels(infoAbility, 15, Color.orange);
        HelperClass.createLabels(infoPotion, 15, Color.orange);
        health.setText("Your current health: " + table.getReference().getHitpoints());
        mana.setText("Your current mana: " + table.getReference().getCurrMana());
        enemyHealth.setText("Enemy's current health: " + opponent.getHitpoints());
        panel.add(health);
        panel.add(mana);
        panel.setBounds(0, 0, 250, 80);
        panel.setBackground(Color.black);
        enemyHealth.setBounds(550, 0, 300, 30);
        enemy.setSize(800, 600);
        enemy.getContentPane().setBackground(Color.black);
        enemy.setLayout(null);
        modelAbilities.addAll(table.getReference().getAbilities());
        modelPotion.addAll(table.getReference().getInventory().getPotions());
        potions = new JList<>(modelPotion);
        potions.setBackground(Color.cyan);
        potions.setForeground(Color.darkGray);
        abilities = new JList<>(modelAbilities);
        abilities.setBackground(Color.green);
        abilities.setForeground(Color.red);
        HelperClass.createButtons(ability, 15);
        HelperClass.createButtons(attack, 15);
        HelperClass.createButtons(potion, 15);
        ability.setBounds(90, 395, 200, 100);
        ability.setEnabled(false);
        ability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int hitpoints;
                Spell item = abilities.getSelectedValue();
                int index = abilities.getSelectedIndex();
                int decision = JOptionPane.showConfirmDialog(null, "Do you really want to " +
                                "use this ability: " + item.getClass().getSimpleName() + " against your enemy?", "Question"
                        , JOptionPane.YES_NO_OPTION);
                if (decision == 0) {
                    item = table.getReference().removeAbility(index, mode);
                    if (item != null) {
                        JOptionPane.showMessageDialog(null, "Attacking with this ability: " + item.getSpellName() +
                                "\n              Loading...");
                        hitpoints = opponent.getHitpoints();
                        modelAbilities.remove(index);
                        if (modelAbilities.isEmpty())
                            panelAbilities.setViewportView(new JLabel("EMPTY"));
                        table.getReference().useAbility(item, opponent);
                        if (hitpoints == opponent.getHitpoints())
                            JOptionPane.showMessageDialog(null, "Enemy dodged your attack or " +
                                    "has protection against your ability: " + item.getSpellName() + " !");
                        else
                            JOptionPane.showMessageDialog(null, "Enemy does not have protection for: "
                                    + item.getSpellName());
                    }
                    mana.setText("Your current mana: " + table.getReference().getCurrMana());
                    enemyHealth.setText("Enemy's current health: " + opponent.getHitpoints());
                } else
                    JOptionPane.showMessageDialog(null, "Action canceled!");
                abilities.clearSelection();
                ability.setEnabled(false);
                if (decision == 0) {
                    if (opponent.getHitpoints() <= 0) {
                        helpClose(board, table, mode);
                    } else
                        enemyTurn(opponent, table, panelMyPotion, panelAbilities, mode, infoPotion, infoAbility);
                }
            }
        });
        abilities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (abilities.isSelectionEmpty()) {
                    return;
                }
                ability.setEnabled(true);
            }
        });
        panelAbilities.setViewportView(abilities);
        if (modelAbilities.isEmpty())
            panelAbilities.setViewportView(new JLabel("EMPTY"));
        panelAbilities.setBounds(140, 110, 560, 100);
        infoAbility.setBounds(0, 110, 200, 90);
        abilities.setLayoutOrientation(JList.VERTICAL);
        abilities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attack.setBounds(295, 395, 200, 100);
        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int hitpoints;
                int decision = JOptionPane.showConfirmDialog(null, "Do you really want to " +
                        "use your basic attack against your enemy?", "Question", JOptionPane.YES_NO_OPTION);
                if (decision == 0) {
                    JOptionPane.showMessageDialog(null, "Attacking enemy with basic attack" +
                            "\n              Loading...");
                    hitpoints = opponent.getHitpoints();
                    int damage = table.getReference().getDamage();
                    JOptionPane.showMessageDialog(null, "You are dealing " + damage + " damage to your enemy!");
                    opponent.receiveDamage(damage);
                    if (hitpoints == ((Enemy) table.getCurrCell().getCellElement()).getHitpoints())
                        JOptionPane.showMessageDialog(null, "The enemy has dodged your attack!");
                } else
                    JOptionPane.showMessageDialog(null, "Action canceled!");
                enemyHealth.setText("Enemy's current health: " + opponent.getHitpoints());
                if (decision == 0) {
                    if (opponent.getHitpoints() <= 0) {
                        helpClose(board, table, mode);
                    } else
                        enemyTurn(opponent, table, panelMyPotion, panelAbilities, mode, infoPotion, infoAbility);
                }
            }
        });
        potion.setBounds(500, 395, 200, 100);
        potion.setEnabled(false);
        potion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Potion item = potions.getSelectedValue();
                int index = potions.getSelectedIndex();
                int decision = JOptionPane.showConfirmDialog(null, "Do you really want to " +
                        "use this potion to recover?", "Question", JOptionPane.YES_NO_OPTION);
                if (decision == 0) {
                    JOptionPane.showMessageDialog(null, "Using this potion " + item.getClass().getSimpleName() +
                            "\n              Loading...");
                    modelPotion.remove(index);
                    if (modelPotion.isEmpty())
                        panelMyPotion.setViewportView(new JLabel("EMPTY"));
                    JOptionPane.showMessageDialog(null, item.utilisePotion(table.getReference(), mode));
                    table.getReference().getInventory().removePotion(index);
                } else
                    JOptionPane.showMessageDialog(null, "Action canceled!");
                health.setText("Your current health: " + table.getReference().getHitpoints());
                mana.setText("Your current mana: " + table.getReference().getCurrMana());
                potions.clearSelection();
                potion.setEnabled(false);
                if (decision == 0)
                    enemyTurn(opponent, table, panelMyPotion, panelAbilities, mode, infoPotion, infoAbility);
            }
        });
        potions.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (potions.isSelectionEmpty())
                    return;
                potion.setEnabled(true);
            }
        });
        panelMyPotion.setViewportView(potions);
        if (modelPotion.isEmpty())
            panelMyPotion.setViewportView(new JLabel("EMPTY"));
        panelMyPotion.setBounds(140, 230, 560, 100);
        infoPotion.setBounds(0, 230, 200, 90);
        abilities.setLayoutOrientation(JList.VERTICAL);
        abilities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enemy.add(panelMyPotion);
        enemy.add(panelAbilities);
        enemy.add(panel);
        enemy.add(ability);
        enemy.add(attack);
        enemy.add(potion);
        enemy.add(enemyHealth);
        enemy.add(infoAbility);
        enemy.add(infoPotion);
        enemy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        enemy.setVisible(true);
    }

    /**
     * O metoda care se ocupa de alegerile inamicului
     */
    public void enemyTurn(Enemy opponent, Grid table, JScrollPane panelPotion, JScrollPane panelAbilities, String mode
            , JLabel infoPotion, JLabel infoAbilities) {
        JOptionPane.showMessageDialog(null, "       Enemy's turn!\n       He/She is attacking you!");
        SplittableRandom random = new SplittableRandom();
        int hitpoints;
        Spell spell;
        int damage;
        attack.setVisible(false);
        ability.setVisible(false);
        potion.setVisible(false);
        panelPotion.setVisible(false);
        panelAbilities.setVisible(false);
        infoAbilities.setVisible(false);
        infoPotion.setVisible(false);
        if (random.nextInt(1, 101) <= 75) {
            hitpoints = table.getReference().getHitpoints();
            damage = opponent.getDamage();
            JOptionPane.showMessageDialog(null, "Enemy decided to use basic attack! " +
                    "He/She is dealing " + damage + " damage");
            table.getReference().receiveDamage(damage);
            if (hitpoints - damage != table.getReference().getHitpoints())
                JOptionPane.showMessageDialog(null, "You succeeded to partially " +
                        "dodge the enemy's damage!");
            health.setText("Your current health: " + table.getReference().getHitpoints());
        } else {
            hitpoints = table.getReference().getHitpoints();
            JOptionPane.showMessageDialog(null, "Enemy decided to use an ability!");
            if (!opponent.getAbilities().isEmpty())
                spell = opponent.removeAbility(random.nextInt(opponent.getAbilities().size()), mode);
            else
                spell = opponent.removeAbility(0, mode);
            opponent.useAbility(spell, table.getReference());
            if (table.getReference().getHitpoints() == hitpoints &&
                    !HelperClass.testFight(table.getReference(), spell)) {
                damage = opponent.getDamage();
                JOptionPane.showMessageDialog(null, "Enemy wants to use basic attack\n" +
                        "He/She does not have enough mana or " +
                        "does not have abilities.\nHe/She is dealing " + damage + " damage");
                table.getReference().receiveDamage(damage);
            }
            if (HelperClass.testFight(table.getReference(), spell)) {
                JOptionPane.showMessageDialog(null, table.getReference().getClass().getSimpleName() +
                        " has protection" + " against: " + spell.getSpellName() + "\nYour hero does not take any damage!");
            } else if (spell != null) {
                JOptionPane.showMessageDialog(null, "Your hero does not have protection for: "
                        + spell.getSpellName());
                if (hitpoints - spell.getSpellDamage() != table.getReference().getHitpoints() &&
                        !HelperClass.testFight(table.getReference(), spell))
                    JOptionPane.showMessageDialog(null, "You succeeded to partially " +
                            "dodge the enemy's damage!");
            }
            health.setText("Your current health: " + table.getReference().getHitpoints());
        }
        if (table.getReference().getHitpoints() <= 0) {
            enemy.dispose();
            JOptionPane.showMessageDialog(null, "\t\t\t          Your hero died\n             Exiting...");
            GameOver.getInstance().endGame(table, mode);
        } else {
            attack.setVisible(true);
            ability.setVisible(true);
            potion.setVisible(true);
            panelPotion.setVisible(true);
            panelAbilities.setVisible(true);
            infoAbilities.setVisible(true);
            infoPotion.setVisible(true);
            JOptionPane.showMessageDialog(null, "Your turn!");
        }
    }

    /**
     * O metoda care reactiveaza tabla de joc in urma victoriei
     */
    public void helpClose(JFrame board, Grid table, String mode) {
        SplittableRandom random = new SplittableRandom();
        enemy.dispose();
        JOptionPane.showMessageDialog(null, "Well Played!\nYou defeated this enemy!");
        JOptionPane.showMessageDialog(null, table.getReference().
                increaseExp(random.nextInt(10, 30), mode));
        if (random.nextInt(1, 101) <= 80) {
            int gold = random.nextInt(100);
            JOptionPane.showMessageDialog(null, "\t\t\tYou also get some gold!: " + gold);
            table.getReference().getInventory().
                    setGold(table.getReference().getInventory().
                            getGold() + gold);
        }
        table.getReference().setDefeatedEnemy(table.getReference().getDefeatedEnemy() + 1);
        board.setVisible(true);
        JOptionPane.showMessageDialog(null, "You got experience after this fight\n" +
                "Time to continue your journey...");
    }

}
