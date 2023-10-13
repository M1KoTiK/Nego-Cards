package m1k.kotik.negocards.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.session.PlaybackState.CustomAction
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.QRCType
import m1k.kotik.negocards.data.qrc.ScannedQRC

class QRCDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "QRC.db"
        private const val DATABASE_VERSION = 1
        //Table scannedQRC-------------------------------------------
        private const val SCANNED_QRC_TABLE_NAME = "scannedQRC"
        private const val CREATE_TABLE_SCANNED_QRC =
            "CREATE TABLE $SCANNED_QRC_TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT,type INTEGER, value TEXT,date TEXT)"

        //-----------------------------------------------------------
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SCANNED_QRC)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $SCANNED_QRC_TABLE_NAME")
    }

    fun add(scannedQRC: ScannedQRC){
        val values = ContentValues()
        values.put("type", scannedQRC.type.ordinal)
        values.put("value", scannedQRC.value)
        values.put("date", scannedQRC.date.toString())
        this.writableDatabase.also {
            it.insert(SCANNED_QRC_TABLE_NAME,null, values)
            it.close()
        }
    }

    fun getScannedQRC(): MutableList<ScannedQRC>{
        val outputList = mutableListOf<ScannedQRC>()
        val cursor = getAll("scannedQRC") ?: return outputList
        if(cursor.count >0) {
            cursor.moveToFirst()
            do {
                var type: QRCType = QRCType.values().first { it.ordinal == cursor.getInt(1) }
                var value = cursor.getString(2)
                var date = SimpleDate.Companion.toSimpleDate(cursor.getString(3)) ?: continue
                outputList.add(ScannedQRC(type, value, date))
            } while (cursor.moveToNext())
        }
        return outputList
    }

    private fun getAll(tableName: String):Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $tableName", null)
    }
}