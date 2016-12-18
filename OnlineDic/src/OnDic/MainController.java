package OnDic;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class MainController implements Initializable{

    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    boolean loginSuccess=false;
    Stage mainStage = new Stage();
    Stage wordStage = new Stage();
    public static String userid ;
    public static String userpass;

/*
*
* log
*
*
*
 */
    @FXML
    private TextField  edit_log;
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
    private void onClickLog(ActionEvent event){//?????button???????
        userid = edit_log.getText();
        userpass = edit_pass.getText();
        if_empty.setText("");
        if_leagel.setText("");
        if(userpass.equals(""))
            if_empty.setText("密码不能为空!");
        if(userid.equals(""))
            if_leagel.setText("用户名不能为空!");
        if(!userpass.equals("")&&!userid.equals("")){
            login();
                bt_log.getScene().getWindow().hide();
                MainStage();

        }
    }

    public  void login(){
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

        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            String loginuse = userid;
            String loginpasswd = userpass;
            UserSet s = new UserSet(1,loginuse, loginpasswd);
            toServer.writeObject(s);
            //  toServer.writeDouble(radius);
            toServer.flush();
            fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
 //               System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }catch (IOException ex1){ex1.printStackTrace();}
    }
    @FXML
    private void onClickRegist(ActionEvent event){//??????button???????
        if_empty.setText("");
        if_leagel.setText("");
        userid = edit_log.getText();
        userpass = edit_pass.getText();
        if(userpass.equals(""))
            if_empty.setText("密码不能为空!");
        if(userid.equals(""))
            if_leagel.setText("用户名不能为空!");
        if(!userpass.equals("")&&!userid.equals("")){
            int temp = regist();
            if(temp == 0) {
                bt_log.getScene().getWindow().hide();
                MainStage();
            }
            else if(temp == 1)
                if_leagel.setText("用户名已存在");
            else if(temp == 2)
                if_leagel.setText("用户名不合法或者过长");
        }
    }

    public  int regist(){
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

        int temp = 0;
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            String loginuse = userid;
            String loginpasswd = userpass;
            UserSet s = new UserSet(0,loginuse, loginpasswd);
            toServer.writeObject(s);
            //  toServer.writeDouble(radius);
            toServer.flush();
            fromServer = new ObjectInputStream(socket.getInputStream());
            try {
                UserSet t = (UserSet) fromServer.readObject();
                temp = t.getErrorcode();
//                System.out.println(t.getServiceType() + "\t" + t.getErrorcode());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }catch (IOException ex1){ex1.printStackTrace();}
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
    @FXML
    private void onClickSearch(ActionEvent event){//??????????output??????????
        username.setText(userid);
        input = inputfield.getText();
        //111
        if(check_youdao.isSelected()&&check_bing.isSelected()&&check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(true);star2.setVisible(true);
            scroll3.setVisible(true);star3.setVisible(true);
            //sort
//			output1.setText(input);
//			output2.setText(input);
//			output3.setText(input);
        }
        //110
        else if(check_youdao.isSelected()&&check_bing.isSelected()&&!check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(true);star2.setVisible(true);
            scroll3.setVisible(false);star3.setVisible(false);
            //sort
//			output1.setText(input);
//			output2.setText(input);
//			output3.setText("");
        }
        //101
        else if(check_youdao.isSelected()&&!check_bing.isSelected()&&check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(true);star2.setVisible(true);
            scroll3.setVisible(false);star3.setVisible(false);
//            output1.setText("???");
            //sort
//			output1.setText(input);
//			output2.setText(input);
//			output3.setText("");
        }
        //011
        else if(!check_youdao.isSelected()&&check_bing.isSelected()&&check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(true);star2.setVisible(true);
            scroll3.setVisible(false);star3.setVisible(false);
            //sort
//			output1.setText(input);
//			output2.setText(input);
//			output3.setText("");
        }
        //100
        else if(check_youdao.isSelected()&&!check_bing.isSelected()&&!check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(false);star2.setVisible(false);
            scroll3.setVisible(false);star3.setVisible(false);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //010
        else if(!check_youdao.isSelected()&&check_bing.isSelected()&&!check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(false);star2.setVisible(false);
            scroll3.setVisible(false);star3.setVisible(false);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //001
        else if(!check_youdao.isSelected()&&!check_bing.isSelected()&&check_jinshan.isSelected()){
            scroll1.setVisible(true);star1.setVisible(true);
            scroll2.setVisible(false);star2.setVisible(false);
            scroll3.setVisible(false);star3.setVisible(false);
//			output1.setText(input);
//			output2.setText("");
//			output3.setText("");
        }
        //000
        else {
            scroll1.setVisible(false);star1.setVisible(false);
            scroll2.setVisible(false);star2.setVisible(false);
            scroll3.setVisible(false);star3.setVisible(false);
//			output1.setText("");
//			output2.setText("");
//			output3.setText("");
        }

    }

    /*            ????                                                */
    @FXML
    private Button star1;//????
    @FXML
    private Button star2;
    @FXML
    private Button star3;
    @FXML
    private void onClickStar1(ActionEvent event){

    }
    @FXML
    private void onClickStar2(ActionEvent event){

    }
    @FXML
    private void onClickStar3(ActionEvent event){

    }

    /*          ?????????                                      */
    public static String reciever;
    @FXML
    private Button make_card;
    @FXML
    private ScrollPane scroll_list;
    @FXML
    private void onClickMakeCard(ActionEvent event){
        username.setText(userid);

    }
    @FXML
    private Button checkuser;
    @FXML
    private ListView<String> list;


    @FXML
    private void onClickCheckUser(ActionEvent event){
        scroll_list.setVisible(true);
        ObservableList<String> items =FXCollections.observableArrayList ("Single", "Double", "Suite", "Family App");
        list.setItems(items);

    }
    @FXML
    private TextField recev;
    @FXML
    private void onEditRecev(ActionEvent event){
        reciever = recev.getText();

    }
    @FXML
    private Button real_send;
    @FXML
    private Label if_suc;
    @FXML
    private void onClickRealSend(ActionEvent event){
//		real_send.setText(reciever);
        if_suc.setText("发送成功!");
//		if_suc.setText("??????");

    }

    public void MainStage(){
        try{
            Pane root = (Pane)FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root,700,550);
            scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.setTitle("Online Dictionary");
            mainStage.setResizable(false);
            mainStage.getIcons().add(new Image(getClass().getResourceAsStream("fanyi.png")));
            mainStage.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO ??????????????

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
    private void onClickCheckWord(ActionEvent event){
        username.setText(userid);
//		if(if_get_card){
        WordStage();
//		}

    }
    public void WordStage(){
        try {
            Pane root = (Pane)FXMLLoader.load(getClass().getResource("WordCard.fxml"));
            Scene scene = new Scene(root,330,150);
            scene.getStylesheets().add(getClass().getResource("WordCardStyle.css").toExternalForm());
            wordStage.setScene(scene);
            wordStage.setTitle("Online Dictionary");
            wordStage.setResizable(false);
            wordStage.getIcons().add(new Image(getClass().getResourceAsStream("fanyi.png")));
            wordStage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button bt_read;
    @FXML
    private Label sender_name;
    @FXML
    private void onClickRead(ActionEvent event){
        sender_name.setText("");
    }
    @FXML
    private Button bt_back;
    @FXML
    private void onClickBack(ActionEvent event){
        bt_back.getScene().getWindow().hide();
    }


    @FXML
    private Button bt_quit;
    @FXML
    private void onClickQuit(ActionEvent event){
        bt_quit.getScene().getWindow().hide();
    }


}
