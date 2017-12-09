package com.farminator.farminator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.farminator.farminator.utils.Utils;

import java.util.List;


public class TasksActivity extends AppCompatActivity {
    private String username;
    private String password;
    private long backPressed=0;
    private TextView tvUsername;
    private RecyclerView rvMyTasks;
    private RecyclerView rvAvailableTasks;
    private MyTasksAdapter myTasksAdapter;
    private MyTasksAdapter availableTasksAdapter;
    private List<Task> myTasks;
    private List<Task> availableTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        //Username handling
        tvUsername = findViewById(R.id.tv_hello);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            username = b.getString("username");
            password = b.getString("password");
        }
        String message = "Hello, "+username;
        tvUsername.setText(message);

        //My tasks RecyclerView
        myTasks = Utils.getMyTasks(username);
        rvMyTasks = findViewById(R.id.rv_my_tasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMyTasks.setLayoutManager(layoutManager);
        myTasksAdapter = new MyTasksAdapter(this, myTasks);
        rvMyTasks.setAdapter(myTasksAdapter);

        //Available tasks RecyclerView
        availableTasks = Utils.getAvailableTasks();
        rvAvailableTasks = findViewById(R.id.rv_available_tasks);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        rvAvailableTasks.setLayoutManager(layoutManager2);
        availableTasksAdapter = new MyTasksAdapter(this, availableTasks);
        rvAvailableTasks.setAdapter(availableTasksAdapter);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (backPressed + 300 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(),
                    "Press BACK twice to exit!", Toast.LENGTH_SHORT)
                    .show();
        }
        backPressed = System.currentTimeMillis();
    }
}
