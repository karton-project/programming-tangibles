package com.alpay.codenotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alpay.codenotes.adapter.ForLoopRecyclerViewAdapter;
import com.alpay.codenotes.base.BaseActivity;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;
import com.alpay.codenotes.utils.interpreter.CodeNotesCompiler;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CodeNotesCompilerActivity extends BaseActivity {

    @BindView(R.id.surface_view)
    SurfaceView cameraView;
    @BindView(R.id.text_view)
    TextView textView;
    @Nullable
    @BindView(R.id.resultLayout)
    LinearLayout resultLayout;
    @Nullable
    @BindView(R.id.resultRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.read_code_button)
    Button readCodeButton;
    CameraSource cameraSource;
    TextRecognizer textRecognizer;

    ForLoopRecyclerViewAdapter forLoopRecyclerViewAdapter;

    final int RequestCameraPermissionID = 1001;
    String code;

    @OnClick(R.id.add_statement_button)
    public void addNewStatement(){
        startActivity(new Intent(CodeNotesCompilerActivity.this, AddStatementActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        ButterKnife.bind(this);
        CodeNotesCompiler.init();
        super.setSupportActionBarName(R.string.codenotescompiler_name);
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        buildCameraSource();
        startCamera();
        recognizeText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.codenotes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_blockly:
                Intent intent = new Intent(this, BlocklyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }


    @OnClick(R.id.read_code_button)
    public void readCode() {
        if (code != null) {
            String[] s = CodeNotesCompiler.compile(code);
            if (s[0].contentEquals("-1") || s[1].contentEquals("NOOUTPUT"))
                Utils.showOKDialog(this, R.string.no_code_dialog_message);
             else
                compileResult(s);
        } else {
            Utils.showOKDialog(this, R.string.no_code_dialog_message);
        }
    }

    @Nullable
    @OnClick(R.id.result_ok_button)
    public void completeResult() {
        ForLoopRecyclerViewAdapter.ForLoopImage.ITEMS = new ArrayList<>();
        prepareCompilerView();
    }

    private void buildCameraSource() {
        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();
    }

    private void startCamera() {
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CodeNotesCompilerActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
    }

    private void recognizeText() {
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < items.size(); ++i) {
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");
                            }
                            code = stringBuilder.toString();
                            textView.setText(code);
                        }
                    });
                }
            }
        });
    }

    private void prepareResultView() {
        resultLayout.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        readCodeButton.setVisibility(View.GONE);
    }

    private void prepareCompilerView() {
        resultLayout.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        readCodeButton.setVisibility(View.VISIBLE);
    }

    private void createMultipleImageViews(int times, String encodedImage) {
        for (int i = 1; i <= times; i++) {
            ForLoopRecyclerViewAdapter.ForLoopImage.ITEMS.add(new ForLoopRecyclerViewAdapter.ForLoopImage(this, encodedImage));
        }
        forLoopRecyclerViewAdapter = new ForLoopRecyclerViewAdapter(ForLoopRecyclerViewAdapter.ForLoopImage.ITEMS);
        recyclerView.setAdapter(forLoopRecyclerViewAdapter);
        ForLoopRecyclerViewAdapter.ForLoopImage.ITEMS = new ArrayList<>();
    }

    private void compileResult(String[] output) {
        int times = Integer.parseInt(output[0]);
        if (times < 0) {
            printToastShort(R.string.error_text);
        } else if (times < 2) {
            prepareResultView();
            createMultipleImageViews(1, output[1]);
        } else if (times < 20) {
            prepareResultView();
            createMultipleImageViews(times, output[1]);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(NavigationManager.BUNDLE_KEY, NavigationManager.VIDEOLIST);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}