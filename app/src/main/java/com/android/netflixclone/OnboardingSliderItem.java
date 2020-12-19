package com.android.netflixclone;

public class OnboardingSliderItem {

    // Here you can use string variable to store url
    // If you want to load image from the internet
    int image;
    String title;
    String desc;

    public OnboardingSliderItem(int image, String title, String desc)
    {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
