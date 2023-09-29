import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class signupPage extends JFrame implements ActionListener {
    JFrame signupFrame;
    JPanel registerPanel, buttonpanel;
    JTextField nametxtfield, ContactNumbertxtfield, CNICtxtfield, addresstxtfield, DOBtxtfield, usernametxtfield,
            passwordtxtfield, confirmpasswordtxtfield;
    JLabel namelbl,title, ContactNumberlbl, CNIClbl, addresslbl, DOBlbl, usernamelbl, passwordlbl, confirmpasswordlbl,picture;
    JDatePickerImpl datePicker;
    JButton createaccountbutton;
    JFrame signupframe;
    ImageIcon images;

    public signupPage(String nothing) {
    };

    public signupPage() {
        signupframe = new JFrame("SignUp Page");
        signupframe.getContentPane().setBackground(new Color(106, 13, 173));
        signupframe.setLayout(null);
        signupframe.setExtendedState(JFrame.MAXIMIZED_BOTH);

        signupframe.setTitle("Registraion Window");

        title = new JLabel("REGISTRATION PAGE");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        signupframe.add(title);

        ImageIcon image = new ImageIcon("logo2.png");
        signupframe.setIconImage(image.getImage());
        signupframe.setDefaultCloseOperation(EXIT_ON_CLOSE);

        images = new ImageIcon("signup.png");


        registerPanel = new JPanel();
        registerPanel.setBounds(200, 100, 1000, 500);
        registerPanel.setLayout(null);
        signupframe.add(registerPanel);
        addwidget();

        picture = new JLabel(images);
        picture.setBounds(30, 10, 500, 470);
        registerPanel.add(picture);

        // buttonpanel = new JPanel();

        // buttonpanel.setSize(100, 100);
        // signupframe.add(buttonpanel);

        signupframe.setVisible(true);
    }

    private void addwidget() {
        namelbl = new JLabel("Name");
        namelbl.setBounds(500,50,100,40);
        namelbl.setBackground(Color.LIGHT_GRAY);
        registerPanel.add(namelbl);


        nametxtfield = new JTextField();
        nametxtfield.setBounds(600,50,300,40);
        registerPanel.add(nametxtfield);

        ContactNumberlbl = new JLabel("Contact Number");
        ContactNumberlbl.setBounds(500,100,100,40);
        registerPanel.add(ContactNumberlbl);


        ContactNumbertxtfield = new JTextField();
        ContactNumbertxtfield.setBounds(600,100,300,40);
        registerPanel.add(ContactNumbertxtfield);

        CNIClbl = new JLabel("CNIC");
        CNIClbl.setBounds(500,150,100,40);
        registerPanel.add(CNIClbl);

        CNICtxtfield = new JTextField();
        CNICtxtfield.setBounds(600,150,300,40);
        registerPanel.add(CNICtxtfield);

        addresslbl = new JLabel("Address");
        addresslbl.setBounds(500,200,100,40);
        registerPanel.add(addresslbl);

        addresstxtfield = new JTextField();
        addresstxtfield.setBounds(600,200,300,40);
        registerPanel.add(addresstxtfield);

        DOBlbl = new JLabel("Date of Birth");
        DOBlbl.setBounds(500,250,100,40);
        registerPanel.add(DOBlbl);
        try {
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            // JDatePanelImpl datePanel = new JDatePanelImpl(model);
            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            datePicker.setBounds(600,250,100,40);
            registerPanel.add(datePicker);

        } catch (Exception e) {
            // TODO: handle exception
        }

        usernamelbl = new JLabel("username");
        usernamelbl.setBounds(500,300,100,40);
        registerPanel.add(usernamelbl);
        usernametxtfield = new JTextField();
        usernametxtfield.setBounds(600,300,100,40);
        registerPanel.add(usernametxtfield);

        passwordlbl = new JLabel("Password");
        passwordlbl.setBounds(500,350,100,40);
        registerPanel.add(passwordlbl);
        passwordtxtfield = new JTextField();
        passwordtxtfield.setBounds(600,350,300,40);
        registerPanel.add(passwordtxtfield);

        confirmpasswordlbl = new JLabel("Confirm Password");
        confirmpasswordlbl.setBounds(500,400,100,40);
        registerPanel.add(confirmpasswordlbl);
        confirmpasswordtxtfield = new JTextField();
        confirmpasswordtxtfield.setBounds(600,400,300,40);
        registerPanel.add(confirmpasswordtxtfield);


        createaccountbutton = new JButton("Sign Up");
        createaccountbutton.setBounds(550,450,100,40);
        registerPanel.add(createaccountbutton);
        createaccountbutton.addActionListener(this);
    }

    public void filehandled() {

        File usersignupsetails = new File("SignupDetails.txt");

        try {
            usersignupsetails.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("unable to create new file");
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter("SignupDetails.txt", true);
            fileWriter.write(nametxtfield.getText() + "," + ContactNumbertxtfield.getText() + ","
                    + CNICtxtfield.getText() + "," + addresstxtfield.getText() + ","
                    + datePicker.getJFormattedTextField().getText() + "," + "{" + usernametxtfield.getText() + "}" + ","
                    + "{" + passwordtxtfield.getText() + "}" + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void datavalidation() throws Exception {
        if (nametxtfield.getText().length() < 5) {
            throw new InputMismatchException();
        }
        try {
            isonlyLetters(nametxtfield.getText());
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Invalid Name", "Invalid Data", 1);
            throw new InputMismatchException();
        }

        if (nametxtfield.getText().trim().isEmpty() || CNICtxtfield.getText().trim().isEmpty()
                || addresstxtfield.getText().trim().isEmpty()
                || datePicker.getJFormattedTextField().getText().trim().isEmpty()
                || ContactNumbertxtfield.getText().trim().isEmpty() || CNICtxtfield.getText().trim().isEmpty()
                || usernametxtfield.getText().trim().isEmpty()
                || passwordtxtfield.getText().trim().isEmpty() || confirmpasswordtxtfield.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Missing fields", "invalid Data", 0);

            // nametxtfield("<html>Text color: <font color='red'>red</font></html>");

            // <span style="color:#ff0000">*</span>
            throw new InputMismatchException();

        }

        // contact number**********
        try {
            if (ContactNumbertxtfield.getText().length() != 11) {
                throw new Exception();
            }
            for (int i = 0; i < ContactNumbertxtfield.getText().length(); i++) {
                Integer.parseInt(Character.toString((ContactNumbertxtfield.getText().charAt(i))));
            }
        } catch (Exception e) {

            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Enter Correct contact number with 11 Digits", "invalid Data", 0);
            throw new InputMismatchException();

        }

        try {
            if (CNICtxtfield.getText().length() < 13) {
                throw new Exception();
            }
            for (int i = 0; i < CNICtxtfield.getText().length(); i++) {
                Integer.parseInt(Character.toString((CNICtxtfield.getText().charAt(i))));
            }

        }

        catch (Exception e) {
            // TODO: handle exception

            JOptionPane.showMessageDialog(null, "Enter Correct 13 digit CNIC", "invalid Data", 0);
            throw new InputMismatchException();
        }

        if (!passwordtxtfield.getText().equals(confirmpasswordtxtfield.getText())) {
            JOptionPane.showMessageDialog(null, "Confirm password do not match the selected password", "invalid Data",
                    0);
            throw new InputMismatchException();
        }

        // username check

        File usersignupsetails = new File("SignupDetails.txt");
        try (Scanner reader = new Scanner(usersignupsetails)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(usernametxtfield.getText());
                System.out.println(line);
                if (line.contains("{" + usernametxtfield.getText() + "}")) {

                    JOptionPane.showMessageDialog(null, "Username already exists! Choose another username",
                            "invalid Data",
                            0);
                    throw new InputMismatchException();
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // code to create a file and add user info in it
        try {
            datavalidation();
            filehandled();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();

        }

    }


    void isonlyLetters(String a) throws InputMismatchException {
        a = a.replace(" ", "");
        for (int i = 0; i < a.length(); i++) {

            boolean check = Character.isLetter(a.charAt(i));

            if (check == false) {

                throw new InputMismatchException();

            }

        }
    }

}
