

public class Main {
    // HIPCHAT EXPORT DIR
    private static String rootPath = "D:\\hipchat_export\\rooms\\";
    public static void main(String[] args) {

        MultiThread mt1 = new MultiThread(
                "room1",
                "https://JANDI_WH_URL",
                rootPath + "DEVELOP"
        );

        MultiThread mt2 = new MultiThread(
                "room2",
                "https://JANDI_WH_URL",
                rootPath + "MANAGE"
        );

        mt1.start();
        mt2.start();

    }

}
