package server.mysqlvps;

/**
 * Created by stdzysta on 2016/12/17.
 */
//This class mainly to realize the function of SQL
import com.sun.org.apache.bcel.internal.generic.NEW;
import jsonparser.parserjson;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
public class MySQLConnect {
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://115.159.205.194:3306/OnlineDicDatabase";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "javathread";
    static final String PASS = "javaod@nju";


    public static boolean checkUserLoginName(String user_Login){
        boolean ExistOrNot=false;
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            //System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            //System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_login FROM userlist WHERE user_login=\'"+user_Login+"\'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               // System.out.print(rs.getString("user_Login"));
                if(rs.getString("user_Login").equals(user_Login)){

                    ExistOrNot=true;
                }
                //System.out.print(rs.getString("user_Login"));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        //System.out.println("Goodbye!");
        return ExistOrNot;
    }

    /**
     * errorcode define
     *     0:login success
     *     1:wrong passwd
     *     2:User didn't exist
     * @param Userlogin
     * @param UserPasswd
     * @return
     */
    public static int loginOldUser(String Userlogin,String UserPasswd) {
        int errorCode = 1;

        if (!checkUserLoginName(Userlogin)) {
            errorCode = 2;
            return errorCode;
        } else {
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
               // System.out.println("Connect sql...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                //System.out.println("selecting...");
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT user_passwd FROM userlist WHERE user_login=\'" + Userlogin + "\'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                   // System.out.print(rs.getString("user_Login"));
                    if (rs.getString("user_passwd").equals(UserPasswd)) {
                        errorCode=0;
                    }
                    else{
                        errorCode=1;
                    }
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                    try {
                        if (conn != null) conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
                //System.out.println("Goodbye!");

                return errorCode;
            }
        }

    /**
     * define
     * 0:success
     * 1:userlogin exist
     * 2:invalid username or length out of range
     *
     * @param NewUser_login
     * @param User_passwd
     * @return
     */
    public static int sigUpNewUser(String NewUser_login,String User_passwd){
        int errorcode=1;
         if(checkUserLoginName(NewUser_login))
            return 1;
        else {
            errorcode=2;
            Connection conn = null;
            Statement stmt = null;
            try {// 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");
                // 打开链接
              //  System.out.println("Connect sql...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // 执行查询
               // System.out.println("selecting...");
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT c1.userid from userlist c1,userlist c2 WHERE c1.userid>c2.userid";
                int idNext=100;
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){idNext=rs.getInt("userid");}
                idNext++;

                sql="INSERT INTO userlist " +
                        "VALUES ("+idNext+",\'"+ NewUser_login+"\',\'" +
                        User_passwd+"\',"+"curDate());";

                stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                int keyValue = -1;
                if (rs.next()) {
                    keyValue = rs.getInt(1);
                }
                if(keyValue>0)
                errorcode=0;
               // System.out.println("returncode"+keyValue+" ");
                // 完成后关闭
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException se) {
                // 处理 JDBC 错误
                se.printStackTrace();
            } catch (Exception e) {
                // 处理 Class.forName 错误
                e.printStackTrace();
            } finally {
                // 关闭资源
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                }// 什么都不做
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
           // System.out.println("Goodbye!");
            return errorcode;
        }
    }

    /**
     * return userlist up to now
     * @return
     */

    public static String returnUserlist() {
        String userList="";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
           // System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
           // System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_login FROM userlist ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               // System.out.print(rs.getString("user_Login"));
                userList+=rs.getString("user_Login")+"\n";
            }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                    try {
                        if (conn != null) conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
           // System.out.println("Goodbye!");
         return userList;
    }

    public static String returnAllUser() {
        String userList="";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
           // System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_login FROM userlist ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                // System.out.print(rs.getString("user_Login"));
                userList+=rs.getString("user_Login")+",";
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        //System.out.println("Goodbye!");
        return userList;
    }




    /**
     * define errorcode Too
     * 0:success
     * 1:userSentNotExist
     * 2:Word Too long or other error
     *
     * @param userSend
     * @param userReceive
     * @param word
     * @return
     */
    public static int sendWord(String userSend,String userReceive,String word){
        //here should have an errorCode too
        int errorCode=1;
        if(checkUserLoginName(userReceive)){
            errorCode=2;
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
               // System.out.println("Connect sql...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
               // System.out.println("selecting...");
                stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO UserSentCard " +
                        "VALUES ("+"\'"+userSend+"\',\'"+ userReceive+"\',\'" +
                        word+"\'"+");";
                stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = stmt.getGeneratedKeys();
                int keyValue = -1;
                if (rs.next()) {
                    keyValue = rs.getInt(1);
                }
                if(keyValue>0)
                    errorCode=0;
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                // 处理 Class.forName 错误
                e.printStackTrace();
            } finally {
                // 关闭资源
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se2) {
                }// 什么都不做
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }}
            else {
                errorCode=1;
        }
        return errorCode;
        }

    /**
     *this function is used to refresh message
     * @param userName
     * @return
     */
    public static String returnWordCardList(String userName) {

        String userCardList="";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
           // System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
                sql = "SELECT userSend,Word FROM UserSentCard WHERE " +
                        "userReceive=\'"+userName+"\';";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                // System.out.print(rs.getString("user_Login"));
                userCardList+="From:"+rs.getString("userSend")+"\n";
                userCardList+=rs.getString("Word")+": from YouDao \n"+
                        parserjson.YouDaoTranslate(rs.getString("Word")) +"\n";
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        //System.out.println("Goodbye!");
        return userCardList;
    }



    public static void main(String[] args) {

        //System.out.println("Result \n"+checkUserLoginName("+1s"));
        System.out.println("end:"+loginOldUser("test","test123"));
    }
}
