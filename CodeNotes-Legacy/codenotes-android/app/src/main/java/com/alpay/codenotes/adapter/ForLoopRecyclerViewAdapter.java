package com.alpay.codenotes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpay.codenotes.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ForLoopRecyclerViewAdapter extends RecyclerView.Adapter<ForLoopRecyclerViewAdapter.ViewHolder> {

    private final List<ForLoopImage> mValues;

    public ForLoopRecyclerViewAdapter(List<ForLoopImage> items) {
        mValues = items;
    }

    @Override
    public ForLoopRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.forloopcardview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ForLoopRecyclerViewAdapter(ForLoopImage.ITEMS));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mImageView.setImageDrawable(mValues.get(position).image);
        holder.mTextView.setText(Integer.toString(position+1));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.forloopDrawable);
            mTextView = (TextView) view.findViewById(R.id.forloopNumber);
        }
    }

    public static class ForLoopImage {
        public static List<ForLoopImage> ITEMS = new ArrayList<>();
        public Drawable image;

        public ForLoopImage(Context context, String encodedImage) {
            this.image = setResultImage(context, encodedImage);
        }

        public Drawable setResultImage(Context context, String encodedImage) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            return drawable;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mValues.size();
    }
}