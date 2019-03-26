package com.yuchengren.demo.app.adaptation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.service.notification.StatusBarNotification
import android.support.v4.app.NotificationCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import com.ycr.module.base.BaseActivity
import com.yuchengren.demo.R
import io.reactivex.Notification
import kotlinx.android.synthetic.main.activity_android_seven_adaptation.*
import java.io.File

/**
 * Created by yuchengren on 2019/3/20.
 */
class AndroidSevenAdaptationActivity: BaseActivity() {

    companion object {
        const val ACTION_NOTIFICATION_DELETE = "com.example.android.activenotifications.delete"
        const val NOTIFICATION_GROUP = "com.example.android.activenotifications.notification_type"
        const val NOTIFICATION_ID_GROUP = 1001
    }
    private lateinit var notificationManager: NotificationManager
    private var notificationId = NOTIFICATION_ID_GROUP + 1

    private val deleteNotificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNumberOfGroupNotification()
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_android_seven_adaptation
    }

    override fun bindView(rootView: View?) {
        super.bindView(rootView)
        btnFilePermission.setOnClickListener {
            //error范例
//            val uri = Uri.fromFile(File(externalCacheDir,"${System.currentTimeMillis()}.jpg"))
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//                putExtra(MediaStore.EXTRA_OUTPUT,uri)
//            }
//            startActivityForResult(intent,1)
            //android.os.FileUriExposedException:
            // file:///storage/emulated/0/Android/data/com.yuchengren.demo/cache/1553050480556.jpg
            // exposed beyond app through ClipData.Item.getUri()
            val photoPath = File(Environment.getExternalStorageDirectory(),"photos")
            val file = File(photoPath,"${System.currentTimeMillis()}.jpg")

            val uri = FileProvider.getUriForFile(this,"com.yuchengren.demo.fileprovider",file)
            //uri:content://com.yuchengren.demo.fileprovider/picture/1553066693109.jpg
            //uri物理地址：/storage/emulated/0/photos/1553066693109.jpg
            Log.e("uri",uri.toString())
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                // 授予目录临时共享权限
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                putExtra(MediaStore.EXTRA_OUTPUT,uri)
            }
            startActivityForResult(intent,1)

            val dir = getDir(cacheDir.path, Context.MODE_WORLD_WRITEABLE)
            //java.lang.SecurityException: MODE_WORLD_WRITEABLE no longer supported

        }

        btnAddNotify.setOnClickListener {
            addNotification()
        }
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(deleteNotificationReceiver, IntentFilter(ACTION_NOTIFICATION_DELETE))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(deleteNotificationReceiver)
    }

    private fun addNotification() {
        val deletePendingIntent = PendingIntent.getBroadcast(this,
                1,Intent(ACTION_NOTIFICATION_DELETE),0)
        val builder = NotificationCompat.Builder(this).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("7.0通知标题").
                setContentText("7.0通知内容").
                setDeleteIntent(deletePendingIntent).
                setGroup(NOTIFICATION_GROUP).
                setAutoCancel(true)
        val notification = builder.build()
        notificationManager.notify(notificationId,notification)
        notificationId ++

        updateNotificationSummary()
        updateNumberOfGroupNotification()
    }

    private fun updateNotificationSummary() {
        val numberOfGroupNotification = getNumberOfGroupNotification()
        if(numberOfGroupNotification > 1){
            val builder = NotificationCompat.Builder(this).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setGroup(NOTIFICATION_GROUP).
                    setGroupSummary(true).
                    setStyle(NotificationCompat.BigTextStyle().setSummaryText("分组有${numberOfGroupNotification}个通知"))

            val notification = builder.build()
            notificationManager.notify(NOTIFICATION_ID_GROUP,notification)
        }else{
            notificationManager.cancel(NOTIFICATION_ID_GROUP)
        }
    }

    private fun updateNumberOfGroupNotification() {
        activeNotifyNum.text = "该分组通知数量:${getNumberOfGroupNotification()}"
    }

    private fun getNumberOfGroupNotification(): Int{
        val statusBarNotifications: Array<StatusBarNotification> = notificationManager.activeNotifications
        for (statusBarNotification in statusBarNotifications) {
            if(statusBarNotification.id == NOTIFICATION_ID_GROUP){
               return statusBarNotifications.size - 1
            }
        }
        return statusBarNotifications.size
    }

}