package com.example.e_kuisioner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText nameEditText;
    private EditText password2EditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button saveButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        myDb = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.edit_name);
        emailEditText = findViewById(R.id.edit_email);
        passwordEditText = findViewById(R.id.edit_password);
        password2EditText = findViewById(R.id.edit_password2);
        saveButton = findViewById(R.id.save_button);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId != null) {
            loadUserDetails(userId);
        } else {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_LONG).show();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserDetails();
            }
        });
    }

    @SuppressLint("Range")
    private void loadUserDetails(String userId) {
        Cursor cursor = myDb.getDataById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndex("NAME")));
            emailEditText.setText(cursor.getString(cursor.getColumnIndex("EMAIL")));
            passwordEditText.setText(cursor.getString(cursor.getColumnIndex("PASSWORD")));
            cursor.close();
        }
    }

    private void saveUserDetails() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String password2 = password2EditText.getText().toString();
        if (!password.equals(password2)) {
            Toast.makeText(EditUserActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isUpdated = myDb.updateUser(userId, email, password, name);

        if (isUpdated) {
            Toast.makeText(this, "User details updated", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_LONG).show();
        }
    }
}
