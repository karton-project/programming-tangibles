package com.alpay.codenotes.adapter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpay.codenotes.R;
import com.alpay.codenotes.models.Content;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;

import java.util.ArrayList;


public class ContentViewAdapter extends RecyclerView.Adapter<ContentViewHolder> {

    private AppCompatActivity appCompatActivity;
    private ArrayList<Content> mContentList;

    public  ContentViewAdapter(AppCompatActivity appCompatActivity, ArrayList<Content> mContentList) {
        this.appCompatActivity = appCompatActivity;
        this.mContentList = mContentList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mView = layoutInflater.inflate(R.layout.content_card_layout, parent, false);
        return new ContentViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, int position) {
        holder.mImage.setImageDrawable(Utils.getDrawableWithName(appCompatActivity, mContentList.get(position).getImage()));
        holder.mTitle.setText(mContentList.get(position).getName());
        holder.mDetail.setText(mContentList.get(position).getDetail());
        holder.mWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationManager.openFragment(appCompatActivity, NavigationManager.VIDEOLIST);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }
}

class ContentViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    TextView mDetail;
    CardView mCardView;
    Button mWatchButton;
    Button mPracticeButton;

    ContentViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.content_card_thumbnail);
        mTitle = itemView.findViewById(R.id.content_card_title);
        mDetail = itemView.findViewById(R.id.content_card_detail);
        mWatchButton = itemView.findViewById(R.id.content_card_watch_video_button);
        mPracticeButton =  itemView.findViewById(R.id.content_card_practice_button);
        mCardView = itemView.findViewById(R.id.content_card_view);
    }
}