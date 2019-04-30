# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#new hunxiao
#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

#保留行号
-keepattributes SourceFile,LineNumberTable
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#-keep class com.mistong.opencourse.entity** {*;}
-keep class com.mistong.opencourse.** {*;}


#忽略警告
-ignorewarning

##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######

#如果引用了v4或者v7包
-dontwarn android.support.**
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep public class com.mistong.opencourse.R$*{
public static final int *;
}
#避免混淆泛型 如果混淆报错建议关掉
#-keepattributes Signature

#jackson混淆
-keep class com.fasterxml.jackson.** {*;}

#xutils包下所有类不要混淆，包括类里面的方法
-keep class com.lidroid.** { *; }
-keep class * extends java.lang.annotation.Annotation { *; }
-keepclassmembers  class  *  extends  android.support.v4.app.Fragment {
    public void  *(android.view.View);
    public boolean *(android.view.View);
}
#-dontwarn com.lidroid.xutils.**
#-keep class com.lidroid.xutils.**{*;}
#event bus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
#JsonObject
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
##share
-keep class com.tencent.mm.sdk.** {
   *;
}

-keepnames class * extends android.view.View

-keep class * extends android.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
#end growing IO
#jinshan player
-dontwarn com.ksyun.**
-keep class com.ksyun.media.player.**{ *; }
-keep class com.ksy.statlibrary.**{ *;}
#jinshan end player
#commons-collections.jar commons-httpclient.jar commons-lang-2.5.jar
-dontwarn org.apache.commons.**
-keep class corg.apache.commons.**{ *; }
# end
#imageLoder
-keep class com.nostra13.universalimageloader.** { *; }
#end
#友盟
-keep class com.umeng.**{*;}
#end
#信鸽推送  IM
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.**  {* ;}
#end
#腾讯IM
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

-keep class tencent.**{*;}
-dontwarn tencent.**

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**
#end

-dontwarn com.squareup.**
-dontwarn okio.**

#arc player
#arc player
-dontwarn com.arcvideo.**
-keep class com.arcvideo.MediaPlayer.**{ *; }
-keep class com.arcvideo.**{ *;}
#jinshan end player


#基线包使用，生成mapping.txt
-printmapping mapping.txt
#生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
#防止inline
-dontoptimize

-keepclassmembers class com.mistong.opencourse.MstApplication {
    #public <init>();
    *;
}

-keep class * extends com.kaike.la.router.api.AbstractMemberInjector
-keep class * implements com.kaike.la.router.IOutDefine
-keepclasseswithmembernames class * {
     @com.kaike.la.router.annotation.* <fields>;
}


#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}