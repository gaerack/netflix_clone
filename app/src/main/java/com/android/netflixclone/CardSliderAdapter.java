package com.android.netflixclone;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

public class CardSliderAdapter extends RecyclerView.Adapter<CardSliderAdapter.CardSliderViewHolder> {



    @NonNull
    @Override
    public CardSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CardSliderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CardSliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;

        CardSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_slider_item);
        }

        void setImage(CardSliderItem cardSliderItem)
        {
            imageView.setImageResource(cardSliderItem.getImage());
        }
    }
}
