package com.farminator.farminator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public class MyTasksAdapter extends RecyclerView.Adapter<MyTasksAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> tasks;

    MyTasksAdapter(Context context,List<Task> t)
    {
        tasks = t;
        mContext = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.task_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        String description = tasks.get(position).getDescription();
        holder.bind(position,description);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        //
        TextView descriptionTextView;
        TextView numberTextView;

        TaskViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.name_text_view);
            numberTextView = itemView.findViewById(R.id.number_text_view);
        }

        void bind(int index, String description) {
            numberTextView.setText(index);
            descriptionTextView.setText(description);
        }

    }
}
