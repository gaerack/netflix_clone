package com.android.netflixclone.model;

import java.util.List;

public class Movie {

    Long country;
    String desc;
    List<String> genre;
    Long length;
    float rating;
    String title;
    String trailer_url;
    Long year;

    public Movie(Long country, String desc, List<String> genre, Long length, float rating, String title, String trailer_url, Long year)
    {
        this.country = country;
        this.desc = desc;
        this.genre = genre;
        this.length = length;
        this.rating = rating;
        this.title = title;
        this.trailer_url = trailer_url;
        this.year = year;
    }
}
