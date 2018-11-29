package com.ycr.kernel.log.printer;

import android.text.TextUtils;

import com.ycr.kernel.log.config.IFileLogPrinterConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class FileLogHelper {
	private static FileLogHelper instance;
	private IFileLogPrinterConfig logFileConfig;

	private FileLogHelper(IFileLogPrinterConfig logFileConfig){
		this.logFileConfig = logFileConfig;
	}

	public static FileLogHelper getInstance(IFileLogPrinterConfig logFileConfig){
		if(instance == null){
			synchronized (FileLogHelper.class){
				if(instance == null){
					instance = new FileLogHelper(logFileConfig);
				}
			}
		}
		return instance;
	}

	public boolean write(int level, String tag, String msg){
		while (isOverMaxCacheSize()){
			if(!deleteOldestFile(logFileConfig.fileRootPath())){
				return false;
			}
		}

		StringBuilder builder = new StringBuilder();
		if(!TextUtils.isEmpty(tag)){
			builder.append(tag);
		}
		if(!TextUtils.isEmpty(msg)){
			builder.append(msg);
		}
		return writeMessage(builder.toString());
	}

	private boolean writeMessage(String message) {
		String fileName = getCacheFileName();
		File file = new File(logFileConfig.fileRootPath(),fileName);
		if(!file.exists()){
			file.mkdirs();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(file,true));
			writer.println(message);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			if(writer != null){
				writer.close();
			}
		}
		return true;
	}

	private String getCacheFileName() {
		SimpleDateFormat format = new SimpleDateFormat(logFileConfig.fileNameDateFormat());
		return format.format(new Date());
	}

	private boolean deleteOldestFile(String path) {
		File file = new File(path);
		List<File> allFiles = getAllFiles(file);
		if(allFiles.size() == 0){
			return true;
		}
		if(allFiles.size() == 1){
			return splitFile(allFiles.get(0));
		}

		Collections.sort(allFiles, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return Long.valueOf(o1.lastModified()).compareTo(o2.lastModified());
			}
		});
		return deleteFile(allFiles.get(0));
	}

	private boolean deleteFile(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			if(files != null && files.length != 0){
				for (File child : files) {
					if(child.isDirectory()){
						if(!deleteFile(file)){
							return false;
						}
					}else{
						if(!file.delete()){
							return false;
						}
					}
				}
			}
		}
		return file.delete();
	}

	private static final float SPLIT_FACTOR = 0.3f;

	private boolean splitFile(File file){
		boolean flag = true;
		String currentFileName = file.getAbsolutePath();
		String tempFileName = currentFileName + "temp";
		File tempFile = new File(tempFileName);
		byte[] readData = new byte[2048];
		RandomAccessFile readFile = null;
		RandomAccessFile writeFile = null;
		try {
			readFile = new RandomAccessFile(currentFileName, "r");
			writeFile = new RandomAccessFile(tempFile, "rw");
			long length = file.length();
			long offset = (long)(length * SPLIT_FACTOR);
			readFile.seek(offset);
			int temp;
			while ((temp = readFile.read(readData)) > 0) {
				writeFile.write(readData, 0, temp);
			}
		} catch (FileNotFoundException e) {
			flag = false;
		} catch (IOException e) {
			flag = false;
		}finally {
			try {
				if (readFile != null) {
					readFile.close();
				}
				if (writeFile != null) {
					writeFile.close();
				}
			} catch (IOException e) {}
		}
		if (flag) {
			if(file.delete()){
				tempFile.renameTo(new File(currentFileName));
			}else{
				tempFile.delete();
			}
		}else{
			tempFile.delete();
		}
		return flag;
	}

	private List<File> getAllFiles(File file){
		List<File> fileList = new ArrayList<>();
		File[] files = file.listFiles();
		if(files != null && files.length != 0){
			for (File child : files) {
				if(child.isFile()){
					fileList.add(child);
				}else{
					fileList.addAll(getAllFiles(file));
				}
			}
		}
		return fileList;
	}



	public boolean isOverMaxCacheSize(){
		return getFolderSize(new File(logFileConfig.fileRootPath())) >= logFileConfig.maxTotalCacheSize();
	}

	public long getFolderSize(File file){
		long size = 0;
		File[] files = file.listFiles();
		if(files == null){
			return size;
		}
		for (File childFile : files) {
			if(childFile.isFile()){
				size += childFile.length();
			}else{
				size += getFolderSize(childFile);
			}
		}
		return size;
	}
}
