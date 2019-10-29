package com.warehousemanager.ui.admin.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

public class ProductsFragment extends Fragment implements FragmentInteraction {

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;

    public ProductsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_admin_products, container, false);
        fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.productsFragmentContainer);
        fragmentManagerHelper.attach(ProductsFragmentList.class);

        Toolbar toolbar = view.findViewById(R.id.toolbarProdMain);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManagerHelper.goBack();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void sendMessage(Message message) {   }
}
