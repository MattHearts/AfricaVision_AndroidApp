package com.avision_amc.africavisionapp;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<UserModel> commentsList;

    public CommentsAdapter(List<UserModel> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        UserModel comment = commentsList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public void bind(UserModel comment) {
            String commentText = comment.getComments() + " - " + comment.getNickname();
            SpannableString spannableString = new SpannableString(commentText);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, comment.getComments().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewComment.setText(spannableString);
        }
    }


}
