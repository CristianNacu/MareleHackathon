package com.farminator.farminator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farminator.farminator.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    EditText et_username;
    EditText et_password;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);

        Button btn = findViewById(R.id.b_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(Utils.checkUser(username,password)) {
                    logIn(username,password);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //SharedPreferences
        setupSharedPreferences();
        if(Utils.checkUser(username,password)){
            logIn(username,password);
        }

    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);
    }

    private void logIn(String username,String password){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Intent loginIntent = new Intent(LoginActivity.this, TasksActivity.class);
        Bundle b =new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        loginIntent.putExtras(b);
        startActivity(loginIntent);
    }
}
