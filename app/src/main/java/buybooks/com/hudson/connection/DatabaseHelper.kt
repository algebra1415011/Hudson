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
                "($USERID TEXT PRIMARY KEY);"
        val CREATE_TABLE1 = "CREATE TABLE $TABLE_NAME1 " +
                "($USERID TEXT PRIMARY KEY,$ISRATED TEXT NOT NULL);"
        db!!.execSQL(CREATE_TABLE)
        db!!.execSQL(CREATE_TABLE1)
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




    //Inserting (rating) data
    fun addRating(userID: String,isRated:String): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERID, userID)
        values.put(ISRATED, isRated)
        val _success = db.insert(TABLE_NAME1, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return Integer.parseInt("$_success") != -1
    }

    //get all users
    fun isRated(userID: String): String {

        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME1"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
//                userID = cursor.getString(cursor.getColumnIndex(USERID))
                if(cursor.getString(cursor.getColumnIndex(USERID)).equals(userID))
                {
                    return cursor.getString(cursor.getColumnIndex(ISRATED))
                }

//                    allUser = "$allUser\n$userID"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return "false"
    }





    companion object {
        private val DB_NAME = "UsersDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "user"
        private val TABLE_NAME1 = "rating"
        private val USERID = "userID"
        private val ISRATED="isRated"


    }
}