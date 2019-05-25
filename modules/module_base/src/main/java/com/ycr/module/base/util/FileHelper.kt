package com.ycr.module.base.util

import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.util.Chars
import com.ycr.kernel.util.FileUtils
import com.ycr.kernel.util.createFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * created by yuchengren on 2019/5/20
 */
object FileHelper {

    @JvmStatic fun getTempFile(suffix: String =""): File {
        return File(getTempFilePath(), getTempFileName(suffix))
    }

    @JvmStatic fun getTempPicFile(suffix: String = ".jpg"): File{
        return getTempFile(suffix)
    }

    @JvmStatic fun createTempFile(suffix: String =""): File{
        return getTempFile(suffix).createFile()
    }

    @JvmStatic fun createTempPicFile(suffix: String = ".jpg"): File{
        return createTempFile(suffix)
    }

    @JvmStatic fun getTempFilePath(): String{
        return getAppFilePath() + Chars.SLASH_FORWARD + "temp"
    }

    @JvmStatic fun getAppExternalPath(): String{
        return FileUtils.getExternalStorage().absolutePath + Chars.SLASH_FORWARD + ContextHelper.getContext().packageName
    }

    @JvmStatic fun getAppInternalPath(): String{
        return ContextHelper.getContext().filesDir.path
    }

    @JvmStatic fun getAppFilePath(): String{
        return if(FileUtils.hasExternalStorage()) getAppExternalPath() else getAppInternalPath()

    }

    @JvmStatic fun getTempFileName(suffix: String = ""): String{
        val time = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CANADA).format(Date())
        val random = Random.nextInt()
        return "${time}_$random$suffix"
    }

}