package com.example.mytodolist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoItem> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public TodoAdapter(List<TodoItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem item = list.get(position);

        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        holder.time.setText(item.getTime());

        int strikeFlags = holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG;
        int clearFlags = holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG);

        if (item.isCompleted()) {
            holder.title.setPaintFlags(strikeFlags);
        } else {
            holder.title.setPaintFlags(clearFlags);
        }

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onClick(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            content = itemView.findViewById(R.id.itemContent);
            time = itemView.findViewById(R.id.itemTime);
        }
    }
}