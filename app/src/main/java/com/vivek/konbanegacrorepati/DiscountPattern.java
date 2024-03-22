package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscountPattern extends AppCompatActivity {

    EditText maxDiscount,Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10;
    int TmaxDiscount,TQ1,TQ2,TQ3,TQ4,TQ5,TQ6,TQ7,TQ8,TQ9,TQ10,sum=0;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pattern);
        getId();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                sum();
                if(sum!=TmaxDiscount){
                    Toast.makeText(getApplicationContext(), "OPPS! Sum of Discount does not match the Maximum Discount allocated ", Toast.LENGTH_SHORT).show();

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
            }
        });
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
        submit=findViewById(R.id.submit);
    }
    private void sum(){
        sum=TQ1+TQ2+TQ3+TQ4+TQ5+TQ6+TQ7+TQ8+TQ9+TQ10;
    }
}