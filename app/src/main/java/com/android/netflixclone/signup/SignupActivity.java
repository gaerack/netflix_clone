package com.android.netflixclone.signup;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.netflixclone.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* Email */
        EditText etEmail = findViewById(R.id.et_signup_email);
        String email = isEmailValid(etEmail.getText().toString()) ? etEmail.getText().toString() : "";

        /* Password */
        EditText etPassword = findViewById(R.id.et_signup_password);
        String password = "";

        /* Signup Button */
        Button btnSignup = findViewById(R.id.btn_signup_signup);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(view -> mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("Signup", "createUserWithEmail:success");
            } else {
                // If sign in fails, display a message to the user.
                Log.w("Signup", "createUserWithEmail:failure", task.getException());
                Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}