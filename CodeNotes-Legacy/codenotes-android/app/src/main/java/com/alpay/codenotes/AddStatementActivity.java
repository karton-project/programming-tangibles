package com.alpay.codenotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.alpay.codenotes.base.BaseActivity;
import com.alpay.codenotes.firebasemodels.Conditional;
import com.alpay.codenotes.firebasemodels.Output;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStatementActivity extends BaseActivity {

    boolean truthValue;
    static final int PICK_IMAGE = 1;
    Bitmap bitmap;
    RadioGroup radioGroup;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @BindView(R.id.newConditionalEditText)
    EditText newConditionalEditText;
    @BindView(R.id.newOutputEditText)
    EditText newOutputEditText;
    @BindView(R.id.selectedImageView)
    ImageView selectedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);
        ButterKnife.bind(this);
        super.setSupportActionBarName(R.string.addstatement_name);
        setTruthValueClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.keepSynced(true);
    }

    // Butterknife does not work very well with radio groups.
    protected void setTruthValueClickListener() {
        radioGroup = (RadioGroup) findViewById(R.id.truthRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.trueButton) {
                    truthValue = true;
                } else if (checkedId == R.id.falseButton) {
                    truthValue = false;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                printToastShort(R.string.select_image_error);
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = decodeSampledBitmapFromStream(inputStream, 300, 300);
                selectedImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.selectImageFromGallery)
    protected void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @OnClick(R.id.saveNewOutputStatement)
    protected void saveNewOutputStatement() {
        String outputText = newOutputEditText.getText().toString();
        if (!outputText.equals("") && bitmap != null) {
            outputText = outputText.replace(" ", "");
            outputText = outputText.toUpperCase();
            checkIfOutputExistsAndSave(outputText, bitmapToBase64(bitmap));
        } else {
            printToastShort(R.string.save_output_statement_error);
        }
    }

    @OnClick(R.id.saveNewLogicStatement)
    protected void saveNewLogicStatement() {
        String conditionalText = newConditionalEditText.getText().toString();
        if (!conditionalText.equals("")) {
            conditionalText = conditionalText.replace(" ", "");
            conditionalText = conditionalText.toUpperCase();
            checkIfConditionalExistsAndSave(conditionalText, truthValue);
        } else {
            printToastShort(R.string.save_logic_statement_error);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    protected Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return getResizedBitmap(bitmap, reqWidth, reqHeight);
    }

    protected String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    boolean conditionalChecker = true;

    protected void checkIfConditionalExistsAndSave(final String conditionalText, final boolean value) {

        final DatabaseReference conditionalRef = databaseReference.child("conditionals");
        conditionalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("conditionalText").getValue().equals(conditionalText)) {
                        printToastShort(R.string.firebase_conditional_exist_error);
                        conditionalChecker = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        if (conditionalChecker) {
            sendConditionalToFirebase(conditionalText, value);
            printToastShort(R.string.saved_conditional_text);
            conditionalChecker = true;
        }
    }

    boolean outputChecker = true;

    protected void checkIfOutputExistsAndSave(final String outputText, final String encodedImage) {
        final DatabaseReference outputRef = databaseReference.child("outputs");
        outputRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("outputText").getValue().equals(outputText)) {
                        printToastShort(R.string.firebase_output_exist_error);
                        outputChecker = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        if (outputChecker) {
            sendOutputToFirebase(outputText, encodedImage);
            printToastShort(R.string.saved_output_text);
            outputChecker = true;
        }
    }

    protected void sendConditionalToFirebase(String text, Boolean value) {
        int val = (value) ? 1 : -1;
        Conditional conditional = new Conditional(text, val);
        databaseReference.child("conditionals").push().setValue(conditional);
    }

    protected void sendOutputToFirebase(String text, String encodedImage) {
        Output output = new Output(text, encodedImage);
        databaseReference.child("outputs").push().setValue(output);
    }

}
