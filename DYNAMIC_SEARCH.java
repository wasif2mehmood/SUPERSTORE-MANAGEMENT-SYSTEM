import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class DYNAMIC_SEARCH extends JFrame implements ActionListener {
    int k;
    JTextField searchField;
    JLabel search, noFound, available_items,picture,title;
    JButton searcherBtn, addToCartBtn, mainmenuBtn,CART;
    JFrame dynamicSearchframe;
    String[] availableProductsarray;
    JComboBox AvailProdcmbobox;

    JPanel panel;

    ImageIcon image;

    DYNAMIC_SEARCH() {
        k = 0;
        dynamicSearchframe = new JFrame("DYNAMIC SEARCH");
        dynamicSearchframe.setLayout(null);
        dynamicSearchframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        dynamicSearchframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dynamicSearchframe.getContentPane().setBackground(new Color(106, 13, 173));

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        dynamicSearchframe.add(panel);

        title = new JLabel("MY SHOP MANAGER");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(560, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        dynamicSearchframe.add(title);

        search = new JLabel("SEARCH");
        search.setBounds(500, 50, 100, 60);
        panel.add(search);

        searchField = new JTextField("please enter name item you want to buy");
        searchField.setBounds(600, 50, 300, 60);
        searchField.addActionListener(this);

        searcherBtn = new JButton("SEARCH");
        searcherBtn.setBounds(600, 150, 100, 40);
        searcherBtn.addActionListener(this::actionPerformed);
        panel.add(searcherBtn);

        searchField.setVisible(true);
        panel.add(searchField);
        dynamicSearchframe.setVisible(true);

        noFound = new JLabel("SORRY PRODUCT NOT FOUND TRY ANOTHER PRODUCT");
        noFound.setFont((new Font("Serif", Font.PLAIN, 30)));
        noFound.setBounds(150, 250, 1000, 400);
        panel.add(noFound);
        noFound.setVisible(false);

        addToCartBtn = new JButton("QUANTITY + 1");
        addToCartBtn.setBounds(600, 450, 180, 40);
        addToCartBtn.addActionListener(this::actionPerformed);
        panel.add(addToCartBtn);
        addToCartBtn.setVisible(false);


        CART = new JButton("VIEW CART");
        CART.setBounds(800, 450, 180, 40);
        CART.addActionListener(this::actionPerformed);
        panel.add(CART);
        CART.setVisible(false);


        available_items = new JLabel("AVAILABLE ITEMS");
        available_items.setBounds(450, 300, 550, 40);
        available_items.setVisible(false);
        panel.add(available_items);


        image = new ImageIcon("dynamic search.png");

        picture = new JLabel(image);
        picture.setBounds(0,40, 500, 400);
        panel.add(picture);
        panel.invalidate();
        panel.validate();
        panel.repaint();


        mainmenuBtn = new JButton("back");
        mainmenuBtn.setBounds(10, 10, 100, 50);
        dynamicSearchframe.add(mainmenuBtn);
        dynamicSearchframe.invalidate();
        dynamicSearchframe.validate();
        dynamicSearchframe.repaint();
        mainmenuBtn.setVisible(true);
        mainmenuBtn.addActionListener(this::actionPerformed);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searcherBtn ||e.getSource().equals(searchField)) {
            if (k > 0) {

                panel.remove(AvailProdcmbobox);
            }
            k += 1;
            String[] prodarray;
            File record = new File("items.txt");
            int n = 0;
            try {
                System.out.println(searchField.getText());
                try (Scanner scanner = new Scanner(record)) {
                    availableProductsarray = new String[100];

                    while (scanner.hasNextLine()) {

                        String line = scanner.nextLine();
                        if(Objects.equals(line, "item:quantity:sales price:purchase price")){continue;}
                        prodarray = line.split(":");
                        System.out.println(Arrays.toString(prodarray));
                        if (prodarray.length==1){continue;}
                        String prod = prodarray[0] + "  price: " + prodarray[2];
                        if (prodarray[0].contains(searchField.getText())) {
                            availableProductsarray[n] = prod;
                            System.out.println("goood");
                            n += 1;
                        }

                    }
                }
                System.out.println(Arrays.toString(availableProductsarray));
                AvailProdcmbobox = new JComboBox(availableProductsarray);
                if (!Objects.equals(availableProductsarray[0], null)) {
                    addToCartBtn.setVisible(true);
                    available_items.setVisible(true);
                    noFound.setVisible(false);
                    // System.out.println("///////////////////////");
                    AvailProdcmbobox.setBounds(600, 300, 300, 50);
                    AvailProdcmbobox.setVisible(true);
                    panel.add(AvailProdcmbobox);
                    AvailProdcmbobox.addActionListener(this);
                    panel.invalidate();
                    panel.validate();
                    panel.repaint();
                    dynamicSearchframe.invalidate();
                    dynamicSearchframe.validate();
                    dynamicSearchframe.repaint();
                } else {
                    noFound.setVisible(true);
                    AvailProdcmbobox.setVisible(false);
                    available_items.setVisible(false);
                    addToCartBtn.setVisible(false);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == addToCartBtn||e.getSource().equals(AvailProdcmbobox)) {
            CART.setVisible(true);
            File cartF = new File("cart.txt");
            try {
                FileWriter cartWriter = new FileWriter(cartF, true);
                cartWriter.write(Objects.requireNonNull(AvailProdcmbobox.getSelectedItem()).toString() + "\n");
                cartWriter.close();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        if (e.getSource() == mainmenuBtn) {
            try {
                new opening_page();
                dynamicSearchframe.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==CART){
            try {
                new veiw_cart();
                dynamicSearchframe.dispose();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static void main(String[] args) {
        new DYNAMIC_SEARCH();
    }

}