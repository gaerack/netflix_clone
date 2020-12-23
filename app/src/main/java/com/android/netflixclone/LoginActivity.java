package com.android.netflixclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private String validEmail = "";
    private String validPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        /* Back Button */
        ImageButton ibBackButton = findViewById(R.id.ib_login_back);
        ibBackButton.setOnClickListener(view -> onBackPressed());

        /* Email */
        EditText etEmail = findViewById(R.id.et_login_email);

        /* Password */
        EditText etPassword = findViewById(R.id.et_login_password);

        /* ProgressBar */
        ProgressBar pbLoading = findViewById(R.id.pb_login_loading);
        pbLoading.setVisibility(View.GONE);

        /* Login Button */
        Button loginButton = findViewById(R.id.btn_login_login);
        loginButton.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            Intent intent = new Intent(this, MainActivity.class);
            validEmail = etEmail.getText().toString();
            validPassword = etPassword.getText().toString();
            pbLoading.setVisibility(View.VISIBLE);

            if(isEmailValid(validEmail) && isPasswordValid(validPassword))
            {
                mAuth.signInWithEmailAndPassword(validEmail, validPassword).addOnSuccessListener(authResult -> {
                    pbLoading.setVisibility(View.GONE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                });
            }
            else
            {
                if(isEmailValid(validEmail)) Toast.makeText(LoginActivity.this, "올바르지 않은 이메일 양식입니다.", Toast.LENGTH_SHORT).show();
                if(isPasswordValid(validPassword)) Toast.makeText(LoginActivity.this, "올바르지 않은 비밀번호 양식입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}