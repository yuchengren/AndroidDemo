package com.yuchengren.mvp.Util;

import android.text.TextUtils;
import android.util.Log;

import com.yuchengren.mvp.Bean.ResponseEntity;
import com.yuchengren.mvp.constant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuchengren on 2016/12/26.
 */

public class AccessServerAPI {

	public interface OnFinishListener<T>{
		void onSuccess(List<T> tList);
		void onFailure(Exception e);
	}



	public static void  postRequestServerDataToList(String url, Map map, final OnFinishListener onFinishListener){

		OkHttpUtil.post(url,map,new OkHttpUtil.ResultCallBack(){
			@Override
			public void onSuccess(String responseJsonString) {
				if(onFinishListener != null){
					try{



					}catch (Exception e){
						onFinishListener.onFailure(e);
					}
				}
			}

			@Override
			public void onFailure(Exception e) {
				if(onFinishListener != null){
					onFinishListener.onFailure(e);
				}
			}
		});
	}

	public static <T> void  getRequestServerDataToList(String url, final Class<T> tClass, final OnFinishListener onFinishListener){

		OkHttpUtil.get(url,new OkHttpUtil.ResultCallBack(){
			@Override
			public void onSuccess(String responseJsonString) {
				if(onFinishListener != null){
					try{
						ResponseEntity responseEntity = GsonUtil.parseJsonToBean(responseJsonString, ResponseEntity.class);
						List<T> tList = parseResponseResultToList(responseEntity, tClass);
						onFinishListener.onSuccess(tList);

					}catch (Exception e){
						onFinishListener.onFailure(e);
					}
				}
			}

			@Override
			public void onFailure(Exception e) {
				if(onFinishListener != null){
					onFinishListener.onFailure(e);
				}
			}
		});
	}

	public static <T> List<T>  parseResponseResultToList(ResponseEntity responseEntity,Class<T> tClass){
		List<T> tList = null;
		if (Constant.REQUEST_SUCCESS.equals(responseEntity.getCode())) {
			String result = responseEntity.getResult();
			if (!TextUtils.isEmpty(result)) {
				tList = GsonUtil.parseJsonToList(result, tClass);
			}
		} else if (Constant.EM0001.equals(responseEntity.getCode()) || Constant.EC0001.equals(responseEntity.getCode()) || Constant.EA0001.equals(responseEntity.getCode()) || Constant.EP0001.equals(responseEntity.getCode())
				|| Constant.ES0001.equals(responseEntity.getCode()) || Constant.EK0001.equals(responseEntity.getCode())) {
			// 数据为空，视为成功
			tList =  new ArrayList();
		} else {
			Log.e("", responseEntity.getMsg());
		}
		return tList;
	}


}
