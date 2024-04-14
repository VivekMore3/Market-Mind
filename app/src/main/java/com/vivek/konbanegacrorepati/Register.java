package com.vivek.konbanegacrorepati;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {



    Button submit ,back;
    ImageButton backButton;
    EditText firstName,lastName,mobileNumber,email,age,password;
    String TfirstName,TlastName,TmobileNumber,Temail,Tage,Tpassword,Tgender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       findid();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();

                if(validateInputs()){
                    saveDetails();
                    showDetailsSavedDialog();

                }
                }
            });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }

    private void getText() {
        TfirstName=firstName.getText().toString();
        TlastName=lastName.getText().toString();
        TmobileNumber=mobileNumber.getText().toString();
        Temail=email.getText().toString();
        Tage=age.getText().toString();
        Tpassword=password.getText().toString();

    }

    private void findid() {
        TextInputLayout firstNameLayout = findViewById(R.id.TLay);
        TextInputLayout lastNameLayout = findViewById(R.id.TLay1);
        TextInputLayout mobileNumberLayout = findViewById(R.id.TLay4);
        TextInputLayout ageLayout = findViewById(R.id.TLay3new);
        TextInputLayout emailLayout = findViewById(R.id.TLayEmail);
        TextInputLayout passwordLayout = findViewById(R.id.passwordI);

        // Find EditText fields inside TextInputLayouts
        firstName = firstNameLayout.findViewById(R.id.firstName);
        lastName = lastNameLayout.findViewById(R.id.lastName);
        mobileNumber = mobileNumberLayout.findViewById(R.id.mobileNumber);
        age = ageLayout.findViewById(R.id.age);
        email = emailLayout.findViewById(R.id.email);
        password = passwordLayout.findViewById(R.id.password);

        submit = findViewById(R.id.Submit);
            back = findViewById(R.id.back); // Assuming backButton is an ImageButton


    }


    private boolean validateInputs() {
        // Validate First Name
        String firstNameText = firstName.getText().toString().trim();
        if (firstNameText.isEmpty()) {
            firstName.setError("First Name is required");
            firstName.requestFocus();
            return false;
        }

        // Validate Last Name
        String lastNameText = lastName.getText().toString().trim();
        if (lastNameText.isEmpty()) {
            lastName.setError("Last Name is required");
            lastName.requestFocus();
            return false;
        }

        // Validate Mobile Number
        String mobileNumberText = mobileNumber.getText().toString().trim();
        if (mobileNumberText.isEmpty()) {
            mobileNumber.setError("Mobile Number is required");
            mobileNumber.requestFocus();
            return false;
        } else if (!isValidMobileNumber(mobileNumberText)) {
            mobileNumber.setError("Invalid Mobile Number");
            mobileNumber.requestFocus();
            return false;
        }
        // Add more validations for mobile number (e.g., length, format) as needed

        // Validate Email
        String emailText = email.getText().toString().trim();
        if (emailText.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }
        // Add more validations for email (e.g., format) as needed

        // Validate Age
        String ageText = age.getText().toString().trim();
        if (ageText.isEmpty()) {
            age.setError("Age is required");
            age.requestFocus();
            return false;
        }
        // Add more validations for age (e.g., minimum and maximum age) as needed

        // Validate Password
        String passwordText = password.getText().toString().trim();
        if (passwordText.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }
        // Add more validations for password (e.g., minimum length, strength) as needed

        // Check Gender selection


        // All validations passed
        return true;
    }

    private boolean isValidMobileNumber(String mobileNumberText) {
        if (mobileNumber.length() != 10) {
            return false;
        }
        // Validate format (you can add more specific format checks if needed)
        return true;

    }

    public void saveDetails(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://"+IpAddress.ipAddress+"/php%20api/KBC/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService=retrofit.create(ApiService.class);

        Call<ResponseAdmin> user=apiService.User
                (TfirstName,TlastName,TmobileNumber
                        ,Temail,Tage,Tpassword,1,1,1,1,1);
        user.enqueue(new Callback<ResponseAdmin>() {
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
                Log.d(TAG, errorMessage);
            }
        });
    }
    private void showDetailsSavedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Details Saved")
                .setMessage("Details saved successfully.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
}