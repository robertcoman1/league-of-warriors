import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Account> accounts;
    private Grid map;
    private Account accountSelected;
    private static Game instance;
    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }
    private Game() {
        this.map = Grid.chooseMapDimensions();
    }
    public Grid getMap() {
        return this.map;
    }
    public void setMap(Grid map) {
        this.map = map;
    }
    public Account getAccount() {
        return this.accountSelected;
    }
    public void setAccount(Account accountSelected) {
        this.accountSelected = accountSelected;
    }
    public ArrayList<Account> getDeserializedAccounts() {
        return this.accounts;
    }
    public void run() {
        this.accounts = JsonInput.deserializeAccounts(); // parse the input file
        this.accountSelected = Authentication.login(accounts); // choose an account
        this.map.setCharacter(Authentication.checkLoginInfo(this.accountSelected, this.accounts));
    }
    public Cell.CellEntityType nextCommand(MainGameFrame frame) throws InvalidCommandException{
        Cell.CellEntityType type = null;
        System.out.println("Possible moves: ");
        if (map.getCurrent().Ox - 1 >= 0)
            System.out.println("You can go North: ");
        if (map.getCurrent().Ox + 1 < map.getWidth())
            System.out.println("You can go South: ");
        if (map.getCurrent().Oy - 1 >= 0)
            System.out.println("You can go West: ");
        if (map.getCurrent().Oy + 1 < map.getLength())
            System.out.println("You can go East: ");
        System.out.println("You can quit");
        System.out.println("Command (go and the direction) ex : go north to go north");
        System.out.println();
        System.out.println("Choose next move: ");
        try {
            frame.waitForInput();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String nextMove = frame.getNextMove();
        switch (nextMove.replace(" ", "").trim().toLowerCase()) {
            case "gonorth" :
                if (map.getCurrent().Ox - 1 >= 0)
                    type = map.get(map.getCurrent().Ox - 1).get(map.getCurrent().Oy).cellType;
                try {
                    map.GoNorth();
                } catch (ImpossibleMove impossibleMove) {}
                break;
            case "gosouth" :
                if (map.getCurrent().Ox + 1 < map.getWidth())
                    type = map.get(map.getCurrent().Ox + 1).get(map.getCurrent().Oy).cellType;
                try {
                    map.GoSouth();
                } catch (ImpossibleMove impossibleMove) {}
                break;
            case "gowest" :
                if (map.getCurrent().Oy - 1 >= 0)
                    type = map.get(map.getCurrent().Ox).get(map.getCurrent().Oy - 1).cellType;
                try {
                    map.GoWest();
                } catch (ImpossibleMove impossibleMove) {}
                break;
            case "goeast" :
                if (map.getCurrent().Oy + 1 < map.getLength())
                    type = map.get(map.getCurrent().Ox).get(map.getCurrent().Oy + 1).cellType;
                try {
                    map.GoEast();
                } catch (ImpossibleMove impossibleMove) {}
                break;
            case "quit" :
                type = Cell.CellEntityType.QUIT;
                break;
            default:
                throw new InvalidCommandException();
        }
        return type;
    }
    public static void main(String[] args) throws IOException {
        Game game = Game.getInstance();
        game.run();

        MainGameFrame frame = new MainGameFrame(game.getMap());
        game.getMap().showMap(frame);
        Interactions interactions = new Interactions();

        while (true) {
            Cell.CellEntityType type = null;
            try {
                type = game.nextCommand(frame);
            } catch (InvalidCommandException e) {}
            if (type == Cell.CellEntityType.ENEMY) {
                frame.setVisible(false);
                interactions.playerVsEnemy();
                frame.setVisible(true);
            }
            if (type == Cell.CellEntityType.SANCTUARY)
                interactions.playerOnSanctuary();
            if (type == Cell.CellEntityType.PORTAL)
                interactions.playerOnPortal();
            if (type == Cell.CellEntityType.QUIT) {
                interactions.playerOnQuit();
            }
            game.getMap().showMap(frame);
        }
    }
}