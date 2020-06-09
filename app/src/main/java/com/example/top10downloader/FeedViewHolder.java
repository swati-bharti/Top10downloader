package com.example.top10downloader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    TextView tvArtist, tvName;
    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.feedImage);
        tvArtist = itemView.findViewById(R.id.txtArtist);
        tvName = itemView.findViewById(R.id.textName);


    }
}
