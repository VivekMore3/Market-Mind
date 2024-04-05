package com.vivek.konbanegacrorepati;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    RadioGroup rgGender ;
    RadioButton rbMale,rbFemale,rbOther;
    Button submit ;
    ImageButton backButton;
    EditText firstName,lastName,mobileNumber,email,age,password;
    String TfirstName,TlastName,TmobileNumber,Temail,Tage,Tpassword,Tgender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       findid();

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button was clicked
                if (checkedId == R.id.Male) {
                    Tgender="male";
                } else if (checkedId == R.id.Female) {
                    Tgender="female";
                } else if (checkedId == R.id.Other) {
                    Tgender="other";
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,MainActivity.class));
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();

                if(validateInputs()){
                    saveDetails();

                }
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
        rgGender = findViewById(R.id.Gender);
        rbMale = findViewById(R.id.Male);
        rbFemale = findViewById(R.id.Female);
        rbOther = findViewById(R.id.Other);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        mobileNumber=findViewById(R.id.mobileNumber);
        email=findViewById(R.id.email);
        age=findViewById(R.id.age);
        password=findViewById(R.id.password);
        submit=findViewById(R.id.Submit);
        backButton=findViewById(R.id.backButton);
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
        if (rgGender.getCheckedRadioButtonId() == -1) {
            // No radio button is checked
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

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
    
}