/**
 * Created by zhangyi on 2016/11/28.
 */
public class JinShanDic {
    public static void main(String[] args) {
        //发送 GET 请求
        String s=HttpRequest.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
        System.out.println(s);

        //发送 POST 请求
        String sr=HttpRequest.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
        System.out.println(sr);
    }
}
