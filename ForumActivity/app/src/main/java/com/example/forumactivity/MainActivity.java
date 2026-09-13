package com.example.forumactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Main Activity displays a list of clickable activity items
 * Clicking an item navigates to DetailActivity with corresponding title/content
 */
public class MainActivity extends AppCompatActivity {

    // Constants for intent extras (key names)
    public static final String EXTRA_TITLE = "com.example.forumactivity.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.forumactivity.EXTRA_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_main); // Set main layout

        // Handle window insets for edge-to-edge compatibility
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize clickable layout items
        LinearLayout dynamicItem1 = findViewById(R.id.dynamic1);
        LinearLayout dynamicItem2 = findViewById(R.id.dynamic2);

        // Set click listener for first dynamic item
        dynamicItem1.setOnClickListener(v -> navigateToDetailActivity(
                ((TextView) v.findViewById(R.id.tv_title1)).getText().toString(),
                ((TextView) v.findViewById(R.id.tv_content1)).getText().toString()
        ));

        // Set click listener for second dynamic item
        dynamicItem2.setOnClickListener(v -> navigateToDetailActivity(
                ((TextView) v.findViewById(R.id.tv_title2)).getText().toString(),
                ((TextView) v.findViewById(R.id.tv_content2)).getText().toString()
        ));
    }

    /**
     * Navigates to DetailActivity and passes title/content via Intent
     *
     * @param title   Title text to display in DetailActivity
     * @param content Content text to display in DetailActivity
     */
    private void navigateToDetailActivity(String title, String content) {
        // Create intent to launch DetailActivity
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

        // Pass data as extras with the intent
        detailIntent.putExtra(EXTRA_TITLE, title);
        detailIntent.putExtra(EXTRA_CONTENT, content);

        // Start the DetailActivity
        startActivity(detailIntent);
    }
}