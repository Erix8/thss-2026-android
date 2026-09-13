package com.example.wechatcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechatcontact.R;
import com.example.wechatcontact.model.ListItem;

import java.util.List;

/**
 * RecyclerView adapter supporting multiple item types
 */
public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<ListItem> list;

    public ContactAdapter(Context context,List<ListItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == ListItem.TYPE_ENTRY){

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_entry,parent,false);
            return new EntryHolder(view);

        }else if(viewType == ListItem.TYPE_HEADER){

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_header,parent,false);
            return new HeaderHolder(view);

        }else{

            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_contact,parent,false);
            return new ContactHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListItem item = list.get(position);

        if(holder instanceof EntryHolder){

            ((EntryHolder) holder).text.setText(item.getText());

        }else if(holder instanceof HeaderHolder){

            ((HeaderHolder) holder).text.setText(item.getText());

        }else if(holder instanceof ContactHolder){

            ((ContactHolder) holder).name.setText(item.getText());

            // Click event showing Toast
            holder.itemView.setOnClickListener(v -> {

                Toast.makeText(context,
                        item.getText(),
                        Toast.LENGTH_SHORT).show();

            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class EntryHolder extends RecyclerView.ViewHolder{

        TextView text;

        public EntryHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.txtEntry);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder{

        TextView text;

        public HeaderHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.txtHeader);
        }
    }

    static class ContactHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ContactHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
        }
    }
}