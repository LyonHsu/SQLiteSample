package com.example.sqlite

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer


class Tool{
    val TAG= Tool::class.toString()

    fun getVersionCode(context:Context):Int{
        var packageManager:PackageManager = context.packageManager
        var packageInfo = packageManager.getPackageInfo(context.packageName,0)
        var versionCode=packageInfo.versionCode
        return versionCode
    }

    fun getVersionName(context:Context):String{
        var versionName:String

            var packageManager: PackageManager = context.packageManager
            var packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName

        return versionName
    }

    fun dpToPx(context: Context,dp:Int):Int{
        if(context == null) {
            return -1;
        }
        val scale = context.resources.displayMetrics.density
        var px =  ((dp * scale + 0.5f).toInt())
        Log.d(TAG,"dpToPx:"+px)
        return px

    }




    fun FormatStackTrace(throwable: Throwable?): String? {
        if (throwable == null) return ""
        var rtn = throwable.stackTrace.toString()
        try {
            val writer: Writer = StringWriter()
            val printWriter = PrintWriter(writer)
            throwable.printStackTrace(printWriter)
            printWriter.flush()
            writer.flush()
            rtn = writer.toString()
            printWriter.close()
            writer.close()
        } catch (e: IOException) {
            Log.e(TAG,"FormatStackTrace "+e)
        } catch (ex: Exception) {
            Log.e(TAG,"FormatStackTrace "+ex)
        }
        return rtn
    }

    fun negate(f:String ):ArrayList<String>{
        var r =negateRemainder(f)
        val lenR = r.size
        var list = ArrayList<String>()
        for (i in 0.. lenR-1){
            val m = negateMultiply(r[i])
            val lenM = m.size
            for (i in 0.. lenM-1){
                val e = negateExcept(m[i])
                val lenE = e.size
                for (i in 0.. lenE-1){
                    val a = negateAdd(e[i])
                    val lenA = a.size
                    for (i in 0.. lenA-1){
                        val L = negateLess(a[i])
                        val lenL = L.size
                        for(i in 0..lenL-1){
                            list.add(L[i])
                            if(lenL>1 && i<lenL-1)
                            list.add("-")
                        }
                        if(lenA>1 && i<lenA-1)
                        list.add("+")
                    }
                    if(lenE>1 && i<lenE-1)
                    list.add("÷")
                }
                if(lenM>1&& i<lenM-1)
                list.add("x")
            }
            if(lenR>1&& i<lenR-1)
                list.add("%")
        }
        Log.d(TAG,"List size:"+list.size)
        for(i in 0..list.size-1){
            Log.d(TAG,"List:"+list[i])
        }
        return list
    }

    fun negateMultiply(f:String):List<String>{
        var x =f.split("x")
        return x
    }

    fun negateExcept(f:String):List<String>{
        var x =f.split("÷")
        return x
    }

    fun negateAdd(f:String):List<String>{
        var x =f.split("+")
        return x
    }

    fun negateLess (f:String):List<String>{
        var x =f.split("-")
        return x
    }

    fun negateRemainder (f:String):List<String>{
        var x =f.split("%")
        return x
    }
}