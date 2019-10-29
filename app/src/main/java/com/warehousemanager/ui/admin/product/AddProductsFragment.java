package com.warehousemanager.ui.admin.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductsFragment extends Fragment implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView picture;
    Product p = new Product();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_products_add,container,false);
        Button btnLoadImage = view.findViewById(R.id.buttonLoadPicture);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        picture = view.findViewById(R.id.picture);
        btnLoadImage.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLoadPicture:
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        ImageHelper imageHelper = new ImageHelperImpl();
                        String pic = imageHelper.convertBitmapToBase64Resized(r.getBitmap());
                        p.setPicture(pic);
                        picture.setImageBitmap(r.getBitmap());
                    }
                })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                    }
                }).show(getFragmentManager());
            break;
            case R.id.btnAdd:
                Product product = new Product();
                product.setName("Prod Namee");
                product.setDescription("Some Description");
                product.setCost(15);
                product.setPrice(25);
                product.setBarcode(String.valueOf(System.currentTimeMillis()));
                product.setPicture("picture");

                IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);
                warehouseService.createProduct(product).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Log.d("AAA", "AAAAA");
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.d("BBB", "BBBBBBB");
                    }
                });
        }
    }
}

