package com.yuchengren.demo.util

import android.util.Base64
import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Created by yuchengren on 2018/11/19.
 */
object CompressUtils {
    private const val  CHARSET_ISO_8859_1 = "ISO-8859-1"

    fun compressString(input: String?): String{
        if(input.isNullOrEmpty()){
            return ""
        }
        val out = ByteArrayOutputStream()
        val gzipOut = GZIPOutputStream(out)
        try {
            gzipOut.write(input?.toByteArray())
        }catch (e: IOException){
            e.printStackTrace()
        }finally {
            try {
                gzipOut.close()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
//        Log.e("compressString byte",out.toByteArray().size.toString())
//        return out.toString(CHARSET_ISO_8859_1)
        return Base64.encodeToString(out.toByteArray(),Base64.DEFAULT)
    }

    fun uncompressString(zippedString: String?): String{
        if(zippedString.isNullOrEmpty()){
            return ""
        }
        val output = ByteArrayOutputStream()
//        val input = ByteArrayInputStream(zippedString?.toByteArray(Charset.forName(CHARSET_ISO_8859_1)))
        val input = ByteArrayInputStream(Base64.decode(zippedString,Base64.DEFAULT))
        val gzipIn = GZIPInputStream(input)
        try {
            val byteArray = ByteArray(1024)
            var n = gzipIn.read(byteArray)
            while(n >= 0){
                output.write(byteArray,0,n)
                n = gzipIn.read(byteArray)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }finally {
            try {
                gzipIn.close()
                output.close()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
        return output.toString()
    }
}