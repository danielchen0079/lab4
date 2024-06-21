package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.databases.R;

public class UserCursorAdapter extends CursorAdapter {

    public UserCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView usernameTextView = view.findViewById(R.id.username_text);

        String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));

        usernameTextView.setText(username);
    }
}
