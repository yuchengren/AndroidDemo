package com.ycr.kernal.log.engine;

/**
 * Created by yuchengren on 2018/7/11.
 */
public interface ILog {
	void v(String tag,String msg,Object...args);
	void v(String tag, Throwable tr, String msg, Object... args);
	void v(String tag, Throwable tr);
	void v(String msg,Object...args);
	void v(Throwable tr, String msg, Object... args);
	void v(Throwable tr);

	void d(String tag,String msg,Object...args);
	void d(String tag, Throwable tr, String msg, Object... args);
	void d(String tag, Throwable tr);
	void d(String msg,Object...args);
	void d(Throwable tr, String msg, Object... args);
	void d(Throwable tr);

	void i(String tag,String msg,Object...args);
	void i(String tag, Throwable tr, String msg, Object... args);
	void i(String tag, Throwable tr);
	void i(String msg,Object...args);
	void i(Throwable tr, String msg, Object... args);
	void i(Throwable tr);

	void w(String tag,String msg,Object...args);
	void w(String tag, Throwable tr, String msg, Object... args);
	void w(String tag, Throwable tr);
	void w(String msg,Object...args);
	void w(Throwable tr, String msg, Object... args);
	void w(Throwable tr);

	void e(String tag,String msg,Object...args);
	void e(String tag, Throwable tr, String msg, Object... args);
	void e(String tag, Throwable tr);
	void e(String msg,Object...args);
	void e(Throwable tr, String msg, Object... args);
	void e(Throwable tr);
}
