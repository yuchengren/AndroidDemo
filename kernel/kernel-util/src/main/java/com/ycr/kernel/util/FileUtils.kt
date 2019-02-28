package com.ycr.kernel.util

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