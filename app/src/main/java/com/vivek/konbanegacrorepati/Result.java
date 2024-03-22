package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    int score;
    String messageText;

    Intent intent;
    TextView result,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findId();
        intent=getIntent();
       score=Integer.parseInt(intent.getStringExtra("Score"));
        messageText=intent.getStringExtra("message");
        result.setText("You got "+String.valueOf(score/10)+" " +
                "Question right");
        message.setText(messageText);


    }

    private void findId() {
        result=findViewById(R.id.result);
        message=findViewById(R.id.message);
    }
}