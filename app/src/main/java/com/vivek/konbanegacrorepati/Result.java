package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    int score;
    String messageText;
    ImageView backButton;
    Intent intent;
    TextView result;
    TextView message;
    String totalDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findId();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this,Instructions.class));
                finish();
            }
        });
        intent=getIntent();
       score=Integer.parseInt(intent.getStringExtra("Score"));
        messageText=intent.getStringExtra("message");
        totalDiscount=intent.getStringExtra("totalDiscount");
        result.setText("CORRECT QUESTIONS:  " + String.valueOf(score/10) +
                "\n\n" +
                "DISCOUNT: " + totalDiscount + "%");

        message.setText(messageText);


    }

    private void findId() {
        result=findViewById(R.id.result);
        message=findViewById(R.id.message);
        backButton=findViewById(R.id.backButton);
    }
}