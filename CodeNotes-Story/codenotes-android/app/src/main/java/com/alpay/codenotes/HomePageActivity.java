package com.alpay.codenotes;

import android.content.Intent;
import android.os.Bundle;

import com.alpay.codenotes.base.BaseActivity;
import com.alpay.codenotes.base.welcome.WelcomeActivity;
import com.alpay.codenotes.utils.NavigationManager;
import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        super.setSupportActionBarName(R.string.welcome_text);
    }

    @OnClick(R.id.home_card_practice)
    public void openProgrammingArea() {
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(NavigationManager.BUNDLE_KEY, NavigationManager.STORYBOARD);
        startActivity(intent);
    }

    @OnClick(R.id.home_card_learn)
    public void openLearningActivities(){
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(NavigationManager.BUNDLE_KEY, NavigationManager.CONTENT);
        startActivity(intent);
    }

    @OnClick(R.id.home_card_assess)
    public void openExercises(){
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(NavigationManager.BUNDLE_KEY, NavigationManager.EXERCISE);
        startActivity(intent);
    }

    @OnClick(R.id.home_card_share)
    public void openSharingCenter() {
        // TODO: Create a sharing center
    }

}
