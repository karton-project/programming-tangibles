package com.alpay.codenotes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpay.codenotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TaskCompletedFragment extends Fragment {
    private static final String currTaskNameBundle = "CURRTASK";
    private static final String nextTaskNameBundle = "NEXTTASK";

    private String currentTaskName;
    private String nextTaskName;

    public View view;
    private Unbinder unbinder;

    @BindView(R.id.current_task_name)
    TextView currentTaskTV;

    @BindView(R.id.next_task_name)
    TextView nextTaskTV;

    public TaskCompletedFragment() {
    }

    public static TaskCompletedFragment newInstance(String currentTaskName, String nextTaskName) {
        TaskCompletedFragment fragment = new TaskCompletedFragment();
        Bundle args = new Bundle();
        args.putString(currTaskNameBundle, currentTaskName);
        args.putString(nextTaskNameBundle, nextTaskName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentTaskName = getArguments().getString(currTaskNameBundle);
            nextTaskName = getArguments().getString(nextTaskNameBundle);
            currentTaskTV.setText(currentTaskName);
            nextTaskTV.setText(nextTaskName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_completed, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }
}
