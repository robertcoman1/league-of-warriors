import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGameFrame extends JFrame {
    private JPanel gameBoard;
    private JPanel statsPanel;
    private String nextMove;
    private final Object lock = new Object();
    public MainGameFrame (Grid map){
        super("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        JPanel panelButtons = makeJPanelButtons();

        JPanel statsPanel = makePanelStats(map.getCharacter());

        JPanel gameBoard = makeJPanelGameBoard(map);

        this.add(panelButtons);
        this.add(statsPanel);
        this.add(gameBoard);

        this.setVisible(false);
    }

    private JPanel makeJPanelButtons() {
        JPanel panelButtons = new JPanel(new GridLayout(4, 1, 10, 10));
        panelButtons.setBounds(0, 0, 150, 150);

        JButton buttonNorth = new JButton("North");
        buttonNorth.setBackground(Color.black);
        buttonNorth.setForeground(Color.red);
        buttonNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    nextMove = "go north";
                    lock.notify();
                }
            }
        });
        JButton buttonSouth = new JButton("South");
        buttonSouth.setBackground(Color.black);
        buttonSouth.setForeground(Color.red);
        buttonSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    nextMove = "go south";
                    lock.notify();
                }
            }
        });
        JButton buttonWest = new JButton("West");
        buttonWest.setBackground(Color.black);
        buttonWest.setForeground(Color.red);
        buttonWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    nextMove = "go west";
                    lock.notify();
                }
            }
        });
        JButton buttonEast = new JButton("East");
        buttonEast.setBackground(Color.black);
        buttonEast.setForeground(Color.red);
        buttonEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    nextMove = "go east";
                    lock.notify();
                }
            }
        });
        panelButtons.add(buttonNorth);
        panelButtons.add(buttonSouth);
        panelButtons.add(buttonWest);
        panelButtons.add(buttonEast);
        return panelButtons;
    }
    private JPanel makePanelStats(MainCharacter character) {
        JPanel statsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        statsPanel.setBounds(0, 150, 200, 400);

        JLabel label1 = new JLabel("Level");
        JLabel label2 = new JLabel("Experience");
        JLabel label3 = new JLabel("Health");
        JLabel label4 = new JLabel("Mana");

        statsPanel.add(label1);
        statsPanel.add(new JLabel(String.valueOf(character.level)));
        statsPanel.add(label2);
        statsPanel.add(new JLabel(String.valueOf(character.xp)));
        statsPanel.add(label3);
        statsPanel.add(new JLabel(String.valueOf(character.health)));
        statsPanel.add(label4);
        statsPanel.add(new JLabel(String.valueOf(character.mana)));

        this.statsPanel = statsPanel;
        return statsPanel;
    }
    private JPanel makeJPanelGameBoard(Grid map) {
        JPanel gameBoard = new JPanel(new GridLayout(map.getWidth(), map.getLength(), 1, 1));
        gameBoard.setBounds(300, 100, 400, 400);

        for (int i = 0 ; i < map.getWidth() ; i++) {
            for (int j = 0 ; j < map.getLength() ; j++) {
                Cell currentCell = map.get(i).get(j);
                JButton button = new JButton();

                ImageIcon unvisitedIcon = new ImageIcon(new ImageIcon("src/question-circle.jpg").
                                getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

                ImageIcon visitedIcon = new ImageIcon(new ImageIcon("src/check-square.jpg").
                                getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

                ImageIcon playerIcon = new ImageIcon(new ImageIcon("src/smile.jpg").
                                getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

                gameBoard.add(button);
                if (!currentCell.visited) {
                    if (currentCell.cellType == Cell.CellEntityType.PLAYER)
                        button.setIcon(playerIcon);
                    else {
                        button.setIcon(unvisitedIcon);
                    }
                } else {
                    if (currentCell.cellType == Cell.CellEntityType.PLAYER)
                        button.setIcon(playerIcon);
                    else
                        button.setIcon(visitedIcon);
                }
            }
        }
        this.gameBoard = gameBoard;
        return gameBoard;
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
    public void updateMap(Grid map) {
        this.getContentPane().remove(gameBoard);
        this.getContentPane().remove(statsPanel);
        this.add(makeJPanelGameBoard(map));
        this.add(makePanelStats(map.getCharacter()));
        this.revalidate();
        this.repaint();
    }
    public String getNextMove() {
        return nextMove;
    }
}
