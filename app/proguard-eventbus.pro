# eventbus
# There's no way to keep all @Observes methods, so use the *Event convention to identify event handlers
-keepclassmembers class * {
    void *(***Event);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}