package server.mysqlvps;

/**
 * Created by zhangyi on 2016/12/15.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class mysqljavaConnect {
    public static void main(String[] args) {
        String driverName="com.mysql.jdbc.Driver";
        String dbURL="jdbc:mysql://115.159.205.194:3306/OnlineDicDatabase";
        String userName="javathread";
        String userPwd="javaod@nju";
        try {
                long begin = System.currentTimeMillis();
                Class.forName(driverName);
                Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
                System.out.println("连接数据库成功");
                Statement stmt = dbConn.createStatement();

                String sql = "select * from userlist";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                System.out.println("id \t passwd \t date");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));// 入如果返回的是int类型可以用getInt()
                }
                dbConn.close();
            long end = System.currentTimeMillis() - begin; // 这段代码放在程序执行后
            System.out.println("耗时：" + end + "毫秒");
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.print("各种失败");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.print("class失败");

        }

    }
}

