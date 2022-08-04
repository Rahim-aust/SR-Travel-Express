package com.example.srtravelexpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerForgetPasswordActivity extends AppCompatActivity {
    // Declaration
    Toolbar toolbar;
    private Button Cpreset;
    private TextInputEditText resetemail;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forget_password);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("        Reset Password");

        resetemail = findViewById(R.id.Customer_reset_email);
        Cpreset = findViewById(R.id.pass_reset);


        //Password reset action and check validation
        Cpreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = resetemail.getText().toString();
                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(CustomerForgetPasswordActivity.this, "Email does not exist,please enter your registered email", Toast.LENGTH_SHORT).show();
                    resetemail.setError("Email is required!");
                    resetemail.requestFocus();

                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(CustomerForgetPasswordActivity.this, "Enter valid email please.", Toast.LENGTH_SHORT).show();
                    resetemail.setError("Valid email is required!");
                    resetemail.requestFocus();
                }else
                {
                    resetPassword(email);
                }
            }
        });
    }

    //reset class
    private void resetPassword(String email) {

        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CustomerForgetPasswordActivity.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CustomerForgetPasswordActivity.this, CustomerLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(CustomerForgetPasswordActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}