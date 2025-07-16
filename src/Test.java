import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Game game = Game.getInstance();
        game.run();
        MainCharacter character = game.getMap().getCharacter();
        game.setMap(Grid.createMapForTest());
        game.getMap().setCharacter(character);
        MainGameFrame frame = new MainGameFrame(game.getMap());
        game.getMap().showMap(frame);
        //game logic
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
            if (type == Cell.CellEntityType.PORTAL) {
                frame.setVisible(false);
                interactions.playerOnPortal();
                frame.setVisible(true);
            }
            if (type == Cell.CellEntityType.QUIT) {
                interactions.playerOnQuit();
            }
            game.getMap().showMap(frame);
        }
    }
}
