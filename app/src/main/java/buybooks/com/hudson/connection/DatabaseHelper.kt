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
        val CREATE_TABLE2 = "CREATE TABLE $TABLE_NAME2 " +
                "($TABLEID INT PRIMARY KEY);"
        val CREATE_TABLE3 = "CREATE TABLE $TABLE_NAME3 " +
                "($USERID TEXT PRIMARY KEY,$COLOR TEXT NOT NULL);"
        db!!.execSQL(CREATE_TABLE)
        db!!.execSQL(CREATE_TABLE1)
        db!!.execSQL(CREATE_TABLE2)
        db!!.execSQL(CREATE_TABLE3)
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
                cursor.close()
                db.close()
                return userID

//                    allUser = "$allUser\n$userID"
//                } while (cursor.moveToNext())
            }
        }
        return "empty"
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

    fun addtableID(tableID: Int): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TABLEID, tableID)
        val _success = db.insert(TABLE_NAME2, null, values)
        db.close()
        Log.v("insertedtableID", "$_success")
        return Integer.parseInt("$_success") != -1
    }

    //get all users
    fun gettableId(): Int {

        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME2"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
//                do {
                var tableID:Int = cursor.getInt(cursor.getColumnIndex(TABLEID))
                cursor.close()
                db.close()
                return tableID

//                    allUser = "$allUser\n$userID"
//                } while (cursor.moveToNext())
            }
        }
        return 0
    }


    fun addcolor(userID: String,color:String): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERID, userID)
        values.put(COLOR, color)
        val _success = db.insert(TABLE_NAME3, null, values)
        db.close()
        Log.v("colorinsertid", "$_success")
        return Integer.parseInt("$_success") != -1
    }

    //get all users
    fun getcolor(): String {

        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME3"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
//                do {
                var color:String = cursor.getString(cursor.getColumnIndex(COLOR))
                cursor.close()
                db.close()
                return color

//                    allUser = "$allUser\n$userID"
//                } while (cursor.moveToNext())
            }
        }
        return "empty"
    }



    companion object {
        private val DB_NAME = "UsersDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "user"
        private val TABLE_NAME1 = "rating"
        private val TABLE_NAME2 = "tableid"
        private val TABLE_NAME3 = "colorid"
        private val USERID = "userID"
        private val ISRATED="isRated"
        private val TABLEID="tableID"
        private val COLOR="color"



    }
}