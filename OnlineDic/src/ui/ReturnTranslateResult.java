package ui;
import api.demo.HttpRequest;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import java.util.ArrayList;

/**
 * Created by stdzysta on 2016/11/28.
 */
public class ReturnTranslateResult {
    public String WordsToTranslate;
    ArrayList<String> arrayRestore=new ArrayList<String>();


    public static void main(String[] args) {


    }

    public String YouDaoTranslate(String WordsToTranslate){
        //http://fanyi.youdao.com/openapi.do?keyfrom=<keyfrom>&key=<key>&type=data&doctype=<doctype>&version=1.1&q=要翻译的文本

        String s=HttpRequest.sendGet("http://fanyi.youdao.com/openapi.do",
                "keyfrom=online-dictionary&key=1058604112&type=data&doctype=json&version=1.1&q="+WordsToTranslate);
        return s;
    }

    public String bingTranslate(String WordsToTranslate){
        //http://fanyi.youdao.com/openapi.do?keyfrom=<keyfrom>&key=<key>&type=data&doctype=<doctype>&version=1.1&q=要翻译的文本
        //Translate.setClientId("onlinedict-demo");
        //Translate.setClientSecret("Agow2xdIQe1m1JN519RP9wLHKNcNAyXHB1I+EPko1qs=");
        try {
 //   String translatedText = Translate.execute(WordsToTranslate, Language.ENGLISH, Language.CHINESE_SIMPLIFIED);
            String translatedText=HttpRequest.sendGet("http://xtk.azurewebsites.net/BingDictService.aspx?Word=",WordsToTranslate);
            return translatedText;
        }catch(java.lang.Exception e){
    System.out.println(e);
}
        String noReturn="error";
        return noReturn;
     // return translatedText;
    }


    public String JinshanTranslate(String WordsToTranslate){
        //http://dict-co.iciba.com/api/dictionary.php?w=hello&key=B9B54B63282B09E32D612FBB95A92258


        String s=HttpRequest.sendGet("http://dict-co.iciba.com/api/dictionary.php",
                "w="+WordsToTranslate+"&key=B9B54B63282B09E32D612FBB95A92258");
        return s;
    }


    public String  BaiduTranslate(String WordsToTranslate){
//http://dict.baidu.com/s?wd=explain&ptype=english
        String s=HttpRequest.sendGet("http:/dict.baidu.com/s",
                "wd="+WordsToTranslate+"&ptype=english");

        System.out.println(s);

        return s;


    }


}
