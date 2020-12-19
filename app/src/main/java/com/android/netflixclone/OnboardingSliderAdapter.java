package com.android.netflixclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class OnboardingSliderAdapter  extends RecyclerView.Adapter<OnboardingSliderAdapter.OnboardingSliderViewHolder>
{
    final List<OnboardingSliderItem> onboardingSliderItems;

    OnboardingSliderAdapter(List<OnboardingSliderItem> onboardingSliderItems)
    {
        this.onboardingSliderItems = onboardingSliderItems;
    }

    @NonNull
    @Override
    public OnboardingSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OnboardingSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.onboarding_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingSliderViewHolder holder, int position)
    {
        holder.setImage(onboardingSliderItems.get(position));
        holder.setTitle(onboardingSliderItems.get(position));
        holder.setDesc(onboardingSliderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingSliderItems.size();
    }

    static class OnboardingSliderViewHolder extends RecyclerView.ViewHolder
    {
        final RoundedImageView imageView;
        final TextView tvOnboardingTitle;
        final TextView tvOnboardingDesc;

        OnboardingSliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.riv_onboarding_image);
            tvOnboardingTitle = itemView.findViewById(R.id.tv_onboarding_title);
            tvOnboardingDesc = itemView.findViewById(R.id.tv_onboarding_desc);
        }

        void setImage(OnboardingSliderItem onboardingSliderItem)
        {
            imageView.setImageResource(onboardingSliderItem.getImage());
        }

        void setTitle(OnboardingSliderItem onboardingSliderItem)
        {
            tvOnboardingTitle.setText(onboardingSliderItem.getTitle());
        }

        void setDesc(OnboardingSliderItem onboardingSliderItem)
        {
            tvOnboardingDesc.setTag(onboardingSliderItem.getDesc());
        }
    }
}
