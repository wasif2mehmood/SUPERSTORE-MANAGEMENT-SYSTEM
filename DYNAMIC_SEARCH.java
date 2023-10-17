import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// The class represents a simple item search GUI application.
public class DYNAMIC_SEARCH extends JFrame implements ActionListener {

    // Instance variables for GUI components and data
    private int searchCount;
    private JTextField searchField;
    private JLabel searchLabel, noFoundLabel, availableItemsLabel, titleLabel;
    private JButton searchButton, addToCartButton, mainMenuButton, viewCartButton;
    private JFrame itemSearchFrame;
    private String[] availableProductsArray;
    private JComboBox<String> availableProductsComboBox;
    private JPanel panel;
    private ImageIcon image;

    // Constructor to set up the initial state of the GUI.
    DYNAMIC_SEARCH() {
        searchCount = 0;
        itemSearchFrame = new JFrame("Item Search");
        itemSearchFrame.setLayout(null);
        itemSearchFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        itemSearchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemSearchFrame.getContentPane().setBackground(new Color(106, 13, 173));

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        itemSearchFrame.add(panel);

        titleLabel = new JLabel("MY SHOP MANAGER");
        titleLabel.setForeground(new Color(255, 69, 69));
        titleLabel.setBounds(560, 10, 400, 100);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        itemSearchFrame.add(titleLabel);

        // GUI components for search functionality
        searchLabel = new JLabel("SEARCH");
        searchLabel.setBounds(500, 50, 100, 60);
        panel.add(searchLabel);

        searchField = new JTextField("Please enter the item you want to buy");
        searchField.setBounds(600, 50, 300, 60);
        searchField.addActionListener(this);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(600, 150, 100, 40);
        searchButton.addActionListener(this);
        panel.add(searchButton);

        searchField.setVisible(true);
        panel.add(searchField);
        itemSearchFrame.setVisible(true);

        // GUI components for displaying search results and handling actions
        noFoundLabel = new JLabel("SORRY, PRODUCT NOT FOUND. TRY ANOTHER PRODUCT");
        noFoundLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        noFoundLabel.setBounds(150, 250, 1000, 400);
        panel.add(noFoundLabel);
        noFoundLabel.setVisible(false);

        addToCartButton = new JButton("ADD TO CART");
        addToCartButton.setBounds(600, 450, 180, 40);
        addToCartButton.addActionListener(this);
        panel.add(addToCartButton);
        addToCartButton.setVisible(false);

        viewCartButton = new JButton("VIEW CART");
        viewCartButton.setBounds(800, 450, 180, 40);
        viewCartButton.addActionListener(this);
        panel.add(viewCartButton);
        viewCartButton.setVisible(false);

        availableItemsLabel = new JLabel("AVAILABLE ITEMS");
        availableItemsLabel.setBounds(450, 300, 550, 40);
        availableItemsLabel.setVisible(false);
        panel.add(availableItemsLabel);

        // GUI component for displaying an image
        image = new ImageIcon("dynamic_search.png");
        JLabel picture = new JLabel(image);
        picture.setBounds(0, 40, 500, 400);
        panel.add(picture);

        // Back button to return to the main menu
        mainMenuButton = new JButton("Back");
        mainMenuButton.setBounds(10, 10, 100, 50);
        itemSearchFrame.add(mainMenuButton);
        mainMenuButton.setVisible(true);
        mainMenuButton.addActionListener(this);
    }

    // ActionPerformed method to handle button clicks and actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton || e.getSource() == searchField) {
            // Handle search button or enter key press
            if (searchCount > 0) {
                panel.remove(availableProductsComboBox);
            }
            searchCount += 1;
            String[] prodArray;
            File record = new File("items.txt");
            int n = 0;
            try {
                try (Scanner scanner = new Scanner(record)) {
                    ArrayList<String> availableProductsList = new ArrayList<>();

                    // Process each line in the items.txt file
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (Objects.equals(line, "item:quantity:sales price:purchase price")) {
                            continue;
                        }
                        prodArray = line.split(":");
                        if (prodArray.length == 1) {
                            continue;
                        }
                        String productName = prodArray[0];
                        String productPrice = prodArray[2];
                        String productInfo = productName + "  Price: " + productPrice;
                        if (productName.contains(searchField.getText())) {
                            availableProductsList.add(productInfo);
                            n += 1;
                        }
                    }

                    availableProductsArray = availableProductsList.toArray(new String[0]);
                }

                availableProductsComboBox = new JComboBox<>(availableProductsArray);

                if (availableProductsArray.length > 0) {
                    addToCartButton.setVisible(true);
                    availableItemsLabel.setVisible(true);
                    noFoundLabel.setVisible(false);

                    availableProductsComboBox.setBounds(600, 300, 300, 50);
                    availableProductsComboBox.setVisible(true);
                    panel.add(availableProductsComboBox);
                    availableProductsComboBox.addActionListener(this);

                } else {
                    noFoundLabel.setVisible(true);
                    availableProductsComboBox.setVisible(false);
                    availableItemsLabel.setVisible(false);
                    addToCartButton.setVisible(false);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == addToCartButton || e.getSource() == availableProductsComboBox) {
            // Handle adding selected item to the cart
            viewCartButton.setVisible(true);
            File cartFile = new File("cart.txt");
            try {
                FileWriter cartWriter = new FileWriter(cartFile, true);
                cartWriter.write(Objects.requireNonNull(availableProductsComboBox.getSelectedItem()).toString() + "\n");
                cartWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == mainMenuButton) {
            // Handle going back to the main menu
            try {
                new opening_page();
                itemSearchFrame.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == viewCartButton) {
            // Handle viewing the cart
            try {
                new veiw_cart();
                itemSearchFrame.dispose();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Main method to create an instance of the ItemSearch class
    public static void main(String[] args) {
        new DYNAMIC_SEARCH();
    }
}
