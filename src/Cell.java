 /**
 * O clasa ce modeleaza un patratel din tabla de joc
 * Membrul 'type' reprezinta tipul celulei (EMPTY,ENEMY,SHOP,FINISH)
 */
public class Cell {
    private int positionOx;
    private int positionOy;

    protected enum TypeCell {
        EMPTY,
        ENEMY,
        SHOP,
        FINISH
    }

    private CellElement cellElement;
    private TypeCell type;
    private boolean visited;

    public Cell(CellElement cellElement, int positionOx, int positionOy, boolean visited) {
        this.cellElement = cellElement;
        this.positionOx = positionOx;
        this.positionOy = positionOy;
        this.visited = visited;
    }

    public TypeCell getEmpty() {
        return TypeCell.EMPTY;
    }

    public TypeCell getEnemy() {
        return TypeCell.ENEMY;
    }

    public TypeCell getShop() {
        return TypeCell.SHOP;
    }

    public TypeCell getFinish() {
        return TypeCell.FINISH;
    }

    public void setType(TypeCell type) {
        this.type = type;
    }

    public TypeCell getType() {
        return type;
    }

    public int getPositionOx() {
        return positionOx;
    }

    public void setPositionOx(int positionOx) {
        this.positionOx = positionOx;
    }

    public int getPositionOy() {
        return positionOy;
    }

    public void setPositionOy(int positionOy) {
        this.positionOy = positionOy;
    }

    public CellElement getCellElement() {
        return cellElement;
    }

    public void setCellElement(CellElement cellElement) {
        this.cellElement = cellElement;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
