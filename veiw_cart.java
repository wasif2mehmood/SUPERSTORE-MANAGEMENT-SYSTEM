import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class veiw_cart implements ActionListener {
    String[] cartItems, updated_cart;
    JFrame cartWin;
    ImageIcon image;
    JLabel cLable,title,picture;
    JComboBox CartItems;
    JButton finOrd, remove_from_cart, mainmenu;
    JPanel panel;

    veiw_cart(String justfor_making_object) {
    }

    veiw_cart() throws FileNotFoundException {
        cartWin = new JFrame("CART");
        cartWin.setLayout(null);
        cartWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        cartWin.getContentPane().setBackground(new Color(106, 13, 173));
        cartWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        cartWin.add(panel);

        title = new JLabel("VIEW CART");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(590, 10, 400, 100);
        title.setFont((new Font("Serif", Font.PLAIN, 30)));
        cartWin.add(title);

        image = new ImageIcon("cart.png");

        picture = new JLabel(image);
        picture.setBounds(80, 10, 500, 470);
        panel.add(picture);

        cLable = new JLabel("CART ITEMS");
        cLable.setFont((new Font("Serif", Font.PLAIN, 30)));
        cLable.setBounds(650, 50, 700, 60);
        panel.add(cLable);
        cartWin.setVisible(true);
        File cart = new File("cart.txt");
        cartItems = new String[100];
        try (FileReader read = new FileReader(cart)) {
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (Scanner reader = new Scanner(cart)) {
            int n = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                cartItems[n] = line;
                n += 1;
            }
        }
        System.out.println(Arrays.toString(cartItems));
        CartItems = new JComboBox(cartItems);
        CartItems.setBounds(600, 150, 300, 50);
        CartItems.setVisible(true);
        panel.add(CartItems);
        panel.validate();
        panel.invalidate();
        panel.repaint();
        cartWin.invalidate();
        cartWin.validate();
        cartWin.repaint();

        remove_from_cart = new JButton("REMOVE FROM CART");
        remove_from_cart.setBounds(600, 400, 180, 40);
        panel.add(remove_from_cart);
        remove_from_cart.addActionListener(this::actionPerformed);

        finOrd = new JButton("FINALIZE ORDER");
        finOrd.setBounds(800, 400, 180, 40);
        if(cartItems[0]==null){finOrd.setVisible(false);
        remove_from_cart.setVisible(false);
        CartItems.setVisible(false);
            cartWin.invalidate();
            cartWin.validate();
            cartWin.repaint();
        cLable.setText("CART IS EMPTY");}
        panel.add(finOrd);
        panel.invalidate();
        panel.validate();
        panel.repaint();
        cartWin.invalidate();
        cartWin.validate();
        cartWin.repaint();
        finOrd.addActionListener(this::actionPerformed);

        mainmenu = new JButton("back");
        mainmenu.setBounds(10, 10, 100, 50);
        cartWin.add(mainmenu);
        mainmenu.setVisible(true);
        mainmenu.addActionListener(this::actionPerformed);

    }

    public static void main(String[] args) throws FileNotFoundException {
        veiw_cart l = new veiw_cart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File record = new File("cart.txt");
        ArrayList<String> newcart = new ArrayList<>();
        ArrayList<String> updatedList = new ArrayList<>();
        Collections.addAll(newcart, cartItems);

        if (e.getSource() == remove_from_cart) {

            panel.remove(CartItems);
            panel.invalidate();
            panel.validate();
            panel.repaint();
            cartWin.invalidate();
            cartWin.validate();
            cartWin.repaint();

            newcart.remove(Objects.requireNonNull(CartItems.getSelectedItem()).toString());
            cartItems = newcart.toArray(new String[100]);
            FileWriter w = null;
            try {
                w = new FileWriter(record);
                w.write("");
                w.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            for (String I : newcart) {
                try {

                    FileWriter WRITE = new FileWriter(record, true);
                    if (I == null)
                        continue;
                    System.out.println(I);
                    WRITE.write(I + "\n");
                    WRITE.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            if(cartItems[0]==null){
                remove_from_cart.setVisible(false);
                cLable.setText("CART IS EMPTY");
                CartItems.setVisible(false);
                finOrd.setVisible(false);
            }



            CartItems = new JComboBox(cartItems);
            CartItems.setBounds(600, 150, 300, 50);
            CartItems.setVisible(true);
            panel.add(CartItems);
            panel.validate();
            panel.invalidate();
            panel.repaint();
            cartWin.invalidate();
            cartWin.validate();
            cartWin.repaint();
            if(cartItems.length==0){finOrd.setVisible(false); }
        }
        if (e.getSource() == finOrd) {
            try {
                File items = new File("items.txt");
                try (Scanner reader = new Scanner(items)) {
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        String[] data = line.split(":");
                        for (String l : newcart) {
                            if (l == null) {
                                break;
                            }
                            String proName = l.split("  ")[0];
                            if (data[0].contains(proName)) {
                                System.out.println(proName);
                                int remQuan = Integer.parseInt(data[1]) - 1;
                                if (remQuan > 0) {
                                    data[1] = String.valueOf(remQuan);
                                    line = data[0] + ":" + data[1] + ":" + data[2] + ":" + data[3];
                                } else {
                                    line = "";
                                }
                            }
                        }
                        updatedList.add(line);

                    }
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                FileWriter writer = new FileWriter("items.txt");
                for (String l : updatedList) {
                    writer.write(l + "\n");

                }
                writer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                new final_order();
                cartWin.dispose();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
        if (e.getSource() == mainmenu) {
            try {
                new opening_page();
                cartWin.dispose();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
