package com.ycr.kernel.util

import android.os.Environment
import java.io.File

/**
 * Created by yuchengren on 2019/2/26.
 */
fun File.deleteFile(){
    if(!exists()){
        return
    }
    if(isFile){
        delete()
    }else{
        val listFiles = listFiles()
        listFiles.forEach {
            it.deleteFile()
        }
        delete()
    }
}

fun File.createFile(): File{
    if(exists()){
        return this
    }
    val dir = parentFile
    if(!dir.exists()){
        dir.mkdirs()
    }
    if(!exists()){
        createNewFile()
    }
    return this
}

object FileUtils {

    @JvmStatic fun hasExternalStorage(): Boolean{
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    @JvmStatic fun getExternalStorage(): File{
        return Environment.getExternalStorageDirectory()
    }

}