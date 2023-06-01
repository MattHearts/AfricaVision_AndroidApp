package com.avision_amc.africavisionapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
        //Inflates the item_comment layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        //Binds the comment data to the ViewHolder at the given positio
        UserModel comment = commentsList.get(position);
        holder.bind(comment);
    }

    //Returns the total number of comments in the list
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
            //Binds the comment data to the TextView
            String commentText = comment.getComments() + " - " + comment.getNickname();
            SpannableString spannableString = new SpannableString(commentText);
            int start = 0;
            int end = comment.getComments().length();
            int oppositeStart = end;
            int oppositeEnd = commentText.length();
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), oppositeStart, oppositeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), oppositeStart, oppositeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewComment.setText(spannableString);
        }
    }


}
