package com.ycr.module.base.util;


import com.ycr.module.base.DemoApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class OkHttpUtil {

	private static final String TAG = OkHttpUtil.class.getName();
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType TEXT_PLAIN = MediaType.parse("text/plain");
	public static final int DEFAULT_READ_TIMEOUT = 10000;
	public static final int DEFAULT_WRITE_TIMEOUT = 10000;
	public static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private OkHttpClient mOkHttpClient;
	private static OkHttpUtil mInstance;


	public interface ResponseCallBack {
		void onSuccess(Call call, String responseJsonString);

		void onFailure(Call call, Exception e);
	}

	private OkHttpUtil() {
	}

	public static OkHttpUtil getInstance() {
		if (mInstance == null) {
			synchronized (OkHttpUtil.class) {
				if (mInstance == null) {
					mInstance = new OkHttpUtil();
				}
			}
		}
		return mInstance;
	}

	public void initClient() {
		initClient(DEFAULT_CONNECT_TIMEOUT);
	}

	public void initClient(long connectTimeout) {
		initClient(DEFAULT_READ_TIMEOUT, DEFAULT_WRITE_TIMEOUT, connectTimeout);
	}

	public void initClient(long readTimeout, long writeTimeout, long connectTimeout) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
		builder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
		builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
		mOkHttpClient = builder.build();
	}

	public List<Call> getQueuedAndRunningCallList(){
		Dispatcher dispatcher = mOkHttpClient.dispatcher();
		List<Call> callList = new ArrayList<>();
		callList.addAll(dispatcher.queuedCalls());
		callList.addAll(dispatcher.runningCalls());
		return callList;
	}

	public OkHttpClient getOkHttpClient() {
		if (mOkHttpClient == null) {
			initClient();
		}
		return mOkHttpClient;
	}

	public void setOkHttpClient(OkHttpClient okHttpClient) {
		mOkHttpClient = okHttpClient;
	}

	public Request buildGetRequest(String url) {
		return new Request.Builder()
				.url(url)
				.build();
	}

	public Request buildGetRequest(String url, Object tag) {
		return new Request.Builder()
				.url(url)
				.tag(tag)
				.build();
	}

	public Request buildPostRequest(String url, String json) {
		return new Request.Builder()
				.url(url)
				.post(RequestBody.create(JSON, json))
				.build();
	}

	public Request buildPostRequestBody(String url, RequestBody requestBodyPost) {
		return new Request.Builder()
				.url(url)
				.post(requestBodyPost)
				.build();
	}

	public void doRequestAsync(Request request, final ResponseCallBack responseCallBack) {
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				if (responseCallBack != null) {
					responseCallBack.onFailure(call,e);
				}
			}

			@Override
			public void onResponse(Call call, Response response) {
				try {
					if (response.code() != 200) {
						throw new RuntimeException("OkHttpUtil.doRequest(),response code is " + response.code());
					}
					ResponseBody responseBody = response.body();
					if (responseBody == null) {
						throw new RuntimeException("OkHttpUtil.doRequest(),response.Body() is null!");
					}
					String responseBodyString = responseBody.string();
					if (responseBodyString == null) {
						throw new RuntimeException("OkHttpUtil.doRequest(),responseBody.string() is null!");
					}
					if (responseCallBack != null) {
						responseCallBack.onSuccess(call,responseBodyString);
					}
				} catch (Exception e) {
					if (responseCallBack != null) {
						responseCallBack.onFailure(call,e);
					}
				}
			}
		});
	}

	public String doRequest(Request request) throws Exception {
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		if (response.code() != 200) {
			throw new RuntimeException("OkHttpUtil.doRequest(),response code is +" + response.code());
		}
		ResponseBody responseBody = response.body();
		if (responseBody == null) {
			throw new RuntimeException("OkHttpUtil.doRequest(),response.Body() is null!");
		}
		String responseBodyString = responseBody.string();
		if (responseBodyString == null) {
			throw new RuntimeException("OkHttpUtil.doRequest(),responseBody.string() is null!");
		}
		return responseBodyString;
	}

	public String getRequest(String url, Object tag) throws Exception {
		Request request = buildGetRequest(url,tag);
		return doRequest(request);
	}

	public String getRequest(String url) throws Exception {
		Request request = buildGetRequest(url);
		return doRequest(request);
	}

	public String postRequest(String url, String json) throws Exception {
		Request request = buildPostRequest(url, json);
		return doRequest(request);
	}

	public void postAsyncRequest(String url, String json, ResponseCallBack responseCallBack){
		Request request = buildPostRequest(url, json);
		doRequestAsync(request,responseCallBack);
	}
	public void postAsyncRequestBody(String url, RequestBody requestBodyPost, ResponseCallBack responseCallBack){
		Request request = buildPostRequestBody(url, requestBodyPost);
		doRequestAsync(request,responseCallBack);
	}


	public static String get(String url) throws Exception {
		getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		return getInstance().getRequest(url);
	}

	public static String get(String url, long connectTimeout, Object tag) throws Exception {
		if (connectTimeout == DEFAULT_CONNECT_TIMEOUT) {
			getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		} else {
			getInstance().initClient(connectTimeout);
		}
		return getInstance().getRequest(url,tag);
	}

	public static String get(String url, long connectTimeout) throws Exception {
		if (connectTimeout == DEFAULT_CONNECT_TIMEOUT) {
			getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		} else {
			getInstance().initClient(connectTimeout);
		}
		return getInstance().getRequest(url);
	}

	public static String post(String url, String json) throws Exception {
		getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		return getInstance().postRequest(url, json);
	}

	public static void postAync(String url, String json, ResponseCallBack responseCallBack) {
		getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		getInstance().postAsyncRequest(url, json,responseCallBack);
	}

	public static void postAyncBody(String url, RequestBody requestBodyPost, ResponseCallBack responseCallBack) {
		getInstance().setOkHttpClient(DemoApplication.getInstance().getDefaultOkHttpClient());
		getInstance().postAsyncRequestBody(url, requestBodyPost,responseCallBack);
	}
}
