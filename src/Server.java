/**
 * Created by zhangyi on 2016/11/22.
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;



public class Server extends JFrame{

    private JTextArea jta=new JTextArea();

    public static void main(String[] args){
        new Server();
    }

    public Server(){
        setLayout(new BorderLayout());
        add(new JScrollPane(jta),BorderLayout.CENTER);

        setTitle("Server");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try{
            ServerSocket serverSocket=new ServerSocket(8000);
            jta.append("Server started at "+new Date()+'\n');

            Socket socket=serverSocket.accept();

            DataInputStream inputFromClient=new DataInputStream(
                    socket.getInputStream());
            DataOutputStream outputToClient=new DataOutputStream(
                    socket.getOutputStream());

            while(true){
                double radius =inputFromClient.readDouble();
                double area=radius*radius*Math.PI;
                outputToClient.writeDouble(area);
                jta.append("Radius received from client:"+radius+'\n');
                jta.append("Area found:"+area+'\n');
            }
        }
        catch(IOException ex){
            System.err.println(ex);
        }
    }
}