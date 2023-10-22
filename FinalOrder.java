import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * The `final_order` class represents a GUI application for finalizing customer orders.
 */
public class FinalOrder implements ActionListener {
    JFrame finWin;
    JPanel panel;
    String[] items = new String[100];
    int bill;
    JTextField customer_name, customer_number;
    JButton paid, unpaid, mainmenu;
    JLabel Bill, Customer_name, Customer_number, title, picture;
    File paid_rec = new File("customerRECORD");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    ImageIcon image;

    /**
     * Constructor for initializing the final order application.
     *
     * @throws FileNotFoundException If the file is not found.
     */
    FinalOrder() throws FileNotFoundException {
        // Initialize the main frame
        finWin = new JFrame("FINAL ORDER");
        finWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        finWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finWin.getContentPane().setBackground(new Color(106, 13, 173));
        finWin.setLayout(null);

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        finWin.add(panel);

        title = new JLabel("FINALIZED ORDER");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        finWin.add(title);

        Customer_name = new JLabel("CUSTOMERS NAME");
        Customer_name.setBounds(450, 50, 300, 60);
        panel.add(Customer_name);

        image = new ImageIcon("FINAL ORDER.png");

        picture = new JLabel(image);
        picture.setBounds(0, 10, 500, 470);
        panel.add(picture);

        customer_name = new JTextField("enter customer's name");
        customer_name.setBounds(600, 50, 300, 60);
        panel.add(customer_name);

        Customer_number = new JLabel("CUSTOMERS NUMBER");
        Customer_number.setBounds(450, 150, 300, 60);
        panel.add(Customer_number);

        customer_number = new JTextField("enter customer's number");
        customer_number.setBounds(600, 150, 300, 60);
        panel.add(customer_number);

        bill = 0;
        int n = 0;
        File cart = new File("cart.txt");
        try (Scanner read = new Scanner(cart)) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                items[n] = line;
                System.out.println(line);
                int pri = Integer.parseInt(line.split(": ")[1]);
                bill += pri;
                n += 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Bill = new JLabel(String.valueOf(bill + " is the total bill calculated"));
        Bill.setBounds(600, 300, 600, 60);
        Bill.setFont((new Font("Serif", Font.PLAIN, 30)));
        panel.add(Bill);
        finWin.setVisible(true);

        paid = new JButton("PAID");
        paid.setBounds(600, 370, 100, 40);
        paid.addActionListener(this::actionPerformed);
        panel.add(paid);

        unpaid = new JButton("UN PAID");
        unpaid.setBounds(800, 370, 100, 40);
        unpaid.addActionListener(this::actionPerformed);
        panel.add(unpaid);

        mainmenu = new JButton("back");
        mainmenu.setBounds(10, 10, 100, 50);
        finWin.add(mainmenu);
        mainmenu.setVisible(true);
        mainmenu.addActionListener(this::actionPerformed);
    }

    /**
     * Action performed method to handle button clicks and actions.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == paid) {
            recwriter(paid.getText());
        }
        if (e.getSource() == unpaid) {
            recwriter(unpaid.getText());
        }
        if (e.getSource() == mainmenu) {
            try {
                new ViewCart();
                finWin.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Check if a string contains only letters (alphabets).
     *
     * @param a The input string to check.
     * @throws InputMismatchException If the input contains non-letter characters.
     * @throws IOException            If an I/O error occurs.
     */
    void isonlyLetters(String a) throws InputMismatchException, IOException {
        a = a.replace(" ", "");
        for (int i = 0; i < a.length(); i++) {
            boolean check = Character.isLetter(a.charAt(i));
            if (!check) {
                paid.setVisible(true);
                unpaid.setVisible(true);
                JOptionPane.showMessageDialog(null, "Enter Correct name", "invalid Data", 0);
                throw new IOException();
            }
        }
    }

    /**
     * Check if a string is a valid 11-digit number.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void NumberCheck() throws IOException {
        try {
            if (customer_number.getText().length() != 11) {
                throw new Exception();
            }
            for (int i = 0; i < customer_number.getText().length(); i++) {
                Integer.parseInt(Character.toString((customer_number.getText().charAt(i))));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter Correct contact number with 11 Digits", "invalid Data", 0);
            paid.setVisible(true);
            unpaid.setVisible(true);
            throw new IOException();
        }
    }

    /**
     * Write the order details to a file and reset the cart.
     *
     * @param l The billing status (Paid or Unpaid).
     */
    public void recwriter(String l) {
        try {
            JOptionPane.showMessageDialog(null, "ORDER PLACED SUCCESSFULLY", "ORDER PLACED", 1);
            Bill.setText("ORDER PLACED");
            paid.setVisible(false);
            unpaid.setVisible(false);
            isonlyLetters(customer_name.getText());
            NumberCheck();
            FileWriter writer = new FileWriter(paid_rec, true);
            writer.write("{" + customer_name.getText() + "}|{" + customer_number.getText() + "}|" + bill
                    + "|"
                    + dtf.format(now) + "|" + l + "|");
            for (String lin : items) {
                if (lin == null) {
                    continue;
                }
                writer.write("  [" + lin + "]");
            }
            writer.write("\n");
            writer.close();
            File cart = new File("cart.txt");
            FileWriter f = new FileWriter(cart);
            f.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Main method to create an instance of the final_order class.
     *
     * @param args Command-line arguments.
     * @throws FileNotFoundException If a file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        new FinalOrder();
    }
}
