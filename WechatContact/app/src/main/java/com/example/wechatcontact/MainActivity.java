package com.example.wechatcontact;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechatcontact.adapter.ContactAdapter;
import com.example.wechatcontact.model.ListItem;
import com.example.wechatcontact.util.ContactData;
import com.example.wechatcontact.view.SideBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SideBarView sideBar;

    List<ListItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        sideBar = findViewById(R.id.sideBar);

        // Use a named LayoutManager so we can call scrollToPositionWithOffset later
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list = ContactData.createList();

        ContactAdapter adapter =
                new ContactAdapter(this,list);

        recyclerView.setAdapter(adapter);

        // Defensive: views inflated from XML should exist, but guard against null
        if (sideBar != null && list != null && recyclerView != null) {
            sideBar.setOnLetterTouchListener(letter -> {
                for (int i = 0; i < list.size(); i++) {
                    if (letter.equals(list.get(i).getLetter())) {
                        final int targetPos = i;
                        // Ensure we scroll after layout and align the item to the top (offset 0)
                        recyclerView.post(() -> layoutManager.scrollToPositionWithOffset(targetPos, 0));
                        break;
                    }
                }
            });
        }
    }
}