package com.example.myapp.ui;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment{
    List<Task> list = new ArrayList<>();
    TaskAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);
        Button btn = view.findViewById(R.id.btnAdd);
        RecyclerView rv = view.findViewById(R.id.recycler);

        initDefaultTasks();
        adapter = new TaskAdapter(list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        btn.setOnClickListener(v -> {
            String t = etTitle.getText().toString().trim();
            String c = etContent.getText().toString().trim();

            if (TextUtils.isEmpty(t) || TextUtils.isEmpty(c)) {
                Toast.makeText(getContext(), "请填写标题和内容", Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.addTask(new Task(t, c));

            etTitle.setText("");
            etContent.setText("");
        });

        return view;
    }

    private void initDefaultTasks() {
        if (!list.isEmpty()) {
            return;
        }

        list.add(new Task("完成注册", "核对个人信息并提交报到材料，完成新生注册流程。"));
        list.add(new Task("领取校园卡", "前往指定地点领取校园卡并开通门禁与消费功能。"));
    }
}
