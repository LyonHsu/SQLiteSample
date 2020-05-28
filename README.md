# SQLiteSample

val DBNAME = "example.db"
val DB_VERSION=4
val tableName = "SQLiteSample"
val NAME = "name"
val TIME ="time"
val ID = "id"
    

新增資料庫
  
     val sql = "CREATE TABLE if not exists $tableName " +
                "( "+ID+" integer PRIMARY KEY autoincrement, " +
                ""+NAME+" TEXT, " +
                ""+TIME+" VARCHAR(50)" +
                ")"
        db.execSQL(sql)

新增資料
  
    fun addName(name:String) {
        val values = ContentValues()
        values.put(NAME, name)
        values.put(TIME, SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(Date()))
        writableDatabase.insert(tableName, null, values)
    }
    
移除資料 此範例每次移除最舊的一筆
  
    fun remove(){
          val id =getNames()[0].id
          writableDatabase.delete(tableName,ID+"="+id,null)
      }
      
      
      
得到資料

    fun getNames(): ArrayList<ItemModel> {
        val cursor = readableDatabase.query(tableName, arrayOf(ID, NAME,TIME), null, null, null, null, null)
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

        LogUtile.d(tableName,"總共有 ${cursor.count} 筆資料")
        return members

    }
