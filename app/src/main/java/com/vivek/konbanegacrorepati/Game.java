package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game extends AppCompatActivity {

    Intent intent;
    List<ComplexityWiseQuestion> allQuestions = new ArrayList<>();
    int Score=0;

    String playerAnswer=null;
    Chronometer timerClock;
    CountDownTimer countDownTimer;
    TextView questionText,score;
    RadioGroup options;
    RadioButton option1, option2, option3, option4,option5;
    Button submit,pause;
    int checkSumitAfterOptionSelection=0;
    int checkOnfinish=1;
    boolean timerStop=false;
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

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(timerStop);
            }
        });

    }
    public void go(boolean bool){
        if(bool==true){
            timerClock.start();
            timerStop=false;
        }
        if(bool==false){
            timerClock.stop();
            timerStop=true;
        }

    }
    //setting all the questions one by one to the xml and adding any functions
    private void displayTheQuestions() {
        if (currentIndex < allQuestions.size()) {
            ComplexityWiseQuestion currentQuestion = allQuestions.get(currentIndex);
            checkQuestion(currentIndex);
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
            go(true);





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
                            checkSumitAfterOptionSelection=1;

                            if(playerAnswer==null){
                                currentIndex=allQuestions.size()-1;
                            }
                            if(currentQuestion.getCorrectAns().equals(playerAnswer)){
                                Toast.makeText(getApplicationContext(),playerAnswer,Toast.LENGTH_SHORT).show();

                                Score=Score+10;
                                Toast.makeText(getApplicationContext(),String.valueOf(Score),Toast.LENGTH_SHORT).show();
                            }
                            else{
                                currentIndex=allQuestions.size()-1;
                                intent.putExtra("message","OOps !! Wrong Answer ");
                                onFinish();
                                cancel();
                                checkOnfinish=0;

                            }


                            if(currentIndex==allQuestions.size()-1)
                                {   cancel();
                                    intent.putExtra("Score",String.valueOf(Score));
                                    intent.putExtra("current index",currentIndex);

                                }
                            else
                                {
                                    if(checkOnfinish==1){
                                        onFinish();
                                        cancel();
                                    }

                                }
                        }
                    });
                }

                public void onFinish() {

                    if(playerAnswer==null||checkSumitAfterOptionSelection==0){


                        intent.putExtra("message","OOps!! Time up For the Question");
                        currentIndex=allQuestions.size()-1;

                    }
                    checkSumitAfterOptionSelection=0;
                    //moving to the result page
                    if(currentIndex==allQuestions.size()-1)
                    {
                        setQuestion();
                        intent.putExtra("Score",String.valueOf(Score));
                        startActivity(intent);
                        finish();
                        finish();
                    }
                    timerClock.stop();
                    currentIndex++;
                    // Move to the next question after the timer finishes
                    playerAnswer=null;
                    displayTheQuestions();
                }
            }.start();
        }
    }

    private void setQuestion() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService=retrofit.create(ApiService.class);
        Call<ResponseAdmin> settingQuestion=apiService.SettingQuestion
                (QuestionNumber.userId,QuestionNumber.complexity1Qno,QuestionNumber.complexity2Qno
                        ,QuestionNumber.complexity3Qno,QuestionNumber.complexity4Qno,QuestionNumber.complexity5Qno);
        settingQuestion.enqueue(new Callback<ResponseAdmin>() {
            @Override
            public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                ResponseAdmin responseRegistration=response.body();
                String success=responseRegistration.getSuccess();
                String message= responseRegistration.getMessage();

                Toast.makeText(getApplicationContext(),"success : "+success+"message  :"+message
                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseAdmin> call, Throwable t) {

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


    private void checkQuestion(int currentIndex) {
        switch (currentIndex){
            case 0:QuestionNumber.complexity1Qno++;
                Log.d("complexity1Qno", String.valueOf(QuestionNumber.complexity1Qno));
            break;
            case 1:QuestionNumber.complexity1Qno++;
                Log.d("complexity1Qno", String.valueOf(QuestionNumber.complexity1Qno));
                break;
            case 2:QuestionNumber.complexity2Qno++;
                Log.d("complexity2Qno", String.valueOf(QuestionNumber.complexity2Qno));
                break;
            case 3:QuestionNumber.complexity2Qno++;
                Log.d("complexity2Qno", String.valueOf(QuestionNumber.complexity2Qno));
                break;
            case 4:QuestionNumber.complexity3Qno++;
                Log.d("complexity3Qno", String.valueOf(QuestionNumber.complexity3Qno));
                break;
            case 5:QuestionNumber.complexity3Qno++;
                Log.d("complexity3Qno", String.valueOf(QuestionNumber.complexity3Qno));
                break;
            case 6:QuestionNumber.complexity4Qno++;
                Log.d("complexity4Qno", String.valueOf(QuestionNumber.complexity4Qno));
                break;
            case 7:QuestionNumber.complexity4Qno++;
                Log.d("complexity4Qno", String.valueOf(QuestionNumber.complexity4Qno));
                break;
            case 8:QuestionNumber.complexity5Qno++;
                Log.d("complexity5Qno", String.valueOf(QuestionNumber.complexity5Qno));
                break;
            case 9:QuestionNumber.complexity5Qno++;
                Log.d("complexity5Qno", String.valueOf(QuestionNumber.complexity5Qno));
                break;


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
        pause=findViewById(R.id.stop);
    }

    private void getTheData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<ComplexityWiseQuestion>> getAllQuestions = apiService.GetAllQuestions(QuestionNumber.userId,QuestionNumber.complexity1Qno,QuestionNumber.complexity2Qno,
                                QuestionNumber.complexity3Qno,QuestionNumber.complexity4Qno,QuestionNumber.complexity5Qno);

        getAllQuestions.enqueue(new Callback<List<ComplexityWiseQuestion>>()  {
            @Override
            public void onResponse(Call<List<ComplexityWiseQuestion>> call, Response<List<ComplexityWiseQuestion>> response) {
               // Log.d("response", response);
                if (response.isSuccessful()) {

                    List<ComplexityWiseQuestion> questions = response.body();


                    setting(questions);
                    displayTheQuestions();
                }
            }

            @Override
            public void onFailure(Call<List<ComplexityWiseQuestion>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setting(List<ComplexityWiseQuestion> questions) {

        allQuestions.addAll(questions);
    }
}

 /*
    //geting the all the question from database from questiontable
    private void getTheData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
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
*/