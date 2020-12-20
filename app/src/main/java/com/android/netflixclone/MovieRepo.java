package com.android.netflixclone;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.netflixclone.model.Movie;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieRepo {

    private static MovieRepo instance;
    private Movie movie = new Movie();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MovieRepo";

    private static OnDataAdded onDataAdded;

    public static MovieRepo getInstance(Context context)
    {
        if(instance == null)
            instance = new MovieRepo();

        onDataAdded = (OnDataAdded) context;
        return instance;
    }

    public MutableLiveData<Movie> getMovie(String movieID)
    {
        fetchMovie(movieID);

        MutableLiveData<Movie> data = new MutableLiveData<>();
        data.setValue(movie);
        return data;
    }

    private void fetchMovie(String movieID)
    {
        db.collection("movies").document(movieID).get()
        .addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists())
            {
                //movie = documentSnapshot.toObject(Movie.class);
                /*movie = new Movie(
                        documentSnapshot.getLong("country"),
                        documentSnapshot.getString("desc"),
                        documentSnapshot.get("genre"),
                        documentSnapshot.getString("length"),
                        documentSnapshot.getDouble("rating"),
                        documentSnapshot.getString("title"),
                        documentSnapshot.getString("trailer_url"),
                        documentSnapshot.getString("year")
                );*/

                movie = new Movie(
                        documentSnapshot.getString("desc"),
                        documentSnapshot.getString("length"),
                        documentSnapshot.getString("title"),
                        documentSnapshot.getString("trailer_url"),
                        documentSnapshot.getString("year")
                );

                Log.e(TAG, "onSuccess: added");
                onDataAdded.added();
            }
        })
        .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
    }
}
