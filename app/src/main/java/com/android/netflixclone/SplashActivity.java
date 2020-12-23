package com.android.netflixclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(getSupportActionBar() != null) getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoLogin();
    }

    private void autoLogin()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = (user != null) ? new Intent(this, MainActivity.class) : new Intent(this, OnboardingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    /*private void logout()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, OnboardingActivity.class);

        mAuth.signOut();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }*/
}