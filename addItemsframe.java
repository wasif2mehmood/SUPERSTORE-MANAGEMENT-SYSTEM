import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * The addItemsframe class represents a GUI application for adding items to a store.
 * It extends JFrame and implements ActionListener.
 */
public class addItemsframe extends JFrame implements ActionListener {
    // JFrame and components for the UI
    JFrame addtostore;
    JLabel itemnamelbl, NoOfItemslbl, purchasePriceLabel, SalePriceLabel, Itemspecificationlbl, noFound, title, picture;
    JTextField itemnametxtfield, NoOfItemstextfield, purchasePricetxtfield, SalePricetxtfield,
            itemspecificationttextField;
    JButton additemsbtn, BackButton;
    JComboBox AvailProdcmbobox;
    String[] availableProductsarray;
    int k = 0;
    JPanel panel;
    ImageIcon image;

    /**
     * Constructor for the addItemsframe class. Sets up the UI components.
     */
    addItemsframe() {
        // Setting up the main JFrame
        addtostore = new JFrame("Add items to Store");
        addtostore.setLayout(null);
        addtostore.getContentPane().setBackground(new Color(106, 13, 173));
        addtostore.setExtendedState(JFrame.MAXIMIZED_BOTH);
        addtostore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating and configuring a JPanel for components
        panel = new JPanel();
        panel.setBounds(100, 100, 1200, 500);
        panel.setLayout(null);
        addtostore.add(panel);

        // Adding a title label to the UI
        title = new JLabel("ITEMS ADDER");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        addtostore.add(title);

        // Adding an image to the UI
        image = new ImageIcon("add.png");
        picture = new JLabel(image);
        picture.setBounds(50, 10, 300, 470);
        panel.add(picture);

        // Adding labels and text fields for item details
        itemnamelbl = new JLabel("Item name");
        itemnamelbl.setBounds(450, 50, 300, 60);
        panel.add(itemnamelbl);

        itemnametxtfield = new JTextField();
        itemnametxtfield.setBounds(550, 50, 300, 60);
        panel.add(itemnametxtfield);
        itemnametxtfield.addActionListener(this);

        // Adding labels and text fields for the number of items
        NoOfItemslbl = new JLabel("Number of items");
        NoOfItemslbl.setBounds(450, 150, 300, 60);
        panel.add(NoOfItemslbl);

        NoOfItemstextfield = new JTextField();
        NoOfItemstextfield.setBounds(550, 150, 300, 60);
        panel.add(NoOfItemstextfield);

        // Adding labels and text fields for purchase price
        purchasePriceLabel = new JLabel("Purchase Price");
        purchasePriceLabel.setBounds(450, 250, 300, 60);
        panel.add(purchasePriceLabel);

        purchasePricetxtfield = new JTextField();
        purchasePricetxtfield.setBounds(550, 250, 300, 60);
        panel.add(purchasePricetxtfield);

        // Adding labels and text fields for sale price
        SalePriceLabel = new JLabel("Sale Price");
        SalePriceLabel.setBounds(450, 350, 300, 60);
        panel.add(SalePriceLabel);

        SalePricetxtfield = new JTextField();
        SalePricetxtfield.setBounds(550, 350, 300, 60);
        panel.add(SalePricetxtfield);

        // Adding buttons for adding items and going back
        additemsbtn = new JButton("Add Item");
        additemsbtn.setBounds(550, 450, 150, 40);
        panel.add(additemsbtn);
        additemsbtn.addActionListener(this);

        BackButton = new JButton("back");
        BackButton.setBounds(10, 10, 100, 50);
        addtostore.add(BackButton);
        BackButton.addActionListener(this);

        addtostore.setVisible(true);
    }

