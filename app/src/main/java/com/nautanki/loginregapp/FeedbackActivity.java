package com.nautanki.loginregapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        textView=findViewById(R.id.feedbackupdated);
        textView.setVisibility(View.GONE);
    }
    public void submit(View view)
    {
        EditText userField = (EditText) findViewById(R.id.feedback);
        String user = (userField.getText()).toString();
        userField.setVisibility(View.GONE);
        Button button=findViewById(R.id.fb);
        button.setVisibility(View.GONE);
        TextView textView=findViewById(R.id.feedbackupdated);
        textView.setVisibility(View.VISIBLE);
    }
}