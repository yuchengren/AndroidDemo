package com.ycr.kernel.log.config;

import android.content.Context;
import android.os.Environment;

import com.ycr.kernel.log.constants.LogLevel;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class FileLogPrinterConfig extends LogPrinterConfig implements IFileLogPrinterConfig {

	public static final long DEFAULT_MAX_TOTAL_SIZE = 10 * 1024 * 1024;
	public static final String DEFAULT_NAME_TIME_FORMAT = "yyyy-MM-dd";
	public static final String LOG_DIR = "logs";
	private Context context;
	private String fileRootPath;
	private long maxTotalCacheSize;
	private String fileNameDateFormat;

	private FileLogPrinterConfig(Context context){
		this.context = context.getApplicationContext();
		fileRootPath = getDefaultFileRootPath(this.context);
		maxTotalCacheSize = DEFAULT_MAX_TOTAL_SIZE;
		fileNameDateFormat = DEFAULT_NAME_TIME_FORMAT;
	}

	private String getDefaultFileRootPath(Context context){
		String fileRootPath;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			fileRootPath = context.getExternalFilesDir(null).getAbsolutePath();
		}else{
			fileRootPath = context.getFilesDir().getAbsolutePath();
		}
		return fileRootPath +"/"+ LOG_DIR;
	}

	public static FileLogPrinterConfig create(Context context){
		return new FileLogPrinterConfig(context);
	}

	public FileLogPrinterConfig setFileRootPath(String fileRootPath) {
		this.fileRootPath = fileRootPath;
		return this;
	}

	public FileLogPrinterConfig setMaxTotalCacheSize(long maxTotalCacheSize) {
		this.maxTotalCacheSize = maxTotalCacheSize;
		return this;
	}

	public FileLogPrinterConfig setFileNameDateFormat(String fileNameDateFormat) {
		this.fileNameDateFormat = fileNameDateFormat;
		return this;
	}

	@Override
	public Context context() {
		return context;
	}

	@Override
	public String fileRootPath() {
		return fileRootPath;
	}

	@Override
	public long maxTotalCacheSize() {
		return maxTotalCacheSize;
	}

	@Override
	public String fileNameDateFormat() {
		return fileNameDateFormat;
	}

	@Override
	public FileLogPrinterConfig setLevel(int level) {
		this.level = level;
		return this;
	}
}
