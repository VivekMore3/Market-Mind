package com.vivek.konbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscountPatternList extends AppCompatActivity {

    EditText search;
    ListView productList;
    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pattern_list);
        getId();

        getList();
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscountPatternList.this,DiscountPattern.class));
            }
        });

    }



    private void getId() {
        search=findViewById(R.id.search);
        productList=findViewById(R.id.List);
        addProduct=findViewById(R.id.addPattern);
    }
    private void getList() {
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
                PatterntAdapter patterntAdapter=new PatterntAdapter(DiscountPatternList.this,patterns);
                productList.setAdapter(patterntAdapter);
            }

            @Override
            public void onFailure(Call<List<GetPattern>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failed  "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("errrrrrrrrrrrrrror", t.getMessage());
            }
        });
    }
}