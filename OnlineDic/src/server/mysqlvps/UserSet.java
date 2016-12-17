package server.mysqlvps;

/**
 * Created by stdzysta on 2016/12/18.
 */
public class UserSet implements java.io.Serializable{
    public String User_login;
    public String User_passwd;

    public UserSet(String User_login,String User_passwd){
        this.User_login=User_login;
        this.User_passwd=User_passwd;
    }

    public String getUser_login(){
        return User_login;
    }

    public String getUser_passwd(){
        return User_passwd;
    }
}
