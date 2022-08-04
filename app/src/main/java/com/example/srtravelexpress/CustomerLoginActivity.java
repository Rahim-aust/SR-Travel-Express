package com.example.srtravelexpress;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class CustomerLoginActivity extends AppCompatActivity {

    //declaration
    private TextInputEditText Cemail, Cpassword;
    private Button Csignin, Csignup, Cfpass;
    private FirebaseAuth authProfile;
    private static final  String TAG = "CustomerLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        Cemail = findViewById(R.id.Customer_login_username);
        Cpassword = findViewById(R.id.Customer_login_password);
        Cfpass = findViewById(R.id.Customer_forget_pass);
        Csignin = findViewById(R.id.Customer_sign_in);
        Csignup = findViewById(R.id.Customer_sign_up);

        authProfile = FirebaseAuth.getInstance();

        //sign in to go home or booking intent
        Csignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textEmail = Cemail.getText().toString();
                String textPassword = Cpassword.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(CustomerLoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    Cemail.setError("Email is required!");
                    Cemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(CustomerLoginActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    Cemail.setError("Valid email is required!");
                    Cemail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(CustomerLoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    Cpassword.setError("Password is required!");
                    Cpassword.requestFocus();
                } else {
                    loginuser(textEmail, textPassword);
                }




            }
        });


        //sign up layout
        Csignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open customer signup activity
                Intent intent = new Intent(getApplicationContext(), CustomerSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //forgot pass
        Cfpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open customer signup activity
                Intent intent = new Intent(getApplicationContext(), CustomerForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    //log in from db
    private void loginuser(String textEmail, String textPassword) {
        authProfile.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerLoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                    startActivity(new Intent(CustomerLoginActivity.this,CustomerBookingActivity.class));
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Cemail.setError("User does not exists or is no longer valid. Please register again");
                        Cemail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Cemail.setError("Invalid credentials. Kindly, check and re-enter");
                        Cemail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(CustomerLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    //User Already logged in
    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(CustomerLoginActivity.this, "Already logged In!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(CustomerLoginActivity.this,CustomerBookingActivity.class));
            finish();

        } else
        {
            Toast.makeText(CustomerLoginActivity.this, "You can log in now", Toast.LENGTH_SHORT).show();

        }
    }
}