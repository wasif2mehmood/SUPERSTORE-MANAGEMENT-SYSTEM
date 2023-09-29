import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class addItemsframe extends JFrame implements ActionListener {
    JFrame addtostore;
    JLabel itemnamelbl, NoOfItemslbl, purchasePriceLabel, SalePriceLabel, Itemspecificationlbl, noFound,title,picture;
    JTextField itemnametxtfield, NoOfItemstextfield, purchasePricetxtfield, SalePricetxtfield,
            itemspecificationttextField;
    JButton additemsbtn, BackButton;
    JComboBox AvailProdcmbobox;
    String[] availableProductsarray;
    int k = 0;
    JPanel panel;
    ImageIcon image;

    addItemsframe() {
        addtostore = new JFrame("Add items to Store");
        addtostore.setLayout(null);
        addtostore.getContentPane().setBackground(new Color(106, 13, 173));
        addtostore.setExtendedState(JFrame.MAXIMIZED_BOTH);
        addtostore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBounds(100, 100, 1200, 500);
        panel.setLayout(null);
        addtostore.add(panel);

        title = new JLabel("ITEMS ADDER");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        addtostore.add(title);


        image = new ImageIcon("add.png");

        picture = new JLabel(image);
        picture.setBounds(50, 10, 300, 470);
        panel.add(picture);

        itemnamelbl = new JLabel("Item name");
        itemnamelbl.setBounds(450, 50, 300, 60);
        panel.add(itemnamelbl);


        itemnametxtfield = new JTextField();
        itemnametxtfield.setBounds(550, 50, 300, 60);
        panel.add(itemnametxtfield);
        itemnametxtfield.addActionListener(this);

        noFound = new JLabel("THIS ITEM DOES NOT EXIST PREVIUOSLY PLEASE ADD IT ");

        // AvailProdcmbobox = new JComboBox<>();
        // AvailProdcmbobox.setBounds(350, 50, 250, 40);
        // addtostore.add(AvailProdcmbobox);

        NoOfItemslbl = new JLabel("Number of items");
        NoOfItemslbl.setBounds(450, 150, 300, 60);
        panel.add(NoOfItemslbl);

        NoOfItemstextfield = new JTextField();
        NoOfItemstextfield.setBounds(550, 150, 300, 60);
        panel.add(NoOfItemstextfield);

        purchasePriceLabel = new JLabel("Purchase Price");
        purchasePriceLabel.setBounds(450, 250, 300, 60);
        panel.add(purchasePriceLabel);

        purchasePricetxtfield = new JTextField();
        purchasePricetxtfield.setBounds(550, 250, 300, 60);
        panel.add(purchasePricetxtfield);

        SalePriceLabel = new JLabel("Sale Price");
        SalePriceLabel.setBounds(450, 350, 300, 60);
        panel.add(SalePriceLabel);

        SalePricetxtfield = new JTextField();
        SalePricetxtfield.setBounds(550, 350, 300, 60);
        panel.add(SalePricetxtfield);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == additemsbtn) {
            JOptionPane.showMessageDialog(null, "ITEM ADDED SUCCESSFULLY", "ITEM ADDED", 1);
            checkEmptyFields();
            checkNumber(NoOfItemstextfield.getText());
            checkNumber(SalePricetxtfield.getText());
            checkNumber(purchasePricetxtfield.getText());
            File itemsfile = new File("items.txt");
            String NewContent = "";
            boolean newItemCheck = true;
            try {

                try (Scanner itemreader = new Scanner(itemsfile)) {
                    while (itemreader.hasNextLine()) {

                        String line = itemreader.nextLine();
                        System.out.println(line);
                        String[] itemsData = line.split(":");

                        System.out.println(itemsData[0].equals(itemnametxtfield.getText()));
                        if (itemsData[0].equals(itemnametxtfield.getText())) {

                            NewContent += itemnametxtfield.getText() + ":"
                                    + (Integer.parseInt(NoOfItemstextfield.getText()) + Integer.parseInt(itemsData[1]))
                                    + ":" + SalePricetxtfield.getText() + ":" + purchasePricetxtfield.getText() + "\n";
                            System.out.println(NewContent);
                            newItemCheck = false;

                        } else {
                            NewContent += line + "\n";

                        }

                    }
                }
            } catch (Exception exc) {
                // TODO: handle exception
                exc.printStackTrace();
            }

            try {

                FileWriter item_writer = new FileWriter(itemsfile);
                item_writer.write(NewContent);
                item_writer.close();
                if (newItemCheck == true) {
                    FileWriter wr = new FileWriter(itemsfile, true);
                    System.out.println("hello");
                    wr.write(itemnametxtfield.getText() + ":" +
                            NoOfItemstextfield.getText() + ":"
                            + SalePricetxtfield.getText()
                            + ":" + purchasePricetxtfield.getText());
                    wr.close();
                }

            } catch (Exception ex) {
                // TODO: handle exception
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
                System.out.println(itemnametxtfield.getText());
                try (Scanner scanner = new Scanner(record)) {
                    availableProductsarray = new String[100];

                    while (scanner.hasNextLine()) {

                        String line = scanner.nextLine();
                        prodarray = line.split(":");
                        System.out.println(Arrays.toString(prodarray));
                        String prod = prodarray[0];

                        if (prodarray[0].contains(itemnametxtfield.getText())) {

                            availableProductsarray[n] = prod;
                            System.out.println("found");
                            n += 1;
                        }

                    }
                }
                // System.out.println(Arrays.toString(availableProductsarray));
                AvailProdcmbobox = new JComboBox(availableProductsarray);
                if (!Objects.equals(availableProductsarray[0], null)) {
                    // addToCartBtn.setVisible(true);
                    noFound.setVisible(false);

                    AvailProdcmbobox.setBounds(900, 50, 200, 40);
                    AvailProdcmbobox.setVisible(true);
                    panel.add(AvailProdcmbobox);
                    AvailProdcmbobox.addActionListener(this);

                    addtostore.invalidate();
                    addtostore.validate();
                    addtostore.repaint();
                } else {
                    System.out.println("else chal rha ha");
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

    public static void main(String[] args) {
        try {
new addItemsframe();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void checkEmptyFields() {
        if (itemnametxtfield.getText().trim().isEmpty() || NoOfItemstextfield.getText().trim().isEmpty()
                || purchasePricetxtfield.getText().trim().isEmpty()
                || SalePricetxtfield.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Missing fields", "invalid Data", 0);

            throw new InputMismatchException();
        }

    }

    void checkNumber(String number) {
        try {

            for (int i = 0; i < number.length(); i++) {
                Integer.parseInt(Character.toString((number.charAt(i))));
            }
        } catch (Exception e) {

            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Enter valid amount", "invalid Data", 0);
            throw new InputMismatchException();

        }
    }
}
