package com.example.my_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ContentPage extends AppCompatActivity {
    private String title;  // 用于保存新闻标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newscontent);

        // Get the views
        TextView titleTextView = findViewById(R.id.news_title);
        TextView contentTextView = findViewById(R.id.news_content);
        ImageView newsImageView = findViewById(R.id.news_image);

        // Get the extras
        title = getIntent().getStringExtra("news_title");
        String content = getIntent().getStringExtra("news_content");
        String imageUrl = getIntent().getStringExtra("news_image_url");

        // Update the views
        titleTextView.setText(title);
        contentTextView.setText(content);

        // Use a library like Glide or Picasso to load the image from the URL
        // For example, if you're using Glide:
        Glide.with(this).load(imageUrl).into(newsImageView);

        EditText commentInput = findViewById(R.id.comment_input);
        Button submitComment = findViewById(R.id.submit_button);
        Button openCommentsPage = findViewById(R.id.view_comments_button);

        submitComment.setOnClickListener(v -> {
            String comment = commentInput.getText().toString();
            String username = MainActivity.username;

            // 将评论存储到数据库
            CommentDatabaseHelper dbHelper = new CommentDatabaseHelper(this, "Comments.db", null, 2);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("news_title", title);
            values.put("username", username);
            values.put("comment", comment);
            db.insert("Comments", null, values);

            commentInput.setText("");  // 清空输入框
        });

        openCommentsPage.setOnClickListener(v -> openCommentPage());
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> goBack(v));

    }

    private void openCommentPage() {
        Intent intent = new Intent(this, CommentPage.class);
        intent.putExtra("news_title", title);
        startActivity(intent);
    }

    public void goBack(View view) {
      finish();
    }
}
