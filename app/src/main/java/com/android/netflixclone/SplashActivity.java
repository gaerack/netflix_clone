package com.android.netflixclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoLogin();
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void autoLogin()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;

        if(user != null)
        {
            storeUIDToSharedPref(user.getUid());
            intent = new Intent(this, MainActivity.class);
        }
        else
        {
            intent = new Intent(this, OnboardingActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void logout()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, OnboardingActivity.class);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        mAuth.signOut();

        editor.putString("uid", "");
        editor.apply();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private String getUIDFromSharedPref()
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("uid", "");
    }

    private void storeUIDToSharedPref(String uid)
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        if(getUIDFromSharedPref().equals(""))
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("uid", uid);
            editor.apply();
        }
    }
}