package com.alpay.codenotes.base;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alpay.codenotes.R;
import com.alpay.codenotes.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NFCActivity {

    private static final String MIME_TEXT_PLAIN = "text/plain";

    private static final String TAG_PROCESSOR = "PROCESSOR";
    private static final String TAG_MOTHERBOARD = "motherboard";
    private static final String TAG_HARDDRIVE = "harddrive";

    private static NfcAdapter mNfcAdapter;

    private enum TAGVIEW {
        HOME, PROCESSOR, MOTHERBOARD, HARDDRIVE;
    }

    public static void setup(AppCompatActivity appCompatActivity) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(appCompatActivity);
        if (mNfcAdapter == null) {
            Utils.showWarningToast(appCompatActivity, R.string.no_nfc_warning, Toast.LENGTH_LONG);
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            Utils.showWarningToast(appCompatActivity, R.string.nfc_disabled_warning, Toast.LENGTH_LONG);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                appCompatActivity.startActivity(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                appCompatActivity.startActivity(intent);
            }
        } else {
            openView(TAGVIEW.HOME);
        }
        handleIntent(appCompatActivity);
    }

    private static void openView(TAGVIEW tagview) {
        switch (tagview) {
            case HOME:

                break;
            case MOTHERBOARD:

                break;
            case HARDDRIVE:

                break;
            case PROCESSOR:

                break;
            default:

                break;
        }
    }

    // Call on new Intent
    public static void handleIntent(AppCompatActivity appCompatActivity) {
        String action = appCompatActivity.getIntent().getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = appCompatActivity.getIntent().getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = appCompatActivity.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
            } else {
                Utils.showErrorToast(appCompatActivity, R.string.error_text, Toast.LENGTH_SHORT);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = appCompatActivity.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    private static class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        // unsupported encoding
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0063;
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.contentEquals(TAG_HARDDRIVE)) {
                    openView(TAGVIEW.HARDDRIVE);
                } else if (result.contentEquals(TAG_MOTHERBOARD)) {
                    openView(TAGVIEW.MOTHERBOARD);
                } else if (result.contentEquals(TAG_PROCESSOR)) {
                    openView(TAGVIEW.PROCESSOR);
                } else {
                    openView(TAGVIEW.HOME);
                }
            }
        }
    }

    //Call when application on state Resume
    public static void setupForegroundDispatch(final AppCompatActivity activity) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        mNfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    //Call when application on state Pause
    public static void stopForegroundDispatch(final AppCompatActivity activity) {
        mNfcAdapter.disableForegroundDispatch(activity);
    }
}
