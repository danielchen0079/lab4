package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import adapter.UserCursorAdapter;
import db.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private MyDatabaseHelper dbHelper;
    private ListView listView;
    private UserCursorAdapter adapter;

    private static final int EDIT_ID = 0;
    private static final int DELETE_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        registerForContextMenu(listView);

        loadUserData();
    }

    private void loadUserData() {
        try {
            db = dbHelper.getReadableDatabase();
            cursor = dbHelper.getAllUsers();

            if (cursor != null) {
                adapter = new UserCursorAdapter(this, cursor);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    if (cursor.moveToPosition(position)) {
                        Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                        String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        int registerType = cursor.getInt(cursor.getColumnIndexOrThrow("register_type"));

                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        intent.putExtra("register_type", registerType);
                        startActivity(intent);
                    }
                });
            }
        } catch (SQLiteException e) {
            // Handle exception silently
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0, EDIT_ID, 0, "Edit");
        menu.add(0, DELETE_ID, 1, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (cursor.moveToPosition(position)) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

            switch (item.getItemId()) {
                case EDIT_ID:
                    dbHelper.updateUser(userId, "newUsername", "newPassword", 0);
                    cursor = dbHelper.getAllUsers();
                    adapter.changeCursor(cursor);
                    return true;
                case DELETE_ID:
                    int rowsDeleted = dbHelper.deleteUser(userId);
                    if (rowsDeleted > 0) {
                        cursor = dbHelper.getAllUsers();
                        adapter.changeCursor(cursor);
                    }
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}
