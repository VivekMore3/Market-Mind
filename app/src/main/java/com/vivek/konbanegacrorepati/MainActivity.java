package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView signUp;
    EditText mobileNumber;
    EditText password;
    Button login;
    String txt_mobileNumber,txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                if(txt_mobileNumber.equals("74475")&&txt_password.equals("vivek@123")){
                    startActivity(new Intent(MainActivity.this,AdminPage.class));

                }
                else {
                    startActivity(new Intent(MainActivity.this,Instructions.class));

                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

    }

    private void gettext() {
        txt_mobileNumber=mobileNumber.getText().toString();
        txt_password=password.getText().toString();
    }

    private void findId() {
        mobileNumber=findViewById(R.id.editTextMobileNumber);
        password=findViewById(R.id.editTextPassword);
        login=findViewById(R.id.buttonLogin);
        signUp=findViewById(R.id.signUp);
    }
}