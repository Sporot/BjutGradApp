package p.sby.gs_qca.util;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class RequestUtil {
    private static RequestUtil requestUtil;
    private final OkHttpClient okHttpClient;
    public static RequestUtil get() { if (requestUtil == null) { requestUtil = new RequestUtil(); } return requestUtil; }
    private RequestUtil() {okHttpClient = new OkHttpClient();}
    private String temp;

    public String sendrequest(final String url,final String sessionid,final String key,final String value){

        FormBody body = new FormBody.Builder().add(key,value).build(); //建立表单类请求体
        Request request = new Request.Builder()
                //.addHeader("Accept-Encoding","gzip, deflate, br")
                //.addHeader("Accpet","*/*")
                .addHeader("cookie", sessionid) //从mysession中获取本会话中的cookie确认登陆状态
                .url(url)
                .post(body).build();
        Call call = okHttpClient.newCall(request);
        System.out.println(request);
        /*处理请求返回回来的json串*/
        try {
            Response response = call.execute();
            String responseData = response.body().string(); //接收服务器response的消息体
//            System.out.print(responseData);
            temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}")+1); //处理从服务器传来的数据，去小括号
            /*处理json数组*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public String MapSend(final String url, final String sessionid, final HashMap<String,String> paramsMap){
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .addHeader("cookie", sessionid)
                .url(url)
                .post(formBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String responseData = response.body().string();
            System.out.println(responseData);

            temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
            System.out.println(temp);
        }catch (IOException e){
                e.printStackTrace();
            }
        return temp;
    }

}
