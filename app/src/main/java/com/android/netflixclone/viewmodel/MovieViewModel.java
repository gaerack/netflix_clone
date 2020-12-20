package com.android.netflixclone.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.MovieRepo;
import com.android.netflixclone.model.Movie;

public class MovieViewModel extends ViewModel {

    MutableLiveData<Movie> liveData;

    public void init(Context context, String movieID)
    {
        if(liveData != null)
            return;

        liveData = MovieRepo.getInstance(context).getMovie(movieID);
    }

    public LiveData<Movie> getMovie()
    {
        return liveData;
    }
}
