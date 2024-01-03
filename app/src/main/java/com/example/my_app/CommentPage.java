package com.example.my_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app.Adapter.CommentsAdapter;
import com.example.my_app.Bean.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentPage extends AppCompatActivity {
    private List<Comment> comments = new ArrayList<>();
    private String title; // 用于保存新闻标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        // 获取新闻标题
        title = getIntent().getStringExtra("news_title");

        // 从数据库中读取评论
        CommentDatabaseHelper dbHelper = new CommentDatabaseHelper(this, "Comments.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Comments", new String[]{"username", "comment"}, "news_title = ?", new String[]{title}, null, null, null);

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String commentText = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
            comments.add(new Comment(username, commentText));
        }

        cursor.close();

        // 初始化 RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        CommentsAdapter adapter = new CommentsAdapter(comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> goBack(v));

    }

    public void goBack(View view) {
        finish();
    }
}

