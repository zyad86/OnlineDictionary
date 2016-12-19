package OnDic;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.mysqlvps.UserSet;

import javax.swing.*;

public class MainController implements Initializable {

    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    boolean ObjectStreamCreate = false;
    boolean loginSuccess = false;
    //boolean checkOnline=false;
    Stage mainStage = new Stage();
    Stage wordStage = new Stage();
    public static String userid;
    public static String userpass;

    /*
    *
    * log
    *
    *
    *
     */



    @FXML
    private TextField edit_log;
    @FXML
    private TextField edit_pass;
    @FXML
    private Button bt_log;
    @FXML
    private Button bt_regist;
    @FXML
    private Label if_leagel;
    @FXML
    private Label if_empty;

    @FXML
    private void onClickLog(ActionEvent event) {//?????button???????
        userid = edit_log.getText();
        userpass = edit_pass.getText();
        if_empty.setText("");
        if_leagel.setText("");
        if (userpass.equals(""))
            if_empty.setText("密码不能为空!");
        if (userid.equals(""))
            if_leagel.setText("用户名不能为空!");
        if (!userpass.equals("") && !userid.equals("")) {
            int temp = login();
            if (temp == 0) {
                bt_log.getScene().getWindow().hide();
                MainStage();
            } else if (temp == 1)
                if_empty.setText("密码错误");
            else if (temp == 2)
                if_leagel.setText("用户不存在");

        }
    }

