package com.avision_amc.africavisionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentsAdapter commentsAdapter;
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Button AddCommentButton = findViewById(R.id.buttonAddComment);
        AddCommentButton.setOnClickListener(view -> {
            // Starts the AddACommentActivity to add a new comment
            Intent intent = new Intent(this, AddACommentActivity.class);
            startActivity(intent);
        });

        Button buttonDeleteComments = findViewById(R.id.buttonDeleteComments);
        buttonDeleteComments.setOnClickListener(view -> {
            // Asks the user to confirm the deletion of all comments
            deleteAllComments();
        });

        Button RefreshCommentsButton = findViewById(R.id.buttonRefreshComments);
        RefreshCommentsButton.setOnClickListener(view -> {
            retrieveComments();
        });

        // Sets up the RecyclerView to display comments
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        appDatabase = AppDatabase.getInstance(this);

        retrieveComments();
    }

    // Retrieves and display the comments from the database to the RecycleView
    private void retrieveComments() {
        appDatabase.userDao().getAll().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> users) {
                if (users != null) {
                    // Reverses the order of the list to display most recent comments at the top
                    Collections.reverse(users);
                    if (users.isEmpty()) {
                        Toast.makeText(CommentsActivity.this, "No comments found", Toast.LENGTH_SHORT).show();
                        commentsAdapter = new CommentsAdapter(users);
                        recyclerViewComments.setAdapter(commentsAdapter);
                    } else {
                        commentsAdapter = new CommentsAdapter(users);
                        recyclerViewComments.setAdapter(commentsAdapter);
                    }
                }
            }
        });
    }

    // Asks the user to confirm the deletion of all comments
    public void deleteAllComments() {
        new AlertDialog.Builder(this)
                .setTitle("Delete All Comments")
                .setMessage("Are you sure you want to piss off everyone?")
                .setPositiveButton("Of course!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCommentsFromDatabase();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteCommentsFromDatabase() {
        new DeleteCommentsTask().execute();
    }


    // AsyncTask to delete comments from the database in the background
    private class DeleteCommentsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Calls the UserDao's deleteAll() method to delete all comments
            appDatabase.userDao().deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(CommentsActivity.this, "All comments deleted, shame on you...", Toast.LENGTH_LONG).show();
        }
    }


}