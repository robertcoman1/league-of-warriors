import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EndFrame extends JFrame {
    private final Object lock = new Object();
    public EndFrame(MainCharacter character) throws IOException {
        super("Info");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 1024);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        JPanel heroPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        heroPanel.setBackground(Color.black);
        heroPanel.setBounds(250, 0, 300, 800);

        BufferedImage imageHero = ImageIO.read(new File("src/hero.jpeg"));
        JLabel hero = new JLabel(new ImageIcon(imageHero));
        heroPanel.add(hero);

        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        statsPanel.setBounds(250, 830, 400, 150);

        statsPanel.add(new JLabel("Name"));
        statsPanel.add(new JLabel(character.name));
        statsPanel.add(new JLabel("Role"));
        statsPanel.add(new JLabel(character.getClass().getName()));
        statsPanel.add(new JLabel("Level"));
        statsPanel.add(new JLabel(String.valueOf(character.level)));
        statsPanel.add(new JLabel("Experience"));
        statsPanel.add(new JLabel(String.valueOf(character.xp)));
        statsPanel.add(new JLabel("Enemies killed"));
        statsPanel.add(new JLabel(String.valueOf(character.enemiesKilled)));

        JButton nextLevel = new JButton("Next");
        nextLevel.setBounds(683, 933, 100, 50);
        nextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    dispose();
                    lock.notify();
                }
            }
        });
        add(heroPanel);
        add(statsPanel);
        add(nextLevel);

        setVisible(true);
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
}
