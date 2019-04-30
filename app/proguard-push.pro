#个推
-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep class org.json.** { *; }

-keep class android.support.v4.app.NotificationCompat { *; }
-keep class android.support.v4.app.NotificationCompat$Builder { *; }
#魅族
-keep class com.meizu.** { *; }
-dontwarn com.meizu.**
#小米
-keep class com.xiaomi.** { *; }
-dontwarn com.xiaomi.push.**
-keep class org.apache.thrift.** { *; }
#华为
-keep class com.huawei.hms.** { *; }
-dontwarn com.huawei.hms.**

#push库 bean-大数据数据绑定
-keep class com.mistong.push.entity.** { *; }