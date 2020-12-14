package com.android.netflixclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Newest Slider */
    private ViewPager2 viewPagerNewestSlider;
    private Handler newestSliderHandler = new Handler();

    private ViewPager2 viewPagerMyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Newest Slider */
        viewPagerNewestSlider = findViewById(R.id.vp_newest_slider);

        // Here, i'm preparing list of images from drawable
        // You can get it from API as well
        List<NewestSliderItem> newestSliderItems = new ArrayList<>();
        newestSliderItems.add(new NewestSliderItem(R.drawable.spiderman));
        newestSliderItems.add(new NewestSliderItem(R.drawable.nutcracker));
        newestSliderItems.add(new NewestSliderItem(R.drawable.toystory));

        viewPagerNewestSlider.setAdapter(new NewestSliderAdapter(newestSliderItems, viewPagerNewestSlider));
        viewPagerNewestSlider.setClipToPadding(false);
        viewPagerNewestSlider.setClipChildren(false);
        viewPagerNewestSlider.setOffscreenPageLimit(3);
        viewPagerNewestSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer cptNewest = new CompositePageTransformer();
        cptNewest.addTransformer(new MarginPageTransformer(40));
        cptNewest.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPagerNewestSlider.setPageTransformer(cptNewest);
        viewPagerNewestSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                newestSliderHandler.removeCallbacks(sliderRunnable);
                newestSliderHandler.postDelayed(sliderRunnable, 2000);
            }
        });

        /* My List Slider */
        viewPagerMyList = findViewById(R.id.vp_mylist);

        List<CardSliderItem> cardSliderItems = new ArrayList<>();
        cardSliderItems.add(new CardSliderItem(R.drawable.shigatsu_wa_kimi_no_uso));
        cardSliderItems.add(new CardSliderItem(R.drawable.seven_deadly_sins));
        cardSliderItems.add(new CardSliderItem(R.drawable.cobra_kai));
        cardSliderItems.add(new CardSliderItem(R.drawable.erased));
        cardSliderItems.add(new CardSliderItem(R.drawable.plastic_memories));

        viewPagerMyList.setAdapter(new CardSliderAdapter(cardSliderItems));
        viewPagerMyList.setClipToPadding(false);
        viewPagerMyList.setClipChildren(false);
        viewPagerMyList.setOffscreenPageLimit(5);

        CompositePageTransformer cptMyList = new CompositePageTransformer();
        cptMyList.addTransformer(new MarginPageTransformer(10));

        viewPagerMyList.setPageTransformer(cptMyList);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPagerNewestSlider.setCurrentItem(viewPagerNewestSlider.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        newestSliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newestSliderHandler.postDelayed(sliderRunnable, 2000);
    }
}