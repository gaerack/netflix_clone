package com.android.netflixclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.netflixclone.R;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ScreenshotsSliderAdapter extends RecyclerView.Adapter<ScreenshotsSliderAdapter.ScreenshotsSliderViewHolder>{

    final List<String> screenshotUrls;

    public ScreenshotsSliderAdapter(List<String> screenshotUrls)
    {
        this.screenshotUrls = screenshotUrls;
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
        holder.setImage(holder.itemView.getContext(), screenshotUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return screenshotUrls.size();
    }

    static class ScreenshotsSliderViewHolder extends RecyclerView.ViewHolder
    {
        final RoundedImageView imageView;
        FirebaseStorage storage = FirebaseStorage.getInstance();

        ScreenshotsSliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_slider_item);
        }

        void setImage(Context context, String screenshotsUrl)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            //imageView.setImageResource(newestSliderItem.getImage());
            //Glide.with(context).load(screenshotsSliderUri).into(imageView);
            StorageReference storageRef = storage.getReferenceFromUrl(screenshotsUrl);
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context).load(uri).into(imageView));
        }
    }
}
