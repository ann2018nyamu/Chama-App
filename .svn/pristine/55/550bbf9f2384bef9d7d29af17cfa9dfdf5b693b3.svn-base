package com.ekenya.echama.inc

import android.app.Application
import android.content.Context
import android.util.Log
import com.ekenya.echama.BuildConfig
import com.squareup.moshi.Moshi
import org.jetbrains.annotations.NotNull
import timber.log.Timber


class AppInfo : Application() {
    companion object {
        val moshi = Moshi.Builder().build()
        lateinit var  appC:Application
         var token:String = ""
    }


    override fun onCreate() {
        super.onCreate()
        appC = this
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
    }


}
class ReleaseTree : @NotNull Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN){
            //SEND ERROR REPORTS TO YOUR Crashlytics.
        }
    }

}