package com.android.netflixclone;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.android.netflixclone.model.Movie;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieRepo {

    private static MovieRepo instance;
    private Movie movie = new Movie();
    private final MutableLiveData<Movie> liveData = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MovieRepo";

    private static OnDataAdded onDataAdded;

    public static MovieRepo getInstance(Context context)
    {
        if(instance == null)
            instance = new MovieRepo();

        onDataAdded = (OnDataAdded) context;
        return instance;
    }

    public MutableLiveData<Movie> getMovie() // String movieID
    {
        //fetchMovie(movieID);

        //MutableLiveData<Movie> data = new MutableLiveData<>();
        //data.setValue(movie);
        return liveData;
    }

    public void setMovie(Context context, String movieID)
    {
        fetchMovie(movieID);
        liveData.setValue(movie);
        Log.e(TAG, "MovieIDSharedPref: "+getMovieIDFromSharedPref(context));

        if(getMovieIDFromSharedPref(context).equals("")) // getMovieIDFromSharedPref(context) == "", liveData.getValue().getDesc() == null
        {
            Log.e(TAG, "movieID: "+movieID);
            Log.e(TAG, "setMovie: null");
            storeMovieIDToSharedPref(context, movieID);
            new android.os.Handler().postDelayed(
                    () -> setMovie(context, movieID),
                    300);
        }
        else
        {
            Log.e(TAG, "movieID(else): "+movieID);
            Log.e(TAG, "setMovie(else): "+liveData.getValue().getDesc());
        }
    }

    private void fetchMovie(String movieID)
    {
        db.collection("movies").document(movieID).get()
        .addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists())
            {
                //movie = documentSnapshot.toObject(Movie.class);
                movie = new Movie(
                        documentSnapshot.getString("country"),
                        documentSnapshot.getString("desc"),
                        documentSnapshot.getString("length"),
                        documentSnapshot.getString("rating"),
                        documentSnapshot.getString("title"),
                        documentSnapshot.getString("trailer_url"),
                        documentSnapshot.getString("year")
                );

                Log.e(TAG, "onSuccess: added");
                Log.e(TAG, "--------------------------------------------------------------");
                onDataAdded.added();
            }
        })
        .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
    }

    private void storeMovieIDToSharedPref(Context context, String curMovieID)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("movieID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("movieID", curMovieID);
        editor.apply();
    }

    private String getMovieIDFromSharedPref(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("movieID", Context.MODE_PRIVATE);
        return sharedPref.getString("movieID", "");
    }
}
