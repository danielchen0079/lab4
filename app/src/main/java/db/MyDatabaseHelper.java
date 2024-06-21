package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT, "
                + "password TEXT, "
                + "register_type INTEGER);");

        insertUser(db, "user1", "pass1", 0);
        insertUser(db, "admin", "adminpass", 1);
        insertUser(db, "john", "johnpass", 0);
        insertUser(db, "jane", "janepass", 0);
        insertUser(db, "alice", "alicepass", 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public long insertUser(SQLiteDatabase db, String username, String password, int registerType) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("register_type", registerType);
        return db.insert("users", null, values);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("users", null, null, null, null, null, null);
    }

    public int deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users", "_id = ?", new String[]{String.valueOf(userId)});
    }

    public int updateUser(int userId, String username, String password, int registerType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("register_type", registerType);
        return db.update("users", values, "_id = ?", new String[]{String.valueOf(userId)});
    }
}
