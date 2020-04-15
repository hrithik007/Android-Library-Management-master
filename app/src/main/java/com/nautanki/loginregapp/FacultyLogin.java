package com.nautanki.loginregapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FacultyLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
    }


    public void login(View view)
    {
        EditText userField = (EditText) findViewById(R.id.username);
        String user = (userField.getText()).toString();
        EditText passField = (EditText) findViewById(R.id.password);
        String pass = (passField.getText()).toString();
        if (user.equals("prabhat")&&(pass.equals("1234"))) {
            Intent intent = new Intent(FacultyLogin.this, SearchDetail.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Please Enter Correct Credentials",Toast.LENGTH_SHORT).show();
        }


    }
}
