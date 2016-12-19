package server.mysqlvps;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
//Define Company YouDao 1 Bing 2 JinShan 3

/**
 * Created by stdzysta on 2016/12/18.
 */
public class MySQLAffection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://115.159.205.194:3306/OnlineDicDatabase";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "javathread";
    static final String PASS = "javaod@nju";

    public static void UserAffectionSend(String User_login, String Word, int LikeCompany) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
           // System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
          //  System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO AffectionTable " +
                    "VALUES (" + LikeCompany + ",\'" + Word + "\',\'" +
                    User_login + "\'" + ");";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int keyValue = -1;
            if (rs.next()) {
                keyValue = rs.getInt(1);
            }
            //System.out.println("returncode" + keyValue + " ");
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
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
       // System.out.println("Goodbye!");
    }

    public static int[] ReturnFavor(String words) {
        int affectionU[] = {1, 2, 3};
        int forYouDao = 0;
        int forBing = 0;
        int forJinshan = 0;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT COUNT(*) AS NUMS FROM AffectionTable" +
                    " WHERE Company=1 AND word=\'"+words+"\';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
              forYouDao=rs.getInt("NUMS");
            }
            sql = "SELECT COUNT(*) AS NUMS FROM AffectionTable" +
                    " WHERE Company=2 AND word=\'"+words+"\';";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                forBing=rs.getInt("NUMS");
            }
            sql = "SELECT COUNT(*) AS NUMS FROM AffectionTable" +
                    " WHERE Company=3 AND word=\'"+words+"\';";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                forJinshan=rs.getInt("NUMS");
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
//Define Company YouDao 1 Bing 2 JinShan 3
        if(forYouDao>forJinshan){
            if(forYouDao>forBing){
                affectionU[0]=1;
                if(forJinshan>forBing){
                    affectionU[1]=3;
                    affectionU[2]=2;}
                else {
                    affectionU[1]=2;
                    affectionU[2]=3;
                }
            }
            else {
                affectionU[0] = 2;
                if(forYouDao>forJinshan){
                    affectionU[1]=1;
                    affectionU[2]=3;
                }
                else{
                    affectionU[1]=3;
                    affectionU[2]=1;
                }
            }
        }else{
            if(forJinshan>forBing){
                affectionU[0]=3;
                if(forBing>forYouDao){
                    affectionU[1]=2;
                    affectionU[2]=1;
                }
                else{
                    affectionU[1]=1;
                    affectionU[2]=2;
                }
            }
            else{
                affectionU[0]=2;
                if(forYouDao>forJinshan){
                    affectionU[1]=1;
                    affectionU[2]=3;
                }
                else{
                    affectionU[1]=3;
                    affectionU[2]=1;
                }
            }}


        return affectionU;

    }

    public static void main(String[] args) {
        //UserAffectionSend("test",  "fantasy", 3);
        int []receive=ReturnFavor("love");


    }

}
