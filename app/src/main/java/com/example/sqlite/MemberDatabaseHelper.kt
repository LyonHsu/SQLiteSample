package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log.d
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * https://ithelp.ithome.com.tw/articles/10193711
 *
 * https://www.jianshu.com/p/06309249f2a0
 */
val DBNAME = "example.db"
val DB_VERSION=4
class MemberDatabaseHelper (context: Context): SQLiteOpenHelper(context, DBNAME, null, DB_VERSION) {
    val tableName = "SQLiteSample"
    val NAME = "name"
    val TIME ="time"
    val ID = "id"
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE if not exists $tableName " +
                "( "+ID+" integer PRIMARY KEY autoincrement, " +
                ""+NAME+" TEXT, " +
                ""+TIME+" VARCHAR(50)" +
                ")"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addName(name:String) {
        val values = ContentValues()
        values.put(NAME, name)
        values.put(TIME, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()))
        writableDatabase.insert(tableName, null, values)
    }

    fun remove(){
        val array = getNames()
        val len = array.size
        val id =getNames()[len-1].id
        writableDatabase.delete(tableName,ID+"="+id,null)
    }


    fun getNames(): ArrayList<ItemModel> {
        //依照時間排序
        val cursor = readableDatabase.query(tableName, arrayOf(ID, NAME,TIME), null, null, null, null, TIME)
        val members = ArrayList<ItemModel>()

        try {
            if(cursor.moveToFirst()){
                do {
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val time = cursor.getString(cursor.getColumnIndex(TIME))
                    val item = ItemModel(id, name,time)
                    LogUtile.d(tableName,"name :"+name+" id:"+id+" time:"+time)
                    members.add(item)
                } while(cursor.moveToNext())

            }
        } catch (e:Exception) {

        } finally {
            if(cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }
        //反序
        Collections.reverse(members);

        LogUtile.d(tableName,"總共有 ${cursor.count} 筆資料")
        return members

    }

    class ItemModel(val id:Int,val name:String, val time:String){

    }
}
