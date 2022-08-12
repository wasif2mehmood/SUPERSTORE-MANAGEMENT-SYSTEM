import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class opening_page implements ActionListener {
    JButton dynamicSearch, additemsbtn, profitLoss, customerRecord, cart, mainmenu;
    JFrame openingWindow;
    JPanel panel;
    JLabel title, picture;
    ImageIcon image;

    opening_page() throws IOException {

        openingWindow = new JFrame("MY SHOP MANAGER");
        openingWindow.getContentPane().setBackground(new Color(106, 13, 173));
        openingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openingWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        title = new JLabel("MY SHOP MANAGER");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        openingWindow.add(title);

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        openingWindow.add(panel);

        image = new ImageIcon("src//imaj.png");

        picture = new JLabel(image);
        picture.setBounds(80, 10, 500, 470);
        panel.add(picture);

        dynamicSearch = new JButton("DYNAMIC SEARCH");
        dynamicSearch.setBounds(650, 10, 200, 50);
        dynamicSearch.addActionListener(this::actionPerformed);
        panel.add(dynamicSearch);

        additemsbtn = new JButton("ADD ITEMS");
        additemsbtn.setBounds(650, 110, 200, 50);
        additemsbtn.addActionListener(this::actionPerformed);
        panel.add(additemsbtn);

        profitLoss = new JButton("PROFIT LOSS CALCULATOR");
        profitLoss.setBounds(650, 210, 200, 50);
        profitLoss.addActionListener(this::actionPerformed);
        panel.add(profitLoss);

        customerRecord = new JButton("CUSTOMER RECORD");
        customerRecord.setBounds(650, 310, 200, 50);
        customerRecord.addActionListener(this::actionPerformed);
        panel.add(customerRecord);

        cart = new JButton("CART");
        cart.setBounds(650, 410, 200, 50);
        cart.addActionListener(this::actionPerformed);
        panel.add(cart);

        openingWindow.setLayout(null);
        openingWindow.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dynamicSearch) {
            DYNAMIC_SEARCH M = new DYNAMIC_SEARCH();
            openingWindow.dispose();
        }
        if (e.getSource() == cart) {
            try {
                veiw_cart veiw_cart = new veiw_cart();
                openingWindow.dispose();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == additemsbtn) {
            try {
                addItemsframe a = new addItemsframe();
                openingWindow.dispose();

            } catch (Exception ex) {
                // TODO: handle exception

            }

        }
        if(e.getSource()==profitLoss){
            profitlosscalculator profitlosscalculator=new profitlosscalculator();
            openingWindow.dispose();
        }

        if(e.getSource()==customerRecord){
            customer_record customer_record=new customer_record();
            openingWindow.dispose();
        }
    }

    class main {
        public static void main(String[] args) throws IOException {

            opening_page o = new opening_page();

        }
    }
}
