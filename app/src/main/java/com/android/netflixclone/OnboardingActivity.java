package com.android.netflixclone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.netflixclone.adapter.OnboardingSliderAdapter;
import com.android.netflixclone.adapter.OnboardingSliderItem;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        hideActionBar();

        /* Login Button */
        Button loginButton = findViewById(R.id.btn_onboarding_login);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        /* Onboarding Slider */
        ViewPager2 viewPagerOnboardingSlider = findViewById(R.id.vp_onboarding);

        List<OnboardingSliderItem> onboardingSliderItems = new ArrayList<>();
        onboardingSliderItems.add(new OnboardingSliderItem(R.drawable.onboarding_1, getResources().getString(R.string.tv_onboarding_title_0), getResources().getString(R.string.tv_onboarding_desc_0)));
        onboardingSliderItems.add(new OnboardingSliderItem(R.drawable.onboarding_2, getResources().getString(R.string.tv_onboarding_title_1), getResources().getString(R.string.tv_onboarding_desc_1)));
        onboardingSliderItems.add(new OnboardingSliderItem(R.drawable.onboarding_0, getResources().getString(R.string.tv_onboarding_title_2), getResources().getString(R.string.tv_onboarding_desc_2)));

        viewPagerOnboardingSlider.setAdapter(new OnboardingSliderAdapter(onboardingSliderItems));
        viewPagerOnboardingSlider.setClipToPadding(false);
        viewPagerOnboardingSlider.setClipChildren(false);
        viewPagerOnboardingSlider.setOffscreenPageLimit(1);
        viewPagerOnboardingSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        /* Onboarding Slider Indicator */
        CircleIndicator3 indicator = findViewById(R.id.ic_for_vp_onboarding);
        indicator.setViewPager(viewPagerOnboardingSlider);

        /* Signup Button */
        AppCompatButton signupButton = findViewById(R.id.btn_onboarding_signup);
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignupActivity.class);
            this.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}