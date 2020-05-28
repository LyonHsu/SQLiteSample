package com.example.sqlite

import android.util.Log

object LogUtile {
    fun e(TAG :String, msg:String){
        if(AppController().getLDeBug())
            Log.e(TAG, msg)
    }

    fun d(TAG :String, msg:String){
        if(AppController().getLDeBug())
            Log.d(TAG, msg)
    }

    fun w(TAG :String, msg:String){
        if(AppController().getLDeBug())
            Log.w(TAG, msg)
    }

    fun i(TAG :String, msg:String){
        if(AppController().getLDeBug())
            Log.i(TAG, msg)
    }
}