package com.nautanki.loginregapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);
    }
    public void signup(View view)
    {
        Intent intent=new Intent(StudentOptions.this,Register.class);
        startActivity(intent);
    }
    public void signin(View view)
    {
        Intent intent=new Intent(StudentOptions.this,MainActivity.class);
        startActivity(intent);
    }
}