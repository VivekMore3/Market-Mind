package com.vivek.konbanegacrorepati;

import static androidx.core.content.ContextCompat.startActivity;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends ArrayAdapter<ProductGetting> {
    Context context;
    List<ProductGetting> productList;
    List<ProductGetting> filteredList;
    public ProductAdapter(Context context, List<ProductGetting> products) {
        super(context, 0, products);
        this.context=context;
        this.productList = products;
        this.filteredList = new ArrayList<>(products != null ? products : new ArrayList<>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductGetting product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item_product, parent, false);
        }

        // Lookup view for data population

        TextView productName = convertView.findViewById(R.id.product_name);
        TextView maxPrice = convertView.findViewById(R.id.product_price);
        TextView maxDiscount = convertView.findViewById(R.id.max_discount);
        ImageView tick=convertView.findViewById(R.id.tick);
        ImageView cross=convertView.findViewById(R.id.cross);


        // Populate the data into the template view using the data object
        productName.setText("Product Name: " + product.getProductName());
        maxPrice.setText("Product Price: " + product.getProductPrice());
        maxDiscount.setText("Max discount: " + product.getMaxDiscount());

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click action for the tick button
                // Here you can pass the product details to the next activity

                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("productId", product.getProductCode());
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("maxPrice", String.valueOf(product.getProductPrice()));
                intent.putExtra("maxDiscount", String.valueOf(product.getMaxDiscount()));
                context.startActivity(intent);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to Delete the product?");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService apiService=retrofit.create(ApiService.class);
                        Call<ResponseAdmin> deleteProduct=apiService.DeleteProduct(product.getProductCode(),product.getProductName(),product.getMaxDiscount());

                        deleteProduct.enqueue(new Callback<ResponseAdmin>() {
                            @Override
                            public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                                ResponseAdmin responseRegistration=response.body();
                                String success=responseRegistration.getSuccess();
                                String message= responseRegistration.getMessage();

                                Intent intent = new Intent(context, AdminPage.class);
                                intent.putExtra("jumpProduct","product");
                                context.startActivity(intent);

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
                                Toast.makeText(context, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


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
