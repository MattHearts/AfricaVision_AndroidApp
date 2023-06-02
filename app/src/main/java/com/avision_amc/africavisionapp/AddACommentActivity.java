package com.avision_amc.africavisionapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddACommentActivity extends AppCompatActivity {

    private EditText editTextNickname;
    private EditText editTextComments;
    private Button buttonSubmit;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acomment);

        editTextNickname = findViewById(R.id.editTextNickame);
        editTextComments = findViewById(R.id.editTextComments);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        //Gets an instance of the AppDatabase
        appDatabase = AppDatabase.getInstance(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editTextNickname.getText().toString().trim();
                String comments = editTextComments.getText().toString().trim();

                if (nickname.isEmpty() || comments.isEmpty()) {
                    Toast.makeText(AddACommentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Creates a new UserModel instance with the provided nickname and comments
                    UserModel user = new UserModel(nickname, comments);

                    // Executes an AsyncTask to insert the user into the database
                    InsertUserAsyncTask task = new InsertUserAsyncTask();
                    task.execute(user);

                    Toast.makeText(AddACommentActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                }
            }
        });
    }

    //AsyncTask to insert a user into the database in the background
    private class InsertUserAsyncTask extends AsyncTask<UserModel, Void, Void> {

        @Override
        protected Void doInBackground(UserModel... users) {
            // Inserts the comments into the database using the UserDao
            //appDatabase.userDao().insert(users[0]);
            UserRepository.insertComment(users[0]);
            return null;
        }
    }

    private void clearFields() {

        editTextComments.setText("");
    }
}
