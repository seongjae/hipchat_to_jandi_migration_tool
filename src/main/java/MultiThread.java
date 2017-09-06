class MultiThread extends Thread { // Thread 클래스를 상속
    String name;
    String JANDI_WEBHOCK_PATH = "";
    String JSONPATH = "";
    HipchatToJandiModule module = new HipchatToJandiModule();
    public MultiThread(String threadName, String jandi_wh_url, String jsonPath) {
        System.out.println(getName() + " 스레드가 생성되었습니다.");
        this.name = threadName;
        this.JANDI_WEBHOCK_PATH = jandi_wh_url;
        this.JSONPATH = jsonPath;
        module.setInfo(JANDI_WEBHOCK_PATH, JSONPATH);
    }

    public void run() {
        System.out.println(getName() + " (" + name + ")");
        try {
            module.parseHipchat();
        } catch (Exception e) { e.printStackTrace(); }
    }
}