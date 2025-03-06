package com.example.e_kuisioner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText email, password, password2, name;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDb = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String nameStr = name.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String password2Str = password2.getText().toString().trim();

                if (emailStr.isEmpty() || passwordStr.isEmpty() || nameStr.isEmpty() || password2Str.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!passwordStr.equals(password2Str)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }

                if (isEmailExists(emailStr)) {
                    Toast.makeText(RegisterActivity.this, "Email already registered", Toast.LENGTH_LONG).show();
                    return;
                }

                String userType = isFirstUser() ? "admin" : "user";

                boolean isInserted = myDb.insertData(emailStr, nameStr, passwordStr, userType);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isFirstUser() {
        return myDb.getAllUsers().getCount() == 0;
    }

    private boolean isEmailExists(String email) {
        return myDb.getDataByEmail(email).getCount() > 0;
    }
}
