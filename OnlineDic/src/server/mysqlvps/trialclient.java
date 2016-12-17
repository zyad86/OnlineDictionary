package server.mysqlvps;


import javafx.scene.control.cell.TextFieldListCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by stdzysta on 2016/12/17.
 */
public class trialclient extends JFrame {

    private JTextField jtf=new JTextField();

    private JTextArea jta=new JTextArea();

    private DataOutputStream toServer;
    private DataInputStream fromServer;
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
                Socket socket=new Socket("localhost",8000);
                fromServer=new DataInputStream(socket.getInputStream());
                toServer=
                        new DataOutputStream(socket.getOutputStream());
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
            try{
                double radius=Double.parseDouble(jtf.getText().trim());
                toServer.writeDouble(radius);
                toServer.flush();

                double area=fromServer.readDouble();

                jta.append("Radius is" +radius+"\n");
                jta.append("Area received from the server is"+area+"\n");
            }
            catch(IOException ex){
                System.err.println(ex);
            }
        }

    }
}
