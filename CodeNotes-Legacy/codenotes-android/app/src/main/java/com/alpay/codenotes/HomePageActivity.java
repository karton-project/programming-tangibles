package com.alpay.codenotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.alpay.codenotes.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alpay.codenotes.utils.Constants;
import com.alpay.codenotes.utils.Utils;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class HomePageActivity extends BaseActivity {

    @BindView(R.id.button_learn)
    LinearLayout buttonLearn;
    @BindView(R.id.button_code)
    LinearLayout buttonCode;
    @BindView(R.id.button_get)
    LinearLayout buttonGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        super.setSupportActionBarName(R.string.app_name);
    }

    @OnClick(R.id.websitecard_link)
    public void visitWebsite(){
        String url = "https://asabuncuoglu13.github.io/CodeNotes/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.button_learn)
    public void openTutorial() {
        Intent intent = new Intent(this, HowToUseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_code)
    public void openCoding() {
        Intent intent = new Intent(this, CodeNotesCompilerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_get)
    public void downloadCards() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CARD_PDF_URL));
        Utils.showOKDialog(this, R.string.download_printable_files, intent);
    }

    @OnClick(R.id.newStatementButton)
    public void openNewStatementActivity(){
        Intent intent = new Intent(this, AddStatementActivity.class);
        startActivity(intent);
    }

}
