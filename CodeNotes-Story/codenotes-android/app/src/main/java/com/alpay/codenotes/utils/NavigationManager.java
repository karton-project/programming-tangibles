package com.alpay.codenotes.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alpay.codenotes.R;
import com.alpay.codenotes.fragments.BlocklyWebViewFragment;
import com.alpay.codenotes.fragments.ContentListFragment;
import com.alpay.codenotes.fragments.ExerciseListFragment;
import com.alpay.codenotes.fragments.QRReaderFragment;
import com.alpay.codenotes.fragments.SketchFragment;
import com.alpay.codenotes.fragments.StoryboardFragment;
import com.alpay.codenotes.fragments.StudyNotesFragment;
import com.alpay.codenotes.fragments.VideoListFragment;
import com.alpay.codenotes.fragments.WebViewFragment;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;

public class NavigationManager {

    public static final String BUNDLE_KEY = "bundlekey";
    public static final String STORYBOARD = "story";
    public static final String ACTIVITY1 = "learn_computer_parts";
    public static final String EXERCISE = "exercises";
    public static final String CONTENT = "content";
    public static final String SKETCH = "sketch";
    public static final String CAMERA = "camera";
    public static final String NOTES = "notes";
    public static final String INFO = "info";
    public static final String BLOCKLY = "blockly";
    public static final String VIDEOLIST = "videolist";

    public static StoryboardFragment storyboardFragment = StoryboardFragment.newInstance();

    public static void openFragment(AppCompatActivity appCompatActivity, String fragmentID) {
        FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
        if (fragmentID.contentEquals(STORYBOARD)) {
            ft.replace(R.id.fragment_container, storyboardFragment);
        } else if (fragmentID.contentEquals(ACTIVITY1)) {
            QRReaderFragment qrReaderFragment = new QRReaderFragment();
            ft.replace(R.id.fragment_container, qrReaderFragment);
        } else if (fragmentID.contentEquals(EXERCISE)) {
            ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
            ft.replace(R.id.fragment_container, exerciseListFragment);
        } else if (fragmentID.contentEquals(CONTENT)) {
            ContentListFragment contentListFragment = new ContentListFragment();
            ft.replace(R.id.fragment_container, contentListFragment);
        } else if (fragmentID.contentEquals(SKETCH)) {
            SketchFragment sketchFragment = new SketchFragment();
            ft.replace(R.id.fragment_container, sketchFragment);
        } else if (fragmentID.contentEquals(INFO)) {
            WebViewFragment webViewFragment = new WebViewFragment();
            ft.replace(R.id.fragment_container, webViewFragment);
        } else if (fragmentID.contentEquals(CAMERA)) {
            if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
                ft.replace(R.id.fragment_container, cameraFragment);
                return;
            }
        } else if (fragmentID.contentEquals(NOTES)) {
            StudyNotesFragment studyNotesFragment = new StudyNotesFragment();
            ft.replace(R.id.fragment_container, studyNotesFragment);
        } else if (fragmentID.contentEquals(VIDEOLIST)) {
            VideoListFragment videoListFragment = new VideoListFragment();
            ft.replace(R.id.fragment_container, videoListFragment);
        } else if (fragmentID.contentEquals(BLOCKLY)) {
            BlocklyWebViewFragment blocklyWebViewFragment = new BlocklyWebViewFragment();
            ft.replace(R.id.fragment_container, blocklyWebViewFragment);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
