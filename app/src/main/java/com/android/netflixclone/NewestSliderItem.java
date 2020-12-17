package com.android.netflixclone;

public class NewestSliderItem
{
    // Here you can use string variable to store url
    // If you want to load image from the internet
    //int image;
    String movieID;
    String storageRef;
    String title;
    final String STORAGE_REF_PREFIX = "gs://netflix-clone-22844.appspot.com/movies/";

    public NewestSliderItem(/*int image, */String movieID, String title)
    {
        //this.image = image;
        this.movieID = movieID;
        this.title = title;
        this.setStorageRef(this.movieID);
    }

    /*public int getImage()
    {
        return image;
    }*/

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
