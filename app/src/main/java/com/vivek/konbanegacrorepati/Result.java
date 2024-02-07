package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    int score;

    Intent intent;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findId();
        intent=getIntent();
       score=Integer.parseInt(intent.getStringExtra("Score"));
        result.setText(String.valueOf(score));


    }

    private void findId() {
        result=findViewById(R.id.result);
    }
}