package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDiscountPattern extends AppCompatActivity {
    Intent intent;
    EditText Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10;
    TextView maxDiscount,addition;
    int TmaxDiscount,TQ1,TQ2,TQ3,TQ4,TQ5,TQ6,TQ7,TQ8,TQ9,TQ10,sum=0,additionText=0;
    ImageView backButton;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_discount_pattern);
        getId();
        getData();
        settext();
        setupTextWatchers();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateDiscountPattern.this,DiscountPatternList.class));
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                sum();
                if(TmaxDiscount==sum){
                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService=retrofit.create(ApiService.class);
                    Call<ResponseAdmin> updatePattern=apiService.UpdatePattern(TmaxDiscount,TQ1,TQ2,TQ3,TQ4,TQ5,TQ6,TQ7,TQ8,TQ9,TQ10);

                    updatePattern.enqueue(new Callback<ResponseAdmin>() {
                        @Override
                        public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                            ResponseAdmin responseRegistration=response.body();
                            String success=responseRegistration.getSuccess();
                            String message= responseRegistration.getMessage();

                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDiscountPattern.this);
                            builder.setTitle("Success");
                            builder.setMessage("Pattern updated successfully.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(UpdateDiscountPattern.this, DiscountPatternList.class);
                                    startActivity(intent);

                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onFailure(Call<ResponseAdmin> call, Throwable t) {
                            String errorMessage = t.getMessage();

                            // If the message is null, display a generic error message
                            if (errorMessage == null) {
                                errorMessage = "Request failed";
                            }
                            Log.d("erooooooooooooooor",errorMessage );
                            // Display the error message in a Toast
                            Toast.makeText(getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDiscountPattern.this);
                    builder.setTitle("Error");
                    builder.setMessage("Indivisual discount Does not add up to the Max discount.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }


    private void settext() {
        maxDiscount.setText(String.valueOf(TmaxDiscount));
        Q1.setText(String.valueOf(TQ1));
        Q2.setText(String.valueOf(TQ2));
        Q3.setText(String.valueOf(TQ3));
        Q4.setText(String.valueOf(TQ4));
        Q5.setText(String.valueOf(TQ5));
        Q6.setText(String.valueOf(TQ6));
        Q7.setText(String.valueOf(TQ7));
        Q8.setText(String.valueOf(TQ8));
        Q9.setText(String.valueOf(TQ9));
        Q10.setText(String.valueOf(TQ10));
    }


    private void getData() {
        intent=getIntent();
        TmaxDiscount=intent.getIntExtra("maxDiscount",0);
        TQ1=intent.getIntExtra("question1",0);
        TQ2=intent.getIntExtra("question2",0);
        TQ3=intent.getIntExtra("question3",0);
        TQ4=intent.getIntExtra("question4",0);
        TQ5=intent.getIntExtra("question5",0);
        TQ6=intent.getIntExtra("question6",0);
        TQ7=intent.getIntExtra("question7",0);
        TQ8=intent.getIntExtra("question8",0);
        TQ9=intent.getIntExtra("question9",0);
        TQ10=intent.getIntExtra("question10",0);
    }

    private void gettext() {
        TmaxDiscount=Integer.parseInt(maxDiscount.getText().toString());
        TQ1=Integer.parseInt(Q1.getText().toString());
        TQ2=Integer.parseInt(Q2.getText().toString());
        TQ3=Integer.parseInt(Q3.getText().toString());
        TQ4=Integer.parseInt(Q4.getText().toString());
        TQ5=Integer.parseInt(Q5.getText().toString());
        TQ6=Integer.parseInt(Q6.getText().toString());
        TQ7=Integer.parseInt(Q7.getText().toString());
        TQ8=Integer.parseInt(Q8.getText().toString());
        TQ9=Integer.parseInt(Q9.getText().toString());
        TQ10=Integer.parseInt(Q10.getText().toString());
    }

    private void getId() {
        maxDiscount=findViewById(R.id.discount);
        Q1=findViewById(R.id.Q1);
        Q2=findViewById(R.id.Q2);
        Q3=findViewById(R.id.Q3);
        Q4=findViewById(R.id.Q4);
        Q5=findViewById(R.id.Q5);
        Q6=findViewById(R.id.Q6);
        Q7=findViewById(R.id.Q7);
        Q8=findViewById(R.id.Q8);
        Q9=findViewById(R.id.Q9);
        Q10=findViewById(R.id.Q10);
        update=findViewById(R.id.submit);
        backButton=findViewById(R.id.backButton);
        addition=findViewById(R.id.addition);
    }
    private void sum(){
        sum=TQ1+TQ2+TQ3+TQ4+TQ5+TQ6+TQ7+TQ8+TQ9+TQ10;
    }
    private void updateAddition() {
        additionText = 0;
        additionText += parseIntOrZero(Q1.getText().toString());
        additionText += parseIntOrZero(Q2.getText().toString());
        additionText += parseIntOrZero(Q3.getText().toString());
        additionText += parseIntOrZero(Q4.getText().toString());
        additionText += parseIntOrZero(Q5.getText().toString());
        additionText += parseIntOrZero(Q6.getText().toString());
        additionText += parseIntOrZero(Q7.getText().toString());
        additionText += parseIntOrZero(Q8.getText().toString());
        additionText += parseIntOrZero(Q9.getText().toString());
        additionText += parseIntOrZero(Q10.getText().toString());

        addition.setText(String.valueOf(additionText));
    }
    private void setupTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateAddition();
            }
        };
        Q1.addTextChangedListener(textWatcher);
        Q2.addTextChangedListener(textWatcher);
        Q3.addTextChangedListener(textWatcher);
        Q4.addTextChangedListener(textWatcher);
        Q5.addTextChangedListener(textWatcher);
        Q6.addTextChangedListener(textWatcher);
        Q7.addTextChangedListener(textWatcher);
        Q8.addTextChangedListener(textWatcher);
        Q9.addTextChangedListener(textWatcher);
        Q10.addTextChangedListener(textWatcher);
    }
    private int parseIntOrZero(String input) {
        return input.isEmpty() ? 0 : Integer.parseInt(input);
    }

}