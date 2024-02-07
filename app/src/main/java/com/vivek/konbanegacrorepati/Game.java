package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
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

    Intent intent;
    List<Question> allQuestions = new ArrayList<>();
    int Score=0;

    String playerAnswer;
    Chronometer timerClock;
    CountDownTimer countDownTimer;
    TextView questionText,score;
    RadioGroup options;
    RadioButton option1, option2, option3, option4,option5;
    Button submit;
    int currentIndex=0;
    long baseTime = System.currentTimeMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findid();
        getTheData();
        intent=new Intent(this, Result.class);
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected radio button from the group using checkedId
                if(option1.isChecked()){
                    playerAnswer="a";
                }
                if(option2.isChecked()){
                    playerAnswer="b";
                }
                if(option3.isChecked()){
                    playerAnswer="c";
                }
                if(option4.isChecked()){
                    playerAnswer="d";
                }
                if(option5.isChecked()){
                    playerAnswer="e";
                }

            }
        });



    }

    //setting all the questions one by one to the xml and adding any functions
    private void displayTheQuestions() {
        if (currentIndex < allQuestions.size()) {
            Question currentQuestion = allQuestions.get(currentIndex);

            options.clearCheck();
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





            // update  time to  display the next question after the specified time
            countDownTimer =new CountDownTimer(questionTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Update the timer with the remaining time
                    long elapsedTime = System.currentTimeMillis() - baseTime;
                    timerClock.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),"currentQuestion.getCorrectAns() ="+currentQuestion.getCorrectAns()+"---"+"playerAnswer = "+playerAnswer,Toast.LENGTH_SHORT).show();
                            //setting the score
                            if(currentQuestion.getCorrectAns().equals(playerAnswer)){
                                Score=Score+10;
                                Toast.makeText(getApplicationContext(),String.valueOf(Score),Toast.LENGTH_SHORT).show();
                            }
                            if(currentIndex==allQuestions.size()-1)
                                {   cancel();
                                    intent.putExtra("Score",String.valueOf(Score));
                                    startActivity(intent);
                                }
                            else
                                {
                                    onFinish();
                                    cancel();
                                }
                        }
                    });
                }

                public void onFinish() {


                    //moving to the result page
                    if(currentIndex==allQuestions.size()-1)
                    {
                        intent.putExtra("Score",String.valueOf(Score));
                        startActivity(intent);
                    }
                    timerClock.stop();
                    currentIndex++;
                    // Move to the next question after the timer finishes
                    displayTheQuestions();
                }
            }.start();
        }
    }


    //method finds the all the ids from Xml file
    private void findid() {
        timerClock = findViewById(R.id.timerClock);
        questionText = findViewById(R.id.questionText);
        options = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option5 = findViewById(R.id.option5);
        score=findViewById(R.id.score);
        submit=findViewById(R.id.submit);
    }



    //geting the all the question from database from questiontable
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