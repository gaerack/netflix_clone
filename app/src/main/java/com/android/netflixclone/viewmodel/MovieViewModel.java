package com.android.netflixclone.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.model.Movie;
import com.google.firebase.firestore.FirebaseFirestore;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<Movie> mutableLiveData;
    private static final String TAG = "MovieViewModel";

    public MovieViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Movie> getMovie() {
        return mutableLiveData;
    }

    public void fetchMovie(String movieID)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies").document(movieID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    mutableLiveData.postValue(documentSnapshot.toObject(Movie.class));
                    Log.e(TAG, "onSuccess: added");
                    Log.e(TAG, "--------------------------------------------------------------");
                })
                .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
    }
}
