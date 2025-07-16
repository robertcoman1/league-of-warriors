public class Cell {
    int Ox;
    int Oy;
    public enum CellEntityType{
        PLAYER,
        VOID,
        ENEMY,
        SANCTUARY,
        PORTAL,
        QUIT
    }
    CellEntityType cellType;
    boolean visited;
}
