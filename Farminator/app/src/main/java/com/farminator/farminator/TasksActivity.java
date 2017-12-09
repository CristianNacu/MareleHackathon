package com.farminator.farminator;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
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

        SpannableString spanString = new SpannableString(message);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);

        tvUsername.setText(spanString);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_refresh:
                myTasks = Utils.getMyTasks(username);
                myTasksAdapter = new MyTasksAdapter(this, myTasks);
                rvMyTasks.setAdapter(myTasksAdapter);

                availableTasks = Utils.getAvailableTasks();
                availableTasksAdapter = new MyTasksAdapter(this, availableTasks);
                rvAvailableTasks.setAdapter(availableTasksAdapter);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

