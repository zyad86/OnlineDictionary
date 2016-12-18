package server.mysqlvps;
 import jdk.internal.org.objectweb.asm.Handle;
 import java.io.*;
 import java.net.*;
 import java.util.*;
 import java.awt.*;
 import javax.swing.*;
 import jsonparser.parserjson;

/**
 * Created by stdzysta on 2016/12/17.
 */
public class MultiThreadServer  extends JFrame{
    private JTextArea jta=new JTextArea();
    public static void main(String[] args){
        new MultiThreadServer();
    }

    public MultiThreadServer() {
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("MultiThreadServer");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("Server started at" + new Date() + '\n');
            int clientNo = 1;
            while (true) {
                Socket socket = serverSocket.accept();
                jta.append("Starting thread for client" + clientNo +
                        "at" + new Date() + "\n");
                //socket.getRemoteSocketAddress();
                InetAddress inetAddress = socket.getInetAddress();
                jta.append("Client" + clientNo + "'s host name is " +
                        inetAddress.getHostName() + "\n");
                jta.append("Client" + clientNo + "'s IP Address is"
                        + socket.getRemoteSocketAddress()+ "\n");

                HandleAClient task = new HandleAClient(socket);
                new Thread(task).start();
                clientNo++;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    class HandleAClient implements Runnable {
        private Socket socket;

        public HandleAClient(Socket socket){
            this.socket=socket;
        }
        public void run(){
            try{
                ObjectInputStream inputFromClient=new ObjectInputStream(
                        socket.getInputStream());
                ObjectOutputStream outputToClient=new ObjectOutputStream(
                        socket.getOutputStream());
                while(true) {
                    UserSet getfromClient=(UserSet) inputFromClient.readObject();

                    switch (getfromClient.getServiceType())
                    {
                        /**
                         * 0  注册 return error code
                         */
                        case 0: {
                            String userLogin=getfromClient.getUser_login();
                            String userPasswd=getfromClient.getUser_passwd();
                            int errorCode=MySQLConnect.sigUpNewUser(userLogin,userPasswd);
                            jta.append("Receive Object:" + getfromClient.getUser_login() + '\n'
                                    +getfromClient.getUser_passwd()+"\t"+errorCode);

                            UserSet returnSort=new UserSet(0,errorCode);
                            outputToClient.writeObject(returnSort);

                        } ;break;
                        case 1: {} ;break;
                        case 2: {} ;break;
                        case 3: {} ;break;
                        case 4: {} ;break;
                        case 5: {} ;break;
                        case 6: {} ;break;
                        case 7: {} ;break;
                        default:{};break;

                    }

                }
            }catch (IOException e){
                System.err.println(e);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}


/**/