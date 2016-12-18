package server.mysqlvps;
/**
 * Created by stdzysta on 2016/12/18.
 */
//define ServiceType
/**
 * 0  注册
 * 1  登录
 * 2  返回值确定注册和登录的errorcode
 * 3  查询
 * 4  用户点赞单词
 * 5  每隔2s返回目前某单词的三个翻译的排行
 * 6  查看所有人员 以及在线离线状况
 * 7  发送单词卡
 */

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import sun.plugin.services.WPlatformService;

/**
 * errorcode
 * sign up
 * 0:success
 * 1:userlogin exist
 * 2:invalid username or length out of range
 *
 * sign in
 * define 0:login success
 * 1:wrong passwd
 * 2:User didn't exist
 *
 * //Define Company
 * YouDao 1
 * Bing 2
 * JinShan 3
 */


public class UserSet implements java.io.Serializable{
    public String user_Login;
    public String user_Passwd;
    public String word;
    public String youDaoTranslation;
    public String bingTranslation;
    public String jinShanTranslation;
    public String user_Send;
    public String all_User;
    public String user_Online;
    public int serviceType;
    public int errorCode;
    public int favorCompany;
    public int[] favorCount=new int[3];


    /**
     * User function login and signup
     * @param serviceType
     * @param user_Login
     * @param user_Passwd
     *
     */
    public UserSet(int serviceType,String user_Login,String user_Passwd){
        this.serviceType=serviceType;
        this.user_Login=user_Login;
        this.user_Passwd=user_Passwd;
    }

    /**
     * User function search word
     * User function return favorCount //点赞排行顺序
     * @param word
     * @param serviceType
     */
    public UserSet(int serviceType,String word){
        this.word=word;
        this.serviceType=serviceType;
    }

    /**
     * User function favor one company word
     * @param serviceType
     * @param user_Login
     * @param word
     * @param favorCompany
     */
    public UserSet(int serviceType,String user_Login,String word,int favorCompany){
        this.serviceType=serviceType;
        this.user_Login=user_Login;
        this.word=word;
        this.favorCompany=favorCompany;
    }
    /**
     * User function get allusers and Users online
     * @param serviceType
     */
    public UserSet(int serviceType){
        this.serviceType=serviceType;
    }

    /**
     * User function send Word card to another user
     * @param serviceType
     * @param word
     * @param user_Login
     * @param user_Send
     */
    public UserSet(int serviceType,String word,String user_Login,String user_Send){
        this.serviceType=serviceType;
        this.word= word;
        this.user_Login=user_Login;
        this.user_Send=user_Send;
    }

    /**
     * Server function return signup/login errorcode
     * @param serviceType
     * @param errorCode
     */
    public UserSet(int serviceType,int errorCode){
        this.serviceType=serviceType;
        this.errorCode=errorCode;
    }

    /**
     * Server return Search Result
     * @param youDaoTranslation
     * @param bingTranslation
     * @param jinShanTranslation
     */
    public UserSet(String youDaoTranslation, String bingTranslation,String jinShanTranslation){
        this.youDaoTranslation=youDaoTranslation;
        this.bingTranslation=bingTranslation;
        this.jinShanTranslation=jinShanTranslation;
    }

    /**
     * Server return favorCount;
     * @param favorCount
     */
    public UserSet(int[] favorCount){
        for(int i=0;i<3;i++)
        this.favorCount[i]=favorCount[i];
    }

    /**
     * Server return all users and online users
     * @param all_User
     * @param user_Online
     */
    public UserSet(String all_User,String user_Online) {
        this.all_User = all_User;
        this.user_Online = user_Online;
    }
//word card to continue
    //public UserSet()


    public String getUser_login(){
        return user_Login;
    }

    public String getUser_passwd(){
        return user_Passwd;
    }

    public String getWord(){
        return word;
    }

    public String getYouDaoTranslation(){
        return youDaoTranslation;
    }
    public String getBingTranslation(){
        return bingTranslation;
    }

    public String getJinshanTranslation(){
        return jinShanTranslation;
    }
    public String getUser_send(){
        return user_Send;
    }

    public String getAll_User(){
        return all_User;
    }

    public String getUser_Online(){
        return user_Online;
    }

    public int getServiceType(){
        return serviceType;
    }
    public int getErrorcode(){
        return errorCode;
    }
    public int getFavorCompany(){
        return favorCompany;
    }
    public int[] getFavorCount(){
        return favorCount;
    }
}
