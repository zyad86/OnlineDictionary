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
        String line = returnJson;
        String pattern = "\"\\btranslation\\b\":\\[(.+?)\\]," +
                "\"\\bbasic\\b\":\\{(.+?\\},).+?\\bweb\\b\":\\[" +
                "(.+)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find( )) {
            //  String youdaotranslationall=m.group(0);
            // System.out.println("Found value: " + m.group(0) );
            //System.out.println("Found value: " + m.group(1) );
            //System.out.println("Found value: " + m.group(3) );
            //System.out.println("Found value: " + youdaoTranslationBasic);
            String youdaoTranslation=m.group(1);
            String youdaoTranslationBasic=m.group(2);
            String youdaoTranslationWeb=m.group(3);
            pattern="(.+?),\"\\bexplains\\b\":\\[(.+?)\\]";
            r = Pattern.compile(pattern);
            m = r.matcher(youdaoTranslationBasic);
            // System.out.println(m.groupCount());
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


            String[] splitString = youdaoTranslationBasicPronuciation.split("\"");
            String afterSplit="";
            for (int i = 0 ; i <splitString.length ; i++ ) {
                afterSplit+=splitString[i];
            }
            String[] splitStringtwo = youdaoTranslationBasicExplains.split("\"");
            String afterSplitwo="";
            for (int i = 0 ; i <splitStringtwo.length ; i++ ) {
                afterSplitwo+=splitStringtwo[i];
            }

            String[] splitStringthree = youdaoTranslationWeb.split("\"");
            String afterSplithree="";
            for (int i = 0 ; i <splitStringthree.length ; i++ ) {
                afterSplithree+=splitStringthree[i];
            }

            return "Basic-Pronuciation:"+afterSplit+"\n"+
                    "BasicExplains:"+ afterSplitwo+"\n"+
                    "Web Explain:"+afterSplithree+"\n";
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
            String BingExplain=m.group(1);
            String[] splitString = BingExplain.split("\"\\},\\{\"");
            String afterSplit="";
            for (int i = 0 ; i <splitString.length ; i++ ) {
                afterSplit+=splitString[i];
                // System.out.println(afterSplit);
            }

            String[] splitStringtwo = afterSplit.split("\"");
            String afterSplitwo="";
            for (int i = 0 ; i <splitStringtwo.length ; i++ ) {
                afterSplitwo+=splitStringtwo[i];
            }

            String[] splitStringthree = afterSplitwo.split("pos:");
            String afterSplitthree="";
            for (int i = 0 ; i <splitStringthree.length ; i++ ) {
                afterSplitthree+=splitStringthree[i]+"\n";
            }
            return  afterSplitthree;
        } else {
            System.out.println("NO MATCH");
            return "No match";
        }

    }

    public static String JinShanTranslate(String Wordstotransalte){
        String returnJson= UtilTranslateAPI.JinshanTranslate(Wordstotransalte);
        String line = returnJson;
        System.out.println(returnJson);

        String pattern = "\\bps\\b\\>(.+?)\\<.+?\\bpos\\b\\>(.+?)\\<" +
                ".+?\\bacceptation\\b" +
                "\\>(.+)\\<\\/\\bacceptation\\b" +
                ".+?\\<\\bsent\\b"+"(.+?)\\<\\/\\bdict\\b";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find( )) {
            // System.out.println("Found value: " + m.group(1) );
            // System.out.println("Found value: " + m.group(3) );
            // String youdaoTranslationBasic=m.group(2);
            // String youdaoTranslationWeb=m.group(3);
            // System.out.println("Found value: " + youdaoTranslationBasic);
            String aboutwordSentence=m.group(4);

            String[] splitStringtwo = aboutwordSentence.split("\\<trans\\>");
            String afterSplitwo="";
            for (int i = 0 ; i <splitStringtwo.length ; i++ ) {
                afterSplitwo+=splitStringtwo[i]+"\n";
            }
            String[] splitStringthree = afterSplitwo.split("\\<\\/trans\\>\\<\\/sent\\>\\<sent\\>\\<orig\\>");
            String finalsplit="";
            for (int i = 0 ; i <splitStringthree.length ; i++ ) {
                finalsplit+=splitStringthree[i]+"\n";
            }
            return "Pronunciation"+m.group(1)+m.group(2)+"\n"+m.group(3)+
                    "\n"+finalsplit;

        } else {
            System.out.println("NO MATCH");
            return "No match";
        }

    }

    public static void main( String args[] ){

        System.out.println( BingTranslate("fantasy"));
        System.out.println( YouDaoTranslate("fantasy"));
        System.out.println( JinShanTranslate("fantasy"));
    }
}
