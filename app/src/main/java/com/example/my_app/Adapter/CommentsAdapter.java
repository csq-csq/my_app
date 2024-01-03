package com.example.my_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app.Bean.Comment;
import com.example.my_app.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private final List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建新的 View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // 将数据绑定到 View
        Comment comment = comments.get(position);
        holder.usernameTextView.setText(comment.getUsername());
        holder.commentTextView.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        // 返回评论的数量
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        final TextView usernameTextView;
        final TextView commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            // 获取 View 中的控件
            usernameTextView = itemView.findViewById(R.id.username);
            commentTextView = itemView.findViewById(R.id.comment);
        }
    }
}
