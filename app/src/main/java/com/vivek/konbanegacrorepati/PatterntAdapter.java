package com.vivek.konbanegacrorepati;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatterntAdapter extends ArrayAdapter<GetPattern> {
    Intent intent;
    private Context mContext;
    private List<GetPattern> mPatternList;
    private List<GetPattern> filteredList;

    public PatterntAdapter(Context context, List<GetPattern> patternList) {
        super(context, 0, patternList);
        mContext = context;
        mPatternList = patternList;
        filteredList = new ArrayList<>(patternList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_one_view_of_pattern, parent, false);
        }

        GetPattern currentPattern = mPatternList.get(position);

        TextView patternTextView = listItem.findViewById(R.id.pattern);
        Button tick=listItem.findViewById(R.id.tick);
        Button cross=listItem.findViewById(R.id.cross);
        patternTextView.setText(String.valueOf(currentPattern.getMaxDiscount()));
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(mContext,UpdateDiscountPattern.class);
                intent.putExtra("maxDiscount",currentPattern.getMaxDiscount());
                intent.putExtra("question1",currentPattern.getQ1());
                intent.putExtra("question2",currentPattern.getQ2());
                intent.putExtra("question3",currentPattern.getQ3());
                intent.putExtra("question4",currentPattern.getQ4());
                intent.putExtra("question5",currentPattern.getQ5());
                intent.putExtra("question6",currentPattern.getQ6());
                intent.putExtra("question7",currentPattern.getQ7());
                intent.putExtra("question8",currentPattern.getQ8());
                intent.putExtra("question9",currentPattern.getQ9());
                intent.putExtra("question10",currentPattern.getQ10());
                mContext.startActivity(intent);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to Delete the Pattern");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService apiService=retrofit.create(ApiService.class);
                        Call<ResponseAdmin> deletePattern=apiService.DeletePattern
                                (currentPattern.getMaxDiscount(), currentPattern.getQ1(),
                                        currentPattern.getQ2(), currentPattern.getQ3(), currentPattern.getQ4(),
                                        currentPattern.getQ5(), currentPattern.getQ6(), currentPattern.getQ7(),
                                        currentPattern.getQ8(), currentPattern.getQ9(), currentPattern.getQ10());

                        deletePattern.enqueue(new Callback<ResponseAdmin>() {
                            @Override
                            public void onResponse(Call<ResponseAdmin> call, Response<ResponseAdmin> response) {
                                ResponseAdmin responseRegistration=response.body();
                                String success=responseRegistration.getSuccess();
                                String message= responseRegistration.getMessage();
                                Intent intent = new Intent(mContext, AdminPage.class);
                                intent.putExtra("jumpProduct","product");
                                mContext.startActivity(intent);
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
                                Toast.makeText(mContext, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss(); // Close the dialog
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });


        return listItem;
    }

    private void deletePattern() {

    }

    public void filter(int searchValue) {
        double tolerance = 0.0001; // Adjust the tolerance level as needed
        filteredList.clear();
        for (GetPattern pattern : mPatternList) {
            if (Math.abs(pattern.getMaxDiscount() - searchValue) < tolerance) {
                filteredList.add(pattern);
            }
        }
        notifyDataSetChanged();
    }

    // Override getCount() to return the size of filtered list
    @Override
    public int getCount() {
        return filteredList.size();
    }

    // Override getItem() to return item from filtered list
    @Override
    public GetPattern getItem(int position) {
        return filteredList.get(position);
    }

    public void clearFilter() {
        filteredList.clear();
        filteredList.addAll(mPatternList);
        notifyDataSetChanged(); // Notify adapter that data set has changed
    }



}
