package com.alpay.codenotes.utils;


import com.alpay.codenotes.R;
import com.alpay.codenotes.fragments.ExerciseFragment;
import com.alpay.codenotes.fragments.QRReaderFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class NavigationManager {

    public static final String BUNDLE_KEY = "bundlekey";
    public static final String HOME = "home";
    public static final String ACTIVITY1 = "learn_computer_parts";
    public static final String EXERCISE = "exercises";

    public static void openFragment(AppCompatActivity appCompatActivity, String fragmentID){
        FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
        if(fragmentID.contentEquals(ACTIVITY1)){
            QRReaderFragment qrReaderFragment = new QRReaderFragment();
            ft.replace(R.id.fragment_container, qrReaderFragment);
        }else if(fragmentID.contentEquals(EXERCISE)){
            ExerciseFragment exerciseFragment = new ExerciseFragment();
            ft.replace(R.id.fragment_container, exerciseFragment);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
