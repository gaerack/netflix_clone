package com.android.netflixclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.netflixclone.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CardSliderAdapter extends RecyclerView.Adapter<CardSliderAdapter.CardSliderViewHolder>
{
    List<CardSliderItem> cardSliderItems;

    public CardSliderAdapter(List<CardSliderItem> cardSliderItems)
    {
        this.cardSliderItems = cardSliderItems;
    }

    @NonNull
    @Override
    public CardSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CardSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.card_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CardSliderViewHolder holder, int position)
    {
        holder.setImage(cardSliderItems.get(position));
    }

    @Override
    public int getItemCount()
    {
        return cardSliderItems.size();
    }

    public class CardSliderViewHolder extends RecyclerView.ViewHolder
    {
        private RoundedImageView imageView;

        public CardSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_slider_item);
        }

        void setImage(CardSliderItem cardSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            imageView.setImageResource(cardSliderItem.getImage());
        }
    }
}
