package com.android.netflixclone.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.repo.MovieRepo;
import com.android.netflixclone.model.Movie;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<Movie> mutableLiveData;

    public void init(Context context, String movieID)
    {
        MovieRepo.getInstance(context).setLiveData(context, movieID);
    }

    public void setMutableLiveDataToViewModel(Context context)
    {
        mutableLiveData = MovieRepo.getInstance(context).getLiveData();
    }

    public LiveData<Movie> getMutableLiveDataFromViewModel()
    {
        return mutableLiveData;
    }
}
