package m1k.kotik.negocards.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.CodeContentType
import m1k.kotik.negocards.data.qrc.CodeType
import m1k.kotik.negocards.data.qrc.ScannedCode

class QRCDB(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "QRC.db"
        private const val DATABASE_VERSION = 1
        //Table scannedQRC-------------------------------------------
        private const val SCANNED_QRC_TABLE_NAME = "scannedQRC"
        private const val CREATE_TABLE_SCANNED_QRC =
            "CREATE TABLE $SCANNED_QRC_TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT,contentType INTEGER, value TEXT,date TEXT, codeType INTEGER)"

        //-----------------------------------------------------------
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SCANNED_QRC)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $SCANNED_QRC_TABLE_NAME")
    }

    fun add(ScannedCode: ScannedCode){
        val values = ContentValues()
        values.put("contentType", ScannedCode.contentType.ordinal)
        values.put("value", ScannedCode.value)
        values.put("date", ScannedCode.date.toString())
        values.put("codeType", ScannedCode.codeType.ordinal)
        this.writableDatabase.also {
            it.insert(SCANNED_QRC_TABLE_NAME,null, values)
            it.close()
        }
    }

    fun getScannedQRC(): MutableList<ScannedCode>{
        val outputList = mutableListOf<ScannedCode>()
        val cursor = getAll("scannedQRC") ?: return outputList
        if(cursor.count >0) {
            cursor.moveToFirst()
            do {
                val contentType: CodeContentType = CodeContentType.values().first { it.ordinal == cursor.getInt(1) }
                val value = cursor.getString(2)
                val date = SimpleDate.Companion.toSimpleDate(cursor.getString(3)) ?: continue
                val codeType = CodeType.values().first { it.ordinal == cursor.getInt(4) }
                outputList.add(ScannedCode(codeType,contentType, value, date))
            } while (cursor.moveToNext())
        }
        return outputList
    }

    private fun getAll(tableName: String):Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $tableName", null)
    }
}