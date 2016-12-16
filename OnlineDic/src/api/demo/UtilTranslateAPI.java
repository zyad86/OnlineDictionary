package api.demo;


//To the overall unity of the call  combine three api
/**
 * Created by stdzysta on 2016/12/17.
 */
public class UtilTranslateAPI {
    public static String YouDaoTranslate(String WordsToTranslate)
    {
        try {
            String s = HttpRequest.sendGet("http://fanyi.youdao.com/openapi.do",
                    "keyfrom=online-dictionary&key=1058604112&type=data&doctype=json&version=1.1&q=" + WordsToTranslate);
            return s;
        } catch (java.lang.Exception e){
            System.out.println(e);
        }
        String noReturn = "error";
        return noReturn;
    }

    public static  String bingTranslate(String WordsToTranslate)
    {
        try {
            String translatedText=HttpRequest.sendGet("http://xtk.azurewebsites.net/BingDictService.aspx","Word="+WordsToTranslate);
            return translatedText;
        }catch(java.lang.Exception e){
            System.out.println(e);
        }
        String noReturn="error";
        return noReturn;
    }

    public static String JinshanTranslate(String WordsToTranslate)
    {
        try {
            String s=HttpRequest.sendGet("http://dict-co.iciba.com/api/dictionary.php",
                    "w="+WordsToTranslate+"&key=B9B54B63282B09E32D612FBB95A92258");
            return s;
        }catch(java.lang.Exception e){
            System.out.println(e);
        }
        String noReturn="error";
        return noReturn;

    }

}

