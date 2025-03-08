import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SplittableRandom;

/**
 * O clasa care contine metoda 'main' (entry-point-ul aplicatiei)
 * Se va afisa o pagina ce permite utilizatorului sa aleaga
 */
public class Test {
    private final JFrame game;
    private final JLabel welcomeLabel;
    private final JButton textMode;
    private final JButton guiMode;
    private final JPanel panelTitle;
    private final JLabel info;

    /**
     * Metode ce se ocupa de afisarea primei pagini
     */
    public void createPanelTitle() {
        panelTitle.setBounds(100, 100, 600, 200);
        panelTitle.setBackground(Color.black);
    }

    public void addLabels() {
        panelTitle.add(welcomeLabel);
        panelTitle.add(info);
    }

    public void createWelcomeLabel() {
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 90));
    }

    public void createInfoLabel() {
        info.setForeground(Color.white);
        info.setFont(new Font("Times New Roman", Font.PLAIN, 45));
    }

    public Test() {
        game = new JFrame("Text Adventure Game");
        welcomeLabel = new JLabel("WELCOME");
        textMode = new JButton("Text Mode");
        guiMode = new JButton("GUI Mode");
        panelTitle = new JPanel();
        info = new JLabel("Select your preferred mode");
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.activateGame();
    }

    /**
     * O metoda ce porneste modul de joc ales de utilizator (cu ajutorul altor metode)
     */
    public void activateGame() {
        Game adventure = Game.getInstance();
        SplittableRandom random = new SplittableRandom();
        game.setSize(800, 600);
        game.getContentPane().setBackground(Color.black);
        game.setLocation(350, 85);
        game.setLayout(null);
        createWelcomeLabel();
        createInfoLabel();
        HelperClass.createButtons(textMode, 28);
        textMode.setBounds(400, 400, 200, 100);
        HelperClass.createButtons(guiMode, 28);
        guiMode.setBounds(175, 400, 200, 100);
        createPanelTitle();
        addLabels();
        game.getContentPane().add(panelTitle);
        game.getContentPane().add(textMode);
        game.getContentPane().add(guiMode);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        textMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.dispose();
                int hardcoded = JOptionPane.showConfirmDialog(null,
                        "Do you want to activate hardcoded mode?", "Question", JOptionPane.YES_NO_OPTION);
                JOptionPane.showMessageDialog(null, "Open your terminal for playing this game!");
                if (hardcoded == 0) {
                    JOptionPane.showMessageDialog(null, HelperClass.hardMessage());
                    adventure.run(Grid.generateTestMap(), "1", String.valueOf(1));
                } else {
                    adventure.run(Grid.generateMap(random.nextInt(10, 15), random.nextInt(5, 15)),
                            "1", String.valueOf(0));
                }
            }
        });
        guiMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.dispose();
                adventure.run(Grid.generateMap(random.nextInt(8, 15), random.nextInt(5, 15)),
                        "2", "0");
            }
        });
    }
}
