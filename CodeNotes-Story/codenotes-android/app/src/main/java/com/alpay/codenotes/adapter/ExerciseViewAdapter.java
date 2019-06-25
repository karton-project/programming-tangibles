package com.alpay.codenotes.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpay.codenotes.R;
import com.alpay.codenotes.models.Exercise;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;

import java.util.ArrayList;


public class ExerciseViewAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

    private AppCompatActivity appCompatActivity;
    private ArrayList<Exercise> mExerciseList;
    private String directoryPath = "";

    public  ExerciseViewAdapter(AppCompatActivity appCompatActivity, ArrayList<Exercise> mExerciseList) {
        this.appCompatActivity = appCompatActivity;
        this.mExerciseList = mExerciseList;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mView = layoutInflater.inflate(R.layout.exercise_card_layout, parent, false);
        return new ExerciseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder holder, int position) {
        holder.mImage.setImageDrawable(Utils.getDrawableWithName(appCompatActivity, mExerciseList.get(position).getImage()));
        holder.mTitle.setText(mExerciseList.get(position).getName());
        holder.mDetail.setText(mExerciseList.get(position).getDescription());
        holder.mWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationManager.openFragment(appCompatActivity, NavigationManager.VIDEOLIST);
            }
        });
        holder.mPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent to detail activity
            }
        });
    }

    public Drawable setResultImage(Context context, String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }
}

class ExerciseViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    TextView mDetail;
    CardView mCardView;
    Button mWatchButton;
    Button mPracticeButton;

    ExerciseViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.exercise_card_thumbnail);
        mTitle = itemView.findViewById(R.id.exercise_card_title);
        mDetail = itemView.findViewById(R.id.exercise_card_detail);
        mWatchButton = itemView.findViewById(R.id.exercise_card_watch_video_button);
        mPracticeButton =  itemView.findViewById(R.id.exercise_card_practice_button);
        mCardView = itemView.findViewById(R.id.exercise_card_view);
    }
}