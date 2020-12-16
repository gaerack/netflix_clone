package com.android.netflixclone.model;

import com.google.firebase.FirebaseApp;

public class Movies {

    private int country;
    private String[] genre;
    private int length;
    private String title;
    private String trailerURL;
    private int year;

    public Movies(int country, String[] genre, int length, String title, String trailerURL, int year) {
        this.country = country;
        this.genre = genre;
        this.length = length;
        this.title = title;
        this.trailerURL = trailerURL;
        this.year = year;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
