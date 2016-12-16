package jsonparser;
import api.demo.UtilTranslateAPI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by stdzysta on 2016/12/17.
 */
public class parserjson
{
    public static String YouDaoTranslate(String Wordstotransalte){
        String returnJson= UtilTranslateAPI.YouDaoTranslate(Wordstotransalte);
        //System.out.println(returnJson);
       // System.out.println(returnJson);
        String line = returnJson;
        String pattern = "\"\\btranslation\\b\":\\[(.+?)\\]," +
                "\"\\bbasic\\b\":\\{(.+?\\},).+?\\bweb\\b" +
                "(\":\\[.+\\])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        // System.out.println(m.groupCount());
       /* String youdaoTranslation;
        String youdaoTranslationBasic;
        String youdaoTranslationWeb;*/

        if (m.find( )) {
            //  String youdaotranslationall=m.group(0);
            // System.out.println("Found value: " + m.group(0) );
            //System.out.println("Found value: " + m.group(1) );
            String youdaoTranslation=m.group(1);
            // System.out.println("Found value: " + m.group(2) );
            String youdaoTranslationBasic=m.group(2);
            // System.out.println("Found value: " + m.group(3) );
            String youdaoTranslationWeb=m.group(3);
            //System.out.println("Found value: " + youdaoTranslationBasic);

            pattern="(.+?),\"\\bexplains\\b\":\\[(.+?)\\]";
            r = Pattern.compile(pattern);
            m = r.matcher(youdaoTranslationBasic);
            System.out.println(m.groupCount());
            String youdaoTranslationBasicPronuciation;
            String youdaoTranslationBasicExplains;
            if (m.find( )) {
                youdaoTranslationBasicPronuciation=m.group(1);
                youdaoTranslationBasicExplains=m.group(2);
                //System.out.println("Found value: " + m.group(1) );
                //System.out.println("Found value: " + m.group(2) );
            } else {
                System.out.println("NO MATCH");
                return "No match";
            }

            return youdaoTranslationBasicPronuciation+"\n"+youdaoTranslationBasicExplains;
        } else {
            System.out.println("NO MATCH");
            return "No match";
        }
    }


    public static String BingTranslate(String Wordstotransalte){
        String returnJson= UtilTranslateAPI.bingTranslate(Wordstotransalte);
        String line = returnJson;
        //return  returnJson;
        String pattern = ".+?\\bdefs\\b\":\\[(.+?)\\]" ;

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find( )) {
            //System.out.println("Found value: " + m.group(1) );
            return  m.group(1);
        } else {
            System.out.println("NO MATCH");
            return "No match";
        }
/*used to sort mp3
        String pattern = ".+?\\bAmE\\b\":\"(.+?)\",\"" +
                "\\bAmEmp3\\b\":\"(.+?)\",\"\\bBrE\\b\":\"" +
                "(.+?)\",\"\\bBrEmp3\\b" ;

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find( )) {

            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2 ));
        } else {
            System.out.println("NO MATCH");
            return "No match";
        }

        return "No match";*/
    }




    public static String JinShanTranslate(String Wordstotransalte){
        String returnJson= UtilTranslateAPI.JinshanTranslate(Wordstotransalte);
        String line = returnJson;

        String pattern = "\\bps\\b\\>(.+?)\\<.+?\\bpos\\b\\>(.+?)\\<" +
                ".+?\\bacceptation\\b" +
                "\\>(.+)\\<\\/\\bacceptation\\b";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find( )) {


             System.out.println("Found value: " + m.group(1) );
           // String youdaoTranslationBasic=m.group(2);
             System.out.println("Found value: " + m.group(3) );
           // String youdaoTranslationWeb=m.group(3);
           // System.out.println("Found value: " + youdaoTranslationBasic);
            return m.group(1)+"\n"+m.group(2)+m.group(3);
        } else {
            System.out.println("NO MATCH");
            return "No match";
        }

    }

    public static void main( String args[] ){

        System.out.println( JinShanTranslate("fantasy"));
    }
}
