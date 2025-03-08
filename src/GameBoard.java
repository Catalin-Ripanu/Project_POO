import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * O clasa ce se ocupa cu toata logica jocului in interfata grafica
 * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class GameBoard {
    private final JFrame board;
    private final List<List<JLabel>> labels;
    private final JButton north;
    private final JButton south;
    private final JButton east;
    private final JButton west;
    private final JLabel health;
    private final JLabel mana;
    private final JLabel experience;
    private final JLabel level;
    private final JLabel story;
    private final JLabel abilities;
    private final JLabel potions;
    private static GameBoard instance = null;

    private GameBoard() {
        board = new JFrame("Adventure Game");
        labels = new ArrayList<>();
        north = new JButton("North");
        south = new JButton("South");
        east = new JButton("East");
        west = new JButton("West");
        health = new JLabel();
        mana = new JLabel();
        level = new JLabel();
        experience = new JLabel();
        potions = new JLabel();
        abilities = new JLabel();
        story = new JLabel("This is the place where you start your journey.Be careful at monsters!");
    }

    public static GameBoard getInstance() {
        if (instance == null)
            return new GameBoard();
        return instance;
    }

    /**
     * O metoda care porneste tabla de joc in GUI
     */
    public void startBoard(Grid table, Map<Cell.TypeCell, List<String>> stories, String mode) {
        JPanel panelLabels = new JPanel(new GridLayout(3, 2, 0, 0));
        JPanel panelButtons = new JPanel();
        JPanel panelMap = new JPanel(new GridLayout(table.getHeight(), table.getWidth(),0,1));
        JLabel information = new JLabel("Your options will be displayed here!");
        JLabel player = new JLabel();
        player.setIcon(new ImageIcon("src/Images/images.jpg"));
        board.setLocation(150, 85);
        board.setLayout(null);
        board.setSize(1300, 600);
        board.setFocusable(true);
        board.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_R: {
                        JOptionPane.showMessageDialog(null, HelperClass.showInstructions(mode));
                    }
                    break;
                    case KeyEvent.VK_U: {
                        JOptionPane.showMessageDialog(null, table.getReference().showAbility(mode));
                    }
                    break;
                    case KeyEvent.VK_I: {
                        JOptionPane.showMessageDialog(null, HelperClass.importantStats(table, mode));
                        JOptionPane.showMessageDialog(null, HelperClass.showStats(table, mode));
                    }
                    break;
                    case KeyEvent.VK_O: {
                        JOptionPane.showMessageDialog(null, HelperClass.profession(table, mode));
                    }
                    break;
                    case KeyEvent.VK_H: {
                        JOptionPane.showMessageDialog(null, "Exiting game..");
                        board.dispose();
                    }
                    break;
                    case KeyEvent.VK_Y: {
                        JOptionPane.showMessageDialog(null, HelperClass.showGoldWeight(table, mode));
                    }
                    break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        board.getContentPane().setBackground(Color.black);
        HelperClass.createButtons(north, 20);
        north.setFocusable(false);
        north.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.goNorth(mode);
                prepareMap(table, stories, information, mode);
            }
        });
        HelperClass.createButtons(south, 20);
        south.setFocusable(false);
        south.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.goSouth(mode);
                prepareMap(table, stories, information, mode);
            }
        });
        HelperClass.createButtons(west, 20);
        west.setFocusable(false);
        west.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.goWest(mode);
                prepareMap(table, stories, information, mode);
            }
        });
        HelperClass.createButtons(east, 20);
        east.setFocusable(false);
        east.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.goEast(mode);
                prepareMap(table, stories, information, mode);
            }
        });
        createLabels(table);
        constructTable(table);
        createMapPanel(panelMap, table, labels);
        panelButtons.add(north, BorderLayout.NORTH);
        panelButtons.add(south, BorderLayout.SOUTH);
        panelButtons.add(east, BorderLayout.WEST);
        panelButtons.add(west, BorderLayout.EAST);
        board.add(panelMap);
        panelButtons.setBounds(475, 500, 350, 50);
        panelMap.setBounds(400, 80, 880, 400);
        player.setBounds(50, 250, 400, 300);
        panelMap.setBackground(Color.black);
        HelperClass.createLabels(story, 16, Color.red);
        HelperClass.createLabels(information, 17, Color.orange);
        story.setBounds(250, 0, 950, 80);
        information.setBounds(10, 100, 400, 100);
        board.add(information);
        board.add(story);
        board.add(panelButtons);
        HelperClass.createLabels(experience, 20, Color.cyan);
        experience.setText("Experience: " + table.getReference().getCurrExp());
        HelperClass.createLabels(mana, 20, Color.cyan);
        mana.setText("Mana: " + table.getReference().getCurrMana());
        HelperClass.createLabels(level, 20, Color.cyan);
        level.setText("Level: " + table.getReference().getCurrLevel());
        HelperClass.createLabels(health, 20, Color.cyan);
        health.setText("Health: " + table.getReference().getHitpoints());
        HelperClass.createLabels(abilities, 20, Color.cyan);
        abilities.setText("Abilities: " + table.getReference().getAbilities().size());
        HelperClass.createLabels(potions, 20, Color.cyan);
        potions.setText("Potions: " + table.getReference().getInventory().getPotions().size());
        panelLabels.add(health);
        panelLabels.add(experience);
        panelLabels.add(mana);
        panelLabels.add(level);
        panelLabels.add(abilities);
        panelLabels.add(potions);
        panelButtons.setBackground(Color.black);
        panelLabels.setBackground(Color.black);
        panelLabels.setBounds(0, 0, 250, 80);
        board.add(panelLabels);
        board.add(player);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setVisible(true);
    }

    /**
     * O metoda care actualizeaza tabla de joc
     */
    public void constructTable(Grid table) {
        for (int i = 0; i < table.getHeight(); i++) {
            for (int j = 0; j < table.getWidth(); j++) {
                int value;
                if (table.get(i).get(j).isVisited())
                    value = 1;
                else
                    value = 0;
                switch (value) {
                    case 1: {
                        if (i == table.getCurrCell().getPositionOx() && j == table.getCurrCell().getPositionOy()) {
                            if (table.get(i).get(j).getCellElement().toCharacter() == 'F')
                                labels.get(i).get(j).setIcon(new ImageIcon("src/Images/finish.jpg"));
                            else
                                labels.get(i).get(j).setIcon(new ImageIcon("src/Images/player.jpg"));
                        } else {
                            switch (table.get(i).get(j).getCellElement().toCharacter()) {
                                case 'S': {
                                    labels.get(i).get(j).setIcon(null);
                                    labels.get(i).get(j).setIcon(new ImageIcon("src/Images/shop.jpg"));
                                }
                                break;
                                case 'E':
                                    labels.get(i).get(j).setIcon(new ImageIcon("src/Images/enemy.jpg"));
                                    break;
                                case 'N':
                                    labels.get(i).get(j).setIcon(new ImageIcon("src/Images/empty.jpg"));
                                    break;
                            }
                        }
                    }
                    break;
                    case 0:
                        labels.get(i).get(j).setIcon(new ImageIcon("src/Images/question.jpg"));
                        break;
                }
            }
        }
    }

    /**
     * O metoda care afiseaza o poveste noua folosind o eticheta
     */
    public String visitStory(Grid table, Map<Cell.TypeCell, List<String>> stories) {
        if (!table.getCurrCell().isVisited()) {
            table.getCurrCell().setVisited(true);
            return obtainStory(table.getCurrCell().getType(), stories);
        }
        return "";
    }

    /**
     * O metoda care returneaza o poveste din dictionar
     */
    public String obtainStory(Cell.TypeCell type, Map<Cell.TypeCell, List<String>> stories) {
        SplittableRandom random = new SplittableRandom();
        return stories.get(type).get(random.nextInt(stories.get(type).size()));
    }

    /**
     * Metode care ajuta la formarea unei table de joc
     */
    public void createLabels(Grid table) {
        for (int i = 0; i < table.getHeight(); i++) {
            List<JLabel> line = new ArrayList<>();
            for (int j = 0; j < table.getWidth(); j++) {
                JLabel element = new JLabel();
                HelperClass.createLabels(element, 30, Color.green);
                line.add(element);
            }
            labels.add(line);
        }
    }

    public void createMapPanel(JPanel panel, Grid table, List<List<JLabel>> labels) {
        for (int i = 0; i < table.getHeight(); i++)
            for (int j = 0; j < table.getWidth(); j++)
                panel.add(labels.get(i).get(j));
    }

    /**
     * O metoda care pregateste tabla/urmatorul eveniment in functie de celula curenta
     */
    public void prepareMap(Grid table, Map<Cell.TypeCell, List<String>> stories, JLabel information, String mode) {
        story.setText(visitStory(table, stories));
        constructTable(table);
        switch (table.getCurrCell().getType()) {
            case EMPTY:
                information.setText("<html>This is an empty cell.<br/>You can move!<br/>" +
                        "Remember to press R if you want to get information!</html>");
                break;
            case SHOP: {
                information.setText("<html>This is a cell with a shop.<br/>You can buy some potions here.<br/>" +
                        "You can use them to help you against enemies!</html>");
                int decision;
                JOptionPane.showMessageDialog(null, "You are in a a shop!");
                decision = JOptionPane.showConfirmDialog(null, "Do you want to buy something?",
                        "Question", JOptionPane.YES_NO_OPTION);
                if (decision == 0) {
                    board.dispose();
                    JOptionPane.showMessageDialog(null, "Entering shop...");
                    ShopBoard.getInstance().goShop(table, board);
                }
                story.setText("");
            }
            break;
            case ENEMY: {
                information.setText("<html>This is a cell with an enemy.<br/>You must fight him/her!<br/>" +
                        "You can use your potions to help you!</html>");
                if (((Enemy) table.getCurrCell().getCellElement()).getHitpoints() > 0) {
                    board.dispose();
                    JOptionPane.showMessageDialog(null, "Enemy is approaching.\n" +
                            "He/She wants your money!");
                    EnemyBoard.getInstance().fightEnemy(table, board, mode);
                } else
                    JOptionPane.showMessageDialog(null, "This enemy is defeated.\nNothing to be done here");
            }
            break;
            case FINISH: {
                information.setText("<html>This is the final cell.<br/>Well done!<br/>" +
                        "I hope that you enjoyed this game!</html>");
                JOptionPane.showMessageDialog(null, """
                        Well done!
                        You found the final cell.""");
                board.dispose();
                GameOver.getInstance().endGame(table, mode);
            }
            break;
        }
        updateLabels(table);
    }

    /**
     * O metoda care actualizeaza etichetele dupa un eveniment
     */
    public void updateLabels(Grid table) {
        experience.setText("Experience: " + table.getReference().getCurrExp());
        mana.setText("Mana: " + table.getReference().getCurrMana());
        level.setText("Level: " + table.getReference().getCurrLevel());
        health.setText("Health: " + table.getReference().getHitpoints());
        abilities.setText("Abilities: " + table.getReference().getAbilities().size());
        potions.setText("Potions: " + table.getReference().getInventory().getPotions().size());
    }
}
