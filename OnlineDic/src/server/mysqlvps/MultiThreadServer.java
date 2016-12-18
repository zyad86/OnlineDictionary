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
    ArrayList <String> usersOnline =new ArrayList<String>();

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
    class checkOnlineOrNot implements Runnable{
        /**
         * every time every oprate will add to onlineList
         * every
         */

        public void run(){







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
                         * 0  register return error code
                         */
                        case 0: {
                            String userLogin=getfromClient.getUser_login();
                            String userPasswd=getfromClient.getUser_passwd();
                            int errorCode=MySQLConnect.sigUpNewUser(userLogin,userPasswd);
                            switch (errorCode){
                                case 0: jta.append("User Regist Success:" + getfromClient.getUser_login() + '\n');break;
                                case 1: jta.append("User_login name already exist:" + getfromClient.getUser_login() + '\n');break;
                                case 2: jta.append("Invalid Username Please Retry:" + getfromClient.getUser_login() + '\n');break;
                                default:jta.append("default nothing ");break;
                            }

                            UserSet returnSet=new UserSet(0,errorCode);
                            outputToClient.writeObject(returnSet);
                        } break;
                        /**
                         *  login return error code
                         */
                        case 1: {
                            String userLogin=getfromClient.getUser_login();
                            String userPasswd=getfromClient.getUser_passwd();
                            int errorCode=MySQLConnect.loginOldUser(userLogin,userPasswd);
                            switch (errorCode){
                                case 0: jta.append("User Login Success:" + getfromClient.getUser_login() + '\n');break;
                                case 1: jta.append("User_login Wrong Password:" + getfromClient.getUser_login() + '\n');break;
                                case 2: jta.append("User_login name not exist:" + getfromClient.getUser_login() + '\n');break;
                                default:jta.append("default nothing ");break;
                            }
                            UserSet returnSet=new UserSet(1,errorCode);
                            outputToClient.writeObject(returnSet);
                        }break;
                        /**
                         * No using function  it is preset And no used after
                         */
                        case 2: {} break;
                        /**
                         * Search Word
                         */
                        case 3: {
                            String word=getfromClient.getWord();
                            UserSet returnSet=new UserSet(parserjson.YouDaoTranslate(word),
                                    parserjson.BingTranslate(word),
                                    parserjson.JinShanTranslate(word));
                            outputToClient.writeObject(returnSet);
                        } break;
                        /**
                         * User favor one company's word translation
                         */
                        case 4: {
                            MySQLAffection.UserAffectionSend(getfromClient.getUser_login(),
                                    getfromClient.getWord(),getfromClient.getFavorCompany());
                        } break;
                        /**
                         *Return favor Count
                         */
                        case 5: {
                            int []favorCount=MySQLAffection.ReturnFavor(getfromClient.getWord());
                            UserSet returnSet=new UserSet(favorCount);
                            outputToClient.writeObject(returnSet);
                        } break;
                        /**
                         *See All people and people online
                         *
                         */
                        case 6: {
                            String allUsers=MySQLConnect.returnUserlist();
                            String UsersOnline="";
                            for(int i=0;i<usersOnline.size();i++){
                                UsersOnline+=usersOnline.get(i);
                            }
                            UserSet returnSet=new UserSet(allUsers,UsersOnline);
                            outputToClient.writeObject(returnSet);
                        }break;
                        /**
                         *Send WordCard
                         */
                        case 7: {
                            //发送的同时刷新一下动态吧
                        }

                        case 8: {
                            String  user_Login=getfromClient.getWord();





                        }
                        default:{} break;

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

/**
 * Client call function
 *0
 *
 * * errorcode
 * sign up
 * 0:success
 * 1:userlogin exist
 * 2:invalid username or length out of range
 toServer = new ObjectOutputStream(socket.getOutputStream());
 String loginuse = "test123411111111eee";
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
 *
 * 1
 toServer = new ObjectOutputStream(socket.getOutputStream());
 String loginuse = "test123411111111eee";
 String loginpasswd = "test";
 UserSet s = new UserSet(1,loginuse, loginpasswd);
 toServer.writeObject(s);
 toServer.flush();
 fromServer = new ObjectInputStream(socket.getInputStream());
 try {
 UserSet t = (UserSet) fromServer.readObject();
 System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
 } catch (ClassNotFoundException ex) {
 ex.printStackTrace();
 }
 *
 * 3
 toServer = new ObjectOutputStream(socket.getOutputStream());
 String word = "test";
 UserSet s = new UserSet(3,word);
 toServer.writeObject(s);
 toServer.flush();
 fromServer = new ObjectInputStream(socket.getInputStream());
 try {
 UserSet t = (UserSet) fromServer.readObject();
 String Youdao=t.getYouDaoTranslation();
 String JinShan=t.getJinshanTranslation();
 String Bing=t.getBingTranslation();
 System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
 } catch (ClassNotFoundException ex) {
 ex.printStackTrace();
 }
 *
 *4
 用户点赞单词没有返hui
 *
 *
 * 5
 toServer = new ObjectOutputStream(socket.getOutputStream());
 UserSet s = new UserSet(5);
 toServer.writeObject(s);
 toServer.flush();
 fromServer = new ObjectInputStream(socket.getInputStream());
 try {
 UserSet t = (UserSet) fromServer.readObject();
    int []favorCount=t.getFavorCount();
 System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
 } catch (ClassNotFoundException ex) {
 ex.printStackTrace();
 }
 *
 *6
 toServer = new ObjectOutputStream(socket.getOutputStream());
 UserSet s = new UserSet(6);
 toServer.writeObject(s);
 toServer.flush();
 fromServer = new ObjectInputStream(socket.getInputStream());
 try {
 UserSet t = (UserSet) fromServer.readObject();
 String all_User=t.getAll_User();
 String user_Online=t.getUser_Online();
 System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
 } catch (ClassNotFoundException ex) {
 ex.printStackTrace();
 *
 */

/**
 * define ServiceType
 * 0  注册
 * 1  登录
 * 2  返回值确定注册和登录的errorcode
 * 3  查询
 * 4  用户点赞单词
 * 5  每隔2s返回目前某单词的三个翻译的排行
 * 6  查看所有人员 以及在线离线状况
 * 7  发送单词卡
 * 8  查看动态 发送单词卡的
 */

/**
 * errorcode
 * sign up
 * 0:success
 * 1:userlogin exist
 * 2:invalid username or length out of range
 *
 * log in
 * define 0:login success
 * 1:wrong passwd
 * 2:User didn't exist
 *
 * //Define Company
 * YouDao 1
 * Bing 2
 * JinShan 3
 */