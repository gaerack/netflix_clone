package com.android.netflixclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    /* Screenshots Slider */
    private ViewPager2 viewPagerScreenshotsSlider;
    private ImageButton imageButtonDetailBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /* My List Slider */
        viewPagerScreenshotsSlider = findViewById(R.id.vp_screenshots);
        imageButtonDetailBack = findViewById(R.id.ib_detail_back);
        imageButtonDetailBack.setOnClickListener(view -> {
            finish();
        });

        List<CardSliderItem> screenshotsSliderItems = new ArrayList<>();
        screenshotsSliderItems.add(new CardSliderItem(R.drawable.nutcracker_0));
        screenshotsSliderItems.add(new CardSliderItem(R.drawable.nutcracker_1));
        screenshotsSliderItems.add(new CardSliderItem(R.drawable.nutcracker_2));

        viewPagerScreenshotsSlider.setAdapter(new CardSliderAdapter(screenshotsSliderItems));
        viewPagerScreenshotsSlider.setClipToPadding(false);
        viewPagerScreenshotsSlider.setClipChildren(false);
        viewPagerScreenshotsSlider.setOffscreenPageLimit(2);
        viewPagerScreenshotsSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPagerScreenshotsSlider.setCurrentItem(1);

        float pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pagerWidth = getResources().getDimensionPixelOffset(R.dimen.screenshotPagerWidth);
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float offsetPx = screenWidth - pageMarginPx - pagerWidth;

        CompositePageTransformer cptScreenshots = new CompositePageTransformer();
        cptScreenshots.addTransformer((page, position) -> page.setTranslationX(position * -offsetPx));

        viewPagerScreenshotsSlider.setPageTransformer(cptScreenshots);
    }
}