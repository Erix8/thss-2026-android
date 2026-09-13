package com.example.mytodolist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText titleInput, contentInput;
    private Button addButton;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private List<TodoItem> todoList;

    private static final String PREF_NAME = "todo_prefs";
    private static final String KEY_LIST = "todo_list";
    private static final String CHANNEL_ID = "todo_channel_v2";
    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // No-op: sendNotification() checks permission again before posting.
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.inputTitle);
        contentInput = findViewById(R.id.inputContent);
        addButton = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        todoList = loadData();

        adapter = new TodoAdapter(todoList, position -> {
            TodoItem item = todoList.get(position);
            item.setCompleted(!item.isCompleted());
            todoList.remove(position);

            if (item.isCompleted()) {
                todoList.add(item);
            } else {
                todoList.add(0, item);
            }

            adapter.notifyDataSetChanged();
            saveData();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setupSwipeToDelete();

        createNotificationChannel();
        requestNotificationPermissionIfNeeded();

        addButton.setOnClickListener(v -> addTodo());
    }

    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION || position < 0 || position >= todoList.size()) {
                    adapter.notifyDataSetChanged();
                    return;
                }

                todoList.remove(position);
                adapter.notifyItemRemoved(position);
                saveData();
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }

    private void addTodo() {
        String title = titleInput.getText().toString().trim();
        String content = contentInput.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) return;

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        TodoItem item = new TodoItem(title, content, time, false);
        todoList.add(0, item);

        adapter.notifyDataSetChanged();
        saveData();
        sendNotification(title);

        titleInput.setText("");
        contentInput.setText("");
    }

    private void sendNotification(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        String message = "Successfully added " + title + " to Todolist.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Todo Added")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        try {
            NotificationManagerCompat.from(this).notify((int) System.currentTimeMillis(), builder.build());
        } catch (SecurityException ignored) {
            // Permission can be revoked while app is running; skip instead of crashing.
        }
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Todo Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

    private void saveData() {
        String json = new Gson().toJson(todoList);
        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(KEY_LIST, json).apply();
    }

    private List<TodoItem> loadData() {
        String json = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(KEY_LIST, null);
        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<TodoItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}