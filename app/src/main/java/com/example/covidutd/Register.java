package com.example.covidutd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mUserName, mEmail, mPassword1, mPassword2;
    Button mRegisterBtn;
    TextView mloginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = findViewById(R.id.userName);
        mEmail = findViewById(R.id.Email);
        mPassword1 = findViewById(R.id.Password1);
        mPassword2 = findViewById(R.id.Password2);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mloginBtn = findViewById(R.id.loginBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();
                String username = mUserName.getText().toString();

                if (mUserName.getText().toString().isEmpty() && mEmail.getText().toString().isEmpty() && mPassword1.getText().toString().isEmpty() && mPassword2.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    mUserName.setError("Username is required!");
                    mEmail.setError("Email is required!");
                    mPassword1.setError("Password is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }

                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(email)) {
                    mUserName.setError("Username is required!");
                    mEmail.setError("Email is required!");
                    return;
                }else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password1)) {
                    mUserName.setError("Username is required!");
                    mPassword1.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password2)) {
                    mUserName.setError("Username is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password1)) {
                    mEmail.setError("Email is required!");
                    mPassword1.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password2)) {
                    mEmail.setError("Email is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(password1) && TextUtils.isEmpty(password2)) {
                    mPassword1.setError("Password is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password1)) {
                    mUserName.setError("Username is required!");
                    mEmail.setError("Email is required!");
                    mPassword1.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password2)) {
                    mUserName.setError("Username is required!");
                    mEmail.setError("Email is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password1) && TextUtils.isEmpty(password2)) {
                    mUserName.setError("Username is required!");
                    mPassword1.setError("Password is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password1) && TextUtils.isEmpty(password2)) {
                    mEmail.setError("Email is required!");
                    mPassword1.setError("Password is required!");
                    mPassword2.setError("Password is required!");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    mUserName.setError("Username is required!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required!");
                    return;
                }

                if (password1.length() < 6) {
                    mPassword1.setError("Password must contain at least 6 characters!");
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    mPassword1.setError("Password is required!");
                    return;
                }

                if (TextUtils.isEmpty(password2)) {
                    mPassword2.setError("You need to confirm your password!");
                    return;
                }

                if (!password1.equals(password2)) {
                    mPassword2.setError("The passwords are different!");
                    mPassword1.setError("The passwords are different!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email was not sent" + e.getMessage());
                                }
                            });

                            Toast.makeText(Register.this, "User created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", username);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        alert(this);
    }

    public void alert(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}