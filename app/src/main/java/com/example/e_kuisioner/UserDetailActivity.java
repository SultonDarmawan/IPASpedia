package com.example.e_kuisioner;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetailActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private TextView userDetailTextView;
    private TextView tokopediaValueTextViewWarna;
    private TextView tokopediaValueTextViewNavigasi;
    private TextView shopeeValueTextViewWarna;
    private TextView shopeeValueTextViewNavigasi;
    private TextView tokopediaValueTextViewWarna2;
    private TextView tokopediaValueTextViewNavigasi2;
    private TextView shopeeValueTextViewWarna2;
    private TextView shopeeValueTextViewNavigasi2;
    private TextView allResults;
    private TextView allResultsUser;
    private TextView totalUser;
    private Button approveButton;
    private Button editButton;
    private Button deleteButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        myDb = new DatabaseHelper(this);
        userDetailTextView = findViewById(R.id.user_detail_text_view);
        approveButton = findViewById(R.id.approve_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        userId = getIntent().getStringExtra("USER_ID");
        if (userId != null) {
            displayUserDetails(userId);
        } else {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_LONG).show();
        }

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveUser();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, EditUserActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteUser();
            }
        });

    }

    private void displayUserDetails(String userId) {
        Cursor userCursor = null;
        Cursor questionnaireCursor = null;
        try {
            userCursor = myDb.getDataById(userId);
            if (userCursor != null && userCursor.moveToFirst()) {
                @SuppressLint("Range") String name = userCursor.getString(userCursor.getColumnIndex("NAME"));
                @SuppressLint("Range") String email = userCursor.getString(userCursor.getColumnIndex("EMAIL"));

                StringBuilder userDetails = new StringBuilder();
                userDetails.append("Nama        : ").append(name).append("\n");
                userDetails.append("Email         : ").append(email).append("\n");

                userDetailTextView.setText(userDetails.toString());

                @SuppressLint("Range") int isApproved = userCursor.getInt(userCursor.getColumnIndex("IS_APPROVED"));
                if (isApproved == 1) {
                    approveButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                } else {
                    approveButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(this, "No user data available", Toast.LENGTH_LONG).show();
            }
        } finally {
            if (userCursor != null && !userCursor.isClosed()) {
                userCursor.close();
            }
            if (questionnaireCursor != null && !questionnaireCursor.isClosed()) {
                questionnaireCursor.close();
            }
        }
    }

    private void approveUser() {
        boolean isApproved = myDb.approveUser(userId);
        if (isApproved) {
            Toast.makeText(this, "User approved", Toast.LENGTH_LONG).show();
            approveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Approval failed", Toast.LENGTH_LONG).show();
        }
    }

    private void confirmDeleteUser() {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteUser() {
        boolean isDeleted = myDb.deleteUser(userId);
        if (isDeleted) {
            Toast.makeText(this, "User deleted", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Deletion failed", Toast.LENGTH_LONG).show();
        }
    }

}
