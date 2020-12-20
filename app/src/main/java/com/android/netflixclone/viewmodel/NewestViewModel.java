package com.android.netflixclone.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.Repo;
import com.android.netflixclone.model.Newest;

import java.util.ArrayList;

public class NewestViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Newest>> liveData;

    public void init(Context context)
    {
        if(liveData != null)
            return;

        liveData = Repo.getInstance(context).getNewests();
    }

    public LiveData<ArrayList<Newest>> getNewests()
    {
        return liveData;
    }
}
