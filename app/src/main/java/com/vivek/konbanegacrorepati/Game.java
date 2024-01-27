package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game extends AppCompatActivity {
    List<Question> allQuestions = new ArrayList<>();
    Chronometer timerClock;
    TextView questionText;
    RadioGroup optionsRadioGroup;
    RadioButton option1, option2, option3, option4,option5;
    int currentIndex=0;
    long baseTime = System.currentTimeMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findid();
        getTheData();




    }


    private void displayTheQuestions() {
        if (currentIndex < allQuestions.size()) {
            Question currentQuestion = allQuestions.get(currentIndex);

            questionText.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOptionA());
            option2.setText(currentQuestion.getOptionB());
            option3.setText(currentQuestion.getOptionC());
            option4.setText(currentQuestion.getOptionD());
            option5.setText(currentQuestion.getOptionE());

            // Set timer based on question's time
            int questionTime = Integer.parseInt(currentQuestion.getTime()) * 1000; // Convert seconds to milliseconds
            long baseTime = System.currentTimeMillis()+questionTime;
            timerClock.setBase(baseTime);
            timerClock.start();

            // Schedule to display the next question after the specified time
            new CountDownTimer(questionTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Update the timer with the remaining time
                    long elapsedTime = System.currentTimeMillis() - baseTime;
                    timerClock.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                }

                public void onFinish() {
                    // Move to the next question after the timer finishes
                    timerClock.stop();
                    currentIndex++;
                    displayTheQuestions();
                }
            }.start();
        }
    }



    private void findid() {
        timerClock = findViewById(R.id.timerClock);
        questionText = findViewById(R.id.questionText);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option5 = findViewById(R.id.option5);
    }

    private void getTheData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.103/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Question>> call = apiService.getQuestions();

        call.enqueue(new Callback<List<Question>>()  {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {

                if (response.isSuccessful()) {
                    List<Question> questions = response.body();

                    setting(questions);
                    displayTheQuestions();


                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setting(List<Question> questions) {

        allQuestions.addAll(questions);
    }
}