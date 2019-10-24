# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5 # 指定代码的压缩级别
-dontusemixedcaseclassnames # 是否使用大小写混合
-dontpreverify # 混淆时是否做预校验
-verbose # 混淆时是否记录日志

-keep public class * extends android.os.Binder
-keep public class * extends android.appwidget.AppWidgetProvider
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.os.IInterface
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends androidx.fragment.app.DialogFragment
-dontwarn java.lang.invoke.**
-ignorewarnings
-dontoptimize
-dontpreverify

-keep class android.app.**{*;}
-keep class androidx.fragment.app.** { *; }

-keep class com.adups.distancedays.activity.** {*;}
-keep class com.adups.distancedays.adapter.** {*;}
-keep class com.adups.distancedays.base.** {*;}
-keep class com.adups.distancedays.db.** {*;}
-keep class com.adups.distancedays.event.** {*;}
-keep class com.adups.distancedays.manager.** {*;}
-keep class com.adups.distancedays.model.** {*;}
-keep class com.adups.distancedays.utils.** {*;}
-keep class com.adups.distancedays.view.** {*;}
-keep class com.color.distancedays.wxapi.** {*;}
-keep class com.color.distancedays.sharelib.** {*;}

-keepclasseswithmembernames class * { # 保持 native 方法不被混淆
   native <methods>;
}

# 泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}

-keep class * implements android.os.Parcelable { #保持Parcelable不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable { #保持Serializable不被混淆
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.color.distancedays.R$*{
     public static final int *;
 }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn Android.webkit.WebViewClient

#### start ProGuard configurations for Butter Knife ####
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#### end ProGuard configurations for Butter Knife ####

#### start ProGuard configurations for greendao ####
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keepclassmembers class **$Properties {*;}

# If you do not use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do not use RxJava:
-dontwarn rx.**
#### end ProGuard configurations for greendao ####

#### start ProGuard configurations for OkHttp ####
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn okio.**
-dontwarn java.nio.file.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class okhttp3.logging.HttpLoggingInterceptor$* {*;}
-keep class okhttp3.internal.http.HttpEngine$* {*;}
-dontwarn android.net.http.**
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#### end ProGuard configurations for OkHttp ####

#### start ProGuard configurations for retrofit2 ####
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
#### end ProGuard configurations for retrofit2 ####

#### start ProGuard configurations for glide ####
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#### end ProGuard configurations for glide ####

#fastjson
#### start ProGuard configurations for fastjson ####
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
#### end ProGuard configurations for fastjson ####

# gson
#### start ProGuard configurations for gson ####
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#### end ProGuard configurations for gson ####

#event bus
#### start ProGuard configurations for event bus ####
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class com.adups.distancedays.event.**{*;}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#### end ProGuard configurations for event bus ####

