package com.example.forumactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * DetailActivity displays the detailed content of the selected item from MainActivity
 * It receives title and content data via Intent extras and renders them on the UI
 * Also provides a return button to navigate back to MainActivity
 */
public class DetailActivity extends AppCompatActivity {

    // Declare UI components
    private TextView tvDetailTitle;
    private TextView tvDetailContent;
    private Button btnBack;

    // Constant keys for Intent extras (consistent with MainActivity)
    public static final String EXTRA_TITLE = "com.example.forumactivity.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.forumactivity.EXTRA_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind layout file to this Activity
        setContentView(R.layout.activity_detail);

        // Initialize UI components by finding views with their IDs
        initViews();

        // Receive data passed from MainActivity
        receiveDataFromMainActivity();

        // Set click listener for return button
        setBackButtonClickListener();
    }

    /**
     * Initialize all UI elements by their resource IDs
     */
    private void initViews() {
        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetailContent = findViewById(R.id.tv_detail_content);
        btnBack = findViewById(R.id.btn_back);
    }

    /**
     * Extract title and content from Intent extras sent by MainActivity
     * If no data is passed, set default empty string to avoid null pointer exception
     */
    private void receiveDataFromMainActivity() {
        Intent intent = getIntent();
        // Get title from Intent, default to empty string if null
        String title = intent.getStringExtra(EXTRA_TITLE) != null ?
                intent.getStringExtra(EXTRA_TITLE) : "";
        // Get content from Intent, default to empty string if null
        String content = intent.getStringExtra(EXTRA_CONTENT) != null ?
                intent.getStringExtra(EXTRA_CONTENT) : "";

        // Set received data to UI components
        tvDetailTitle.setText(title);
        tvDetailContent.setText(content);
    }

    /**
     * Set click listener for return button
     * Finish current activity to navigate back to MainActivity
     */
    private void setBackButtonClickListener() {
        btnBack.setOnClickListener(v -> {
            // Close current DetailActivity and return to previous stack (MainActivity)
            finish();
        });
    }
}