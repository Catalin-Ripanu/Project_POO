import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 /**
 * O clasa ce se ocupa de autentificarea uilizatorului prin intermediul interfetei grafice
  * 'Singleton' intrucat este nevoie de o singura instanta
 */
public class LoginForm {
    private final JPasswordField passRegister;
    private final JTextField emailRegister;
    private final JTextField gameRegister;
    private final JTextField countryRegister;
    private final JTextField nameRegister;
    private final JFrame login;
    private final JButton buttonLogin;
    private final JButton buttonRegister;
    private final JLabel eml;
    private final JLabel pass;
    private final JPanel panel;
    private final JTextField email;
    private final JPasswordField password;
    private static LoginForm instance = null;

    private LoginForm() {
        login = new JFrame("Login Form");
        panel = new JPanel();
        email = new JTextField(15);
        password = new JPasswordField(15);
        buttonLogin = new JButton("Login");
        eml = new JLabel("Email");
        pass = new JLabel("Password");
        buttonRegister = new JButton("Register");
        passRegister = new JPasswordField(15);
        emailRegister = new JTextField(15);
        gameRegister = new JTextField(15);
        countryRegister = new JTextField(15);
        nameRegister = new JTextField(15);

    }

    public static LoginForm getInstance() {
        if (instance == null)
            return new LoginForm();
        return instance;
    }

      /**
      * O metoda ce se ocupa cu afisarea paginii de autentificare
      */
    public void createLogin(List<Account> accounts, Grid table, Map<Cell.TypeCell, List<String>> stories, String mode) {
        JLabel optionLogin = new JLabel("Login Here!");
        JLabel optionRegister = new JLabel("Register here!");
        JLabel nameLabel = new JLabel("Name");
        JLabel countryLabel = new JLabel("Country");
        JLabel gamesLabel = new JLabel("Favorite Game");
        JLabel image=new JLabel();
        JLabel emailLabel = new JLabel("Email");
        JLabel passLabel = new JLabel("Password");
        login.setSize(800, 600);
        login.setLocation(350, 85);
        panel.setLayout(null);
        image.setIcon(new ImageIcon("src/Images/download.jpg"));
        image.setBounds(75,300,500,200);
        optionLogin.setForeground(Color.red);
        optionLogin.setBounds(125, 60, 200, 40);
        optionLogin.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        optionRegister.setForeground(Color.red);
        optionRegister.setBounds(460, 60, 200, 40);
        optionRegister.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        eml.setBounds(100, 150, 70, 20);
        eml.setForeground(Color.red);
        panel.add(eml);
        panel.add(email);
        panel.add(image);
        emailLabel.setBounds(450, 150, 70, 20);
        emailLabel.setForeground(Color.red);
        panel.add(emailLabel);
        panel.add(emailRegister);
        pass.setBounds(100, 197, 70, 20);
        pass.setForeground(Color.red);
        panel.add(pass);
        panel.add(password);
        passLabel.setBounds(450, 197, 70, 20);
        passLabel.setForeground(Color.red);
        panel.add(passLabel);
        panel.add(passRegister);
        nameLabel.setBounds(450, 245, 70, 20);
        nameLabel.setForeground(Color.red);
        panel.add(nameLabel);
        panel.add(nameRegister);
        countryLabel.setBounds(450, 290, 70, 20);
        countryLabel.setForeground(Color.red);
        panel.add(countryLabel);
        panel.add(countryRegister);
        gamesLabel.setBounds(450, 321, 100, 50);
        gamesLabel.setForeground(Color.red);
        panel.add(gamesLabel);
        panel.add(gameRegister);
        email.setBounds(100, 169, 193, 28);
        password.setBounds(100, 217, 193, 28);
        emailRegister.setBounds(450, 169, 193, 28);
        passRegister.setBounds(450, 217, 193, 28);
        nameRegister.setBounds(450, 265, 193, 28);
        countryRegister.setBounds(450, 310, 193, 28);
        gameRegister.setBounds(450, 357, 193, 28);
        buttonLogin.setBounds(100, 252, 193, 25);
        buttonRegister.setBounds(450, 390, 193, 25);
        buttonLogin.setForeground(Color.red);
        buttonLogin.setBackground(Color.black);
        buttonRegister.setForeground(Color.red);
        buttonRegister.setBackground(Color.black);
        panel.add(buttonLogin);
        panel.add(optionLogin);
        panel.add(optionRegister);
        panel.add(buttonRegister);
        login.getContentPane().add(panel);
        panel.setBackground(Color.black);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setVisible(true);
        actionLog(accounts, table, stories, mode);
    }

    /**
    * O metoda ajutatoare care se ocupa de actiunile butoanelor
    */
    public void actionLog(List<Account> accounts, Grid table, Map<Cell.TypeCell, List<String>> stories, String mode) {
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean protect = false;
                int cont = 0;
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getInformation().getCredentials().
                            getEmail().equals(email.getText()) &&
                            accounts.get(i).getInformation().getCredentials().
                                    getPassword().equals(String.valueOf(password.getPassword()))) {
                        protect = true;
                        cont = i;
                        break;
                    }
                }
                if (protect) {
                    AccountCharacters.getInstance().showList(accounts.get(cont), table, stories, mode);
                    login.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Password/Email\nCheck the JSON File!");
                    email.setText("");
                    password.setText("");
                    email.requestFocus();
                }
            }
        });
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exists;
                Account account=new Account();
                List<String> favorite=new ArrayList<>();
                favorite.add(gameRegister.getText());
                Credentials data=new Credentials(emailRegister.getText(),String.valueOf(passRegister.getPassword()));
                try {
                    Account.Information information=new Account.Information.InformationBuilder(data)
                            .name(nameRegister.getText())
                            .country(countryRegister.getText())
                            .favoriteGames(favorite)
                            .build();
                    account.setInformation(information);
                    account.setMapsCompleted(0);
                    exists=WriteFile.createAccount("src/IN_file/login.json",account);
                    if(exists)
                    {
                        emailRegister.setText("");
                        nameRegister.setText("");
                        passRegister.setText("");
                        countryRegister.setText("");
                        gameRegister.setText("");
                    }
                    else {
                        AccountCharacters.getInstance().showList(account, table, stories, mode);
                        login.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
