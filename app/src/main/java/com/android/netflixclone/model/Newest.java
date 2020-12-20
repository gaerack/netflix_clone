package com.android.netflixclone.model;

public class Newest {

    String movieID;
    String storageRef;
    String title;
    final String STORAGE_REF_PREFIX = "gs://netflix-clone-22844.appspot.com/movies/";

    public Newest(String movieID, String title)
    {
        this.movieID = movieID;
        this.title = title;
        this.setStorageRef(this.movieID);
    }

    public String getTitle() {
        return title;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setStorageRef(String movieID) {
        this.storageRef = STORAGE_REF_PREFIX+movieID+"/"+movieID+".jpg";
    }

    public String getStorageRef() {
        return storageRef;
    }
}
