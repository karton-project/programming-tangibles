package com.alpay.codenotes.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alpay.codenotes.R;
import com.alpay.codenotes.base.BarcodeCaptureActivity;
import com.alpay.codenotes.utils.Utils;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class QRReaderFragment extends Fragment {

    private static final String LOG_TAG = QRReaderFragment.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    View view;
    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qrreader, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.scan_barcode_button)
    public void scanBarcode() {
        Intent intent = new Intent(getActivity().getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    if (barcode != null) {
                        String url = barcode.displayValue;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                } else {
                    Utils.showWarningToast((AppCompatActivity) getActivity(), R.string.error_text, Toast.LENGTH_SHORT);
                }
            } else Log.e(LOG_TAG, "Barcode error");
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}