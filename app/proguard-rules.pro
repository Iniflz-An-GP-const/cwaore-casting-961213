# =====================================================================
# R8 / ProGuard rules — Titan Stats release build
#
# Release build ships with isMinifyEnabled = true and isShrinkResources = true:
# R8 strips unused code/resources and renames classes, methods and fields.
# Below is the minimal set of rules needed to avoid runtime crashes.
#
# Mapping file (needed to decode stack traces from Play Console):
#   app/build/outputs/mapping/release/mapping.txt
# =====================================================================

# ---------------------------------------------------------------------
# Stack traces
# ---------------------------------------------------------------------
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ---------------------------------------------------------------------
# Attributes Kotlin/libraries need to read generic types
# ---------------------------------------------------------------------
-keepattributes Signature,InnerClasses,EnclosingMethod
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

# ---------------------------------------------------------------------
# kotlinx.serialization + type-safe Navigation Compose
# (NavRoute in gbgmail.c183.summitsport.app.nav is @Serializable and its serializer
# is looked up reflectively by KClass — without these rules R8 strips
# the companion/INSTANCE and navigation fails with SerializationException.)
# ---------------------------------------------------------------------
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# ---------------------------------------------------------------------
# Room (AppDatabase, CartDao, OrderDao)
# ---------------------------------------------------------------------
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-dontwarn androidx.room.paging.**

# ---------------------------------------------------------------------
# OkHttp
# ---------------------------------------------------------------------
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ---------------------------------------------------------------------
# Retrofit + Gson (wrapper network layer)
# ---------------------------------------------------------------------
# ApiRequest/ApiResponse/ApiVisitRequest are parsed by Gson reflectively, by
# field name. Renaming fields silently breaks the JSON: build succeeds,
# debug works, release sends keys "a", "b", "c" and gets empty responses.
#
# Both rules are required:
#   1) keep,allowobfuscation keeps the classes from being stripped, but
#      still allows the class names themselves to be renamed (Gson does
#      not use class names);
#   2) keepclassmembers pins field names (what Gson actually reads).
-keep,allowobfuscation class gourmet.cwaorecasting.hubapp.guide.data.service.** {
    <fields>;
}
-keepclassmembers class gourmet.cwaorecasting.hubapp.guide.data.service.** {
    <fields>;
}

# Retrofit builds a dynamic proxy off annotations + generic signature.
# Interface/method names aren't needed (all info lives in annotations).
-keep,allowobfuscation interface gourmet.cwaorecasting.hubapp.guide.data.service.ApiService {
    *;
}

# ---------------------------------------------------------------------
# WorkManager (gourmet.cwaorecasting.hubapp.guide.worker.VisitRequestWorker — push click tracking)
# ---------------------------------------------------------------------
# WorkManager stores the worker class name as a string in its own DB;
# a queued task must still find the class after an update.
-keep class * extends androidx.work.ListenableWorker

# ---------------------------------------------------------------------
# Type-safe Navigation Compose route classes
# ---------------------------------------------------------------------
# Routes are resolved at runtime by their fully-qualified class name
# (serialName). R8 renaming the class breaks nav with
# "Cannot find class with name ...". Keep the names (not just members).
-keep class gourmet.cwaorecasting.hubapp.data.model.Difficulty { *; }
-keep class gourmet.cwaorecasting.hubapp.ui.composable.navigation.NavRoute
-keep class gourmet.cwaorecasting.hubapp.ui.composable.navigation.NavRoute$* { *; }

# ---------------------------------------------------------------------
# Other
# ---------------------------------------------------------------------
# Compose, DataStore, Coil, Retrofit, OkHttp, Gson and Firebase ship
# their own consumer ProGuard rules inside their artifacts — no need to
# duplicate them here.

-classobfuscationdictionary dict.txt
-obfuscationdictionary      dict.txt