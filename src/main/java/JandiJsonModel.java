public class JandiJsonModel {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public JandiJsonModel(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                "\"body\" : " + "\"" + body + "\"" +
                '}';
    }
}
