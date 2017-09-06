import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HipchatToJandiModule {
    static int postInterval = 400;
    static int sleepTime = 600000;
    String JANDI_WEBHOCK_PATH = "";
    String JSONPATH = "";
    long textCnt = 0;

    public void setInfo(String JANDI_WH_URL, String jsonPath) {
        JANDI_WEBHOCK_PATH = JANDI_WH_URL;
        JSONPATH = jsonPath;
    }

    public void parseHipchat() {
        List filelist = subDirList(JSONPATH);

        for (int filesCnt = 0 ; filesCnt < filelist.size() ; filesCnt++ ) {
            String json = getHipchatJsonFile(JSONPATH + "\\" + filelist.get(filesCnt).toString());
            JsonArray jsonObject = new JsonParser().parse(json.toString()).getAsJsonArray();
//            System.out.println("size : " + jsonObject.size() );


            for (int i = 0; i < jsonObject.size(); i++) {


                try {
                    String message = getMessage(jsonObject.get(i).getAsJsonObject());
                    String name = getName(jsonObject.get(i).getAsJsonObject());
                    String date = getDate(jsonObject.get(i).getAsJsonObject());
                    String file = getFile(jsonObject.get(i).getAsJsonObject());
                    String filename = getFileName(jsonObject.get(i).getAsJsonObject());
                    String data = "";
                    message = message.replace("\"", "'");
                    message = message.replace("\t", " ");
                    message = message.replace("\n", " ");
                    message = message.replace("\\", " ");


                    String title = "[DATE : " + date + "] [" + name + "]" + " ";
                    if (file.equals(""))
                        data = message;
                    else
                        data = "[[FILE : " + filename + "]](" + file + ")";

                    JandiJsonModel jsondata = new JandiJsonModel(title + data);
                    textCnt++;

                    if (textCnt % postInterval == 0) {
                        System.out.println("Waiting....(Avoid Blocking)");
                        Thread.sleep(sleepTime);
                        jandiPostRequest(jsondata.toString());
                    } else {
                        System.out.println(jsondata.toString());
                        jandiPostRequest(jsondata.toString());
                    }

                } catch (Exception e) {
                    System.out.println("It is not JSON Object.");
                }
            }
        }
    }

    private List subDirList(String source){
        File dir = new File(source);
        File[] fileList = dir.listFiles();
        List filelist = new ArrayList();

        try{
            for(int i = 0 ; i < fileList.length ; i++){
                File file = fileList[i];
                if(file.isFile()){
                    filelist.add(i,file.getName());
                }else if(file.isDirectory()){
                    subDirList(file.getCanonicalPath().toString());
                }
            }
        }catch(IOException e){
        }

        return filelist;
    }



    private String getMessage(JsonObject json) {
        String ret = "";
        try {
            ret = json.get("message").getAsString();
        } catch (Exception e ) {
        }
        return ret;
    }

    private String getDate(JsonObject json) {
        String ret = "";
        try {
            ret = json.get("date").getAsString();
        } catch (Exception e) {
        }
        return ret;
    }

    private String getName(JsonObject json) {
        String ret = "";
        try {
            JsonObject tmp = json.get("from").getAsJsonObject();
            ret = tmp.get("name").getAsString();
        } catch (Exception e) {
        }
        return ret;
    }

    private String getFile(JsonObject json) {
        String ret = "";
        try {
            JsonObject tmp = json.get("file").getAsJsonObject();
            ret = tmp.get("url").getAsString();
        } catch (Exception e) {
        }
        return ret;
    }

    private String getFileName(JsonObject json) {
        String ret = "";
        try {
            JsonObject tmp = json.get("file").getAsJsonObject();
            ret = tmp.get("name").getAsString();
        } catch (Exception e) {
        }
        return ret;
    }

    private String getHipchatJsonFile(String path) {
        String out = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String s;

            while ((s = in.readLine()) != null) {
                out = new StringBuffer().append(out).append(s).toString();
            }
            in.close();
        } catch (IOException e) {

        }
//        System.out.println(out);
        return out;
    }

    private void jandiPostRequest(String Message) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(JANDI_WEBHOCK_PATH);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = Message;

            httpCon.setRequestProperty("Accept", "application/vnd.tosslab.jandi-v2+json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST Request Option
            httpCon.setDoOutput(true);
            // InputStream으로 Response Option
            httpCon.setDoInput(true);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
            }
            catch (IOException e) {

                e.printStackTrace();
                System.out.println(Message);
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
        }
    }
}
