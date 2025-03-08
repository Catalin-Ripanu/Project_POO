import Exceptions.InvalidCommandException;
import java.util.*;

/**
 * Aceasta clasa modeleaza tot jocul cu ajutorul unor metode specializate
 * Metoda 'run' se ocupa cu modul de joc
 * S-a folosit 'Singleton' intrucat este nevoie de o singura instanta
 */
public class Game {
    private final List<Account> accounts;
    private final Map<Cell.TypeCell, List<String>> stories;
    private static Game instance = null;

    private Game() {
        accounts = new ArrayList<>();
        stories = new TreeMap<>();
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /**
     * O metoda ce extrage informatiile din fisierele de intrare si care porneste un mod de joc
     * Modul de joc este ales in functie de parametrul 'mode'
     */
    public void run(Grid table, String mode, String hardcoded) {
        try {
            ReadFile reader = ReadFile.getInstance();
            reader.readInputAccounts("src/IN_file/login.json", accounts);
            reader.readInputStories("src/IN_file/stories.json", stories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (mode) {
            case "1":
                textModeGame(table, mode, hardcoded);
                break;
            case "2": {
                LoginForm.getInstance().createLogin(accounts, table, stories, mode);
            }
            break;
        }
    }

    /**
     * O metoda ce se ocupa de afisarea conturilor/personajelor
     * De asemenea,aceasta metoda afiseaza jocul in terminal daca s-a ales contul/personajul
     */
    public void textModeGame(Grid table, String mode, String hardcoded) {
        Scanner scanner = new Scanner(System.in);
        String value;
        int counter = 0;
        int clean = 100;
        try {
            cleanTerminal(clean);
            System.out.println("\t\t\t------Displaying accounts------");
            HelperClass.showInformation(counter, accounts, hardcoded);
            if (hardcoded.equals("1")) {
                start(scanner, hardcoded, table, clean, mode);
                value = "1";
            } else
                value = scanner.nextLine();
            while (!value.equals("H")) {
                switch (value) {
                    case "1": {
                        /*
                         * Aceasta parte se ocupa de autentificare
                         */
                        testPassword(scanner, counter, hardcoded);
                        cleanTerminal(clean);
                        System.out.println("\t\t\t\t\t\t\t------Password accepted!------");
                            value = accepted(counter, scanner,hardcoded,table,clean,mode);
                            /*
                             * Aceasta parte se ocupa de alegerea/crearea unui caracter
                             */
                            while (value.equals("R")) {
                                createCharacter(accounts.get(counter), scanner, clean);
                                value = accepted(counter, scanner,hardcoded,table,clean,mode);
                            }
                        if (value.equals("E")) {
                            int size = accounts.get(counter).getCharacters().size() - 1;
                            if (!hardcoded.equals("1")) {
                                System.out.println("\t\t\t------Type a number from this set {0,.."
                                        + size + "}" + " to choose a character------");
                                value = scanner.nextLine();
                            } else {
                                value = "0";
                            }
                            if (Integer.parseInt(value) < 0 || Integer.parseInt(value) > size)
                                throw new InvalidCommandException();
                            table.setReference(accounts.get(counter).getCharacters().get(Integer.parseInt(value)));
                            cleanTerminal(clean);
                            /*
                             * Aceasta parte se ocupa de pornirea jocului
                             */
                            if(hardcoded.equals("1"))
                                System.out.println("\t\t\tHardcoded mode is on!");
                            System.out.println("\t\t\t------Starting the game------");
                            if (hardcoded.equals("1")) {
                                System.out.println("\n" + HelperClass.showInstructions("2")
                                +"\t\t\tPress T to show the map\n");
                            } else
                                HelperClass.showInstructions(mode);
                            table.showTable();
                            System.out.println("\n\t\t\t------Press P to start moving !!!------");
                            value = scanner.nextLine();
                            if (!value.equals("P")) {
                                while (helperFunc(value)) {
                                    optionsCharacter(value, table, clean, mode, hardcoded);
                                    System.out.println("\n\t\t\t------Press P to start moving !!!------");
                                    value = scanner.nextLine();
                                }
                                if (value.equals("H"))
                                    break;
                                if (!value.equals("P"))
                                    throw new InvalidCommandException();
                            }
                            System.out.println("\t\t\t------You can move now.Follow my instructions!------");
                            value = commandsCharacter(table, clean, scanner, mode, hardcoded);
                            System.out.println("\t\t\t------Closing the game------");
                        }

                    }
                    /*
                     * Aceste parti se ocupa cu navigarea prin lista de conturi
                     * "2" -> contul urmator
                     * "3" -> contul precedent
                     */
                    break;
                    case "2": {
                        if (counter + 1 == accounts.size())
                            System.out.println("Cannot do that.This is the last account");
                        else {
                            counter++;
                            cleanTerminal(clean);
                            HelperClass.showInformation(counter, accounts, hardcoded);
                        }
                        value = scanner.nextLine();
                    }
                    break;
                    case "3": {
                        if (counter - 1 < 0)
                            System.out.println("Cannot do that.This is the first account");
                        else {
                            counter--;
                            cleanTerminal(clean);
                            HelperClass.showInformation(counter, accounts, hardcoded);
                        }
                        value = scanner.nextLine();
                    }
                    break;
                    default:
                        throw new InvalidCommandException();
                }
            }
        } catch (InvalidCommandException error) {
            error.printStackTrace();
        }
    }

    /**
     * O metoda ce prezinta utilizatorului situatia curenta din joc
     */
    public void startMoving(Grid table, int clean, Scanner scanner, String mode, String hardcoded) throws InvalidCommandException {
        String command;
        System.out.println("\t\t\t------You can move now(Shop visited or enemy defeated).Well Done!------");
        command = scanner.nextLine();
        optionsCharacter(command, table, clean, mode, hardcoded);
    }

    /**
     * O metoda ce afiseaza lista de optiuni disponibile in functie de celula curenta
     */
    public String commandsCharacter(Grid table, int clean, Scanner scan, String mode, String hardcoded) throws InvalidCommandException {
        String command;
        boolean start = false;
        int cont = 0;
        while (table.getReference().getHitpoints() > 0) {
            switch (table.getCurrCell().getType()) {
                /*
                 * Cazul in care celula este goala
                 * Personajul se poate misca doar daca se introduce litera 'P' in terminal ( in modul test/'hardcoded' )
                 */
                case EMPTY: {
                    if (table.getCurrCell().getCellElement().toCharacter() == 'N' && !start) {
                        System.out.println("\t\t\tThis is the place where you start your journey.Be careful at monsters!");
                        start = true;
                    }
                    if (hardcoded.equals("1")) {
                        switch (cont) {
                            case 0: {
                                start(scan, hardcoded, table, clean, mode);
                                command = "D";
                                optionsCharacter(command, table, clean, mode, hardcoded);
                            }
                            break;
                            case 1: {
                                start(scan, hardcoded, table, clean, mode);
                                command = "S";
                                optionsCharacter(command, table, clean, mode, hardcoded);
                            }
                            break;
                        }
                    } else {
                        command = scan.nextLine();
                        optionsCharacter(command, table, clean, mode, hardcoded);
                        if (command.equals("H"))
                            return command;

                    }
                }
                break;
                /*
                 * Cazul in care celula prezinta un magazin
                 * Jucatorul poate sa aleaga potiunile doar daca modul test/'hardcoded' este dezactivat
                 */
                case SHOP: {
                    System.out.println("\t\t\t------You are in shop." +
                            "Do you wish to buy something (1 for Yes,0 for No,P if hardcoded mode is on) ?------");
                    if (hardcoded.equals("1")) {
                        start(scan, hardcoded, table, clean, mode);
                        command = "1";
                    } else
                        command = scan.nextLine();
                    switch (command) {
                        case "1": {
                            Potion potion;
                            String helper = "yes";
                            /*
                             * Un ciclu ce prezinta lista de potiuni din magazin
                             * Se actualizeaza lista atunci cand utilizatorul doreste sa mai cumpere
                             */
                            while (!helper.equals("no")) {
                                System.out.println("\t\t\t------Type 'exit' to exit the shop------");
                                potion = ((Shop) table.getCurrCell().getCellElement()).getPotionShop(hardcoded);
                                table.getReference().buyPotion(potion);
                                if (potion != null) {
                                    System.out.println("\t\t\t------Do you want to buy another potion (If you have gold)" +
                                            "(Type 'yes' or 'no') ?.Do not type 'exit' now.Type P if hardcoded mode is on------");
                                    if (hardcoded.equals("1"))
                                        start(scan, hardcoded, table, clean, mode);
                                    if (hardcoded.equals("1") && cont == 0) {
                                        helper = "yes";
                                    } else if (hardcoded.equals("0")) {
                                        helper = scan.nextLine();
                                        if (!helper.equals("yes") && !helper.equals("no"))
                                            throw new InvalidCommandException();
                                    }
                                    if (hardcoded.equals("1") && cont == 1)
                                        helper = "no";
                                    cont++;
                                } else
                                    break;
                            }
                            System.out.println("\t\t\t------Your hero visited the shop.Exiting------");
                            cont--;
                        }
                        break;
                        case "0": {
                            System.out.println("\t\t\t------Exiting shop------");
                        }
                        break;
                        default:
                            throw new InvalidCommandException();
                    }
                    /*
                     * Aceasta parte reprezinta continuarea jocului
                     */
                    if (hardcoded.equals("1")) {
                        start(scan, hardcoded, table, clean, mode);
                        command = "D";
                        optionsCharacter(command, table, clean, mode, hardcoded);
                    } else {
                        start(scan, hardcoded, table, clean, mode);
                        cleanTerminal(clean);
                        table.showTable();
                        startMoving(table, clean, scan, mode, hardcoded);
                    }
                }
                break;
                /*
                 * Cazul in care celula prezinta un inamic
                 */
                case ENEMY: {
                    int hitpoints;
                    Spell spell;
                    Enemy enemy = ((Enemy) table.getCurrCell().getCellElement());
                    /*
                     * Daca celula nu a mai fost vizitata (se mentioneaza faptul ca inamicul nu reapare)
                     */
                    if (enemy.getHitpoints() > 0) {
                        if (hardcoded.equals("0"))
                            HelperClass.dangerEnemy();
                        else {
                            System.out.println("\t\t\t------The enemy is approaching!.He/She wants your money------");
                            System.out.println("\t\t\t------Hardcoded mode is on!You cannot make decisions!------");
                        }
                    }
                    SplittableRandom random = new SplittableRandom();
                    /*
                     * Un ciclu ce modeleaza lupta
                     * Aceasta lupta este pe runde
                     */
                    while (enemy.getHitpoints() > 0
                            && table.getReference().getHitpoints() > 0) {
                        /*
                         * Se afiseaza viata/mana in fiecare runda
                         */
                        System.out.println("\t\t\tYour HP: " + table.getReference().getHitpoints() +
                                " Your mana: " + table.getReference().getCurrMana());
                        System.out.println("\t\t\tEnemy's HP: " + enemy.getHitpoints());
                        System.out.println("\t\t\t------Your turn------");
                        /*
                         * Daca modul test/'hardcoded' este activat -> jucatorul nu poate alege
                         */
                        if (hardcoded.equals("1")) {
                            start(scan, hardcoded, table, clean, mode);
                            if (!table.getReference().getAbilities().isEmpty())
                                command = "3";
                            else if (!table.getReference().getInventory().getPotions().isEmpty())
                                command = "2";
                            else
                                command = "1";
                        } else {
                            command = scan.nextLine();
                            while (command.equals("U") || command.equals("I")) {
                                System.out.println("\t\t\t------You are looking at your available abilities/potions." +
                                        "Be careful! The enemy hates to wait------");
                                optionsCharacter(command, table, clean, mode, hardcoded);
                                System.out.println("\t\t\tRemember! '1' for basic attack,'2' for potion and '3' for ability!");
                                command = scan.nextLine();
                            }
                        }
                        switch (command) {
                            case "1": {
                                /*
                                 * Daca s-a ales atacul normal ('basic attack')
                                 */
                                hitpoints = enemy.getHitpoints();
                                int damage = table.getReference().getDamage();
                                System.out.println("\t\t\tYou are dealing " + damage + " damage to your enemy!");
                                enemy.receiveDamage(damage);
                                if (hitpoints == enemy.getHitpoints())
                                    System.out.println("\t\t\tThe enemy has dodged your attack!");
                            }
                            break;
                            case "2": {
                                /*
                                 * Daca s-a ales folosirea unei potiuni
                                 */
                                table.getReference().getInventory().showPotions(mode);
                                if (!table.getReference().getInventory().getPotions().isEmpty()) {
                                    System.out.println("\t\t\tType the number of the position");
                                    if (hardcoded.equals("1")) {
                                        command = String.valueOf(random.nextInt(0, table.getReference().
                                                getInventory().getPotions().size()));
                                        System.out.println(command);
                                    } else
                                        command = scan.nextLine();
                                    table.getReference().getInventory().
                                            removePotion(Integer.parseInt(command)).utilisePotion(table.getReference(), mode);
                                }
                            }
                            break;
                            case "3": {
                                /*
                                 * Daca s-a ales folosirea unei abilitati
                                 */
                                hitpoints = enemy.getHitpoints();
                                table.getReference().showAbility(mode);
                                if (!table.getReference().getAbilities().isEmpty()) {
                                    System.out.println("\t\t\tType the number of the position");
                                    if (hardcoded.equals("1")) {
                                        command = String.valueOf(random.nextInt(0, table.getReference().
                                                getAbilities().size()));
                                        System.out.println(command);
                                    } else
                                        command = scan.nextLine();
                                    spell = table.getReference().
                                            removeAbility(Integer.parseInt(command), mode);
                                    table.getReference().useAbility(spell, enemy);
                                    if (hitpoints == enemy.getHitpoints() && spell != null)
                                        System.out.println("\t\t\tEnemy has dodged your attack or " +
                                                "has protection against your ability: " + spell.getSpellName() + " !");
                                    else if (spell != null)
                                        System.out.println("\t\t\tEnemy does not have protection for: " + spell.getSpellName());
                                    if (hardcoded.equals("1") && spell == null)
                                        if(table.getReference().getInventory().getPotions().size()!=1)
                                        table.getReference().getInventory().
                                                removePotion(1).utilisePotion(table.getReference(), mode);
                                }
                            }
                            break;
                            default:
                                throw new InvalidCommandException();
                        }
                        /*
                         * Aceasta parte modeleaza deciziile inamicului
                         * 75% pentru atacul normal,25% pentru abilitati
                         */
                        if (enemy.getHitpoints() > 0) {
                            System.out.println("\t\t\t------Enemy's turn------");
                            if (random.nextInt(1, 101) <= 75) {
                                hitpoints = table.getReference().getHitpoints();
                                int damage = enemy.getDamage();
                                System.out.println("\t\t\tEnemy decided to use basic attack! He/She is dealing " + damage + " damage");
                                table.getReference().receiveDamage(damage);
                                if (hitpoints - damage != table.getReference().getHitpoints())
                                    System.out.println("\t\t\tYou succeeded to partially dodge the enemy's damage!");
                            } else {
                                System.out.println("\t\t\tEnemy decided to use an ability!");
                                hitpoints = table.getReference().getHitpoints();
                                if (!enemy.getAbilities().isEmpty())
                                    spell = enemy.removeAbility
                                            (random.nextInt(enemy.getAbilities().size()), mode);
                                else
                                    spell = enemy.removeAbility(0, mode);
                                enemy.useAbility(spell, table.getReference());
                                /*
                                 * Cazul in care inamicul nu mai are mana/abilitati
                                 */
                                if (table.getReference().getHitpoints() == hitpoints &&
                                        !HelperClass.testFight(table.getReference(), spell)) {
                                    int damage = enemy.getDamage();
                                    HelperClass.enemyDecision(damage);
                                    table.getReference().receiveDamage(damage);
                                }
                                /*
                                 * Cazul in care personajul are protectie impotriva abilitatii
                                 */
                                if (HelperClass.testFight(table.getReference(), spell)) {
                                    HelperClass.enemyDecision(table, spell);
                                }
                                /*
                                 * Cazul in care personajul nu are protectie
                                 */
                                else if (spell != null) {
                                    System.out.println("\t\t\tYour hero does not have protection for: " + spell.getSpellName());
                                    if (hitpoints - spell.getSpellDamage() != table.getReference().getHitpoints() &&
                                            !HelperClass.testFight(table.getReference(), spell))
                                        System.out.println("\t\t\tYou succeeded to partially dodge the enemy's damage!");
                                }

                            }
                        }
                        /*
                         * Partea ce reprezinta victoria personajului
                         */
                        else {
                            System.out.println("\t\t\tYou defeated the enemy!.Your experience is increasing");
                            table.getReference().increaseExp(random.nextInt(25, 75), mode);
                            if (random.nextInt(1, 101) <= 80) {
                                int gold = random.nextInt(100);
                                System.out.println("\t\t\tYou also get some gold!: " + gold);
                                table.getReference().getInventory().
                                        setGold(table.getReference().getInventory().
                                                getGold() + gold);
                            }
                            if (hardcoded.equals("0"))
                                start(scan, hardcoded, table, clean, mode);
                        }
                    }
                    /*
                     * Partea ce reprezinta infrangerea personajului
                     */
                    if (table.getReference().getHitpoints() <= 0) {
                        System.out.println("\t\t\tYour hero died.Exiting the game.Your stats: ");
                        HelperClass.showStats(table, mode);
                    }
                    /*
                     * Partea ce continua jocul in urma victoriei
                     */
                    else {
                        table.getReference().setDefeatedEnemy(table.getReference().getDefeatedEnemy() + 1);
                        table.showTable();
                        if (hardcoded.equals("1")) {
                            start(scan, hardcoded, table, clean, mode);
                            command = "S";
                            optionsCharacter(command, table, clean, mode, hardcoded);

                        } else
                            startMoving(table, clean, scan, mode, hardcoded);
                    }
                }
                break;
                /*
                 * Cazul in care celula reprezinta finalul jocului
                 */
                case FINISH: {
                    System.out.println("\n\t\t\tWell done!.You found the final cell.You won this game");
                    HelperClass.showStats(table, mode);
                    return "H";
                }
            }
        }
        return "H";
    }

    /**
     * O metoda ce afiseaza o poveste atunci cand celula nu este vizitata
     */
    public void visitStory(Grid table) {
        if (!table.getCurrCell().isVisited()) {
            table.getCurrCell().setVisited(true);
            showStory(table.getCurrCell().getType());
        }
        if (table.getCurrCell().getType() != Cell.TypeCell.SHOP
                && table.getCurrCell().getType() != Cell.TypeCell.ENEMY) {
            table.showTable();
        }
    }

    /**
     * O metoda ce doreste apasarea tastei 'P' pentru a continua jocul
     */
    public void start(Scanner scan, String hardcoded, Grid table, int clean, String mode) throws InvalidCommandException {
        String message;
        if (hardcoded.equals("1"))
            message = "\t\t\t------Press P to make an action!!!------";
        else
            message = "\t\t\t------Press P to start moving!!!------";
        System.out.println(message);
        String command = scan.nextLine();
        if (!command.equals("P")) {
            while (helperFunc(command)) {
                optionsCharacter(command, table, clean, mode, hardcoded);
                System.out.println(message);
                command = scan.nextLine();
            }
            if (!command.equals("P"))
                throw new InvalidCommandException();
        }
    }

    /**
     * O metoda ce se ocupa de selectarea unei povesti din dictionar
     */
    public void showStory(Cell.TypeCell type) {

        SplittableRandom random = new SplittableRandom();
        System.out.println("\t\t\t******" + stories.get(type).get(random.nextInt(stories.get(type).size())) + "******");
    }

    /**
     * O metoda ce modeleaza comenzile date de la tastatura
     * Exemplu: W,A,S,D pentru a deplasa personajul
     */
    public void optionsCharacter(String move, Grid table, int clean, String mode, String hardcoded) throws InvalidCommandException {
        switch (move) {
            case "A": {
                cleanTerminal(clean);
                table.goWest(mode);
                visitStory(table);
            }
            break;
            case "S": {
                cleanTerminal(clean);
                table.goSouth(mode);
                visitStory(table);
            }
            break;
            case "D": {
                cleanTerminal(clean);
                table.goEast(mode);
                visitStory(table);
            }
            break;
            case "W": {
                cleanTerminal(clean);
                table.goNorth(mode);
                visitStory(table);
            }
            break;
            case "U":
                table.getReference().showAbility(mode);
                break;
            case "I": {
                HelperClass.importantStats(table, mode);
                HelperClass.showStats(table, mode);
            }
            break;
            case "O":
                HelperClass.profession(table, mode);
                break;
            case "H": {
                System.out.println("\t\t\t------Exiting game------");
                table.getReference().setHitpoints(0);
            }
            break;
            case "Y": {
                HelperClass.showGoldWeight(table, mode);
            }
            break;
            case "R": {
                if (hardcoded.equals("1"))
                    System.out.println("\n" + HelperClass.showInstructions("2"));
                else
                    HelperClass.showInstructions(mode);
            }
            break;
            case "T":
                table.showTable();
                break;
            default:
                throw new InvalidCommandException();
        }
    }

    /**
     * O metoda ce se ocupa cu verificarea parolei atunci cand se alege un cont
     */
    public void testPassword(Scanner scanner, int counter, String hardcoded) throws InvalidCommandException {
        System.out.println("\t\t\t------Password required------\n\t\t\tPlease write the password");
        String pass;
        if (!hardcoded.equals("1")) {
            pass = scanner.nextLine();
            String verify = accounts.get(counter).getInformation().getCredentials().getPassword();
            while (!pass.equals(accounts.get(counter).getInformation().getCredentials().getPassword())) {
                System.out.println("\t\t\t------Incorrect password!------\n\t\t\tTry again." +
                        "(Go to JSON file)" + " " + verify);
                pass = scanner.nextLine();
            }
        } else {
            System.out.println("\t\t\t------Press P to make an action!!!------");
            pass = scanner.nextLine();
            if (!pass.equals("P"))
                throw new InvalidCommandException();
        }
    }

    /**
     * O metoda ce afiseaza lista de personaje atunci cand autentificarea s-a efectuat cu succes
     */
    public String accepted(int counter, Scanner scanner,String hardcoded,Grid table,int clean,String mode)
            throws InvalidCommandException {
        System.out.println("\t\t\t------Displaying characters stored in this account------");
        System.out.println(accounts.get(counter).showCharacters());
        if(!hardcoded.equals("1")) {
            System.out.println("\t\t\t------Press E to choose a character------" +
                    "\n\t\t\t------Press R to create a new character------");
            return scanner.nextLine();
        }
        else {
            start(scanner, hardcoded, table, clean, mode);
            return "E";
        }
    }

    /**
     * O metoda ce se ocupa de crearea unui personaj nou
     */
    public void createCharacter(Account account, Scanner scan, int clean) {
        String heroName;
        String heroClass;
        String level;
        String experience;
        System.out.println("\t\t\tYou wish to create a character.Please type it's name");
        heroName = scan.nextLine();
        System.out.println("\t\t\tType the profession.Choose from this list: {Warrior,Rogue,Mage}");
        heroClass = scan.nextLine();
        while (!heroClass.equals("Warrior") && !heroClass.equals("Rogue") && !heroClass.equals("Mage")) {
            System.out.println("\t\t\tWrong profession!.Please type an element from the given list");
            heroClass = scan.nextLine();
        }
        System.out.println("\t\t\tType your character's level (positive integer or you will get an error !)");
        level = scan.nextLine();
        System.out.println("\t\t\tType your character's experience (positive integer or you will get an error !)");
        experience = scan.nextLine();
        account.addCharacterFactory(heroClass, heroName, Integer.parseInt(experience), Integer.parseInt(level));
        cleanTerminal(clean);
        System.out.println("\t\t\t------The character was created------");
    }

     /**
     * O metoda ce "curata" terminalul
     */
    public void cleanTerminal(int clean) {
        for (int i = 0; i < clean; i++)
            System.out.println();
    }

    /**
    * O metoda auxiliara ce verifica corectitudinea comenzii
    */
    public boolean helperFunc(String command) {
        return command.equals("U") || command.equals("I")
                || command.equals("O") || command.equals("Y")
                || command.equals("R") || command.equals("T");
    }
}
