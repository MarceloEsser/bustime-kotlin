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
-ignorewarnings
-keepattributes Signature
-keepattributes Annotation
-keepattributes SourceFile,LineNumberTable

-keep class org.jetbrains.kotlin.** {
*;
}
-keep class androidx.room.** {
*;
}
-keep class com.android.support.** {
*;
}
-keep class org.jetbrains.kotlinx.** {
*;
}
-keep class com.squareup.retrofit2.** {
*;
}
-keep class com.squareup.okhttp3.** {
*;
}
-keep class com.airbnb.android.** {
*;
}
-keep class marcelo.esser.com.bustimek.model.** {
*;
}
-keep class marcelo.esser.com.bustimek.helper.** {
*;
}
-keep class marcelo.esser.com.bustimek.interfaces.** {
*;
}
-keep class marcelo.esser.com.bustimek.service.** {
*;
}
