package com.android.netflixclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Newest Slider */
    private ViewPager2 viewPagerNewestSlider;
    final Handler newestSliderHandler = new Handler();

    /* My List Slider */
    private ViewPager2 viewPagerMyListSlider;

    /* Popular Slider */
    private ViewPager2 viewPagerPopularSlider;

    /* Firebase */
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideActionBar();

        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth

        /* Newest Slider */
        viewPagerNewestSlider = findViewById(R.id.vp_newest_slider);
        List<NewestSliderItem> newestSliderItems = new ArrayList<>();
        CollectionReference colRef = db.collection("movies");
        colRef.orderBy("year", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                for (QueryDocumentSnapshot document : task.getResult())
                {
                    Log.d("Movies", document.getString("title")); // D/Movies
                    newestSliderItems.add(new NewestSliderItem(document.getId(), document.getString("title")));
                }
                initNewestSlider(newestSliderItems);
            }
            else
            {
                Log.d("Movies", "Error getting documents: ", task.getException());
            }
        });

        //newestSliderItems.add(new NewestSliderItem(R.drawable.spiderman_far_from_home, "SPIDER-MAN: FAR FROM HOME"));
        //newestSliderItems.add(new NewestSliderItem(R.drawable.nutcracker, "THE NUTCRACKER AND THE FOUR REALMS"));
        //newestSliderItems.add(new NewestSliderItem(R.drawable.toy_story_4, "TOY STORY 4"));

        /*viewPagerNewestSlider.setAdapter(new NewestSliderAdapter(newestSliderItems, viewPagerNewestSlider));
        viewPagerNewestSlider.setClipToPadding(false);
        viewPagerNewestSlider.setClipChildren(false);
        viewPagerNewestSlider.setOffscreenPageLimit(3);
        viewPagerNewestSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        float newestViewPagerWidth = getResources().getDimensionPixelOffset(R.dimen.newestPagerWidth);
        float newestOffsetPx = SCREEN_WIDTH - PAGE_MARGIN_PX - newestViewPagerWidth;

        /*CompositePageTransformer cptNewest = new CompositePageTransformer();
        cptNewest.addTransformer((page, position) -> page.setTranslationX(position * -newestOffsetPx));
        cptNewest.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPagerNewestSlider.setPageTransformer(cptNewest);
        viewPagerNewestSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                newestSliderHandler.removeCallbacks(sliderRunnable);
                newestSliderHandler.postDelayed(sliderRunnable, 2000);
            }
        });*/

        /* My List Slider */
        viewPagerMyListSlider = findViewById(R.id.vp_mylist);

        List<CardSliderItem> myListSliderItems = new ArrayList<>();
        myListSliderItems.add(new CardSliderItem(R.drawable.shigatsu_wa_kimi_no_uso));
        myListSliderItems.add(new CardSliderItem(R.drawable.seven_deadly_sins));
        myListSliderItems.add(new CardSliderItem(R.drawable.cobra_kai));
        myListSliderItems.add(new CardSliderItem(R.drawable.erased));
        myListSliderItems.add(new CardSliderItem(R.drawable.plastic_memories));

        viewPagerMyListSlider.setAdapter(new CardSliderAdapter(myListSliderItems));
        viewPagerMyListSlider.setClipToPadding(false);
        viewPagerMyListSlider.setClipChildren(false);
        viewPagerMyListSlider.setOffscreenPageLimit(3);
        viewPagerMyListSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPagerMyListSlider.setCurrentItem(1);

        float PAGE_MARGIN_PX = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        float myListViewPagerWidth = getResources().getDimensionPixelOffset(R.dimen.pagerWidth);
        float myListOffsetPx = SCREEN_WIDTH - PAGE_MARGIN_PX - myListViewPagerWidth;

        CompositePageTransformer cptMyList = new CompositePageTransformer();
        cptMyList.addTransformer((page, position) -> page.setTranslationX(position * -myListOffsetPx));

        viewPagerMyListSlider.setPageTransformer(cptMyList);

        /* Popular Slider */
        viewPagerPopularSlider = findViewById(R.id.vp_popular);

        List<CardSliderItem> popularSliderItems = new ArrayList<>();
        popularSliderItems.add(new CardSliderItem(R.drawable.stranger_things));
        popularSliderItems.add(new CardSliderItem(R.drawable.endgame));
        popularSliderItems.add(new CardSliderItem(R.drawable.oitnb));
        popularSliderItems.add(new CardSliderItem(R.drawable.daredevil));

        viewPagerPopularSlider.setAdapter(new CardSliderAdapter(popularSliderItems));
        viewPagerPopularSlider.setClipToPadding(false);
        viewPagerPopularSlider.setClipChildren(false);
        viewPagerPopularSlider.setOffscreenPageLimit(3);
        viewPagerPopularSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPagerPopularSlider.setCurrentItem(1);

        CompositePageTransformer cptPopular = new CompositePageTransformer();
        cptPopular.addTransformer((page, position) -> page.setTranslationX(position * -myListOffsetPx));

        viewPagerPopularSlider.setPageTransformer(cptPopular);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.signInAnonymously();
    }

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

    /* Newest Slider */
    private void initNewestSlider(List<NewestSliderItem> fetchedNewestSliderItems)
    {
        CompositePageTransformer cptNewest = new CompositePageTransformer();
        float PAGE_MARGIN_PX = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        float newestViewPagerWidth = getResources().getDimensionPixelOffset(R.dimen.newestPagerWidth);
        float newestOffsetPx = SCREEN_WIDTH - PAGE_MARGIN_PX - newestViewPagerWidth;

        viewPagerNewestSlider.setAdapter(new NewestSliderAdapter(fetchedNewestSliderItems, viewPagerNewestSlider));
        viewPagerNewestSlider.setClipToPadding(false);
        viewPagerNewestSlider.setClipChildren(false);
        viewPagerNewestSlider.setOffscreenPageLimit(3);
        viewPagerNewestSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        cptNewest.addTransformer((page, position) -> page.setTranslationX(position * -newestOffsetPx));
        cptNewest.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
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
    }

    final Runnable sliderRunnable = () -> viewPagerNewestSlider.setCurrentItem(viewPagerNewestSlider.getCurrentItem() + 1);

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }
}