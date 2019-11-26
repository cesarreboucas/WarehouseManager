package com.warehousemanager.ui.scanner;


import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.warehousemanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    public static int mCameraId = 1;

    boolean canPost = true;
    private Handler handler;
    Runnable runnable;

    private FrameLayout barCodeView;

    public BarcodeScannerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
      View view = inflater.inflate(R.layout.fragment_barcode_scanner, container, false);

      handler = new Handler(Looper.getMainLooper());

      mScannerView = new ZXingScannerView(getActivity());
      barCodeView = view.findViewById(R.id.barCodeView);
      barCodeView.addView(mScannerView);

      mFlash = false;
      mAutoFocus = true;
      mSelectedIndices = null;
      mCameraId = -1;

      return view;
    }

    @Override
    public void onCreate(Bundle state) {
      super.onCreate(state);
      setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
      super.onResume();
      mScannerView.setResultHandler(this);
      mScannerView.startCamera(mCameraId);
      mScannerView.setFlash(mFlash);
      mScannerView.setAutoFocus(mAutoFocus);
      mScannerView.setFormats(Arrays.asList(BarcodeFormat.EAN_13, BarcodeFormat.CODE_128));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
    }

    @Override
    public void handleResult(final Result rawResult) {
      if (canPost) {
        canPost = false;
        runnable = new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getContext(), "Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().name(), Toast.LENGTH_SHORT).show();
            canPost = true;
            mScannerView.resumeCameraPreview(BarcodeScannerFragment.this);
          }
        };
        handler.postDelayed(runnable, 3000);
      }

    }

    @Override
    public void onPause() {
      super.onPause();
      mScannerView.stopCamera();
      handler.removeCallbacks(runnable);
    }
}
