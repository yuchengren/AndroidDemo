package com.ycr.kernel.log.config;

import android.content.Context;
import android.os.Environment;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class FileLogPrinterConfig extends LogPrinterConfig implements IFileLogPrinterConfig {

	public static final long DEFAULT_MAX_TOTAL_SIZE = 10 * 1024 * 1024;
	public static final String DEFAULT_FOLDER_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_FILE_DATE_FORMAT = "HH:mm";
	public static final String LOG_DIR = "logs";
	private Context context;
	private String fileRootPath;
	private long maxTotalCacheSize;
	private String folderNameDateFormat;
	private String fileNameDateFormat;

	private FileLogPrinterConfig(Context context){
		this.context = context.getApplicationContext();
		fileRootPath = getDefaultFileRootPath(this.context);
		maxTotalCacheSize = DEFAULT_MAX_TOTAL_SIZE;
		folderNameDateFormat = DEFAULT_FOLDER_DATE_FORMAT;
		fileNameDateFormat = DEFAULT_FILE_DATE_FORMAT;
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

	public FileLogPrinterConfig setFolderNameDateFormat(String folderNameDateFormat) {
		this.folderNameDateFormat = folderNameDateFormat;
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
	public String folderNameDateFormat() {
		return folderNameDateFormat;
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
