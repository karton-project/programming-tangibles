package com.alpay.codenotes.fragments;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alpay.codenotes.R;
import com.alpay.codenotes.adapter.ContentViewAdapter;
import com.alpay.codenotes.models.Content;
import com.alpay.codenotes.utils.DownloadFileTask;
import com.alpay.codenotes.utils.NavigationManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContentListFragment extends Fragment{

    private View view;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Content> contentArrayList;
    private Unbinder unbinder;
    private StorageReference mStorageRef;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.content_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.content_progress_bar)
    ProgressBar progressBar;

    @OnClick(R.id.take_note_button)
    public void startTakeNoteAction(){
        NavigationManager.openFragment((AppCompatActivity) getActivity(), NavigationManager.NOTES);
    }

    public ContentListFragment() {
        // default public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.learn_color)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_card_learn_title);
        initFirebase();
        generateContentList();
        return view;
    }

    @Override
    public void onDetach() {
        unbinder.unbind();
        super.onDetach();
    }

    private void createDownloadTask(String imageName){
        new DownloadFileTask().execute("content/", imageName);
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    protected void initFirebase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("contents");
        databaseReference.keepSynced(true);
    }

    private void populateRecyclerView() {
        final ContentViewAdapter adapter = new ContentViewAdapter((AppCompatActivity) getActivity(), contentArrayList);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    private void generateContentList() {
        contentArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Content content = dataSnapshot.getValue(Content.class);
                    contentArrayList.add(content);
                    createDownloadTask(content.getImage());
                }
                setUpRecyclerView();
                populateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });
    }
}
