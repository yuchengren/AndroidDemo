#for dagger
#Keep the annotated things annotated
-keepattributes *Annotation*

#Keep the dagger annotation classes themselves
-keep @interface dagger.*,javax.inject.*

#Keep the Modules intact
#-keep @dagger.Module class *

#-Keep the fields annotated with @Inject of any class that is not deleted.
-keepclassmembers class * {
  @javax.inject.* <fields>;
}

#-Keep the names of classes that have fields annotated with @Inject and the fields themselves.
-keepclassmembers class * {
  @javax.inject.* <fields>;
}

# Keep the generated classes by dagger-compile
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class dagger.**{*;}
-dontwarn dagger.**

-keep class * implements AppComponent{

}