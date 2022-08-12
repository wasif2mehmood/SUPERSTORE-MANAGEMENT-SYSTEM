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

public class final_order implements ActionListener {
    JFrame finWin;
    JPanel panel;
    String[] items=new String[100];
    int bill;
    JTextField customer_name, customer_number;
    JButton paid, unpaid, mainmenu;
    JLabel Bill, Customer_name, Customer_number,title,picture;
    File paid_rec = new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\SHOP MANAGEMENT SYSTEM\\src\\customerRECORD");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    ImageIcon image;
    final_order() throws FileNotFoundException {

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

        image = new ImageIcon("C:\\Users\\Wasif Mehmood\\IdeaProjects\\MY SHOP MANAGER\\src\\FINAL ORDER.png");

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
        int n=0;
        File cart = new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\SHOP MANAGEMENT SYSTEM\\src\\cart.txt");
        Scanner read = new Scanner(cart);
        while (read.hasNextLine()) {
            String line = read.nextLine();
            items[n]=line;
            System.out.println(line);
            int pri = Integer.parseInt(line.split(": ")[1]);
            bill += pri;
            n+=1;
        }

        Bill = new JLabel(String.valueOf(bill + " is total bill calculated"));
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
                veiw_cart veiw_cart = new veiw_cart();
                finWin.dispose();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void NumberCheck() throws IOException {
        try {
            if (customer_number.getText().length() != 11) {
                throw new Exception();
            }
            for (int i = 0; i < customer_number.getText().length(); i++) {
                Integer.parseInt(Character.toString((customer_number.getText().charAt(i))));
            }
        } catch (Exception ex) {

            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Enter Correct contact number with 11 Digits", "invalid Data", 0);
            paid.setVisible(true);
            unpaid.setVisible(true);
            throw new IOException();

        }
    }

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
    public void recwriter(String l){
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
                    + dtf.format(now)+"|"+ l +"|");
            for (String lin:items){
                if(lin==null){continue;}
                writer.write("  ["+lin+"]");
            }
            writer.write("\n");
            writer.close();
            // veiw_cart v = new veiw_cart();
            File cart = new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\SHOP MANAGEMENT SYSTEM\\src\\cart.txt");
            FileWriter f = new FileWriter(cart);
            f.close();

            // v.remove_from_cart.addActionListener(v);
            // v.actionPerformed(e.getSource().equals(v.remove_from_cart));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        final_order final_order=new final_order();
    }

}
