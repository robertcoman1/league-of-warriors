import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleFrame extends JFrame {
    private JPanel heroStats;
    private JPanel enemyStats;
    private final Object lock = new Object();
    private String action;
    public BattleFrame(MainCharacter character, Enemy enemy) throws IOException {
        super("battle");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 1024);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        JPanel heroPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        heroPanel.setBackground(Color.black);
        heroPanel.setBounds(0, 0, 300, 800);

        JPanel enemyPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        enemyPanel.setBounds(500, 0, 300, 800);

        BufferedImage imageHero = ImageIO.read(new File("src/hero.jpeg"));
        JLabel hero = new JLabel(new ImageIcon(imageHero));
        heroPanel.add(hero);

        BufferedImage imageEnemy = ImageIO.read(new File("src/enemy.jpeg"));
        JLabel enemyImage = new JLabel(new ImageIcon(imageEnemy));
        enemyPanel.add(enemyImage);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonsPanel.setBounds(350, 0, 100, 100);

        JButton attackButton = new JButton("Attack");
        buttonsPanel.add(attackButton);
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    action = "attack";
                    lock.notify();
                }
            }
        });
        JButton abilityButton = new JButton("Ability");
        buttonsPanel.add(abilityButton);
        abilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    action = "use ability";
                    lock.notify();
                }
            }
        });

        JPanel heroStats = makeHeroStats(character);

        JPanel enemyStats = makeEnemyStats(enemy);

        add(enemyPanel);
        add(heroPanel);
        add(buttonsPanel);
        add(heroStats);
        add(enemyStats);

        setVisible(true);
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
    public String getAction() {
        return action;
    }
    private JPanel makeHeroStats(MainCharacter character) {
        JPanel heroStats = new JPanel(new GridLayout(2, 2, 10, 10));
        heroStats.setBounds(0, 800, 400, 200);

        heroStats.add(new JLabel("Health"));
        heroStats.add(new JLabel(String.valueOf(character.health)));
        heroStats.add(new JLabel("Mana"));
        heroStats.add(new JLabel(String.valueOf(character.mana)));

        this.heroStats = heroStats;
        return heroStats;
    }
    private JPanel makeEnemyStats(Enemy enemy) {
        JPanel enemyStats = new JPanel(new GridLayout(2, 2, 10, 10));
        enemyStats.setBounds(543, 800, 400, 200);

        enemyStats.add(new JLabel(String.valueOf(enemy.health)));
        enemyStats.add(new JLabel("Health"));
        enemyStats.add(new JLabel(String.valueOf(enemy.mana)));
        enemyStats.add(new JLabel("Mana"));

        this.enemyStats = enemyStats;
        return enemyStats;
    }
    public void updateBattleFrame(MainCharacter character, Enemy enemy) {
        this.getContentPane().remove(heroStats);
        this.getContentPane().remove(enemyStats);

        this.add(makeHeroStats(character));
        this.add(makeEnemyStats(enemy));

        this.revalidate();
        this.repaint();
    }
}
