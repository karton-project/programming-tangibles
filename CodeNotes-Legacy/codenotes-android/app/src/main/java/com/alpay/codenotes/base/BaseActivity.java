package com.alpay.codenotes.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alpay.codenotes.R;
import com.alpay.codenotes.SendFeedbackActivity;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    ActionBar actionBar;
    String chapterName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        actionBar = getSupportActionBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Utils.initFirebaseAnalytics(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chapterName = bundle.getString(NavigationManager.BUNDLE_KEY);
            NavigationManager.openFragment(this, chapterName);
        }else{
            // do something
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.getClass().getSimpleName().equals("SendFeedbackActivity"))
            getMenuInflater().inflate(R.menu.action_survey_menu, menu);
        else
            getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                Intent intent = new Intent(this, SendFeedbackActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected void printToastShort(int resID){
        Toast.makeText(this, getResources().getString(resID), Toast.LENGTH_SHORT).show();
    }

    protected void printToastLong(int resID){
        Toast.makeText(this, getResources().getString(resID), Toast.LENGTH_LONG).show();
    }

    protected void setSupportActionBarName(int resID) {
        actionBar.setTitle(resID);
    }

}
