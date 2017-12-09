package com.farminator.farminator;

import android.content.Intent;
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
                    Intent loginIntent = new Intent(LoginActivity.this, TasksActivity.class);
                    Bundle b =new Bundle();
                    b.putString("username",username);
                    b.putString("password",password);
                    loginIntent.putExtras(b);
                    startActivity(loginIntent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
