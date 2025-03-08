import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * O clasa ce se ocupa de modelarea magazinului prin intermediul interfetei grafice
 * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class ShopBoard {
    private final JFrame shop;
    private final JLabel gold;
    private final JLabel weight;
    private JList<Potion> potions;
    private JList<Potion> myPotion;
    private final DefaultListModel<Potion> modelPotion;
    private final DefaultListModel<Potion> modelMyPotion;
    private final JButton buy;
    private final JButton remove;
    private final JButton exit;
    private static ShopBoard instance = null;

    private ShopBoard() {
        shop = new JFrame("Shop");
        gold = new JLabel();
        weight = new JLabel();
        modelPotion = new DefaultListModel<>();
        modelMyPotion = new DefaultListModel<>();
        buy = new JButton("Buy this potion");
        remove = new JButton("Remove from inventory");
        exit = new JButton("Exit from this shop");
    }

    public static ShopBoard getInstance() {
        if (instance == null)
            return new ShopBoard();
        return instance;
    }

    /**
     * O metoda ce se ocupa de afisarea magazinului
     */
    public void goShop(Grid table, JFrame board) {
        JScrollPane panelPotion = new JScrollPane();
        JLabel labelShop = new JLabel("Potions from shop =>");
        JLabel labelInventory = new JLabel("Potions from inventory =>");
        JLabel info = new JLabel("Select a potion from a list to make an action!");
        JScrollPane panelMyPotion = new JScrollPane();
        shop.setLocation(350, 85);
        HelperClass.createLabels(gold, 16, Color.blue);
        HelperClass.createLabels(weight, 16, Color.blue);
        HelperClass.createLabels(labelInventory, 15, Color.red);
        HelperClass.createLabels(labelShop, 15, Color.red);
        HelperClass.createLabels(info, 17, Color.green);
        info.setBounds(250, 300, 350, 90);
        labelShop.setBounds(0, 50, 200, 90);
        labelInventory.setBounds(0, 200, 200, 90);
        gold.setBounds(0, 400, 250, 30);
        gold.setText("Your current gold: " + table.getReference().getInventory().getGold());
        weight.setBounds(0, 450, 250, 30);
        weight.setText("Your remaining weight: " + table.getReference().getInventory().calculateWeight());
        shop.setSize(800, 600);
        shop.getContentPane().setBackground(Color.black);
        shop.setLayout(null);
        modelPotion.addAll(((Shop) table.getCurrCell().getCellElement()).getPotions());
        modelMyPotion.addAll(table.getReference().getInventory().getPotions());
        potions = new JList<>(modelPotion);
        potions.setForeground(Color.cyan);
        potions.setBackground(Color.blue);
        myPotion = new JList<>(modelMyPotion);
        myPotion.setBackground(Color.orange);
        myPotion.setForeground(Color.magenta);
        HelperClass.createButtons(buy, 15);
        HelperClass.createButtons(remove, 15);
        HelperClass.createButtons(exit, 15);
        remove.setBounds(185, 385, 200, 100);
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Potion item = myPotion.getSelectedValue();
                int index = myPotion.getSelectedIndex();
                int decision = JOptionPane.showConfirmDialog(null, "Do you really want to " +
                                "remove this potion: " + item.getClass().getSimpleName() + " from your inventory?", "Question"
                        , JOptionPane.YES_NO_OPTION);
                if (decision == 0) {
                    JOptionPane.showMessageDialog(null, "The selected potion will be removed!");
                    modelMyPotion.remove(index);
                    table.getReference().getInventory().getPotions().remove(index);
                    weight.setText("Your remaining weight: " + table.getReference().getInventory().calculateWeight());
                } else
                    JOptionPane.showMessageDialog(null, "Action canceled!");
                myPotion.clearSelection();
                remove.setEnabled(false);
            }
        });
        myPotion.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (myPotion.isSelectionEmpty()) {
                    return;
                }
                remove.setEnabled(true);
            }
        });
        panelMyPotion.setViewportView(myPotion);
        panelMyPotion.setBounds(175, 200, 600, 100);
        myPotion.setLayoutOrientation(JList.VERTICAL);
        myPotion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buy.setBounds(385, 385, 200, 100);
        buy.setEnabled(false);
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Potion item = potions.getSelectedValue();
                int decision = JOptionPane.showConfirmDialog(null, "Do you really want to " +
                                "buy this potion: " + item.getClass().getSimpleName() + " from this shop?", "Question"
                        , JOptionPane.YES_NO_OPTION);
                switch (decision) {
                    case 0: {
                        if (table.getReference().getInventory().getGold() < item.getPrice())
                            JOptionPane.showMessageDialog(null, "You do not have enough gold!");
                        if (item.getWeight() > table.getReference().getInventory().calculateWeight())
                            JOptionPane.showMessageDialog(null, "You do not have enough space in inventory!");

                        if (table.getReference().getInventory().getGold() >= item.getPrice() &&
                                table.getReference().getInventory().calculateWeight() >= item.getWeight()) {
                            JOptionPane.showMessageDialog(null, "Buying in progress...");
                            modelMyPotion.addElement(item);
                            table.getReference().getInventory().getPotions().add(item);
                            table.getReference().getInventory().setGold(table.getReference().getInventory().getGold() - item.getPrice());
                            ((Shop) table.getCurrCell().getCellElement()).getPotions().remove(potions.getSelectedIndex());
                            modelPotion.remove(potions.getSelectedIndex());
                            weight.setText("Your remaining weight: " + table.getReference().getInventory().calculateWeight());
                            gold.setText("Your current gold: " + table.getReference().getInventory().getGold());
                            if (modelPotion.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "This shop does not " +
                                        "have potions anymore (Sold out) !");
                                helpClose(board);
                            }
                        }
                    }
                    break;
                    default:
                        JOptionPane.showMessageDialog(null, "Canceled action..\nTry to buy a different potion");
                }
                potions.clearSelection();
                buy.setEnabled(false);
            }
        });
        potions.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (potions.isSelectionEmpty()) {
                    return;
                }
                buy.setEnabled(true);
            }
        });
        panelPotion.setViewportView(potions);
        panelPotion.setBounds(175, 50, 600, 100);
        potions.setLayoutOrientation(JList.VERTICAL);
        potions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exit.setBounds(585, 385, 200, 100);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpClose(board);
            }
        });
        shop.add(labelInventory);
        shop.add(labelShop);
        shop.add(panelMyPotion);
        shop.add(panelPotion);
        shop.add(buy);
        shop.add(remove);
        shop.add(weight);
        shop.add(gold);
        shop.add(exit);
        shop.add(info);
        shop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shop.setVisible(true);
        if (modelPotion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "This shop does not " +
                    "have potions anymore (Sold out) !");
            helpClose(board);
        }
    }

    /**
     * O metoda care incheie interactiunea cu magazinul
     * De asemenea,activeaza tabla de joc
     */
    public void helpClose(JFrame board) {
        JOptionPane.showMessageDialog(null, "Exiting shop..\nSee you next time!");
        shop.dispose();
        board.setVisible(true);
        JOptionPane.showMessageDialog(null, "You visited this shop.\nTime to continue your journey...");
    }
}
