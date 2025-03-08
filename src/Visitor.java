 /**
 * O interfata implementata de clasele care modeleaza abilitatile din joc
 */
public interface Visitor <T extends Entity>{
    void visit(T entity);
}
