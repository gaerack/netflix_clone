package com.android.netflixclone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.netflixclone.adapter.ScreenshotsSliderAdapter;
import com.android.netflixclone.model.Movie;
import com.android.netflixclone.viewmodel.MovieViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnDataAdded {

    /* Movie Info */
    private Uri repImageURL;
    private ImageView ivDetailRepImage;
    private ImageButton ibDetailPlay;
    private TextView tvDetailTitle;
    private TextView tvDetailGenre;
    private RatingBar rbRating;
    private TextView tvDetailYear;
    private TextView tvDetailCountry;
    private TextView tvDetailLength;
    private TextView tvDetailDesc;

    /* Screenshots */
    private ViewPager2 viewPagerScreenshotsSlider;
    private final static String STORAGE_REF_PREFIX = "gs://netflix-clone-22844.appspot.com/movies/";

    private static final String TAG = "Detail";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        hideActionBar();

        ImageButton ibDetailBack = findViewById(R.id.ib_detail_back);
        ibDetailBack.setOnClickListener(view -> finish());

        /* Intent */
        Intent intent = getIntent();
        String movieID = intent.getExtras().getString("movieID");
        repImageURL = (Uri) intent.getExtras().get("repImageURL");

        /* Movie Info */
        MovieViewModel movieViewModel = new ViewModelProvider(DetailActivity.this).get(MovieViewModel.class);
        movieViewModel.init(DetailActivity.this, movieID);
        movieViewModel.setMutableLiveDataToViewModel(this);
        movieViewModel.getMutableLiveDataFromViewModel().observe(this, movie -> {
            Log.e(TAG, "movie: "+movie.getDesc());
            setMovieInfo(movie);
        });

        ivDetailRepImage = findViewById(R.id.iv_detail_rep_image);
        ivDetailRepImage.setImageResource(R.drawable.newest_placeholder);

        ibDetailPlay = findViewById(R.id.ib_detail_play);

        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetailTitle.setText(R.string.hs_detail_title);

        tvDetailGenre = findViewById(R.id.tv_detail_genre);
        tvDetailGenre.setText(R.string.hs_detail_genre);

        rbRating = findViewById(R.id.rb_rating);
        rbRating.setRating(0);

        tvDetailYear = findViewById(R.id.tv_detail_year);
        tvDetailYear.setText(R.string.hs_detail_year);

        tvDetailCountry = findViewById(R.id.tv_detail_country);
        tvDetailCountry.setText(R.string.hs_detail_country);

        tvDetailLength = findViewById(R.id.tv_detail_length);
        tvDetailLength.setText(R.string.hs_detail_length);

        tvDetailDesc = findViewById(R.id.tv_detail_desc);
        tvDetailDesc.setText(R.string.tv_detail_desc);

        /* Screenshots Slider */
        viewPagerScreenshotsSlider = findViewById(R.id.vp_screenshots);
        initScreenshotsSlider(movieID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emptyMovieIDToSharedPref();
        SharedPreferences sharedPref = this.getSharedPreferences("movieID", Context.MODE_PRIVATE);
        Log.e(TAG, "MovieIDSharedPref: "+sharedPref.getString("movieID", ""));
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

    // slide the view from below itself to the current position
    public void slideIn(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                view.getWidth(),                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideOut(View view){
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                view.getWidth(),                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    /* Movie Info */
    private void emptyMovieIDToSharedPref()
    {
        SharedPreferences sharedPref = this.getSharedPreferences("movieID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("movieID", "");
        editor.apply();
    }

    @Override
    public void added()
    {
        Log.e(TAG, "added on DetailActivity");
    }

    private void setMovieInfo(Movie movie)
    {
        List<String> genreList;
        String mergedGenre = "";
        Glide.with(this).load(repImageURL).into(ivDetailRepImage);
        ibDetailPlay.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailer_url())));
        });
        tvDetailTitle.setText(movie.getTitle());

        tvDetailYear.setText(movie.getYear());
        tvDetailCountry.setText(movie.getCountry());
        tvDetailLength.setText(movie.getLength());
        tvDetailDesc.setText(movie.getDesc());

        if(movie.getDesc() != null)
        {
            rbRating.setRating(Float.parseFloat(movie.getRating()));

            genreList = (List<String>)movie.getGenre();

            for(String genre : genreList)
                mergedGenre += genre+"  ";

            tvDetailGenre.setText(mergedGenre);
        }
    }

    /* Screenshots Slider */
    private void initScreenshotsSlider(String movieID)
    {
        float pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pagerWidth = getResources().getDimensionPixelOffset(R.dimen.screenshotPagerWidth);
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float offsetPx = screenWidth - pageMarginPx - pagerWidth;
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();

        ScreenshotsSliderAdapter screenshotsSliderAdapter = new ScreenshotsSliderAdapter(getScreenshots(movieID));
        viewPagerScreenshotsSlider.setAdapter(screenshotsSliderAdapter);
        viewPagerScreenshotsSlider.setClipToPadding(false);
        viewPagerScreenshotsSlider.setClipChildren(false);
        viewPagerScreenshotsSlider.setOffscreenPageLimit(3);
        viewPagerScreenshotsSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPagerScreenshotsSlider.setCurrentItem(1);

        compositePageTransformer.addTransformer((page, position) -> page.setTranslationX(position * -offsetPx));

        viewPagerScreenshotsSlider.setPageTransformer(compositePageTransformer);
    }

    private List<String> getScreenshots(String movieID)
    {
        List<String> screenshotURLs = new ArrayList<>();

        for(int i = 0; i < 3; i++)
            screenshotURLs.add(STORAGE_REF_PREFIX+movieID+"/"+movieID+"_"+i+".jpg");

        return screenshotURLs;
    }
}