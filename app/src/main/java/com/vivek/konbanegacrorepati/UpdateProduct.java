package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateProduct extends AppCompatActivity {
    TextView productCode;
    EditText productName,productPrice;
    Button update,clear;
    String productCodeText,productNameText;
    String productPriceText,productDiscountText;
    Intent intent;
    Spinner spinner;
    List<String> patternNames = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        getid();

        getDataFromPreviousContent();
        setDataOnTextField();
        gettext();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService=retrofit.create(ApiService.class);
                Call<ResponseAdmin> updateProduct=apiService.UpdateProduct(productCodeText,productNameText,Integer.parseInt(productPriceText),Integer.parseInt(productDiscountText));

                updateProduct.enqueue(new Callback<ResponseAdmin>() {
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
                        Log.d("erooooooooooooooor",errorMessage );
                        // Display the error message in a Toast
                        Toast.makeText(getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void gettext() {
        productCodeText=productCode.getText().toString();
        productNameText=productName.getText().toString();


        productPriceText=productPrice.getText().toString();
        if (spinner.getSelectedItem() != null) {
            productDiscountText = spinner.getSelectedItem().toString();
        }
    }

    private void setDataOnTextField() {
        productCode.setText(String.valueOf(productCodeText));
        productName.setText(productNameText);
        productPrice.setText(productPriceText);
        setSpinner();
    }

    private void setSpinner() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService=retrofit.create(ApiService.class);

        Call<List<GetPattern>> gettingPattern=apiService.GettingPattern();
        gettingPattern.enqueue(new Callback<List<GetPattern>>() {
            @Override
            public void onResponse(Call<List<GetPattern>> call, Response<List<GetPattern>> response) {
                List<GetPattern> patterns = response.body();

                for (GetPattern pattern : patterns) {
                    patternNames.add(String.valueOf(pattern.getMaxDiscount()));
                }

                adapter = new ArrayAdapter<>(UpdateProduct.this, android.R.layout.simple_spinner_item, patternNames);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GetPattern>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failed  "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("errrrrrrrrrrrrrror", t.getMessage());
            }
        });
    }

    private void getDataFromPreviousContent() {
        Intent intent = getIntent();
        if (intent != null) {
            productCodeText = intent.getStringExtra("productId");
            productNameText = intent.getStringExtra("productName");
            productPriceText= intent.getStringExtra("maxPrice");
            productDiscountText = intent.getStringExtra("maxDiscount");

        }
    }

    private void getid() {
        productCode=findViewById(R.id.product_code);
        productName=findViewById(R.id.product_name);
        productPrice=findViewById(R.id.product_price);
        spinner=findViewById(R.id.discount);
        update=findViewById(R.id.update);
        clear=findViewById(R.id.clear);
    }
}