import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 * The `LoginFrame` class represents a graphical user interface for user login and account creation.
 */
class LoginFrame extends JFrame implements ActionListener {

    JFrame login_frame;
    ImageIcon image;
    JLabel username, picture;
    JLabel password;
    JTextField userTextField;
    JPasswordField passwordField;

    JCheckBox showPassword;
    JButton loginButton;
    JButton createAccount = new JButton("CREATE ACCOUNT");
    JLabel passStat, title;
    JPanel panel;

    /**
     * Constructor to set up the login frame and its components.
     */
    LoginFrame() {
        // Initialize the main login frame
        login_frame = new JFrame();
        login_frame.getContentPane().setBackground(new Color(106, 13, 173));
        login_frame.setLayout(null);
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        title = new JLabel("LOGIN PAGE");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(580, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        login_frame.add(title);

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        login_frame.add(panel);

        username = new JLabel("USERNAME");
        username.setBounds(600, 100, 100, 30);
        panel.add(username);

        password = new JLabel("PASSWORD");
        password.setBounds(600, 200, 100, 30);
        panel.add(password);

        userTextField = new JTextField();
        userTextField.setBounds(700, 100, 200, 30);
        panel.add(userTextField);

        passwordField = new JPasswordField();
        passwordField.setBounds(700, 200, 200, 30);
        passwordField.setEchoChar('*');
        panel.add(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(700, 250, 150, 30);
        showPassword.setFocusable(false);
        panel.add(showPassword);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(600, 300, 100, 30);
        loginButton.setFocusable(false);
        panel.add(loginButton);

        createAccount = new JButton("CREATE ACCOUNT");
        createAccount.setBounds(850, 300, 100, 30);
        panel.add(createAccount);

        passStat = new JLabel();
        passStat.setLocation(600, 400);
        passStat.setFont(new Font("Arial", Font.PLAIN, 20));
        passStat.setSize(500, 25);
        panel.add(passStat);

        image = new ImageIcon("login.png");

        picture = new JLabel(image);
        picture.setBounds(40, 0, 550, 500);
        panel.add(picture);

        login_frame.setVisible(true);

        loginButton.addActionListener(this);
        createAccount.addActionListener(this);
        showPassword.addActionListener(this);
    }

    /**
     * Handle actions performed by buttons and checkboxes.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createAccount) {
            new signupPage();
        }
        if (e.getSource() == loginButton) {
            String status = "";
            String userText;
            String pwdText;
            userText = "{" + userTextField.getText() + "}";
            System.out.println(userText);
            pwdText = "{" + String.valueOf(passwordField.getPassword()) + "}";
            System.out.println(pwdText);
            File UserFile = new File("SignupDetails.txt");
            try {
                try (Scanner userFileScanner = new Scanner(UserFile)) {
                    while (userFileScanner.hasNextLine()) {
                        String line = userFileScanner.nextLine();
                        if (line.contains(userText) && line.contains(pwdText) && !userText.equals("")
                                && !pwdText.equals("")) {
                            JOptionPane.showMessageDialog(null, "LOGGED IN SUCCESSFULLY", "LOGGED IN", 1);
                            status = "SUCCESSFULLY LOGGED IN";
                            login_frame.dispose();
                            break;
                        } else {
                            status = "WRONG PASSWORD";
                        }
                    }
                } catch (HeadlessException e1) {
                    e1.printStackTrace();
                }
                passStat.setText(status);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}

/**
 * The main `Login` class to start the application.
 */
public class Login {
    public static void main(String[] a) {
        new LoginFrame();
    }
}
