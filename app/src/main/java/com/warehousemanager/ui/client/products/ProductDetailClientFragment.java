package com.warehousemanager.ui.client.products;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class ProductDetailClientFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    Product product = new Product();

    ImageView picture;
    TextView txtName;
    TextView txtDescription;
    TextView txtPrice;
    FragmentManagerHelper fragmentManagerHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_product_detail,container,false);

        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsClientContainer);

        picture = view.findViewById(R.id.picture);
        txtName = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtPrice = view.findViewById(R.id.txtPrice);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(product.getBarcode()!="") {
            txtName.setText(product.getName());
            txtDescription.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getSalePrice()));
            ImageHelper imageHelper = new ImageHelperImpl();
            Bitmap pic = imageHelper.convertBase64ToBitmap(product.getPicture());
            picture.setImageBitmap(pic);
        }

    }
}
