package com.example.e_kuisioner;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    DatabaseHelper myDb;
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
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        myDb = new DatabaseHelper(this);

        userDetailTextView = findViewById(R.id.user_detail_text_view);
        tokopediaValueTextViewWarna = findViewById(R.id.tokopedia_value_text_view_warna);
        tokopediaValueTextViewNavigasi = findViewById(R.id.tokopedia_value_text_view_navigasi);
        shopeeValueTextViewWarna = findViewById(R.id.shopee_value_text_view_warna);
        shopeeValueTextViewNavigasi = findViewById(R.id.shopee_value_text_view_navigasi);
        tokopediaValueTextViewWarna2 = findViewById(R.id.tokopedia_value_text_view_warna2);
        tokopediaValueTextViewNavigasi2 = findViewById(R.id.tokopedia_value_text_view_navigasi2);
        shopeeValueTextViewWarna2 = findViewById(R.id.shopee_value_text_view_warna2);
        shopeeValueTextViewNavigasi2 = findViewById(R.id.shopee_value_text_view_navigasi2);
        allResults = findViewById(R.id.all_results);
        allResultsUser = findViewById(R.id.all_results_user);
        totalUser = findViewById(R.id.total_user);

        userId = getIntent().getStringExtra("USER_ID");


        Cursor userCursor = myDb.getDataById(userId);
        if (userCursor != null && userCursor.moveToFirst()) {
            @SuppressLint("Range") String name = userCursor.getString(userCursor.getColumnIndex("NAME"));
            @SuppressLint("Range") int age = userCursor.getInt(userCursor.getColumnIndex("AGE"));
            @SuppressLint("Range") String job = userCursor.getString(userCursor.getColumnIndex("JOB"));
            @SuppressLint("Range") String email = userCursor.getString(userCursor.getColumnIndex("EMAIL"));

            StringBuilder userDetails = new StringBuilder();
            userDetails.append("Nama        : ").append(name).append("\n");
            userDetails.append("Umur         : ").append(age).append("\n");
            userDetails.append("Pekerjaan : ").append(job).append("\n");
            userDetails.append("Email         : ").append(email).append("\n");

            userDetailTextView.setText(userDetails.toString());

        } else {
            Toast.makeText(this, "No user data available", Toast.LENGTH_LONG).show();
        }


    }

}
