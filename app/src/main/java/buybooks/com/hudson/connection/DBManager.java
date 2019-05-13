package buybooks.com.hudson.connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
//    DBManager dbManager;
//    dbManager=new DBManager(this);
//        dbManager.open();

//    if(dbManager.addTran(new Transaction(tdate.getText().toString(),payto.getText().toString(),Double.parseDouble(amount.getText().toString()))))
//    {
//        dbManager.close();
//        Intent intent=new Intent(AddTransactionDetails.this,WelcomeActivity.class);
//        transaction=new Transaction(tdate.getText().toString(),payto.getText().toString(),Double.parseDouble(amount.getText().toString()));
//        intent.putExtra("transaction",transaction);
//        startActivity(intent);
//    }



//    public Boolean addTran(Transaction transaction) {
//        ContentValues contentValue = new ContentValues();
//        contentValue.put(DatabaseHelper.DATE, transaction.getDate());
//        contentValue.put(DatabaseHelper.PAYABLETO, transaction.getPayable_to());
//        contentValue.put(DatabaseHelper.AMOUNT, transaction.getAmount());
//        Log.d("value",String.valueOf(database.insert(DatabaseHelper.TABLE_NAME, null, contentValue)));
//        return database.insert(DatabaseHelper.TABLE_NAME, null, contentValue)>0;
//    }

    public Cursor getAllTransaction() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.DATE, DatabaseHelper.PAYABLETO,DatabaseHelper.AMOUNT };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}