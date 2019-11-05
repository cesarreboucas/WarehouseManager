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

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;



public class ProductDetailClientFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    Product product = new Product();

    ImageView picture;
    TextView txtName;
    TextView txtDescription;
    TextView txtPrice;
    Button btnAddToCart;
    FragmentManagerHelper fragmentManagerHelper;

    WarehouseDatabase warehouseDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
        product.setQuantity(1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_product_detail,container,false);

        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsClientContainer);

        warehouseDatabase = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());

        btnAddToCart = view.findViewById(R.id.btnAddCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warehouseDatabase.productDao().insertProduct(product);
                Toast.makeText(getContext(), "Product added to the shopping cart.", Toast.LENGTH_SHORT).show();

            }
        });

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
