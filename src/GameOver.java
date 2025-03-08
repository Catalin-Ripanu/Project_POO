import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * O clasa care afiseaza pagina finala
 * Aceasta pagina este afisata atunci cand personajul gaseste celula finala sau atunci cand moare
 * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class GameOver {
    private final JFrame over;
    private final JLabel message;
    private final JLabel info;
    private final JButton close;
    private static GameOver instance = null;

    private GameOver() {
        over = new JFrame("Game Over");
        message = new JLabel("Game Over!");
        info = new JLabel();
        close = new JButton("Exit");
    }

    public static GameOver getInstance() {
        if (instance == null)
            return new GameOver();
        return instance;
    }

    /**
    * O metoda ce se ocupa de pagina finala
    */
    public void endGame(Grid table, String mode) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Click to see your stats =>");
        panel.setBackground(Color.black);
        over.setSize(800, 600);
        over.getContentPane().setBackground(Color.black);
        over.setLocation(350, 85);
        over.setLayout(null);
        message.setForeground(Color.white);
        message.setFont(new Font("Times New Roman", Font.PLAIN, 90));
        label.setForeground(Color.red);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        info.setForeground(Color.white);
        info.setFont(new Font("Times New Roman", Font.PLAIN, 45));
        close.setBackground(Color.black);
        close.setForeground(Color.RED);
        close.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        label.setBounds(125, 400, 350, 100);
        close.setBounds(450, 400, 200, 100);
        panel.setBounds(100, 40, 600, 250);
        panel.add(message);
        panel.add(info);
        if (table.getReference().getHitpoints() > 0)
            info.setText("<html>Well Played!<br/>You won this game!</html>");
        else
            info.setText("<html>Your hero died!<br/>Be careful next time!");
        over.getContentPane().add(panel);
        over.add(close);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, HelperClass.showStats(table, mode));
                over.dispose();
            }
        });
        over.add(label);
        over.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        over.setVisible(true);
    }
}
