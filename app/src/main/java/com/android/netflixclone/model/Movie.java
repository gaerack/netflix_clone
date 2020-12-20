package com.android.netflixclone.model;

public class Movie {

    Long country;
    String desc;
    //Object genre;
    String length;
    //Double rating;
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

    public Movie(Long country, String desc, String length, String title, String trailer_url, String year)
    {
        this.country = country;
        this.desc = desc;
        this.length = length;
        this.title = title;
        this.trailer_url = trailer_url;
        this.year = year;
    }

    public String getCountry() {
        return convertContryCodeToString(Integer.parseInt(String.valueOf(country)));
    }

    public String getDesc() {
        return desc;
    }

    /*public Object getGenre() {
        return genre;
    }*/

    public String getLength() {
        return length+" min";
    }

    /*public Double getRating() {
        return rating;
    }*/

    public String getTitle() {
        return title;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public String getYear() {
        return year;
    }

    private String convertContryCodeToString(int countryCode)
    {
        String countryString = "";

        switch(countryCode)
        {
            case 1:
                countryString = "USA";
                break;
            case 81:
                countryString = "Japan";
                break;
            case 82:
                countryString = "Korea";
                break;
        }

        return countryString;
    }
}
