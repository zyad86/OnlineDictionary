package server.mysqlvps;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.control.cell.TextFieldListCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
/**
 * Created by stdzysta on 2016/12/17.
 */
public class trialclient extends JFrame {
    private JTextField jtf=new JTextField();
    private JTextArea jta=new JTextArea();
    private Socket socket;

    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    public static void main(String [] args){
        new trialclient();

    }

    public trialclient(){
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Enter radius"),BorderLayout.WEST);
        p.add(jtf,BorderLayout.CENTER);
        jtf.setHorizontalAlignment(JTextField.RIGHT);

        setLayout(new BorderLayout());
        add(p,BorderLayout.NORTH);
        add(new JScrollPane(jta),BorderLayout.CENTER);

        jtf.addActionListener(new TextFieldListener());

        setTitle("Client");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        boolean connected=false;

        while(!connected){
            try{
                 socket=new Socket("localhost",8000);
              //  double radius=Double.parseDouble(jtf.getText().trim());
                connected=true;
            }
            catch (IOException ex){
                jta.append(ex.toString()+"trying to reconnect in 1s \n");
            }
            if(!connected)
            try{
                Thread.sleep(1000);
            }
            catch(java.lang.InterruptedException e) {
                jta.append(e.toString()+'\n');
            }
        }
    }

    private class TextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try {
                toServer = new ObjectOutputStream(socket.getOutputStream());
                String loginuse = "test123411111111eee111111";
                String loginpasswd = "test";
                UserSet s = new UserSet(0,loginuse, loginpasswd);
                toServer.writeObject(s);
                //  toServer.writeDouble(radius);
                toServer.flush();
                fromServer = new ObjectInputStream(socket.getInputStream());
                try {
                    UserSet t = (UserSet) fromServer.readObject();
                    System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }catch (IOException ex1){ex1.printStackTrace();}
            jta.append("serviceType" +"\n");
            jta.append("Area received from the server is"+"\n");
            }
        }
    }
