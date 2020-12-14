package com.android.netflixclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.slider.Slider;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class NewestSliderAdapter extends RecyclerView.Adapter<NewestSliderAdapter.NewestSliderViewHolder> {

    private List<NewestSliderItem> newestSliderItems;
    private ViewPager2 viewPager2;

    NewestSliderAdapter(List<NewestSliderItem> newestSliderItems, ViewPager2 viewPager2) {
        this.newestSliderItems = newestSliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public NewestSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewestSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.newest_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewestSliderViewHolder holder, int position) {
        holder.setImage(newestSliderItems.get(position));
        if(position == newestSliderItems.size() - 2)
        {
            viewPager2.post(runnable) ;
        }
    }

    @Override
    public int getItemCount() {
        return newestSliderItems.size();
    }

    class NewestSliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;

        NewestSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newest_slider_item);
        }

        void setImage(NewestSliderItem newestSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            imageView.setImageResource(newestSliderItem.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            newestSliderItems.addAll(newestSliderItems);
            notifyDataSetChanged();
        }
    };
}
