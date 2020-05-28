package com.example.sqlite

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import java.util.*


var DeBug:Boolean=false
class AppController : Application(){
    val TAG="AppController"
    var appController: AppController = this

    override fun onCreate() {
        super.onCreate()
        this.appController = this

        val DeBug = getAppMetaDataBoolean(appController, "isDeBug", false)
        setLDeBug(DeBug)

        Log.e(TAG,"onCreate isDeBug:"+DeBug)
    }

    @Synchronized
    fun getInstance(): AppController? {
        return this.appController
    }

    private fun getAppMetaDataBoolean(
        context: Context,
        metaName: String,
        defaultValue: Boolean
    ): Boolean {
        try { //application标签下用getApplicationinfo，如果是activity下的用getActivityInfo
            val packageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(context.packageName,PackageManager.GET_META_DATA)
            val metaData =  applicationInfo.metaData
            val value =metaData.getBoolean(metaName, defaultValue)
            if(value)
                print("isDeBug meta-data         $metaName = $value")
            return value
        } catch (e: Exception) {
            Log.e(TAG,"isDeBug:"+Tool().FormatStackTrace(e))
            return defaultValue
        }
    }

    fun getLDeBug(): Boolean {
//        Log.e(TAG,"getLDeBug isDeBug:"+DeBug)
        return  DeBug as Boolean
    }

    fun setLDeBug(dsBug:Boolean){
        DeBug=dsBug
    }
}