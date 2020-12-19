package com.android.netflixclone;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ScreenshotsSliderAdapter extends RecyclerView.Adapter<ScreenshotsSliderAdapter.ScreenshotsSliderViewHolder>{

    final List<Uri> screenshotsSliderUris;

    ScreenshotsSliderAdapter(List<Uri> screenshotsSliderUris)
    {
        this.screenshotsSliderUris = screenshotsSliderUris;
    }

    @NonNull
    @Override
    public ScreenshotsSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ScreenshotsSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.card_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotsSliderViewHolder holder, int position)
    {
        holder.setImage(holder.itemView.getContext(), screenshotsSliderUris.get(position));
    }

    @Override
    public int getItemCount() {
        return screenshotsSliderUris.size();
    }

    static class ScreenshotsSliderViewHolder extends RecyclerView.ViewHolder
    {
        final RoundedImageView imageView;

        ScreenshotsSliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_slider_item);
        }

        void setImage(Context context, Uri screenshotsSliderUri)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            //imageView.setImageResource(newestSliderItem.getImage());
            Glide.with(context).load(screenshotsSliderUri).into(imageView);
        }
    }
}
