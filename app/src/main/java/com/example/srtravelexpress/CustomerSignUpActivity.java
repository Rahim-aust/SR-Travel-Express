package com.example.srtravelexpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerSignUpActivity extends AppCompatActivity {

    // create object of DatabaseReference class to access firebase's Realtime Database

    private static final String TAG = "CustomerSignUpActivity";
    private TextInputEditText Cfullname, Cphone, Cemail, /*Cusername,*/Cpassword, Cconpass;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button Csignin, Csignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);


        Cfullname = findViewById(R.id.Customer_signup_fullname);
        Cemail = findViewById(R.id.Customer_signup_email);
        Cphone = findViewById(R.id.Customer_signup_phone);
        Cpassword = findViewById(R.id.Customer_signup_password);
        Cconpass = findViewById(R.id.Customer_signup_conpassword);
        radioGroup = findViewById(R.id.RG_Gender);
        radioGroup.clearCheck();

        Csignin = findViewById(R.id.Customer_sign_in);
        Csignup = findViewById(R.id.Customer_sign_up);


        //sign up and create info for db
        Csignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // get data from Edittexts into String Variable
                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedGenderId);

                String fullnameTxt = Cfullname.getText().toString();
                String emailTxt = Cemail.getText().toString();
                String phoneTxt = Cphone.getText().toString();
                /*String usernameTxt = Cusername.getText().toString();*/
                String passwordTxt = Cpassword.getText().toString();
                String conpasswordTxt = Cconpass.getText().toString();
                String textGender;

                String phoneRegex = "^(?:\\+88|88)?(01[3-9]\\d{8})$";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(phoneRegex);
                mobileMatcher = mobilePattern.matcher(phoneTxt);

                // Check if user fill all the fields before sending data to firebase

                if (TextUtils.isEmpty(fullnameTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    Cfullname.setError("Full name is required!");
                    Cfullname.requestFocus();
                } else if (TextUtils.isEmpty(phoneTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    Cphone.setError("Phone number is required!");
                    Cphone.requestFocus();
                } else if (phoneTxt.length() != 11) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    Cphone.setError("Phone Number is required!");
                    Cphone.requestFocus();
                }else if (!mobileMatcher.find()) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    Cphone.setError("Phone Number is not valid!");
                    Cphone.requestFocus();
                } else if (TextUtils.isEmpty(emailTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    Cemail.setError("Email is required!");
                    Cemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    Cemail.setError("Valid email is required!");
                    Cemail.requestFocus();
                } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    radioButton.setError("Gender is required!");
                    radioButton.requestFocus();
                } else if (TextUtils.isEmpty(passwordTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    Cpassword.setError("Password is required!");
                    Cpassword.requestFocus();
                } else if (passwordTxt.length() < 6) {
                    Toast.makeText(CustomerSignUpActivity.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    Cpassword.setError("Password too weak!");
                    Cpassword.requestFocus();
                } else if (TextUtils.isEmpty(conpasswordTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    Cconpass.setError("Password confirmation is required!");
                    Cconpass.requestFocus();
                } else if (!passwordTxt.equals(conpasswordTxt)) {
                    Toast.makeText(CustomerSignUpActivity.this, "Password should be same", Toast.LENGTH_SHORT).show();
                    Cconpass.setError("Password confirmation is required!");
                    Cconpass.requestFocus();

                    Cpassword.clearComposingText();
                    Cconpass.clearComposingText();
                } else {
                    textGender = radioButton.getText().toString();
                    registerUser(fullnameTxt, emailTxt, phoneTxt, textGender, passwordTxt);
                }

            }
        });
        //already sign up
        Csignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerSignUpActivity.this, CustomerLoginActivity.class));
                finish();
            }
        });

    }

    //register user
    private void registerUser(String fullnameTxt, String emailTxt, String phoneTxt, String textGender, String passwordTxt) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(CustomerSignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(fullnameTxt, emailTxt, textGender, phoneTxt);
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(CustomerSignUpActivity.this, "User Register Succesfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CustomerSignUpActivity.this, CustomerBookingActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CustomerSignUpActivity.this, "User Registered failed.Please try again.", Toast.LENGTH_SHORT).show();

                            }

                            //firebaseUser.sendEmailVerification();

                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Cemail.setError("Your mail is invalid or already in use. Kindly re-enter");
                        Cemail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Cemail.setError("User is already registered with this email. use another email.");
                        Cemail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(CustomerSignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}