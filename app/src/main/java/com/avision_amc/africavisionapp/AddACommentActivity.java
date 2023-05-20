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

        appDatabase = AppDatabase.getInstance(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editTextNickname.getText().toString().trim();
                String comments = editTextComments.getText().toString().trim();

                if (nickname.isEmpty() || comments.isEmpty()) {
                    Toast.makeText(AddACommentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel user = new UserModel(nickname, comments);
                    InsertUserAsyncTask task = new InsertUserAsyncTask();
                    task.execute(user);
                    Toast.makeText(AddACommentActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                }
            }
        });
    }

    private class InsertUserAsyncTask extends AsyncTask<UserModel, Void, Void> {

        @Override
        protected Void doInBackground(UserModel... users) {
            appDatabase.userDao().insert(users[0]);
            return null;
        }
    }

    private void clearFields() {
        editTextComments.setText("");
    }
}
