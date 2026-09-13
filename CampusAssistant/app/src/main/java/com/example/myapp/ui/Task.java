package com.example.myapp.ui;

public class Task {
    public String title;
    public String content;
    public boolean done = false;
    public long createdAt;
    public long completedAt = 0L;

    public Task(String t, String c) {
        title = t;
        content = c;
        createdAt = System.currentTimeMillis();
    }
}