    /**
     * Handles action events triggered by components.
     *
     * @param e The ActionEvent object representing the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == additemsbtn) {
            // Display a success message
            JOptionPane.showMessageDialog(null, "ITEM ADDED SUCCESSFULLY", "ITEM ADDED", 1);
            // Check for empty fields and valid numbers
            checkEmptyFields();
            checkNumber(NoOfItemstextfield.getText());
            checkNumber(SalePricetxtfield.getText());
            checkNumber(purchasePricetxtfield.getText());
            // File handling to update or add items
            File itemsfile = new File("items.txt");
            String NewContent = "";
            boolean newItemCheck = true;
            try {
                try (Scanner itemreader = new Scanner(itemsfile)) {
                    while (itemreader.hasNextLine()) {
                        String line = itemreader.nextLine();
                        String[] itemsData = line.split(":");
                        if (itemsData[0].equals(itemnametxtfield.getText())) {
                            NewContent += itemnametxtfield.getText() + ":"
                                    + (Integer.parseInt(NoOfItemstextfield.getText()) + Integer.parseInt(itemsData[1]))
                                    + ":" + SalePricetxtfield.getText() + ":" + purchasePricetxtfield.getText() + "\n";
                            newItemCheck = false;
                        } else {
                            NewContent += line + "\n";
                        }
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            try {
                FileWriter item_writer = new FileWriter(itemsfile);
                item_writer.write(NewContent);
                item_writer.close();
                if (newItemCheck) {
                    FileWriter wr = new FileWriter(itemsfile, true);
                    wr.write(itemnametxtfield.getText() + ":" +
                            NoOfItemstextfield.getText() + ":"
                            + SalePricetxtfield.getText()
                            + ":" + purchasePricetxtfield.getText());
                    wr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(itemnametxtfield)) {
            if (k > 0) {
                panel.remove(AvailProdcmbobox);
                panel.invalidate();
                panel.validate();
                panel.repaint();
            }
            k += 1;
            String[] prodarray;
            File record = new File("items.txt");
            int n = 0;
            try {
                try (Scanner scanner = new Scanner(record)) {
                    availableProductsarray = new String[100];
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        prodarray = line.split(":");
                        String prod = prodarray[0];
                        if (prodarray[0].contains(itemnametxtfield.getText())) {
                            availableProductsarray[n] = prod;
                            n += 1;
                        }
                    }
                }
                AvailProdcmbobox = new JComboBox(availableProductsarray);
                if (!Objects.equals(availableProductsarray[0], null)) {
                    noFound.setVisible(false);
                    AvailProdcmbobox.setBounds(900, 50, 200, 40);
                    AvailProdcmbobox.setVisible(true);
                    panel.add(AvailProdcmbobox);
                    AvailProdcmbobox.addActionListener(this);
                    addtostore.invalidate();
                    addtostore.validate();
                    addtostore.repaint();
                } else {
                    addtostore.remove(AvailProdcmbobox);
                    noFound.setBounds(850, 50, 400, 50);
                    noFound.setVisible(true);
                    panel.add(noFound);
                    panel.invalidate();
                    panel.validate();
                    panel.repaint();
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (Exception exc) {
                // Handle exception
            }
        }

        if (e.getSource().equals(AvailProdcmbobox)) {
            itemnametxtfield.setText(AvailProdcmbobox.getSelectedItem().toString());
        }

        if (e.getSource().equals(BackButton)) {
            try {
                new opening_page();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            addtostore.dispose();
        }
    }

    /**
     * Main method to launch the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        try {
            new addItemsframe();
        } catch (Exception e) {
            // Handle exception
        }
    }

    /**
     * Checks for empty fields in the UI.
     * If any field is empty, it displays a message and throws an InputMismatchException.
     */
    public void checkEmptyFields() {
        if (itemnametxtfield.getText().trim().isEmpty() || NoOfItemstextfield.getText().trim().isEmpty()
                || purchasePricetxtfield.getText().trim().isEmpty()
                || SalePricetxtfield.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Missing fields", "invalid Data", 0);
            throw new InputMismatchException();
        }
    }

    /**
     * Checks if a given string represents a valid number.
     *
     * @param number The string to be checked.
     * @throws InputMismatchException If the string is not a valid number.
     */
    void checkNumber(String number) {
        try {
            for (int i = 0; i < number.length(); i++) {
                Integer.parseInt(Character.toString((number.charAt(i))));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter valid amount", "invalid Data", 0);
            throw new InputMismatchException();
        }
    }
}
