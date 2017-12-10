package com.farminator.farminator;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

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
    private int undoId;
    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            refreshTasks();
      /* and here comes the "trick" */
            handler.postDelayed(this, 5000);
        }
    };


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
        String message = "Hello, " + username;

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

        refreshTasks();


        //Swiping for available tasks
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int)viewHolder.itemView.getTag();
                if(swipeDir==ItemTouchHelper.LEFT){
                    Utils.claimTask(id,username);
                }
                else{
                    Utils.claimTask(id,username);
                }
                //refresh lists
                refreshTasks();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if(dX > 0){
                        paint.setColor(Color.parseColor("#FFC107"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        drawText("CLAIM", c, background, paint);
                    } else {
                        paint.setColor(Color.parseColor("#FFC107"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        paint.setColor(Color.WHITE);
                        paint.setAntiAlias(true);
                        paint.setTextSize(40);
                        drawText("CLAIM", c, background, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(rvAvailableTasks);

        //Swiping to complete tasks or uncomplete them
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int id = (int)viewHolder.itemView.getTag();
                if(swipeDir==ItemTouchHelper.LEFT){
                    Utils.undo(id);
                }
                else{
                    Utils.completeTask(id);
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.linear),getString(R.string.action_undo),Snackbar.LENGTH_SHORT);
                    mySnackbar.setAction(R.string.action_undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.undo(id);
                            refreshTasks();
                        }
                    });
                    mySnackbar.show();
                }
                //refresh lists
                refreshTasks();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if(dX > 0){
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        drawText("COMPLETE", c, background, paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        drawText("ABANDON", c, background, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(rvMyTasks);

        //refresh every x seconds
        handler.postDelayed(runnable, 5000);
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(40);
        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(p.getTextSize()/2), p);
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

    public void refreshTasks(){
        myTasks = Utils.getMyTasks(username);
        myTasksAdapter = new MyTasksAdapter(this, myTasks);
        rvMyTasks.setAdapter(myTasksAdapter);
        if(myTasksAdapter.getItemCount()==0){
            TextView et = findViewById(R.id.tv_my_tasks);
            et.setText(R.string.no_tasks);
        }
        else
        {
            TextView et = findViewById(R.id.tv_my_tasks);
            et.setText(R.string.your_tasks);
        }

        availableTasks = Utils.getAvailableTasks();
        availableTasksAdapter = new MyTasksAdapter(this, availableTasks);
        rvAvailableTasks.setAdapter(availableTasksAdapter);
        if(availableTasksAdapter.getItemCount()==0){
            TextView et = findViewById(R.id.tv_available_tasks);
            et.setText(R.string.no_available);
        }
        else{
            TextView et = findViewById(R.id.tv_available_tasks);
            et.setText(R.string.available_tasks);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_refresh:
                refreshTasks();
                return true;
            case R.id.action_logout:
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.apply();
                finish();
                return true;
            case R.id.action_reset:
                Utils.reset(undoId);
                refreshTasks();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