    public int login() {
        /*
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket("localhost", 8000);
                //  double radius=Double.parseDouble(jtf.getText().trim());
                connected = true;
            } catch (IOException ex) {
                //               jta.append(ex.toString()+"trying to reconnect in 1s \n");
            }
            if (!connected)
                try {
                    Thread.sleep(1000);
                } catch (java.lang.InterruptedException e) {
//                    jta.append(e.toString()+'\n');
                }
        }*/

        int temp = 0;
        try {
            //  toServer = new ObjectOutputStream(socket.getOutputStream());
            String loginuse = userid;
            String loginpasswd = userpass;
            UserSet s = new UserSet(1, loginuse, loginpasswd);
            toServer.writeObject(s);
            //  toServer.writeDouble(radius);
            toServer.flush();
            //  fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
                temp = t.getErrorcode();
                //               System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return temp;
    }

    @FXML
    private void onClickRegist(ActionEvent event) {//??????button???????
        if_empty.setText("");
        if_leagel.setText("");
        userid = edit_log.getText();
        userpass = edit_pass.getText();
        if (userpass.equals(""))
            if_empty.setText("密码不能为空!");
        if (userid.equals(""))
            if_leagel.setText("用户名不能为空!");
        if (!userpass.equals("") && !userid.equals("")) {
            int temp = regist();
            if (temp == 0) {
                bt_log.getScene().getWindow().hide();
                MainStage();
            } else if (temp == 1) {
                if_leagel.setText("用户名已存在");
                System.out.println("用户名已存在");
            } else if (temp == 2)
                if_leagel.setText("用户名不合法或者过长");
        }
    }

    public int regist() {
        /*
        boolean connected=false;
        while(!connected){
            try{
                socket=new Socket("localhost",8000);
                //  double radius=Double.parseDouble(jtf.getText().trim());
                connected=true;
            }
            catch (IOException ex){
                //               jta.append(ex.toString()+"trying to reconnect in 1s \n");
            }
            if(!connected)
                try{
                    Thread.sleep(1000);
                }
                catch(java.lang.InterruptedException e) {
//                    jta.append(e.toString()+'\n');
                }
        }
*/
        int temp = 0;
        try {
            // toServer = new ObjectOutputStream(socket.getOutputStream());
            String loginuse = userid;
            String loginpasswd = userpass;
            UserSet s = new UserSet(0, loginuse, loginpasswd);
            toServer.writeObject(s);
            //  toServer.writeDouble(radius);
            toServer.flush();
            //   fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
                temp = t.getErrorcode();
//                System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return temp;
    }




/*
*
*
* main
*
* */


    @FXML
    public Label username;
    /*           ???????                                             */
    public static String input;
    @FXML
    private Button search;
    @FXML
    private TextField inputfield;
    @FXML
    private TextArea output1;//????????
    @FXML
    private TextArea output2;
    @FXML
    private TextArea output3;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private ScrollPane scroll2;
    @FXML
    private ScrollPane scroll3;
    @FXML
    private CheckBox check_bing;
    @FXML
    private CheckBox check_youdao;
    @FXML
    private CheckBox check_jinshan;
    int[] favorCount;
    String Youdao;//1
    String JinShan;//3
    String Bing;//2
    String youdao = "YouDao：\n";
    String jinshan = "JinShan：\n";
    String bing = "Bing：\n";

    @FXML
    private void onClickSearch(ActionEvent event) {//??????????output??????????

        youdao = "YouDao：\n";
        jinshan = "JinShan：\n";
        bing = "Bing：\n";
        username.setText(userid);
        input = inputfield.getText();
        search();
        youdao = youdao + Youdao;
        jinshan = jinshan + JinShan;
        bing = bing + Bing;
/*

*/
        int count = 0;
        //111
        if (check_youdao.isSelected() && check_bing.isSelected() && check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(true);
            star2.setVisible(true);
            scroll3.setVisible(true);
            star3.setVisible(true);
            //sort
            switch (favorCount[0]) {
                case 1:
                    output1.setText(youdao);
                    break;
                case 2:
                    output1.setText(bing);
                    break;
                case 3:
                    output1.setText(jinshan);
                    break;
                default:
                    break;
            }
            switch (favorCount[1]) {
                case 1:
                    output2.setText(youdao);
                    break;
                case 2:
                    output2.setText(bing);
                    break;
                case 3:
                    output2.setText(jinshan);
                    break;
                default:
                    break;
            }
            switch (favorCount[2]) {
                case 1:
                    output3.setText(youdao);
                    break;
                case 2:
                    output3.setText(bing);
                    break;
                case 3:
                    output3.setText(jinshan);
                    break;
                default:
                    break;
            }

        }
        //110
        else if (check_youdao.isSelected() && check_bing.isSelected() && !check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(true);
            star2.setVisible(true);
            scroll3.setVisible(false);
            star3.setVisible(false);
            count = 0;
            if (favorCount[0] != 3) {
                switch (favorCount[0]) {
                    case 1:
                        output1.setText(youdao);
                        count++;
                        break;
                    case 2:
                        output1.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (favorCount[1] != 3) {
                switch (favorCount[0]) {
                    case 1:
                        if (count == 0)
                            output1.setText(youdao);
                        else
                            output2.setText(youdao);
                        count++;
                        break;
                    case 2:
                        if (count == 0)
                            output1.setText(bing);
                        else
                            output2.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (count != 2) {
                switch (favorCount[2]) {
                    case 1:
                        output2.setText(youdao);
                        count++;
                        break;
                    case 2:
                        output2.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
        }
        //101
        else if (check_youdao.isSelected() && !check_bing.isSelected() && check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(true);
            star2.setVisible(true);
            scroll3.setVisible(false);
            star3.setVisible(false);
            count = 0;
            if (favorCount[0] != 2) {
                switch (favorCount[0]) {
                    case 1:
                        output1.setText(youdao);
                        count++;
                        break;
                    case 3:
                        output1.setText(jinshan);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (favorCount[1] != 2) {
                switch (favorCount[0]) {
                    case 1:
                        if (count == 0)
                            output1.setText(youdao);
                        else
                            output2.setText(youdao);
                        count++;
                        break;
                    case 3:
                        if (count == 0)
                            output1.setText(jinshan);
                        else
                            output2.setText(jinshan);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (count != 2) {
                switch (favorCount[2]) {
                    case 1:
                        output2.setText(youdao);
                        count++;
                        break;
                    case 3:
                        output2.setText(jinshan);
                        count++;
                        break;
                    default:
                        break;
                }
            }
        }
        //011
        else if (!check_youdao.isSelected() && check_bing.isSelected() && check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(true);
            star2.setVisible(true);
            scroll3.setVisible(false);
            star3.setVisible(false);
            count = 0;
            if (favorCount[0] != 1) {
                switch (favorCount[0]) {
                    case 3:
                        output1.setText(jinshan);
                        count++;
                        break;
                    case 2:
                        output1.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (favorCount[1] != 1) {
                switch (favorCount[0]) {
                    case 3:
                        if (count == 0)
                            output1.setText(jinshan);
                        else
                            output2.setText(jinshan);
                        count++;
                        break;
                    case 2:
                        if (count == 0)
                            output1.setText(bing);
                        else
                            output2.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            if (count != 2) {
                switch (favorCount[2]) {
                    case 3:
                        output2.setText(jinshan);
                        count++;
                        break;
                    case 2:
                        output2.setText(bing);
                        count++;
                        break;
                    default:
                        break;
                }
            }
            //sort
//			output1.setText(input);
//			output2.setText(input);
//			output3.setText("");
        }
        //100
        else if (check_youdao.isSelected() && !check_bing.isSelected() && !check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(false);
            star2.setVisible(false);
            scroll3.setVisible(false);
            star3.setVisible(false);
            for (int i = 0; i < 3; i++)
                if (favorCount[i] == 1)
                    output1.setText(youdao);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //010
        else if (!check_youdao.isSelected() && check_bing.isSelected() && !check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(false);
            star2.setVisible(false);
            scroll3.setVisible(false);
            star3.setVisible(false);
            for (int i = 0; i < 3; i++)
                if (favorCount[i] == 2)
                    output1.setText(bing);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //001
        else if (!check_youdao.isSelected() && !check_bing.isSelected() && check_jinshan.isSelected()) {
            scroll1.setVisible(true);
            star1.setVisible(true);
            scroll2.setVisible(false);
            star2.setVisible(false);
            scroll3.setVisible(false);
            star3.setVisible(false);
            for (int i = 0; i < 3; i++)
                if (favorCount[i] == 3)
                    output1.setText(jinshan);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //000
        else {
            scroll1.setVisible(false);
            star1.setVisible(false);
            scroll2.setVisible(false);
            star2.setVisible(false);
            scroll3.setVisible(false);
            star3.setVisible(false);
//			output1.setText("");
//			output2.setText("");
//			output3.setText("");
        }

    }


    public void search() {
//        try {
//            socket = new Socket("localhost", 8000);
//        } catch (IOException ex) {}

        try {
            // toServer = new ObjectOutputStream(socket.getOutputStream());
//            String word = "test";
            UserSet s = new UserSet(3, input);
            toServer.writeObject(s);
            toServer.flush();
            //  fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
                Youdao = t.getYouDaoTranslation();
                JinShan = t.getJinshanTranslation();
                Bing = t.getBingTranslation();
//                System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }

/*
        try {
            socket = new Socket("localhost", 8000);
        } catch (IOException ex) {}*/
        try {
            // toServer = new ObjectOutputStream(socket.getOutputStream());
            UserSet s1 = new UserSet(5,input);
            toServer.writeObject(s1);
            toServer.flush();
            //fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
                favorCount = t.getFavorCount();
//                System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
    }


    /*            ????                                                */
    int favor;
    @FXML
    private Button star1;//????
    @FXML
    private Button star2;
    @FXML
    private Button star3;

    @FXML
    private void onClickStar1(ActionEvent event) {
        String temp = output1.getText();
        if (temp.charAt(0) == 'Y')
            favor = 1;
        else if (temp.charAt(0) == 'B')
            favor = 2;
        else
            favor = 3;
        star(favor);

    }

    @FXML
    private void onClickStar2(ActionEvent event) {
        String temp = output2.getText();
        if (temp.charAt(0) == 'Y')
            favor = 1;
        else if (temp.charAt(0) == 'B')
            favor = 2;
        else
            favor = 3;

        star(favor);

    }

    @FXML
    private void onClickStar3(ActionEvent event) {
        String temp = output3.getText();
        if (temp.charAt(0) == 'Y')
            favor = 1;
        else if (temp.charAt(0) == 'B')
            favor = 2;
        else
            favor = 3;
        star(favor);

    }

    public void star(int favor) {
        try {
            //    toServer = new ObjectOutputStream(socket.getOutputStream());
            String word = input;
            String user_login = userid;
            UserSet s = new UserSet(4, user_login, word, favor);
            toServer.writeObject(s);
            toServer.flush();
        } catch (IOException ex) {
        }

    }


    public static String reciever;
    @FXML
    private Button make_card;
    @FXML
    private ScrollPane scroll_list;

    @FXML
    private void onClickMakeCard(ActionEvent event) {
        username.setText(userid);

    }

    @FXML
    private Button checkuser;
    @FXML
    private TextArea list;
    String all_User;
    String user_Online;
    @FXML
    private void onClickCheckUser(ActionEvent event) {
        checkuser();
        scroll_list.setVisible(true);
//        ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "Family App");
        useronline.setText(user_Online);
    }
    @FXML
    private Button checkalluser;
    @FXML
    private TextArea useronline;
    @FXML
    private ScrollPane scroll_user;
    @FXML
    private void alluser(ActionEvent event) {
        checkuser();
        scroll_user.setVisible(true);
//        ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "Family App");
        list.setText(all_User);
    }

    public void checkuser() {
        try{
            //           toServer = new ObjectOutputStream(socket.getOutputStream());
            UserSet s = new UserSet(6);
            toServer.writeObject(s);
            toServer.flush();
            //           fromServer = new ObjectInputStream(socket.getInputStream());
            try{
                UserSet t = (UserSet) fromServer.readObject();
                user_Online = t.getUser_Online();
                all_User = t.getAll_User();


            } catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        catch (IOException ex) {
        }
    }

    @FXML
    private TextField recev;

    @FXML
    private void onEditRecev(ActionEvent event) {
        reciever = recev.getText();

    }

    @FXML
    private Button real_send;
    @FXML
    private Label if_suc;

    @FXML
    private void onClickRealSend(ActionEvent event) {
//		real_send.setText(reciever);
        reciever = recev.getText();
        if_suc.setText("");
        if(reciever.equals("all"))
        {    send();
            if_suc.setText("发送成功!");}
        else if(all_User.indexOf(reciever) == -1)
            if_suc.setText("该用户不存在");
        else {
            send();
            if_suc.setText("发送成功!");
        }

    }
    public void send(){
        try{
//          toServer = new ObjectOutputStream(socket.getOutputStream());
            UserSet s = new UserSet(7,inputfield.getText(),userid,reciever);
            toServer.writeObject(s);
            toServer.flush();
        }
        catch (IOException ex) {
        }
    }

    public void MainStage() {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root, 700, 550);
            scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.setTitle("Online Dictionary");
            mainStage.setResizable(false);
            mainStage.getIcons().add(new Image(getClass().getResourceAsStream("fanyi.png")));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean connected = false;

    //ObjectStreamCreate
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO ??????????????

        while (!connected) {
            try {
                socket = new Socket("115.159.205.194", 8000);
//                socket = new Socket("localhost", 8000);
                //  double radius=Double.parseDouble(jtf.getText().trim());
                toServer = new ObjectOutputStream(socket.getOutputStream());
                fromServer = new ObjectInputStream(socket.getInputStream());
                connected = true;
                ObjectStreamCreate = true;
                checkOnlineOrNot checkOnlineTask=new checkOnlineOrNot();
                new Thread(checkOnlineTask).start();
                try{
                    //           toServer = new ObjectOutputStream(socket.getOutputStream());
                    UserSet s = new UserSet(6);
                    toServer.writeObject(s);
                    toServer.flush();

                    //           fromServer = new ObjectInputStream(socket.getInputStream());
                    try{
                        UserSet t = (UserSet) fromServer.readObject();
                        user_Online = t.getUser_Online();
                        all_User = t.getAll_User();


                    } catch (ClassNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                catch (IOException ex) {
                }

            } catch (IOException ex) {
                //               jta.append(ex.toString()+"trying to reconnect in 1s \n");
            }
            if (!connected)
                try {
                    Thread.sleep(1000);
                } catch (java.lang.InterruptedException e) {
//                    jta.append(e.toString()+'\n');
                }
        }



    }

     class checkOnlineOrNot implements Runnable{
        public void run(){
            while(true){
            try{
                Thread.sleep(15000);}
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            UserSet s = new UserSet(2,userid);
            try {
                toServer.writeObject(s);
                toServer.flush();
            }
            catch(IOException ex2){
                ex2.printStackTrace();
            }
        }}
    }

    /*?????*/
//	boolean if_get_card = false;
    @FXML
    private Button check_word;

    //	@FXML
//	if(if_get_card){
//		check_word.setStyle("-fx-background-color: red");
//	}


    @FXML
    private void onClickCheckWord(ActionEvent event) {
        username.setText(userid);
//		if(if_get_card){
        WordStage();
//		}

    }


    public void WordStage() {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("WordCard.fxml"));
            Scene scene = new Scene(root, 330, 150);
            scene.getStylesheets().add(getClass().getResource("WordCardStyle.css").toExternalForm());
            wordStage.setScene(scene);
            wordStage.setTitle("Online Dictionary");
            wordStage.setResizable(false);
            wordStage.getIcons().add(new Image(getClass().getResourceAsStream("fanyi.png")));
            wordStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button bt_read;
    @FXML
    private TextArea word_card;
    @FXML
    private void onClickRead(ActionEvent event) {
        checkword();
        word_card.setText(wordCardList);

    }
    String wordCardList="";
    public void checkword() {
        try {
            UserSet s = new UserSet(8, userid);
            toServer.writeObject(s);
            toServer.flush();
            try {
                UserSet t = (UserSet) fromServer.readObject();
                wordCardList = t.getGetWordCard();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button bt_back;

    @FXML
    private void onClickBack(ActionEvent event) {
        bt_back.getScene().getWindow().hide();
    }


    @FXML
    private Button bt_quit;

    @FXML
    private void onClickQuit(ActionEvent event) {
        bt_quit.getScene().getWindow().hide();
    }


}
