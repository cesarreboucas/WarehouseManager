package com.warehousemanager.ui.client.shopping_cart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartList extends Fragment {


    public ShoppingCartList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_shopping_cart_list, container, false);
    }

}
