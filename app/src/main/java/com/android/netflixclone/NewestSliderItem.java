package com.android.netflixclone;

public class NewestSliderItem
{
    // Here you can use string variable to store url
    // If you want to load image from the internet
    final int image;
    final String title;

    NewestSliderItem(int image, String title)
    {
        this.image = image;
        this.title = title;
    }

    public int getImage()
    {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
