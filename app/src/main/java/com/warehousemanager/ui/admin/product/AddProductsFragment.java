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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductsFragment extends Fragment implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    Product product = new Product();

    ImageView picture;
    TextView txtName;
    TextView txtDescription;
    TextView txtCost;
    TextView txtPrice;
    TextView txtBarcode;
    Button btnAction;
    FragmentManagerHelper fragmentManagerHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_products_add,container,false);

        Button btnLoadImage = view.findViewById(R.id.buttonLoadPicture);
        btnAction = view.findViewById(R.id.btnAdd);
        btnLoadImage.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);

        picture = view.findViewById(R.id.picture);
        txtName = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtCost = view.findViewById(R.id.txtCost);
        txtBarcode = view.findViewById(R.id.txtBarcode);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(product.getBarcode()!="") {
            txtName.setText(product.getName());
            txtDescription.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtCost.setText(String.valueOf(product.getCost()));
            txtBarcode.setText(product.getBarcode());
            ImageHelper imageHelper = new ImageHelperImpl();
            Bitmap pic = imageHelper.convertBase64ToBitmap(product.getPicture());
            picture.setImageBitmap(pic);
            btnAction.setText("Save");
        }

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.buttonLoadPicture:
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        ImageHelper imageHelper = new ImageHelperImpl();
                        String pic = imageHelper.convertBitmapToBase64Resized(r.getBitmap());
                        product.setPicture(pic);
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
                //product = new Product();
                try{
                    product.setName(txtName.getText().toString());
                    product.setDescription(txtDescription.getText().toString());
                    product.setCost(Double.parseDouble(txtCost.getText().toString()));
                    product.setPrice(Double.parseDouble(txtPrice.getText().toString()));
                    product.setBarcode(txtBarcode.getText().toString());

                } catch (Exception e) {

                }

                IWarehouseService warehouseService = WarehouseService.getInstance()
                        .create(IWarehouseService.class);
                warehouseService.createProduct(product).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                        fragmentManagerHelper.goBack();
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(v.getContext(), "Error creating product", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}

