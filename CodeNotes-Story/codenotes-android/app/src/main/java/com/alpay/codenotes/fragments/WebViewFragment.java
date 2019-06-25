package com.alpay.codenotes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.alpay.codenotes.R;
import com.alpay.codenotes.SendFeedbackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WebViewFragment extends Fragment {

    private View view;
    private Unbinder unbinder;

    @BindView(R.id.info_fragment_webview)
    WebView webView;

    @OnClick(R.id.info_fragment_feedback_button)
    public void sendFeedback(){
        Intent intent = new Intent(getActivity(), SendFeedbackActivity.class);
        startActivity(intent);
    }

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web_view, container, false);
        unbinder =  ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        webView.loadUrl("https://asabuncuoglu13.github.io/CodeNotes/");
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

}
