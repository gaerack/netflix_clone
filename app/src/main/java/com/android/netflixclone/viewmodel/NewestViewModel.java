package com.android.netflixclone.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.netflixclone.model.Newest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewestViewModel extends ViewModel {

    private final MutableLiveData<List<Newest>> mutableLiveData;
    private final List<Newest> fetchedList = new ArrayList<>();
    private static final String TAG = "NewestViewModel";

    public NewestViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Newest>> getNewest() {
        return mutableLiveData;
    }

    public void fetchNewest()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot documentSnapshot : list)
                    {
                        //arrayList.add(documentSnapshot.toObject(Newest.class));
                        fetchedList.add(new Newest(documentSnapshot.getId(), documentSnapshot.getString("title")));
                    }

                    mutableLiveData.postValue(fetchedList);
                    Log.e(TAG, "onSuccess: added");
                    Log.e(TAG, "--------------------------------------------------------------");
                })
                .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
    }
}
