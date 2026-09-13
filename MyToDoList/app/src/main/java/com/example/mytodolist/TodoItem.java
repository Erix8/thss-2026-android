package com.example.mytodolist;

public class TodoItem {
    private String title;
    private String content;
    private String time;
    private boolean completed;

    public TodoItem(String title, String content, String time, boolean completed) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.completed = completed;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTime() { return time; }
    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }
}
