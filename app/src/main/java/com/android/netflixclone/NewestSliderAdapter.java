package com.android.netflixclone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class NewestSliderAdapter extends RecyclerView.Adapter<NewestSliderAdapter.NewestSliderViewHolder>
{
    final List<NewestSliderItem> newestSliderItems;
    final ViewPager2 viewPager2;

    NewestSliderAdapter(List<NewestSliderItem> newestSliderItems, ViewPager2 viewPager2)
    {
        this.newestSliderItems = newestSliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public NewestSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new NewestSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.newest_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewestSliderViewHolder holder, int position)
    {
        holder.setImage(newestSliderItems.get(position));
        holder.setTitle(newestSliderItems.get(position));
        if(position == newestSliderItems.size() - 2) viewPager2.post(runnable);
    }

    @Override
    public int getItemCount() {
        return newestSliderItems.size();
    }

    static class NewestSliderViewHolder extends RecyclerView.ViewHolder
    {
        final RoundedImageView imageView;
        final TextView textView;

        NewestSliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.riv_newest_image);
            imageView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            textView = itemView.findViewById(R.id.tv_newest_title);
        }

        void setImage(NewestSliderItem newestSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            imageView.setImageResource(newestSliderItem.getImage());
        }

        void setTitle(NewestSliderItem newestSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            textView.setText(newestSliderItem.getTitle());
        }
    }

    final Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            newestSliderItems.addAll(newestSliderItems);
            notifyDataSetChanged();
        }
    };
}
