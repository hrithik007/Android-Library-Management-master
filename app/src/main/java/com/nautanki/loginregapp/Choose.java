package com.nautanki.loginregapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Choose extends AppCompatActivity {
    TextView ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().hide();
        ms=findViewById(R.id.wlcmsg);
        User user=new User(Choose.this);
        ms.setText("Welcome "+user.getName());

    }
    public void feedback(View view)
    {
        Intent intent=new Intent(Choose.this,FeedbackActivity.class);
        startActivity(intent);
    }
    public void faqs(View view)
    {
        Intent intent=new Intent(Choose.this,Faqs.class);
        startActivity(intent);
    }

    public void bookIssue(View view) {
        Intent intent = new Intent(Choose.this, IssuePage.class);
        startActivity(intent);
    }
    public void bookreturn(View view) {
        Intent intent = new Intent(Choose.this, LoginSuccess.class);
        startActivity(intent);
    }



    public void search(View view) {
        Intent intent = new Intent(Choose.this, BookActivity.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        new User(this).removeUser();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}

