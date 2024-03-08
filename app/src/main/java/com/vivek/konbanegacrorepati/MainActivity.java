package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView signUp;
    EditText mobileNumber;
    EditText password;
    Button login;
    String txt_mobileNumber,txt_password;
    Intent intent;
    int userId=0;

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
                else if(userId>0){

                    intent=new Intent(MainActivity.this, Instructions.class);
                    QuestionNumber.userId=userId;
                    startActivity(intent);
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

    private void getLoginDetail() {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService=retrofit.create(ApiService.class);
        Call<LoginDetail> login=apiService.Login
                (txt_mobileNumber,txt_password);
        login.enqueue(new Callback<LoginDetail>() {
            @Override
            public void onResponse(Call<LoginDetail> call, Response<LoginDetail> response) {
                LoginDetail responseRegistration=response.body();
                String success=responseRegistration.getSuccess();
                String message= responseRegistration.getMessage();
                userId=responseRegistration.getUserid();


                Toast.makeText(getApplicationContext(),"success : "+success+"message  :"+message+"userId  :"+userId
                        ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginDetail> call, Throwable t) {

                String errorMessage = t.getMessage();

                // If the message is null, display a generic error message
                if (errorMessage == null) {
                    errorMessage = "Request failed";
                }

                // Display the error message in a Toast
                Toast.makeText(getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void gettext() {
        txt_mobileNumber=mobileNumber.getText().toString();
        txt_password=password.getText().toString();
        getLoginDetail();
    }

    private void findId() {
        mobileNumber=findViewById(R.id.editTextMobileNumber);
        password=findViewById(R.id.editTextPassword);
        login=findViewById(R.id.buttonLogin);
        signUp=findViewById(R.id.signUp);
    }
}