package com.example.myapp.ui;
import android.graphics.Paint;
import android.view.*;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH>{
    List<Task> list;

    public TaskAdapter(List<Task> l) {
        list = l;
        sortTasks();
    }

    public void addTask(Task task) {
        list.add(task);
        sortTasks();
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvContent = v.findViewById(R.id.tvContent);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int v) {
        View view = LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_task, p, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        Task t = list.get(i);
        h.tvTitle.setText(t.title);
        h.tvContent.setText(t.content);

        if (t.done) {
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            h.tvContent.setPaintFlags(h.tvContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            h.itemView.setAlpha(0.55f);
        } else {
            h.tvTitle.setPaintFlags(h.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            h.tvContent.setPaintFlags(h.tvContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            h.itemView.setAlpha(1f);
        }

        h.itemView.setOnClickListener(v -> {
            int position = h.getBindingAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            Task clickedTask = list.get(position);
            clickedTask.done = !clickedTask.done;
            clickedTask.completedAt = clickedTask.done ? System.currentTimeMillis() : 0L;

            sortTasks();
            notifyDataSetChanged();
        });
    }

    private void sortTasks() {
        list.sort((a, b) -> {
            if (a.done != b.done) {
                return a.done ? 1 : -1;
            }

            if (!a.done) {
                return Long.compare(a.createdAt, b.createdAt);
            }

            return Long.compare(a.completedAt, b.completedAt);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
