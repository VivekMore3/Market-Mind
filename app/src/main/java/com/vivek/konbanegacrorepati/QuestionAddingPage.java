package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionAddingPage extends AppCompatActivity {

    TextView reset;
    EditText question;
    RadioGroup option;
    RadioButton optionA,optionB,optionC,optionD,optionE;

    EditText optionA_txt,optionB_txt,optionC_txt,optionD_txt,optionE_txt;
    RadioGroup complexity;
    RadioButton c01,c02,c03,c04,c05;
    EditText time;
    Button submit,product;


    String t_question,t_optionA,t_optionB,t_optionC,t_optionD,t_optionE;
    char t_correctAns;
    int t_time,t_complexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_adding_page);

        //getting all the ids
        findId();


        //getting the correct option
        option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected radio button from the group using checkedId
                if(optionA.isChecked()){
                    t_correctAns='a';
                }
                if(optionB.isChecked()){
                    t_correctAns='b';
                }
                if(optionC.isChecked()){
                    t_correctAns='c';
                }
                if(optionD.isChecked()){
                    t_correctAns='d';
                }
                if(optionE.isChecked()){
                    t_correctAns='e';
                }

            }
        });



        //getting the complexity
        complexity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected radio button from the group using checkedId
                if(c01.isChecked()){
                    t_complexity=1;
                }
                if(c02.isChecked()){
                    t_complexity=2;
                }
                if(c03.isChecked()){
                    t_complexity=3;
                }
                if(c04.isChecked()){
                    t_complexity=4;
                }
                if(c05.isChecked()){
                    t_complexity=5;
                }

            }
        });



        //clearing all the fields
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettext();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService=retrofit.create(ApiService.class);

                if(t_complexity==1){
                    Call<ResponseAdmin> complexity1=apiService.Complexity1
                            (t_question,t_optionA,t_optionB
                                    ,t_optionC,t_optionD,t_optionE,t_correctAns,t_time);
                    complexity1.enqueue(new Callback<ResponseAdmin>() {
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

                else if(t_complexity==2){
                    Call<ResponseAdmin> complexity2=apiService.Complexity2
                            (t_question,t_optionA,t_optionB
                                    ,t_optionC,t_optionD,t_optionE,t_correctAns,t_time);
                    complexity2.enqueue(new Callback<ResponseAdmin>() {
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
                else if(t_complexity==3){
                    Call<ResponseAdmin> complexity3=apiService.Complexity3
                            (t_question,t_optionA,t_optionB
                                    ,t_optionC,t_optionD,t_optionE,t_correctAns,t_time);
                    complexity3.enqueue(new Callback<ResponseAdmin>() {
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
                else if(t_complexity==4){
                    Call<ResponseAdmin> complexity4=apiService.Complexity4
                            (t_question,t_optionA,t_optionB
                                    ,t_optionC,t_optionD,t_optionE,t_correctAns,t_time);
                    complexity4.enqueue(new Callback<ResponseAdmin>() {
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
                else if(t_complexity==5){
                    Call<ResponseAdmin> complexity5=apiService.Complexity5
                            (t_question,t_optionA,t_optionB
                                    ,t_optionC,t_optionD,t_optionE,t_correctAns,t_time);
                    complexity5.enqueue(new Callback<ResponseAdmin>() {
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
                /*Call<ResponseAdmin> admin=apiService.Admin
                        (t_question,t_optionA,t_optionB
                                ,t_optionC,t_optionD,t_optionE,t_correctAns,t_complexity,t_time);
                admin.enqueue(new Callback<ResponseAdmin>() {
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
                */
            }
        });




    }

    private void resetField() {
        question.getText().clear();
        optionA_txt.getText().clear();
        optionB_txt.getText().clear();
        optionC_txt.getText().clear();
        optionD_txt.getText().clear();
        optionE_txt.getText().clear();
        time.getText().clear();

        // Uncheck radio buttons in RadioGroups
        option.clearCheck();
        complexity.clearCheck();
    }

    private void gettext() {
        t_question=question.getText().toString();

        t_optionA=optionA_txt.getText().toString();
        t_optionB=optionB_txt.getText().toString();
        t_optionC=optionC_txt.getText().toString();
        t_optionD=optionD_txt.getText().toString();
        t_optionE=optionE_txt.getText().toString();



        t_time=Integer.parseInt(time.getText().toString());

    }

    private void findId() {
        reset=findViewById(R.id.reset);
        question=findViewById(R.id.question);
        option=findViewById(R.id.option);
        optionA=findViewById(R.id.optionA);
        optionB=findViewById(R.id.optionB);
        optionC=findViewById(R.id.optionC);
        optionD=findViewById(R.id.optionD);
        optionE=findViewById(R.id.optionE);
        optionA_txt=findViewById(R.id.optionA_txt);
        optionB_txt=findViewById(R.id.optionB_txt);
        optionC_txt=findViewById(R.id.optionC_txt);
        optionD_txt=findViewById(R.id.optionD_txt);
        optionE_txt=findViewById(R.id.optionE_txt);
        complexity=findViewById(R.id.complexity);
        c01=findViewById(R.id.c01);
        c02=findViewById(R.id.c02);
        c03=findViewById(R.id.c03);
        c04=findViewById(R.id.c04);
        c05=findViewById(R.id.c05);
        time=findViewById(R.id.time);
        submit=findViewById(R.id.submit);
        //product=findViewById(R.id.product);
    }
}