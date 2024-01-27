package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instructions extends AppCompatActivity {
    Button startTheGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        findId();
        startTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Instructions.this,Game.class));

            }
        });
    }

    private void findId() {
        startTheGame=findViewById(R.id.proceed);
    }
}