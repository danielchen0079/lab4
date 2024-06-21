package com.example.databases;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        TextView usernameTextView = findViewById(R.id.username_text_view);
        TextView passwordTextView = findViewById(R.id.password_text_view);
        TextView registerTypeTextView = findViewById(R.id.register_type_text_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            String password = extras.getString("password");
            int registerType = extras.getInt("register_type");

            usernameTextView.setText("Username: " + username);
            passwordTextView.setText("Password: " + password);
            registerTypeTextView.setText("Register Type: " + registerType);
        }
    }
}
