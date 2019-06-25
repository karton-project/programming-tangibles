package com.alpay.codenotes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpay.codenotes.R;
import com.alpay.codenotes.adapter.YoutubeVideoAdapter;
import com.alpay.codenotes.listener.RecyclerViewOnClickListener;
import com.alpay.codenotes.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideoListFragment extends Fragment {

    private static final String TAG = VideoListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private ArrayList<String> youtubeVideoArrayList;
    private YouTubePlayer youTubePlayer;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public View view;

    public VideoListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_video_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initFirebase();
        generateVideoList();
    }

    protected void initFirebase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("videos");
        databaseReference.keepSynced(true);
    }

    private void initializeYoutubePlayer() {
        youTubePlayerFragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);
        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize(Constants.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.cueVideo(youtubeVideoArrayList.get(0));
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }

    private void setUpRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void populateRecyclerView() {
        final YoutubeVideoAdapter adapter = new YoutubeVideoAdapter(getActivity(), youtubeVideoArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (youTubePlayerFragment != null && youTubePlayer != null) {
                    adapter.setSelectedPosition(position);
                    youTubePlayer.cueVideo(youtubeVideoArrayList.get(position));
                }
            }
        }));
    }

    private void generateVideoList() {
        youtubeVideoArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String videoID = postSnapshot.getValue(String.class);
                    youtubeVideoArrayList.add(videoID);
                }
                initializeYoutubePlayer();
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