

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20161125000032743";
    private static final String SECURITY_KEY = "WyYdtdANyq4gbMaNsg7E";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "为什么翻译不了";
        System.out.println(api.getTransResult(query, "auto", "en"));
    }

}
