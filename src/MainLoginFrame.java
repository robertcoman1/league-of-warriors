import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLoginFrame extends JFrame implements ActionListener {
    private JTextField emailText;
    private JTextField passwordText;
    private String email;
    private String password;
    private final Object lock = new Object();

    public MainLoginFrame() {
        super("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBounds(250, 150, 300, 300);

        JLabel emailLabel = new JLabel("Enter email:");
        JLabel passwordLabel = new JLabel("Enter password:");
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(250,50,300,50);
        JButton button = new JButton("login");
        button.setBounds(1,1,1,1);
        button.addActionListener(this);
        emailText = new JTextField();
        passwordText = new JTextField();

        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(button);
        this.add(panel);

        this.add(loginLabel);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (lock) {
            email = emailText.getText();
            password = passwordText.getText();
            lock.notify();
        }
    }
    public void waitForInput() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
