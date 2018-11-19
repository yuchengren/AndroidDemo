package com.yuchengren.mvp.util

import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by yuchengren on 2018/11/19.
 */
object CompressUtils {

    fun compressString(input: String?): String{
        if(input.isNullOrEmpty()){
            return ""
        }
//        val out = ByteArrayOutputStream()
//        val gzipOut = ZipOutputStream(out)
//        gzipOut.putNextEntry(ZipEntry("0"))
//        gzipOut.write(input?.toByteArray())
////        gzipOut.close()
//        gzipOut.closeEntry()

        val out2 = ByteArrayOutputStream()
        val gzipOut2 = GZIPOutputStream(out2)
//        gzipOut2.putNextEntry(ZipEntry("1"))
        gzipOut2.write(input?.toByteArray())
        gzipOut2.close()
//        gzipOut2.closeEntry()

        Log.e("compressString byte",out2.toByteArray().size.toString())
        return out2.toString("ISO-8859-1")
    }

    fun uncompressString(zippedString: String?): String{
        if(zippedString.isNullOrEmpty()){
            return ""
        }
        val output = ByteArrayOutputStream()
        val input = ByteArrayInputStream(zippedString?.toByteArray(Charset.forName("ISO-8859-1")))
        val gzipIn = GZIPInputStream(input)
        val byteArray = ByteArray(1024)
        var n = gzipIn.read(byteArray)
        while(n >= 0){
            output.write(byteArray,0,n)
            n = gzipIn.read(byteArray)
        }
        return output.toString()
    }
}