package com.android.netflixclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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
        holder.setImage(holder.itemView.getContext(), newestSliderItems.get(position));
        holder.setTitle(newestSliderItems.get(position));
        holder.setTagOnTextView(newestSliderItems.get(position));

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

        void setImage(Context context, NewestSliderItem newestSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            //imageView.setImageResource(newestSliderItem.getImage());
            //Glide.with(context).load(R.drawable.newest_placeholder).into(imageView);
            StorageReference storageRef = storage.getReferenceFromUrl(newestSliderItem.getStorageRef());
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                repImageURL = uri;
                Glide.with(context).load(uri).into(imageView);
            });
        }

        void setTitle(NewestSliderItem newestSliderItem)
        {
            // If you want to display image from the internet
            // You can put code here using glide or picasso.
            textView.setText(newestSliderItem.getTitle());
        }

        void setTagOnTextView(NewestSliderItem newestSliderItem)
        {
            textView.setTag(newestSliderItem.getMovieID());
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
