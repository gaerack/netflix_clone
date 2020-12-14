package com.android.netflixclone;

public class NewestSliderItem
{
    // Here you can use string variable to store url
    // If you want to load image from the internet
    private int image;

    NewestSliderItem(int image)
    {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
