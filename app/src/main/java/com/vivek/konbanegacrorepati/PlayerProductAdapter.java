package com.vivek.konbanegacrorepati;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerProductAdapter extends ArrayAdapter<ProductGetting> {
    Context context;
    GetPattern pattern;
    List<ProductGetting> productList;
    List<ProductGetting> filteredList;

    public PlayerProductAdapter(Context context, List<ProductGetting> products) {
        super(context, 0, products);
        this.context = context;
        this.productList = products;
        this.filteredList = new ArrayList<>(products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ProductGetting product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_player_product_watch, parent, false);
        }

        // Lookup view for data population

        TextView productName = convertView.findViewById(R.id.product_name);
        TextView maxPrice = convertView.findViewById(R.id.max_discount);

        // Populate the data into the template view using the data object

        productName.setText("Product Name: " + product.getProductName());
        maxPrice.setText("Max Discount: " + product.getMaxDiscount());

        // Set OnClickListener for the convertView (item view)
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                Toast.makeText(context, "Clicked on " + product.getProductName(), Toast.LENGTH_SHORT).show();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService=retrofit.create(ApiService.class);

                Call<List<GetPattern>> GettingPatternForQuestion=apiService.GettingPatternForQuestion(product.getMaxDiscount());
                GettingPatternForQuestion.enqueue(new Callback<List<GetPattern>>() {
                    @Override
                    public void onResponse(Call<List<GetPattern>> call, Response<List<GetPattern>> response) {
                        List<GetPattern> patterns = response.body();
                        pattern=patterns.get(0);
                        ProductDiscountRecord.questionWiseDiscount[0]= pattern.getQ1();
                        ProductDiscountRecord.questionWiseDiscount[1]= pattern.getQ2();
                        ProductDiscountRecord.questionWiseDiscount[2]= pattern.getQ3();
                        ProductDiscountRecord.questionWiseDiscount[3]= pattern.getQ4();
                        ProductDiscountRecord.questionWiseDiscount[4]= pattern.getQ5();
                        ProductDiscountRecord.questionWiseDiscount[5]= pattern.getQ6();
                        ProductDiscountRecord.questionWiseDiscount[6]= pattern.getQ7();
                        ProductDiscountRecord.questionWiseDiscount[7]= pattern.getQ8();
                        ProductDiscountRecord.questionWiseDiscount[8]= pattern.getQ9();
                        ProductDiscountRecord.questionWiseDiscount[9]= pattern.getQ10();
                        Intent intent = new Intent(context, Game.class);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Start Game");
                        builder.setMessage("Do you want to start the game?");
                        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Start the game
                                Intent intent = new Intent(context, Game.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancel, do nothing
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<List<GetPattern>> call, Throwable t) {
                        Toast.makeText(context,"failed  "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("errrrrrrrrrrrrrror", t.getMessage());
                    }
                });
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void filter(String searchText) {
        filteredList.clear();
        if (searchText.isEmpty()) {
            filteredList.addAll(productList); // Add all products if search text is empty
        } else {
            searchText = searchText.toLowerCase();
            for (ProductGetting product : productList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    filteredList.add(product); // Add product if its name matches the search text
                }
            }
        }
        notifyDataSetChanged(); // Notify adapter that data set has changed
    }

    // Override getCount() to return the size of filtered list
    @Override
    public int getCount() {
        return filteredList.size();
    }

    // Override getItem() to return item from filtered list
    @Override
    public ProductGetting getItem(int position) {
        return filteredList.get(position);
    }

}
