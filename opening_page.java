import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The `opening_page` class represents the main menu of the shop management application.
 */
class opening_page implements ActionListener {
    JButton dynamicSearch, additemsbtn, profitLoss, customerRecord, cart, mainmenu;
    JFrame openingWindow;
    JPanel panel;
    JLabel title, picture;
    ImageIcon image;

    /**
     * Constructor to set up the opening page with menu buttons.
     *
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Handle actions performed by the menu buttons.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dynamicSearch) {
            DYNAMIC_SEARCH M = new DYNAMIC_SEARCH();
            openingWindow.dispose();
        }
        if (e.getSource() == cart) {
            try {
                new veiw_cart();
                openingWindow.dispose();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == additemsbtn) {
            try {
                new addItemsframe();
                openingWindow.dispose();
            } catch (Exception ex) {
                // TODO: handle exception
            }
        }

        if (e.getSource() == profitLoss) {
            new profitlosscalculator();
            openingWindow.dispose();
        }

        if (e.getSource() == customerRecord) {
            new customer_record();
            openingWindow.dispose();
        }
    }

    /**
     * The main method to start the application.
     *
     * @param args Command-line arguments.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        new opening_page();
    }
}
