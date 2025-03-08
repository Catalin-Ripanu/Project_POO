import javax.swing.*;
import java.util.*;
 /**
  *  O clasa ce se ocupa de tabla de joc
  *  Tabla e o lista de liste de 'Cell'
 */
public class Grid extends ArrayList<List<Cell>> {
    private int height;
    private int width;
    private Character reference;
    private Cell currCell;

    private Grid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Character getReference() {
        return reference;
    }

    public void setReference(Character reference) {
        this.reference = reference;
    }

    public Cell getCurrCell() {
        return currCell;
    }

    public void setCurrCell(Cell currCell) {
        this.currCell = currCell;
    }

    /**
    * O metoda care genereaza o tabla folosind 'SplittableRandom'
    * Tabla poseda celule generate aleator
    */
    public static Grid generateMap(int height, int width) {
        Grid table = new Grid(height, width);
        SplittableRandom random = new SplittableRandom();
        boolean shop = false;
        boolean enemy = false;
        boolean finish = false;
        int contShop = 0;
        int contEnemy = 0;
        Cell cell = new Cell(new CellElement() {
            @Override
            public char toCharacter() {
                return 'N';
            }
        }, 0, 0, true);
        cell.setType(cell.getEmpty());
        table.currCell = cell;
        List<Cell> line = new ArrayList<>();
        line.add(cell);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0 && i == 0)
                    continue;
                if (j == width - 1 && i == height - 1 && !finish) {
                    table.addCell(line, i, j, 4);
                    continue;
                }
                /*
                * Partea ce se ocupa de crearea celulelor
                */
                int value = random.nextInt(0, 6);
                switch (value) {
                    case 0: {
                        table.addCell(line, i, j, new Shop());
                        contShop++;
                    }
                    break;
                    case 1: {
                        if (contShop < 2 && i >= height - 3) {
                            table.addCell(line, i, j, new Shop());
                            shop = true;
                            contShop++;
                        }
                    }
                    break;
                    case 2: {
                        table.addCell(line, i, j, new Enemy());
                        contEnemy++;
                    }
                    break;
                    case 3: {
                        if (contEnemy < 4 && i >= height - 3) {
                            table.addCell(line, i, j, new Enemy());
                            enemy = true;
                            contEnemy++;
                        }
                    }
                    break;
                    case 4: {
                        if (!finish && i >= height - 3 && j>= width -3 ) {
                            finish = true;
                            table.addCell(line, i, j, value);
                        } else
                            table.addCell(line, i, j, value - 1);
                    }
                    break;
                }
                if (value != 0 && value != 2 && value != 4 && !shop && !enemy) {
                    table.addCell(line, i, j, value);
                }
                enemy = false;
                shop = false;
            }
            table.add(line);
            if (i != height - 1)
                line = new ArrayList<>();
        }
        return table;
    }

    /**
    * O metoda care genereaza tabla modului 'hardcoded'
    */
    public static Grid generateTestMap() {
        Grid table = new Grid(5, 5);
        Cell cell = new Cell(new CellElement() {
            @Override
            public char toCharacter() {
                return 'N';
            }
        }, 0, 0, true);
        cell.setType(cell.getEmpty());
        table.currCell = cell;
        List<Cell> line1 = new ArrayList<>();
        List<Cell> line2 = new ArrayList<>();
        List<Cell> line3 = new ArrayList<>();
        List<Cell> line4 = new ArrayList<>();
        List<Cell> line5 = new ArrayList<>();
        line1.add(cell);
        table.addCell(line1, 0, 1, 3);
        table.addCell(line1, 0, 2, 3);
        table.addCell(line1, 0, 3, new Shop());
        table.addCell(line1, 0, 4, 3);
        table.add(line1);
        for (int i = 0; i < 3; i++)
            table.addCell(line2, 1, i, 3);
        table.addCell(line2, 1, 3, new Shop());
        table.addCell(line2, 1, 4, 3);
        table.add(line2);
        table.addCell(line3, 2, 0, new Shop());
        for (int i = 1; i <= 4; i++)
            table.addCell(line3, 2, i, 3);
        table.add(line3);
        for (int i = 0; i < 4; i++)
            table.addCell(line4, 3, i, 3);
        table.addCell(line4, 3, 4, new Enemy());
        table.add(line4);
        for (int i = 0; i < 4; i++)
            table.addCell(line5, 4, i, 3);
        table.addCell(line5, 4, 4, 4);
        table.add(line5);
        return table;
    }

    /**
    * O metoda care se ocupa de crearea celulelor 'ENEMY' si 'SHOP'
    */
    public void addCell(List<Cell> line, int i, int j, CellElement element) {
        Cell cell;
        cell = new Cell(element, i, j, false);
        switch (element.getClass().getSimpleName()) {
            case "Shop":
                cell.setType(cell.getShop());
                break;
            case "Enemy":
                cell.setType(cell.getEnemy());
                break;
        }
        line.add(cell);
    }

      /**
      * O metoda care se ocupa de crearea celulelor 'FINISH' si 'EMPTY'
      */
    public void addCell(List<Cell> line, int i, int j, int value) {
        Cell cell;
        if (value == 4) {
            cell = new Cell(new CellElement() {
                @Override
                public char toCharacter() {
                    return 'F';
                }
            }, i, j, false);
            cell.setType(cell.getFinish());
        } else {
            cell = new Cell(new CellElement() {
                @Override
                public char toCharacter() {
                    return 'N';
                }
            }, i, j, false);
            cell.setType(cell.getEmpty());
        }
        line.add(cell);
    }

    /**
    * O metoda care afiseaza tabla de joc
    */
    public void showTable() {
        System.out.print("\t\t\t");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value;
                if (get(i).get(j).isVisited())
                    value = 1;
                else
                    value = 0;
                switch (value) {
                    case 1: {
                        if (i == currCell.getPositionOx() && j == currCell.getPositionOy()) {
                            System.out.print("P" + get(i).get(j).getCellElement().toCharacter() + " ");
                        } else
                            System.out.print(get(i).get(j).getCellElement().toCharacter() + " ");
                    }
                    break;
                    case 0:
                        System.out.print("? ");
                        break;
                }
            }
            if (i < height - 1)
                System.out.print("\n\t\t\t");
            else
                System.out.println();
        }
    }

    /**
    * O metoda care ofera bani personajului atunci cand celula curenta nu a mai fost vizitata
    * Probabilitatea este de 20%
    */
    public void luckyCharacter(String mode) {
        SplittableRandom random = new SplittableRandom();
        boolean chance = random.nextInt(1, 101) <= 20;
        if (chance) {
            int value = random.nextInt(65);
            String message = "\t\t\tYour hero found " + value + " gold in this strange place!\n";
            if (mode.equals("2"))
                JOptionPane.showMessageDialog(null, message);
            else
                System.out.println(message);
            reference.getInventory().setGold(reference.getInventory().getGold() + value);
        }
    }

    /**
    * O metoda care actualizeaza celula curenta
    */
    public void updateCell(String mode) {
        currCell = get(reference.getPositionOx()).get(reference.getPositionOy());
        if (!currCell.isVisited()) {
            luckyCharacter(mode);
        }
    }

    /**
    * O metoda care afiseaza un mesaj de eroare atunci cand mutarea nu este corecta
    */
    public void error(String text, String mode) {
        String message = "\t\t\tInvalid move!\n" + "\t\t\tYour hero cannot move to " + text + " !\n";
        if (mode.equals("2"))
            JOptionPane.showMessageDialog(null, message);
        else
            System.out.println(message);
    }

     /**
      * O metoda care deplaseaza personajul spre nord
      */
    public void goNorth(String mode) {
        if (currCell.getPositionOx() != 0) {
            reference.setPositionOx(reference.getPositionOx() - 1);
            updateCell(mode);
        } else {
            error("north", mode);

        }
    }

     /**
      * O metoda care deplaseaza personajul spre est
      */
    public void goEast(String mode) {
        if (currCell.getPositionOy() != width - 1) {
            reference.setPositionOy(reference.getPositionOy() + 1);
            updateCell(mode);
        } else {
            error("east", mode);

        }
    }

     /**
      * O metoda care deplaseaza personajul spre vest
      */
    public void goWest(String mode) {
        if (currCell.getPositionOy() != 0) {
            reference.setPositionOy(reference.getPositionOy() - 1);
            updateCell(mode);
        } else {
            error("west", mode);

        }
    }
      /**
      * O metoda care deplaseaza personajul spre sud
      */
    public void goSouth(String mode) {
        if (currCell.getPositionOx() != height - 1) {
            reference.setPositionOx(reference.getPositionOx() + 1);
            updateCell(mode);
        } else {
            error("south", mode);

        }
    }

}
