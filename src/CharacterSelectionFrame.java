import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelectionFrame extends JFrame implements ActionListener {
    private String characterName;
    private JTextField text;
    private final Object lock = new Object();
    public CharacterSelectionFrame(Account acc) {
        super("Character selection");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(acc.characters.size() + 2,1));
        panel.setBounds(250, 150, 300, 300);
        JLabel characterLabel = new JLabel("Choose your character");
        characterLabel.setBounds(250,100,300,50);
        for (MainCharacter character : acc.characters) {
            JLabel label = new JLabel(character.name);
            panel.add(label);
        }

        text = new JTextField();
        panel.add(text);

        JButton button = new JButton("select");
        button.addActionListener(this);
        panel.add(button);

        this.add(panel);
        this.add(characterLabel);
        this.setVisible(true);
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (lock) {
            characterName = text.getText();
            lock.notify();
        }
    }
    public String getCharacterName(){
        return characterName;
    }
}
