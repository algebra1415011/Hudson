package buybooks.com.hudson.connection

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($USERID TEXT PRIMARY KEY)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Inserting (Creating) data
    fun addUser(userID: String): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERID, userID)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return Integer.parseInt("$_success") != -1
    }

    //get all users
    fun getUsers(): String {
        var userID: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
//                do {
                    userID = cursor.getString(cursor.getColumnIndex(USERID))


//                    allUser = "$allUser\n$userID"
//                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return userID
    }

    companion object {
        private val DB_NAME = "UsersDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "user"
        private val USERID = "userID"

    }
}