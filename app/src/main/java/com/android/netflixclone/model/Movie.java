package com.android.netflixclone.model;

import java.util.List;

public class Movie {

    String country;
    String desc;
    //Object genre;
    String length;
    String rating;
    String title;
    String trailer_url;
    String year;

    public Movie() {}

    /*public Movie(Long country, String desc, Object genre, String length, Double rating, String title, String trailer_url, String year)
    {
        //this.country = country;
        this.desc = desc;
        //this.genre = genre;
        this.length = length;
        //this.rating = rating;
        this.title = title;
        this.trailer_url = trailer_url;
        this.year = year;
    }*/

    public Movie(String country, String desc, String length, String rating, String title, String trailer_url, String year)
    {
        this.country = country;
        this.desc = desc;
        this.length = length;
        this.rating = rating;
        this.title = title;
        this.trailer_url = trailer_url;
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public String getDesc() {
        return desc;
    }

    /*public List<String> getGenre() {
        return (List<String>)genre;
    }*/

    public String getLength() {
        return length+" min";
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public String getYear() {
        return year;
    }
}
