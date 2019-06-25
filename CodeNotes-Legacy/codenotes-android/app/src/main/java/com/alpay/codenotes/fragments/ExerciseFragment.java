package com.alpay.codenotes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpay.codenotes.R;
import com.alpay.codenotes.adapter.ExerciseViewAdapter;
import com.alpay.codenotes.firebasemodels.Exercise;
import com.alpay.codenotes.utils.listener.RecyclerViewOnClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseFragment extends Fragment {

    private View view;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Exercise> exerciseArrayList;
    RecyclerView recyclerView;

    public ExerciseFragment() {
        // default public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exercise, container, false);
        initFirebase();
        generateExerciseList();
        return view;
    }

    private void setUpRecyclerView() {
        recyclerView = view.findViewById(R.id.exercise_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    protected void initFirebase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("exercises");
        databaseReference.keepSynced(true);
    }

    private void populateRecyclerView() {
        final ExerciseViewAdapter adapter = new ExerciseViewAdapter(view.getContext(), exerciseArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(view.getContext(), new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //do something
            }
        }));
    }

    private void generateExerciseList() {
        exerciseArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Exercise exercise = postSnapshot.getValue(Exercise.class);
                    exerciseArrayList.add(exercise);
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
