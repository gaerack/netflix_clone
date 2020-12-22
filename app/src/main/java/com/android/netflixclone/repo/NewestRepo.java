package com.android.netflixclone.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.netflixclone.OnDataAdded;
import com.android.netflixclone.model.Newest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NewestRepo {

    private static NewestRepo instance;
    private final ArrayList<Newest> arrayList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Repo";

    private static OnDataAdded onDataAdded;

    public static NewestRepo getInstance(Context context)
    {
        if(instance == null)
            instance = new NewestRepo();

        onDataAdded = (OnDataAdded) context;
        return instance;
    }

    public MutableLiveData<ArrayList<Newest>> getNewests()
    {
        fetchNewests();

        MutableLiveData<ArrayList<Newest>> data = new MutableLiveData<>();
        data.setValue(arrayList);
        return data;
    }

    private void fetchNewests()
    {
        db.collection("movies").get()
         .addOnSuccessListener(queryDocumentSnapshots -> {
             if(!queryDocumentSnapshots.isEmpty())
             {
                 List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                 for(DocumentSnapshot documentSnapshot : list)
                 {
                     //arrayList.add(documentSnapshot.toObject(Newest.class));
                     arrayList.add(new Newest(documentSnapshot.getId(), documentSnapshot.getString("title")));
                 }

                 Log.e(TAG, "onSuccess: added");
                 onDataAdded.added();
             }
         })
        .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
    }
}
