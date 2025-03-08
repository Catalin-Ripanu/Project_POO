import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * O clasa ce afiseaza pagina cu lista de personaje din contul ales prin intermediul interfetei grafice
 * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class AccountCharacters {
    private final JFrame frame;
    private JList<Character> list;
    private final JLabel name;
    private final JTextField heroName;
    private final JLabel level;
    private final JTextField heroLevel;
    private final JLabel exp;
    private final JTextField heroExp;
    private final JLabel profession;
    private final JTextField heroProfession;
    private final JButton create;
    private final JButton start;
    private final DefaultListModel<Character> characters;
    private static AccountCharacters instance = null;

    private AccountCharacters() {
        frame = new JFrame("Account's characters");
        heroName = new JTextField(15);
        name = new JLabel("Name");
        heroExp = new JTextField(15);
        exp = new JLabel("Experience (Positive integer)");
        heroLevel = new JTextField(15);
        level = new JLabel("Current Level (Positive integer)");
        heroProfession = new JTextField(15);
        profession = new JLabel("Class (Warrior/Mage/Rogue)");
        create = new JButton("Add to my list of heroes");
        start = new JButton("Start your journey");
        characters = new DefaultListModel<>();
    }

    public static AccountCharacters getInstance() {
        if (instance == null)
            return new AccountCharacters();
        return instance;
    }

    /**
    * O metoda ce se ocupa de pagina cu lista respectiva
    */
    public void showList(Account account, Grid table, Map<Cell.TypeCell, List<String>> stories, String mode) {
        JScrollPane scroll = new JScrollPane();
        JPanel panelCreate = new JPanel(new GridLayout(0, 2));
        List<JTextField> listText = new ArrayList<>();
        JLabel select = new JLabel("Select your character!");
        JLabel or = new JLabel("OR");
        JLabel get = new JLabel("Create a character!");
        JLabel fun = new JLabel("Have Fun!");
        JLabel luck = new JLabel("Good Luck!");
        frame.setLocation(350, 85);
        HelperClass.createLabels(select, 25, Color.red);
        select.setBounds(60, 30, 250, 30);
        HelperClass.createLabels(get, 25, Color.red);
        get.setBounds(465, 30, 250, 30);
        HelperClass.createLabels(luck, 30, Color.orange);
        luck.setBounds(300, 200, 200, 100);
        HelperClass.createLabels(fun, 30, Color.green);
        fun.setBounds(325, 450, 200, 100);
        HelperClass.createLabels(or, 25, Color.red);
        or.setBounds(370, 30, 250, 30);
        listText.add(heroName);
        listText.add(heroExp);
        listText.add(heroLevel);
        listText.add(heroProfession);
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                boolean enable = true;
                for (JTextField text : listText)
                    if (text.getText().isEmpty()) {
                        enable = false;
                    }
                create.setEnabled(enable);
            }
        };
        for (JTextField text : listText)
            text.getDocument().addDocumentListener(listener);
        panelCreate.setBackground(Color.black);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(null);
        characters.addAll(account.getCharacters());
        list = new JList<>(characters);
        panelCreate.setBounds(400, 100, 385, 100);
        HelperClass.createButtons(create, 20);
        create.setBounds(400, 350, 250, 100);
        create.setEnabled(false);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = heroProfession.getText();
                if (string.equals("Warrior") || string.equals("Rogue") || string.equals("Mage")) {
                    if(HelperClass.isNumber(heroExp.getText()) && HelperClass.isNumber(heroLevel.getText())) {
                        Character character;
                        account.addCharacterFactory(heroProfession.getText(), heroName.getText(),
                                Integer.parseInt(heroExp.getText()), Integer.parseInt(heroLevel.getText()));
                        try {
                            WriteFile.createCharacter("src/IN_file/login.json", account,
                                    account.getCharacters().get(account.getCharacters().size() - 1));
                        }
                        catch (Exception error)
                        {
                            error.printStackTrace();
                        }
                        characters.addElement(account.getCharacters().get(account.getCharacters().size() - 1));
                        scroll.setViewportView(list);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Type positive integers!");
                } else
                    JOptionPane.showMessageDialog(null, "Wrong class\nType Warrior/Mage/Rogue!");
                heroName.setText("");
                heroProfession.setText("");
                heroLevel.setText("");
                heroExp.setText("");
            }
        });
        HelperClass.createButtons(start, 20);
        start.setBounds(125, 350, 250, 100);
        start.setEnabled(false);
        HelperClass.createLabels(name, 15, Color.white);
        HelperClass.createLabels(level, 15, Color.white);
        HelperClass.createLabels(exp, 15, Color.white);
        HelperClass.createLabels(profession, 15, Color.white);
        panelCreate.add(name);
        panelCreate.add(heroName);
        panelCreate.add(level);
        panelCreate.add(heroLevel);
        panelCreate.add(exp);
        panelCreate.add(heroExp);
        panelCreate.add(profession);
        panelCreate.add(heroProfession);
        scroll.setViewportView(list);
        if (characters.isEmpty())
            scroll.setViewportView(new JLabel("EMPTY"));
        list.setForeground(Color.red);
        list.setBackground(Color.cyan);
        scroll.setBounds(0, 100, 350, 100);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (list.isSelectionEmpty()) {
                    return;
                }
                start.setEnabled(true);
            }
        });
        frame.add(scroll);
        frame.add(panelCreate);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setReference(list.getSelectedValue());
                GameBoard play = GameBoard.getInstance();
                frame.dispose();
                play.startBoard(table, stories, mode);
                JOptionPane.showMessageDialog(null, HelperClass.showInstructions(mode));
            }
        });
        frame.add(start);
        frame.add(create);
        frame.add(select);
        frame.add(get);
        frame.add(or);
        frame.add(fun);
        frame.add(luck);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}