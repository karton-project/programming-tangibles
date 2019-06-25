package com.alpay.codenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alpay.codenotes.fragments.StudyNotesFragment;
import com.alpay.codenotes.models.StudyNoteItem;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddToDoActivity extends AppCompatActivity{

    private TextInputEditText mToDoTextBodyEditText;
    private StudyNoteItem mUserStudyNoteItem;
    private LinearLayout mEditTextParentLinearLayout;
    private FloatingActionButton mToDoSendFloatingActionButton;
    private String mUserEnteredText;
    private int mUserColor;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_test);
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().hide();

        mUserStudyNoteItem = (StudyNoteItem) getIntent().getSerializableExtra(StudyNotesFragment.TODOITEM);

        mUserEnteredText = mUserStudyNoteItem.getToDoText();
        mUserColor = mUserStudyNoteItem.getTodoColor();

        mToDoTextBodyEditText = (TextInputEditText) findViewById(R.id.userToDoEditText);
        mToDoSendFloatingActionButton = (FloatingActionButton) findViewById(R.id.makeToDoFloatingActionButton);
        mEditTextParentLinearLayout = findViewById(R.id.editTextParentLinearLayout);
        mEditTextParentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(mToDoTextBodyEditText);
            }
        });

        mToDoTextBodyEditText.requestFocus();
        if (bundle.getString("textFromCamera") != null) {
            String text = bundle.getString("textFromCamera");
            mToDoTextBodyEditText.setText(text);
        } else {
            mToDoTextBodyEditText.setText(mUserEnteredText);
        }

        if(bundle.get(StudyNotesFragment.TODOITEM) != null){
            StudyNoteItem item = (StudyNoteItem) bundle.get(StudyNotesFragment.TODOITEM);
            mToDoTextBodyEditText.setText(item.getToDoText());
        }

        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mToDoTextBodyEditText.setSelection(mToDoTextBodyEditText.length());

        mToDoTextBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredText = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mToDoSendFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToDoTextBodyEditText.length() <= 0) {
                    mToDoTextBodyEditText.setError("Error");
                } else {
                    makeResult(RESULT_OK);
                    finish();
                }
                hideKeyboard(mToDoTextBodyEditText);
            }
        });

    }

    public void hideKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    public void makeResult(int result) {
        Intent i = new Intent();
        if (mUserEnteredText.length() > 0) {
            String capitalizedString = Character.toUpperCase(mUserEnteredText.charAt(0)) + mUserEnteredText.substring(1);
            mUserStudyNoteItem.setToDoText(capitalizedString);
        } else {
            mUserStudyNoteItem.setToDoText(mUserEnteredText);
        }
        mUserStudyNoteItem.setTodoColor(mUserColor);
        i.putExtra(StudyNotesFragment.TODOITEM, mUserStudyNoteItem);
        setResult(result, i);
    }

    public static String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }
}

