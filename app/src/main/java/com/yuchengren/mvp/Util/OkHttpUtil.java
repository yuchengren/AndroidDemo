package com.yuchengren.mvp.Util;

import com.yuchengren.mvp.interfaces.callback.RequestCallBack;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class OkHttpUtil {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mOkHttpClient;
    private static OkHttpUtil mInstance;

    public static void request(String url, String request,RequestCallBack callback){
        //在访问服务器成功获取到数据的时候，调用callback.onSuccess()
    //在失败的时候，调用callback.onFail()
    }

//    OkHttpClient client = new OkHttpClient();
//
//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");
//
//    OkHttpClient client = new OkHttpClient();
//
//    String post(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }

    public interface ResultCallBack{
        void onSuccess(String responseJsonString);
        void onFailure(Exception e);
    }


    public OkHttpUtil(OkHttpClient okHttpClient) {
        if(okHttpClient == null ){
            mOkHttpClient = new OkHttpClient();
        }else{
            mOkHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtil initClient(OkHttpClient okHttpClient){
        if(mInstance == null){
            synchronized (OkHttpUtil.class){
                if(mInstance == null){
                    mInstance = new OkHttpUtil(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtil getInstance(){
        return initClient(null);
    }

    public Request buildGetRequest(String url){
        return  new Request.Builder()
                .url(url)
                .build();
    }

    public Request buildPostRequest(String url,String json){
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON,json))
                .build();
    }

    public void doRequest(Request request,final ResultCallBack resultCallBack){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(resultCallBack != null){
                    resultCallBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String responseBodyString = response.body().toString();
                    if(resultCallBack != null){
                        resultCallBack.onSuccess(responseBodyString);
                    }
                }catch (Exception e){
                    if(resultCallBack != null){
                        resultCallBack.onFailure(e);
                    }
                }
            }
        });
    }

    public void getRequest(String url, ResultCallBack resultCallBack){
        Request request = buildGetRequest(url);
        doRequest(request,resultCallBack);
    }

    public void postRequest(String url,String json, ResultCallBack resultCallBack){
        Request request = buildPostRequest(url, json);
        doRequest(request,resultCallBack);
    }

    public static void get(String url,ResultCallBack resultCallBack){
        getInstance().getRequest(url,resultCallBack);
    }

    public static void post(String url, String json,ResultCallBack resultCallBack){
        getInstance().postRequest(url,json,resultCallBack);
    }


    public static void post(String url, Map map,ResultCallBack resultCallBack){
        String json = GsonUtil.formatObjectToJson(map);
        getInstance().postRequest(url,json,resultCallBack);
    }






}
