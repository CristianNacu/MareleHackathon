package com.farminator.farminator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class TasksActivity extends AppCompatActivity {
    String username;
    String password;

    TextView tv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tv_username = findViewById(R.id.tv_hello);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            username = b.getString("username");
            password = b.getString("password");
        }
        String message = "Hello, "+username;
        tv_username.setText(message);



    }
}
