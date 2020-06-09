package com.example.top10downloader;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private Context context;
    private ArrayList<Feed> feeds;

    public FeedAdapter(Context context, ArrayList<Feed> feeds) {
        this.context = context;
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_layout,parent,false);

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.tvName.setText(feeds.get(position).getName());
        holder.tvArtist.setText(feeds.get(position).getArtist());
        Picasso.with(context).load(feeds.get(position).getImageURL()).into(holder.imageView);

    }



    @Override
    public int getItemCount() {
        return feeds.size();
    }
}
