package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscountPattern extends AppCompatActivity {

    EditText maxDiscount,Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10;
    int TmaxDiscount,TQ1,TQ2,TQ3,TQ4,TQ5,TQ6,TQ7,TQ8,TQ9,TQ10,sum=0;
    TextView addition;
    ImageButton backButton;
    int additionText=0;

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pattern);
        getId();
        setupTextWatchers();
        gettext();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscountPattern.this,DiscountPatternList.class));
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                sum();
                if(sum!=TmaxDiscount){
                    showSumMismatchDialog();
                }
                else{
                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService=retrofit.create(ApiService.class);

                    Call<ResponseAdmin> productPattern=apiService.ProductPattern(TmaxDiscount,TQ1,TQ2,TQ3,TQ4,TQ5,TQ6,TQ7,TQ8,TQ9,TQ10);
                    productPattern.enqueue(new Callback<ResponseAdmin>() {
                        @Override
                        public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                            ResponseAdmin responseRegistration=response.body();
                            String success=responseRegistration.getSuccess();
                            String message= responseRegistration.getMessage();
                                showPatternRecordedDialog();


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
            }
        });
    }

    private void gettext() {
        try {
            TmaxDiscount = Integer.parseInt(maxDiscount.getText().toString());
            TQ1 = parseIntOrZero(Q1.getText().toString());
            TQ2 = parseIntOrZero(Q2.getText().toString());
            TQ3 = parseIntOrZero(Q3.getText().toString());
            TQ4 = parseIntOrZero(Q4.getText().toString());
            TQ5 = parseIntOrZero(Q5.getText().toString());
            TQ6 = parseIntOrZero(Q6.getText().toString());
            TQ7 = parseIntOrZero(Q7.getText().toString());
            TQ8 = parseIntOrZero(Q8.getText().toString());
            TQ9 = parseIntOrZero(Q9.getText().toString());
            TQ10 = parseIntOrZero(Q10.getText().toString());
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails
            Toast.makeText(getApplicationContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
        submit=findViewById(R.id.submit);
        addition=findViewById(R.id.addition);
        backButton=findViewById(R.id.backButton);
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

    private int parseIntOrZero(String input) {
        return input.isEmpty() ? 0 : Integer.parseInt(input);
    }
    private void showSumMismatchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sum Mismatch");
        builder.setMessage("The sum of your discounts does not match the maximum discount allocated.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showPatternRecordedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success");
        builder.setMessage("Pattern recorded successfully.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(DiscountPattern.this, AdminPage.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}