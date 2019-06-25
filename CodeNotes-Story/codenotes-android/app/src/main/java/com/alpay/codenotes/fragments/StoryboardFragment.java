package com.alpay.codenotes.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alpay.codenotes.R;
import com.alpay.codenotes.StoryViewActivity;
import com.alpay.codenotes.adapter.StoryViewAdapter;
import com.alpay.codenotes.models.Frame;
import com.alpay.codenotes.utils.Utils;
import com.alpay.codenotes.view.utils.MarginDecoration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StoryboardFragment extends Fragment{

    private View view;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<Frame> frameList = new ArrayList<>();
    private StoryViewAdapter storyViewAdapter;
    private String directoryPath;
    private String fileName;
    private Unbinder unbinder;
    public static final int PICK_IMAGE = 1;
    private static int currentFrameID = -1;

    @BindView(R.id.new_frame_layout)
    RelativeLayout newFrameLayout;

    @BindView(R.id.storyboard_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.storyboard_progressbar)
    ProgressBar progressBar;

    @OnClick(R.id.run_button)
    public void startStoryView(){
        Intent intent = new Intent(getActivity(), StoryViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.new_chapter_button)
    public void createNewChapterHeader(){
        Frame.addNewEmptyHeader();
        refreshStoryBoard(frameList.size() - 1);
        newFrameLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.new_frame_button)
    public void createNewFrame(){
        Frame.addNewEmptyFrame();
        refreshStoryBoard(frameList.size() - 1);
        newFrameLayout.setVisibility(View.GONE);
    }

    public static StoryboardFragment newInstance(){
        return new StoryboardFragment();
    }

    public StoryboardFragment() {
        // Required empty public constructor
    }

    public static void setCurrentFrameID(int frameID) {
        currentFrameID = frameID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_storyboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.story_build_color)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_card_practice_title);
        frameList = Frame.listAll(Frame.class);
        if(frameList.size() == 0){
            Frame.initEmptyStoryBoard((AppCompatActivity) getActivity());
        }
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onDetach() {
        unbinder.unbind();
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshStoryBoard(currentFrameID-1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            fileName = "stry" + currentFrameID;
            if (!directoryPath.isEmpty()) {
                directoryPath += "/CodeNotes/Drawable";
            } else {
                directoryPath = "storage/self/primary/CodeNotes/Drawable";
            }
            if (data != null) {
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = Utils.decodeSampledBitmapFromStream(inputStream, 600, 600);
                    File file = new File(directoryPath + "/" + fileName);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                    Frame.updateFrameImageName(currentFrameID, fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpRecyclerView() {
        recyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    public void refreshStoryBoard(int position){
        frameList = Frame.listAll(Frame.class);
        storyViewAdapter = new StoryViewAdapter(this, frameList);
        recyclerView.setAdapter(storyViewAdapter);
        recyclerView.scrollToPosition(position);
        progressBar.setVisibility(View.GONE);
    }

    public void addNewFrame(){
        newFrameLayout.setVisibility(View.VISIBLE);
    }

}
