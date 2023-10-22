import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * The `profitlosscalculator` class represents a profit and loss calculator application.
 */
public class ProfitLossCalculator implements ActionListener {
    JDatePickerImpl datePicker;

    JLabel label, stat, total_sales, earnings, title;
    JFrame win;
    JButton sub, mainmenuBtn;
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
    JPanel panel;

    /**
     * Constructor to set up the profit and loss calculator application.
     */
    ProfitLossCalculator() {
        win = new JFrame("PROFIT LOSS CALCULATOR");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.getContentPane().setBackground(new Color(106, 13, 173));
        win.setExtendedState(JFrame.MAXIMIZED_BOTH);
        win.setLayout(null);

        panel = new JPanel();
        panel.setBounds(200, 100, 1000, 500);
        panel.setLayout(null);
        win.add(panel);

        title = new JLabel("PROFIT LOSS CALCULATOR");
        title.setForeground(new Color(255, 69, 69));
        title.setBounds(500, 10, 400, 100);
        title.setFont(new Font("Serif", Font.PLAIN, 30));
        win.add(title);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(50, 50, 200, 200);
        panel.add(datePicker);

        label = new JLabel("PLEASE SELECT DATE YOU WANT TO CACLULATE PROFIT LOSS");
        label.setBounds(50, 10, 500, 30);
        panel.add(label);

        stat = new JLabel("NO RECORD FOR SELECTED DATE");
        stat.setBounds(250, 200, 500, 30);
        stat.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(stat);
        stat.setVisible(false);

        total_sales = new JLabel();
        total_sales.setBounds(250, 200, 700, 30);
        total_sales.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(total_sales);

        mainmenuBtn = new JButton("back");
        mainmenuBtn.setBounds(10, 10, 100, 50);
        win.add(mainmenuBtn);
        mainmenuBtn.setVisible(true);
        mainmenuBtn.addActionListener(this::actionPerformed);

        earnings = new JLabel();
        earnings.setBounds(250, 400, 700, 30);
        earnings.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(earnings);

        sub = new JButton("SUBMIT");
        sub.setBounds(50, 300, 100, 40);
        panel.add(sub);
        panel.invalidate();
        panel.validate();
        panel.repaint();
        sub.addActionListener(this::actionPerformed);
        win.setVisible(true);
    }

    public static void main(String[] args) {
        ProfitLossCalculator profitlosscalculator = new ProfitLossCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] sold = new String[200];
        int m = 0;
        if (e.getSource() == sub) {
            int TOTAL_SALES = 0, TOTAL_PURCHASE = 0;
            File file = new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\SHOP MANAGEMENT SYSTEM\\src\\customerRECORD");
            try {
                try (Scanner reader = new Scanner(file)) {
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        line = line.replace("[", "");
                        line = line.replace("]", "");

                        if (line.contains(datePicker.getJFormattedTextField().getText())) {
                            stat.setVisible(false);
                            earnings.setVisible(true);
                            total_sales.setVisible(true);
                            String[] arr = line.split("  ");
                            System.out.println(Arrays.toString(arr));

                            for (int n = 1; n < arr.length; n += 2) {
                                sold[m] = arr[n];
                                TOTAL_SALES += Integer.parseInt(arr[n + 1].split(": ")[1]);
                                m += 1;
                            }
                        }
                    }
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
                File item = new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\SHOP MANAGEMENT SYSTEM\\src\\items.txt");
                try (Scanner read = new Scanner(item)) {
                    while (read.hasNextLine()) {
                        String line = read.nextLine();
                        String[] array = line.split(":");
                        for (String s : sold) {
                            if (s == null) {
                                continue;
                            }
                            System.out.println(s);
                            if (line.contains(s)) {
                                System.out.println(line);
                                TOTAL_PURCHASE += Integer.parseInt(array[3]);
                            }
                        }
                    }
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }

                earnings.setVisible(true);
                total_sales.setVisible(true);
                if (TOTAL_PURCHASE == 0 || TOTAL_SALES == 0) {
                    stat.setVisible(true);
                    earnings.setVisible(false);
                    total_sales.setVisible(false);
                }
                total_sales.setText(TOTAL_SALES + " IS YOUR TOTAL SALES ON " + datePicker.getJFormattedTextField().getText());
                earnings.setText(TOTAL_SALES - TOTAL_PURCHASE + " IS YOUR TOTAL EARNING ON " + datePicker.getJFormattedTextField().getText());
                System.out.println(TOTAL_SALES);
                System.out.println(TOTAL_PURCHASE);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == mainmenuBtn) {
            try {
                new OpeningPage();
                win.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
