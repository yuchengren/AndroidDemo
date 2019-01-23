package com.yuchengren.demo.app.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ycr.kernel.log.LogHelper;
import com.yuchengren.demo.R;
import com.ycr.module.base.entity.People;
import com.ycr.module.base.util.GsonUtil;
import com.ycr.module.base.util.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuchengren on 2017/12/20.
 */

public class RxAndroidActivity extends AppCompatActivity implements View.OnClickListener{
	public final String TAG = getClass().getSimpleName();
	private Button btn_test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_android);
		btn_test = (Button) findViewById(R.id.btn_test);
		btn_test.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_test:
//				test();
//				testSetThread();
				test2();
				break;
			default:
				break;
		}
	}

	private void test() {
		//被观察者(事件的产生者）
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> e) throws Exception {
				//ObservableEmitter 发射器 发送事件
				//例如子线程里访问数据库或者访问网络，获取到数据后

				/*Observable 发送 onComplete 或 onError 事件后，Observable 可以继续发送事件，但 Observer 不再接收事件
				Observable 可以不发送 onComplete 或 onError 事件
				onComplete和onError事件互斥，同时调用会导致程序崩溃
				onComplete可以多次发送，onError事件唯一，多次发送导致程序崩溃
				*/
				e.onNext(1);
				e.onNext(2);
				e.onNext(3);
				e.onComplete();
//				e.onError(new RuntimeException("error"));

				e.onNext(4);
			}
		});
		//观察者（事件的消费者）
		Observer<Integer> observer = new Observer<Integer>() {
			private Disposable disposable;
			@Override
			public void onSubscribe(Disposable d) {
				//Observable先调用了onSubscribe，才开始发送事件
				//Disposable 英文含义一次性的，Disposable.dispose()取消订阅，观察者不再接收事件
				this.disposable = d;
				LogHelper.d(TAG,"Observer:onSubscribe ");
			}

			@Override
			public void onNext(Integer s) {
				LogHelper.d(TAG,"Observer:onNext " + s);
				ToastHelper.show(String.valueOf(s));
				if(s >= 2){
					disposable.dispose();
				}
			}

			@Override
			public void onError(Throwable e) {
				LogHelper.d(TAG,"Observer:onError " + e.toString());
			}

			@Override
			public void onComplete() {
				LogHelper.d(TAG,"Observer:onComplete ");
			}
		};
		observable.subscribe(observer);

	}

	private void testSetThread() {
		/* Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
		Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
		Schedulers.newThread() 代表一个常规的新线程
		AndroidSchedulers.mainThread() 代表Android的主线程
		*/
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> e) throws Exception {
				LogHelper.d(TAG,"Observable,CurrentThreadName="+Thread.currentThread().getName());
				e.onNext(1);
				Thread.sleep(3000);
				e.onNext(2);
				e.onComplete();
			}
		})
		.subscribeOn(Schedulers.io())//指定Observable事件产生的线程 CurrentThreadName=RxCachedThreadScheduler-1
//		.subscribeOn(Schedulers.newThread())//多次指定Observable线程，只有第一次有效，CurrentThreadName=RxNewThreadScheduler-1
				.observeOn(AndroidSchedulers.mainThread())//指定Observer事件消费的线程，CurrentThreadName=main
//				.observeOn(Schedulers.computation())//多次指定Observer线程，会顺序切换线程，accept事件在最后一个线程中，CurrentThreadName=RxComputationThreadPool-1
				.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(Integer integer) throws Exception {
						LogHelper.d(TAG,"Observer,CurrentThreadName="+Thread.currentThread().getName());
						//Consumer 只接收onNext事件
						LogHelper.d(TAG,"Observer:accept " + integer);
						ToastHelper.show(String.valueOf(integer));
					}
				});
	}

	private void test2() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				//IO线程中做网络访问等耗时操作
//				String responseJsonString = OkHttpUtil.get("http://192.168.0.1:8080/TradeType=getPeoples");
//				String responseJsonString = "[{\"gendar\":29,\"name\":\"ren\",\"sex\":0},{\"gendar\":30,\"name\":\"ling\",\"sex\":1}]";
				List<People> peopleList = new ArrayList<>();
				peopleList.add(new People("ren", 0, 29));
				peopleList.add(new People("ling",1,30));
				LogHelper.e(TAG,GsonUtil.formatObjectToJson(peopleList));

				e.onNext(GsonUtil.formatObjectToJson(peopleList));
			}
		}).map(new Function<String, List<People>>() {
			@Override
			public List<People> apply(String s) throws Exception {
				LogHelper.d(TAG,"map,CurrentThreadName="+Thread.currentThread().getName());
				List<People> peopleList = GsonUtil.parseJsonToList(s,People.class);
				//json解析成实体类
				return peopleList;
			}
		}).doOnNext(new Consumer<List<People>>() {
			@Override
			public void accept(List<People> people) throws Exception {
				LogHelper.d(TAG,"doOnNext,CurrentThreadName="+Thread.currentThread().getName());
				//存入数据库
			}
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<List<People>>() {
					@Override
					public void accept(List<People> people) throws Exception {
						//主线程中刷新页面
					}
				});
	}
}
