package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instructions extends AppCompatActivity {
    Button startTheGame,logOut;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        findId();
        getData();
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish the current activity if needed

            }
        });
        startTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent=new Intent(Instructions.this, PlayerProductList.class);

                startActivity(intent);
            }
        });
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<GettingQuestionNumbers>> getAllQuestionNumber = apiService.GetAllQuestionNumber(QuestionNumber.userId);

        getAllQuestionNumber.enqueue(new Callback<List<GettingQuestionNumbers>>()  {
            @Override
            public void onResponse(Call<List<GettingQuestionNumbers>> call, Response<List<GettingQuestionNumbers>> response) {
                // Log.d("response", response);
                if (response.isSuccessful()) {

                    List<GettingQuestionNumbers> questions = response.body();
                    GettingQuestionNumbers question= questions.get(0);
                    QuestionNumber.complexity1Qno=question.getComplexity1Qno();
                    QuestionNumber.complexity2Qno=question.getComplexity2Qno();
                    QuestionNumber.complexity3Qno=question.getComplexity3Qno();
                    QuestionNumber.complexity4Qno=question.getComplexity4Qno();
                    QuestionNumber.complexity5Qno=question.getComplexity5Qno();
                }
            }

            @Override
            public void onFailure(Call<List<GettingQuestionNumbers>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("failureeeeeee", t.getMessage());
            }
        });
    }

    private void findId() {
        startTheGame=findViewById(R.id.proceed);
        logOut=findViewById(R.id.logOut);
    }
}