package com.android.netflixclone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends AppCompatActivity implements OnDataAdded {

    private static final String TAG = "Detail";

    /* Movie Info */
    private MovieViewModel movieViewModel;
    private String movieID;
    private Uri repImageURL;
    private ImageView ivDetailRepImage;
    private ImageButton ibDetailPlay;
    private TextView tvDetailTitle;
    private RatingBar rbRating;
    private TextView tvDetailYear;
    private TextView tvDetailCountry;
    private TextView tvDetailLength;
    private TextView tvDetailDesc;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        hideActionBar();

        ImageButton ibDetailBack = findViewById(R.id.ib_detail_back);
        ibDetailBack.setOnClickListener(view -> {
            finish();
        });

        /* Intent */
        Intent intent = getIntent();
        movieID = intent.getExtras().getString("movieID");
        repImageURL = (Uri) intent.getExtras().get("repImageURL");

        /* Movie Info */
        movieViewModel = new ViewModelProvider(DetailActivity.this).get(MovieViewModel.class);
        movieViewModel.init(DetailActivity.this, movieID);
        movieViewModel.setMovieToLiveData(this);
        movieViewModel.getMovieFromViewModel().observe(this, movie -> {
            Log.e(TAG, "movie: "+movie.getDesc());
            setMovieInfo(movie);
        });

        ivDetailRepImage = findViewById(R.id.iv_detail_rep_image);
        ivDetailRepImage.setImageResource(R.drawable.newest_placeholder);

        ibDetailPlay = findViewById(R.id.ib_detail_play);

        /*ibDetailPlay.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailer_url())));
        });*/

        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetailTitle.setText(R.string.hs_detail_title);

        /*TextView tvDetailGenre = findViewById(R.id.tv_detail_genre);
        String mergedGenre = "";

        for(String genre : movie.getGenre())
            mergedGenre += genre+"  ";

        tvDetailGenre.setText(mergedGenre);*/

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

        // Firebase
        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("movies").document(movieID);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot document = task.getResult();

                ibDetailPlay.setOnClickListener(view -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(document.getString("trailer_url"))));
                });

                List<String> genreArray = (List<String>)document.get("genre");
                String mergedGenre = "";
                for(String genre : genreArray)
                    mergedGenre += genre+"  ";

                rbRating.setRating(Float.parseFloat(String.valueOf(document.get("rating"))));

                tvDetailGenre.setText(mergedGenre);
                tvDetailYear.setText(String.valueOf(document.getLong("year")));
                tvDetailCountry.setText(convertContryCodeToString(Integer.parseInt(String.valueOf(document.getLong("country")))));
                tvDetailLength.setText(document.getLong("length")+" min");
                tvDetailDesc.setText(document.getString("desc"));
            }
            else
            {
                Log.d("Detail", "get failed with ", task.getException());
            }
        });*/

        /* Screenshots Slider */
        ViewPager2 viewPagerScreenshotsSlider = findViewById(R.id.vp_screenshots);

        List<Uri> screenshotsSliderUris = new ArrayList<>();

        // Firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference screenshotsRef = storage.getReference("movies").child(movieID);
        screenshotsRef.listAll()
        .addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems())
            {
                item.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    screenshotsSliderUris.add(uri);
                })
                .addOnCompleteListener(task -> {
                    if(task.isComplete() && (screenshotsSliderUris.size() == listResult.getItems().size()))
                        initScreenshotsSlider(screenshotsSliderUris.subList(0, screenshotsSliderUris.size() - 1), viewPagerScreenshotsSlider);
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeMovieIDToSharedPref();
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
    private void removeMovieIDToSharedPref()
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
        Glide.with(this).load(repImageURL).into(ivDetailRepImage);
        ibDetailPlay.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailer_url())));
        });
        tvDetailTitle.setText(movie.getTitle());
        //rbRating.setRating(Float.parseFloat(movie.getRating()));
        tvDetailYear.setText(movie.getYear());
        tvDetailCountry.setText(movie.getCountry());
        tvDetailLength.setText(movie.getLength());
        tvDetailDesc.setText(movie.getDesc());
    }

    /* Screenshots Slider */
    private void initScreenshotsSlider(List<Uri> fetchedScreenshotsSliderUris, ViewPager2 viewPager2)
    {
        float pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pagerWidth = getResources().getDimensionPixelOffset(R.dimen.screenshotPagerWidth);
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float offsetPx = screenWidth - pageMarginPx - pagerWidth;
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();

        viewPager2.setAdapter(new ScreenshotsSliderAdapter(fetchedScreenshotsSliderUris));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.setCurrentItem(1);

        compositePageTransformer.addTransformer((page, position) -> page.setTranslationX(position * -offsetPx));

        viewPager2.setPageTransformer(compositePageTransformer);
    }
}