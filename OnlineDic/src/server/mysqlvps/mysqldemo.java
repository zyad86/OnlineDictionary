package server.mysqlvps;

/**
 * Created by zhangyi on 2016/12/15.
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;


public class mysqldemo {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://115.159.205.194:3306/OnlineDicDatabase";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "javathread";
    static final String PASS = "javaod@nju";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("Connect sql...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println("selecting...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_login, user_passwd, signupdate FROM userlist";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String id  = rs.getString("user_login");
                String name = rs.getString("user_passwd");
                String url = rs.getString("signupdate");
                // 输出数据
                System.out.print("user_login: " + id);
                System.out.print(", user_passwd: " + name);
                System.out.print(", signupdate: " + url);
                System.out.print("\n");
            }
            // 完成后关闭
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
        System.out.println("Goodbye!");
    }
}