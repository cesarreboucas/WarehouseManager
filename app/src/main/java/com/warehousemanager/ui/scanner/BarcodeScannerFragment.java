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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.db.entities.WarehouseHang;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarcodeScannerFragment extends Fragment implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    public static int mCameraId = 1;

    IWarehouseService warehouseService;
    WarehouseDatabase warehouseDatabase;

    boolean canPost = true;
    private Handler handler;
    private Runnable runnable;

    private ProgressBar progressBar;

    private TextView txtCode;
    private TextView txtProductName;
    private TextView txtProductQuantity;
    private TextView txtWarehouseQuantity;

    private Button btnCheckout;

    private CircleImageView productImage;

    private MovementOrder movementOrder;

    private FrameLayout barCodeView;

    String side = "";
    String code = "";
    ProductHang productHang = null;

    public BarcodeScannerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
      View view = inflater.inflate(R.layout.fragment_barcode_scanner, container, false);

      handler = new Handler(Looper.getMainLooper());

      warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);
      warehouseDatabase = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());

      txtCode = view.findViewById(R.id.txtCode);
      txtCode.setText("");
      txtProductName = view.findViewById(R.id.txtProductName);
      txtProductName.setText("");
      txtProductQuantity = view.findViewById(R.id.txtProductQuantity);
      txtProductQuantity.setText("");
      txtWarehouseQuantity = view.findViewById(R.id.txtWarehouseQuantity);
      txtWarehouseQuantity.setText("");

      productImage = view.findViewById(R.id.productImage);
      progressBar = view.findViewById(R.id.progressBar);

      btnCheckout = view.findViewById(R.id.btnCheckout);
      btnCheckout.setOnClickListener(this);

      mScannerView = new ZXingScannerView(getActivity());
      barCodeView = view.findViewById(R.id.barCodeView);
      barCodeView.addView(mScannerView);

      mFlash = false;
      mAutoFocus = true;
      mSelectedIndices = null;
      mCameraId = -1;

      movementOrder = new MovementOrder();
      try {
          movementOrder = (MovementOrder) getArguments().getSerializable("MOVEMENT_ORDER");
      } catch (Exception ex) {
          Log.d("ERROR", ex.getMessage());
      }

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
        code = rawResult.getText().trim();
      if (canPost) {
          progressBar.setVisibility(View.VISIBLE);
          final User user = warehouseDatabase.userDao().getUser();
        canPost = false;
        runnable = new Runnable() {
          @Override
          public void run() {
            warehouseService.getTodoProduct(user.getFavouriteWarehouse(), code).enqueue(new Callback<ProductHang>() {
                @Override
                public void onResponse(Call<ProductHang> call, Response<ProductHang> response) {
                    if(response.isSuccessful()) {
                        productHang = response.body();
                        txtProductName.setText(productHang.getName());
                        txtCode.setText(rawResult.getText());
                        txtProductQuantity.setText(movementOrder.getQuantity() + "");
                        txtWarehouseQuantity.setText(productHang.getWarehouses().get(0).getQuantity_in_stock() + "");

                    } else {
                        Toast.makeText(getContext(), "Cound not get the product with the bar code " + rawResult.getText(), Toast.LENGTH_SHORT).show();
                    }
                    canPost = true;
                    mScannerView.resumeCameraPreview(BarcodeScannerFragment.this);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<ProductHang> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to reach the server", Toast.LENGTH_SHORT).show();
                    Log.d("USER", t.getMessage());
                    canPost = true;
                    mScannerView.resumeCameraPreview(BarcodeScannerFragment.this);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
          }
        };
        handler.postDelayed(runnable, 2000);
      }

    }

    @Override
    public void onPause() {
      super.onPause();
      mScannerView.stopCamera();
      handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        if(productHang == null) {
            Toast.makeText(getContext(), "You need to scan the item first!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = warehouseDatabase.userDao().getUser();
        String favoriteWarehouse = user.getFavouriteWarehouse();
        if(favoriteWarehouse.equals(movementOrder.getWarehouseReceiver())) {
            side = "received";
        } else if(favoriteWarehouse.equals(movementOrder.getWarehouseSender())) {
            side = "sent";
        }
        String id = movementOrder.getId();
        progressBar.setVisibility(View.VISIBLE);
        UpdateMovementOrder updateMovementOrder = new UpdateMovementOrder(side, id);
        warehouseService.updateMovementOrder(updateMovementOrder).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Movement order updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Could not update movement order!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to reach the server!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public class UpdateMovementOrder {
        private String side;
        private String id;

        public UpdateMovementOrder(String side, String id) {
            this.side = side;
            this.id = id;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
