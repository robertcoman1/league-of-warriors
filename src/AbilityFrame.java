import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AbilityFrame extends JFrame {
    private JPanel imagesPanel;
    private JPanel statsPanel;
    private int nrOfAbility;
    private final Object lock = new Object();
    public AbilityFrame(ArrayList<Spell> abilities) throws IOException {
        super("Abilities");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        JPanel imagesPanel = makeJPanelImages(abilities);
        JPanel statsPanel = makeJPanelStats(abilities);

        add(imagesPanel);
        add(statsPanel);
        setVisible(false);
    }
    private JPanel makeJPanelImages(ArrayList<Spell> abilities) throws IOException {
        JPanel imagesPanel = new JPanel(new GridLayout(1, abilities.size(), 10, 10));
        imagesPanel.setBounds(20, 0, 750, 400);

        BufferedImage fire = ImageIO.read(new File("src/fire.jpg"));
        BufferedImage ice = ImageIO.read(new File("src/iceImage.jpg"));
        BufferedImage earth = ImageIO.read(new File("src/earth.jpg"));

        for (Spell spell : abilities) {
            JLabel imageLabel = null;
            if (spell.getClass() == Fire.class)
                imageLabel = new JLabel(new ImageIcon(fire));
            else if (spell.getClass() == Ice.class)
                imageLabel = new JLabel(new ImageIcon(ice));
            else if (spell.getClass() == Earth.class)
                imageLabel = new JLabel(new ImageIcon(earth));

            imagesPanel.add(imageLabel);
        }
        this.imagesPanel = imagesPanel;
        return imagesPanel;
    }
    private JPanel makeJPanelStats(ArrayList<Spell> abilities) {
        JPanel statsPanel = new JPanel(new GridLayout(4, abilities.size(), 10, 10));
        statsPanel.setBounds(20, 400, 750, 150);

        ArrayList<JButton> buttons = new ArrayList<>();
        for (int i = 0 ; i < abilities.size() ; i++)
            buttons.add(new JButton("use"));
        for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < abilities.size() ; j++) {
                if (i == 0) {
                    JLabel nameLabel = new JLabel("Name :" + abilities.get(j).getSpell());
                    statsPanel.add(nameLabel);
                }
                else if (i == 1) {
                    JLabel manaLabel = new JLabel("Mana :" + abilities.get(j).manaCost);
                    statsPanel.add(manaLabel);
                }
                else if (i == 2) {
                    JLabel damageLabel = new JLabel("Damage :" + abilities.get(j).damage);
                    statsPanel.add(damageLabel);
                }
                else {
                    statsPanel.add(buttons.get(j));
                    int buttonIndex = j;
                    buttons.get(j).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            synchronized (lock) {
                                nrOfAbility = buttonIndex;
                                lock.notify();
                            }
                        }
                    });
                }
            }
        }
        this.statsPanel = statsPanel;
        return statsPanel;
    }
    public void update(ArrayList<Spell> abilities) throws IOException {
        this.getContentPane().remove(imagesPanel);
        this.getContentPane().remove(statsPanel);
        this.add(makeJPanelImages(abilities));
        this.add(makeJPanelStats(abilities));
        this.revalidate();
        this.repaint();
    }
    public int getNrOfAbility() {
        return nrOfAbility;
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
}
