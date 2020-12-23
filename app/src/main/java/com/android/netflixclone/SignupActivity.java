package com.android.netflixclone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private String validEmail = "";
    private String validPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        /* Back Button */
        ImageButton ibBackButton = findViewById(R.id.ib_signup_back);
        ibBackButton.setOnClickListener(view -> onBackPressed());

        /* Email */
        EditText etEmail = findViewById(R.id.et_signup_email);

        /* Password */
        EditText etPassword = findViewById(R.id.et_signup_password);

        /* Confirm Password */
        EditText etConfirmPassword = findViewById(R.id.et_signup_confirm_password);

        /* ProgressBar */
        ProgressBar pbLoading = findViewById(R.id.pb_signup_loading);
        pbLoading.setVisibility(View.GONE);

        /* Signup Button */
        Button btnSignup = findViewById(R.id.btn_signup_signup);
        btnSignup.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = db.collection("users");
            Map<String, Object> newUser = new HashMap<>();
            validEmail = etEmail.getText().toString();
            validPassword = (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) ? etConfirmPassword.getText().toString() : "";

            pbLoading.setVisibility(View.VISIBLE);

            if (isEmailValid(validEmail) && isPasswordValid(validPassword))
            {
                mAuth.createUserWithEmailAndPassword(validEmail, validPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        newUser.put("email", validEmail);
                        newUser.put("uid", mAuth.getCurrentUser().getUid());
                        newUser.put("created_at", FieldValue.serverTimestamp());
                        newUser.put("password", sha256(validPassword));
                        collectionReference.document(mAuth.getCurrentUser().getUid()).set(newUser).addOnSuccessListener(aVoid -> {
                            pbLoading.setVisibility(View.GONE);
                            finish();
                        });
                    }
                    else
                    {
                        // If sign in fails, display a message to the user.
                        Log.w("Signup", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                if(isEmailValid(validEmail)) Toast.makeText(SignupActivity.this, "올바르지 않은 이메일 양식입니다.", Toast.LENGTH_SHORT).show();
                if(isPasswordValid(validPassword)) Toast.makeText(SignupActivity.this, "비밀번호는 6자리 이상으로 설정하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}