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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductList extends AppCompatActivity {
    EditText search;
    ListView productList;
    Button addProduct;
    //List<ProductGetting> products=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getList();
        getId();
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductList.this,product.class));
            }
        });
    }

    public void getList() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService=retrofit.create(ApiService.class);

        Call<List<ProductGetting>> getProducts=apiService.GetProducts();
        getProducts.enqueue(new Callback<List<ProductGetting>>() {
            @Override
            public void onResponse(Call<List<ProductGetting>> call, Response<List<ProductGetting>> response) {
                List<ProductGetting> products = response.body();
                ProductAdapter adapter = new ProductAdapter(ProductList.this, products);
                productList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProductGetting>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failed  "+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("errrrrrrrrrrrrrror", t.getMessage());
            }
        });
    }

    private void getId() {
        search=findViewById(R.id.search);
        productList=findViewById(R.id.list);
        addProduct=findViewById(R.id.addProduct);
    }
}