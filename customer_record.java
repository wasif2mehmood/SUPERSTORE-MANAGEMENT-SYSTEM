import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class customer_record implements ActionListener {
    JFrame recwin;

JPanel panel;
    JTextArea recordTEXT;
    JTextField name,number;
    JLabel rname,rnumber,status;
    JButton submit,addtopaid,mainmenuBtn;
    customer_record(){
        recwin = new JFrame("FINAL ORDER");
        recwin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        recwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recwin.getContentPane().setBackground(new Color(106, 13, 173));
        recwin.setLayout(null);


        panel = new JPanel();
        panel.setBounds(100, 100, 600, 500);
        panel.setLayout(null);
        recwin.add(panel);



        rname=new JLabel("NAME");
        rname.setBounds(50,60,50,50);
        panel.add(rname);


        submit=new JButton("SUBMIT");
        submit.setBounds(50,300,100,50);
        panel.add(submit);
        submit.addActionListener(this::actionPerformed);

        addtopaid=new JButton("ADD TO PAID");
        addtopaid.setBounds(300,300,200,50);
        panel.add(addtopaid);
        addtopaid.setVisible(false);
        addtopaid.addActionListener(this::actionPerformed);


        name=new JTextField();
        name.setBounds(150,60,200,40);
        panel.add(name);


        rnumber=new JLabel("NUMBER");
        rnumber.setBounds(50,160,50,50);
        panel.add(rnumber);


        number=new JTextField();
        number.setBounds(150,160,200,40);
        panel.add(number);




        status=new JLabel("NO RECORD FOUND");
        status.setBounds(150,400,300,50);
        status.setFont((new Font("Serif", Font.PLAIN, 30)));
        panel.add(status);
        status.setVisible(false);

        recordTEXT = new JTextArea();
        recwin.setFont(new Font("Arial", Font.PLAIN, 15));
        recordTEXT.setBounds(770,50,600, 600);
        recordTEXT.setLineWrap(true);
        recwin.add(recordTEXT);
        recwin.setBackground(Color.black);

        mainmenuBtn = new JButton("back");
        mainmenuBtn.setBounds(10, 10, 100, 50);
        recwin.add(mainmenuBtn);
        mainmenuBtn.setVisible(true);
        mainmenuBtn.addActionListener(this::actionPerformed);






        recwin.setVisible(true);
    }

    public static void main(String[] args) {
        customer_record customer_record=new customer_record();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nrec="";
        String s="";
        String[] rec = new String[5];
        File PAID=new File("C:\\Users\\Wasif Mehmood\\IdeaProjects\\MY SHOP MANAGER\\src\\customerRECORD");
        if (e.getSource() == submit) {

            try {
                Scanner read=new Scanner(PAID);
                while (read.hasNextLine()){
                    String line=read.nextLine();
                    if(line.contains("{"+name.getText()+"}")&&line.contains("{"+number.getText()+"}")){
                        status.setVisible(false);
                    rec=line.split("\\|");
                        System.out.println(Arrays.toString(rec));
                    nrec+="NAME: "+name.getText()+" \nNUMBER: "+number.getText()+" \nTOTAL BILL:"+rec[2]+" \nDATE TIME:"+rec[3]+" \nBILLING STATUS:"+rec[4]+" \nITEMS BOUGHT:"+rec[5]+"\n";
                    if (Objects.equals(rec[4], "UN PAID")){
                        addtopaid.setVisible(true);

                    }
                    else {addtopaid.setVisible(false);}

                    }

                recordTEXT.setText(nrec);

                }
                if (rec[0]==null){status.setVisible(true);}
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        }
        if(e.getSource()==addtopaid){
            JOptionPane.showMessageDialog(null, "CUSTOMER ADDED TO PAID CATEGORY SUCCESSFULLY", "CATEGORY CHANGED", 1);
            String[] record = new String[330];
            try {
                Scanner reader=new Scanner(PAID);
                int n=0;
                while (reader.hasNextLine()){
                    String line=reader.nextLine();
                    rec=line.split("\\|");
                    if(line.contains("{"+name.getText()+"}")&&line.contains("{"+number.getText()+"}")) {
                        //System.out.println(Arrays.toString(rec));
                        rec[4]="PAID";
                         s+="NAME: "+name.getText()+" \nNUMBER: "+number.getText()+" \nTOTAL BILL:"+rec[2]+" \nDATE TIME:"+rec[3]+" \nBILLING STATUS:"+rec[4]+" \nITEMS BOUGHT:"+rec[5]+"\n";
                        recordTEXT.setText(s);
                    }

                    record[n]="{" + rec[0] + "}|{" + rec[1] + "}|" + rec[2]
                            + "|"
                            + rec[3]+"|"+ rec[4] +"|"+rec[5];
                            n+=1;
                    }

                FileWriter WRITE=new FileWriter(PAID);
                for (String S:record){
                    if (S==null){
                        continue;
                    }
                    WRITE.write(S+"\n");
                }
                WRITE.close();

                } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }
        if (e.getSource() == mainmenuBtn) {
            try {
                opening_page opening_page = new opening_page();
                recwin.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
