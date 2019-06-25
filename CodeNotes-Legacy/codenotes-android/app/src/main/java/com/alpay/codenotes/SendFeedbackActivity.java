package com.alpay.codenotes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alpay.codenotes.base.BaseActivity;
import com.alpay.codenotes.firebasemodels.Feedback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendFeedbackActivity extends BaseActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @BindView(R.id.feedbackTitleEditText)
    EditText feedbackTitleEditText;

    @BindView(R.id.feedbackDetailEditText)
    EditText feedbackDetailEditText;

    @BindView(R.id.formView)
    ScrollView formView;

    @BindView(R.id.successView)
    LinearLayout successView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        ButterKnife.bind(this);
        super.setSupportActionBarName(R.string.sendfeedback_name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.keepSynced(true);
    }

    @Nullable
    @OnClick(R.id.sendFeedBackButton)
    protected void sendFeedBack() {
        String feedbackTitle = feedbackTitleEditText.getText().toString();
        String feedbackDetail = feedbackDetailEditText.getText().toString();

        if (feedbackTitle.equals("")) {
            printToastShort(R.string.sendfeedback_formtitle_required);
            return;
        }
        if (feedbackDetail.equals("")) {
            printToastShort(R.string.sendfeedback_formcontent_required);
            return;
        }
        sendFeedbackToFirebase(feedbackTitle, feedbackDetail);
        changeToSuccessView();
    }

    @Nullable
    @OnClick(R.id.feedbackSentOkButton)
    protected void backToLatestScreen() {
        super.onBackPressed();
    }

    protected void sendFeedbackToFirebase(String title, String detail) {
        Feedback feedback = new Feedback(title, detail);
        databaseReference.child("userFeedback").push().setValue(feedback);
    }

    protected void changeToSuccessView() {
        formView.setVisibility(View.GONE);
        successView.setVisibility(View.VISIBLE);
    }

}
