package com.alpay.codenotes.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alpay.codenotes.HowToUseActivity;
import com.alpay.codenotes.R;
import com.alpay.codenotes.base.welcome.WelcomeActivity;
import com.alpay.codenotes.fragments.SketchFragment;
import com.alpay.codenotes.fragments.StoryboardFragment;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;

import top.defaults.colorpicker.ColorPickerPopup;

public class BaseActivity extends AppCompatActivity {

    ActionBar actionBar;
    String bundleKey = "";
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        actionBar = getSupportActionBar();
        Utils.initFirebaseAnalytics(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            bundleKey = bundle.getString(NavigationManager.BUNDLE_KEY);
            NavigationManager.openFragment(this, bundleKey);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundleKey.equals(NavigationManager.INFO))
            getMenuInflater().inflate(R.menu.empty_menu, menu);
        else if (bundleKey.equals(NavigationManager.SKETCH))
            getMenuInflater().inflate(R.menu.action_sketch, menu);
        else if (bundleKey.equals(NavigationManager.STORYBOARD))
            getMenuInflater().inflate(R.menu.action_storyboard, menu);
        else
            getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.add_new_frame:
                StoryboardFragment storyboardFragment = NavigationManager.storyboardFragment;
                storyboardFragment.addNewFrame();
                return true;
            case R.id.menu_clear:
                SketchFragment.inkView.clear();
                return true;
            case R.id.menu_draw:
                SketchFragment.inkView.setColor(Color.BLACK);
                return true;
            case R.id.menu_erase:
                SketchFragment.inkView.setColor(Color.WHITE);
                return true;
            case R.id.menu_color:
                new ColorPickerPopup.Builder(this)
                        .initialColor(Color.BLUE) // Set initial color
                        .enableBrightness(true) // Enable brightness slider or not
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle(getResources().getString(R.string.ok))
                        .cancelTitle(getResources().getString(R.string.cancel))
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                SketchFragment.inkView.setColor(color);
                            }

                            @Override
                            public void onColor(int color, boolean fromUser) {

                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void printToastShort(int resID) {
        Toast.makeText(this, getResources().getString(resID), Toast.LENGTH_SHORT).show();
    }

    public void printToastLong(int resID) {
        Toast.makeText(this, getResources().getString(resID), Toast.LENGTH_LONG).show();
    }

    public void setSupportActionBarName(int resID) {
        actionBar.setTitle(resID);
    }

}
