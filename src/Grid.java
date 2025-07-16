import java.util.*;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int length;
    private int width;
    private MainCharacter character;
    private Cell current;

    private Grid(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void setCharacter(MainCharacter character) {
        this.character = character;
    }

    public MainCharacter getCharacter() {
        return character;
    }

    public Cell getCurrent() {
        return current;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public static Grid create_map (int length, int width) {
        Grid gameTable = new Grid(length, width);
        if (length > 10 || width > 10 || length * width < 8) {
            System.out.println("Can't create map of the size " + length + " by " + width);
            return null;
        }
        ArrayList<ArrayList<Integer>> visited = new ArrayList<>(width);
        Random rand = new Random();

        for (int i = 0 ; i < width ; i++) {
            gameTable.add(i, new ArrayList<>(length));
            for (int j = 0 ; j < length ; j++) {
                gameTable.get(i).add(j, new Cell());
                Cell cell = gameTable.get(i).get(j);
                cell.Ox = i;
                cell.Oy = j;
                cell.visited = false;
                cell.cellType = Cell.CellEntityType.VOID;
            }
            visited.add(new ArrayList<>(Collections.nCopies(length, 0)));
        }

        int nrSanctuary = rand.nextInt(2, length * width / 4 + 1);
        int nrEnemies = rand.nextInt(4, length * width / 2 + 1);
        int nrPortals = 1;
        int nrPlayer = 1;

        placeCellsType(gameTable, visited, length, width, Cell.CellEntityType.SANCTUARY, nrSanctuary);
        placeCellsType(gameTable, visited, length, width, Cell.CellEntityType.ENEMY, nrEnemies);
        placeCellsType(gameTable, visited, length, width, Cell.CellEntityType.PORTAL, nrPortals);
        placeCellsType(gameTable, visited, length, width, Cell.CellEntityType.PLAYER, nrPlayer);

        return gameTable;
    }

    public void GoNorth() throws ImpossibleMove{
        if (current.Ox <= 0)
            throw new ImpossibleMove();

        Cell newCurrent = this.get(current.Ox - 1).get(current.Oy);
        newCurrent.cellType = Cell.CellEntityType.PLAYER;
        current.cellType = Cell.CellEntityType.VOID;
        current.visited = true;
        current = newCurrent;
    }

    public void GoWest() throws ImpossibleMove{
        if (current.Oy <= 0)
            throw new ImpossibleMove();

        Cell newCurrent = this.get(current.Ox).get(current.Oy - 1);
        newCurrent.cellType = Cell.CellEntityType.PLAYER;
        current.cellType = Cell.CellEntityType.VOID;
        current.visited = true;
        current = newCurrent;
    }

    public void GoSouth() throws ImpossibleMove{
        if (current.Ox >= width - 1)
            throw new ImpossibleMove();

        Cell newCurrent = this.get(current.Ox + 1).get(current.Oy);
        newCurrent.cellType = Cell.CellEntityType.PLAYER;
        current.cellType = Cell.CellEntityType.VOID;
        current.visited = true;
        current = newCurrent;
    }

    public void GoEast() throws ImpossibleMove{
        if (current.Oy >= length - 1)
            throw new ImpossibleMove();
        Cell newCurrent = this.get(current.Ox).get(current.Oy + 1);
        newCurrent.cellType = Cell.CellEntityType.PLAYER;
        current.cellType = Cell.CellEntityType.VOID;
        current.visited = true;
        current = newCurrent;
    }

    public static void placeCellsType(Grid grid, ArrayList<ArrayList<Integer>> visited,
                                      int length, int width, Cell.CellEntityType type, int nrOfEntities) {
        Random rand = new Random();
        while (nrOfEntities != 0) {
            int row = rand.nextInt(0, width);
            int column = rand.nextInt(0, length);
            if (visited.get(row).get(column) == 0) {
                grid.get(row).get(column).cellType = type;
                if (type == Cell.CellEntityType.PLAYER)
                    grid.current = grid.get(row).get(column);
                nrOfEntities -= 1;
                visited.get(row).set(column, 1);
            }
        }
    }

    public void showMap(MainGameFrame frame) {
        if (frame.isVisible() == true) {
            frame.updateMap(this);
        }
        else
            frame.setVisible(true);
        for (int i = 0 ; i < this.width ; i++) {
            for (int j = 0 ; j < this.length ; j++) {
                Cell currentCell = this.get(i).get(j);
                if (!currentCell.visited) {
                    if (currentCell.cellType == Cell.CellEntityType.PLAYER)
                        System.out.print("P  ");
                    else
                        System.out.print("N  ");
                } else {
                    if (currentCell.cellType == Cell.CellEntityType.PLAYER)
                        System.out.print("P  ");
                    else
                        System.out.print("V  ");
                }
            }
            System.out.println();
        }
    }

    public static Grid chooseMapDimensions() {
        Scanner scanner = new Scanner(System.in);

        int length = new Random().nextInt(1, 11);
        int width = new Random().nextInt(8 / length + 1, 11);

        return create_map(length, width);
    }

    public static Grid createMapForTest() {
        Grid map = create_map(5, 5);
        for (int i = 0 ; i < map.width ; i ++) {
            ArrayList<Cell> row = map.get(i);
            for (Cell cell : row) {
                cell.cellType = Cell.CellEntityType.VOID;
            }
        }
        assert map != null;

        map.getFirst().getFirst().cellType = Cell.CellEntityType.PLAYER;
        map.current = map.getFirst().getFirst();
        map.getFirst().get(3).cellType = Cell.CellEntityType.SANCTUARY;
        map.get(1).get(3).cellType = Cell.CellEntityType.SANCTUARY;
        map.get(2).getFirst().cellType = Cell.CellEntityType.SANCTUARY;
        map.get(3).getLast().cellType = Cell.CellEntityType.ENEMY;
        map.getLast().get(3).cellType = Cell.CellEntityType.SANCTUARY;
        map.getLast().getLast().cellType = Cell.CellEntityType.PORTAL;

        return map;
    }
}
