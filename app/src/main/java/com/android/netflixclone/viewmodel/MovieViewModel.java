package com.android.netflixclone.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.MovieRepo;
import com.android.netflixclone.model.Movie;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<Movie> liveData;
    //private MovieRepo movieRepo;
    private static final String TAG = "MovieViewModel";

    public void init(Context context, String movieID)
    {
        //if(liveData != null)
        //    return;

        //liveData = MovieRepo.getInstance(context).getMovie(movieID);
        MovieRepo.getInstance(context).setMovie(context, movieID);
    }

    public void setMovieToLiveData(Context context)
    {
        liveData = MovieRepo.getInstance(context).getMovie();
    }

    // Subscribe live data
    public LiveData<Movie> getMovieFromViewModel()
    {
        return liveData;
    }
}
