package com.android.netflixclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.netflixclone.DetailActivity;
import com.android.netflixclone.R;
import com.android.netflixclone.model.Newest;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Objects;

public class NewestSliderAdapter extends RecyclerView.Adapter<NewestSliderAdapter.NewestSliderViewHolder>
{
    final List<Newest> newests;
    final ViewPager2 viewPager2;

    public NewestSliderAdapter(List<Newest> newests, ViewPager2 viewPager2)
    {
        this.newests = newests;
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
        holder.setImage(holder.itemView.getContext(), newests.get(position));
        holder.setTitle(newests.get(position));
        holder.setTagOnTextView(newests.get(position));

        if(position == newests.size() - 2) viewPager2.post(runnable);
    }

    @Override
    public int getItemCount() {
        return newests.size();
    }

    static class NewestSliderViewHolder extends RecyclerView.ViewHolder
    {
        final RoundedImageView imageView;
        final TextView textView;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Uri repImageURL;

        NewestSliderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.riv_newest_image);
            textView = itemView.findViewById(R.id.tv_newest_title);

            imageView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra("movieID", (String)textView.getTag());
                    intent.putExtra("title", (String)textView.getText());
                    intent.putExtra("repImageURL", repImageURL);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)itemView.getContext(), imageView, Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));
                    itemView.getContext().startActivity(intent, options.toBundle());
                    //itemView.getContext().startActivity(intent);
                    //((Activity) itemView.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }

        void setImage(Context context, Newest newest)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            //imageView.setImageResource(newestSliderItem.getImage());
            //Glide.with(context).load(R.drawable.newest_placeholder).into(imageView);
            StorageReference storageRef = storage.getReferenceFromUrl(newest.getStorageRef());
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                repImageURL = uri;
                Glide.with(context).load(uri).into(imageView);
            });
        }

        void setTitle(Newest newest)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            //textView.setText(newestSliderItem.getTitle());
            textView.setText(newest.getTitle());
        }

        void setTagOnTextView(Newest newest)
        {
            textView.setTag(newest.getMovieID());
        }
    }

    final Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            newests.addAll(newests);
            notifyDataSetChanged();
        }
    };
}
