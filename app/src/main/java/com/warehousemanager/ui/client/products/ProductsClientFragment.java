package com.warehousemanager.ui.client.products;


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

public class ProductsClientFragment extends Fragment implements FragmentInteraction {

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;

    public ProductsClientFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_client_products, container, false);
        fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.productsClientContainer);
        Bundle bundle = new Bundle();
        bundle.putString("test", "Testando arguments");
        fragmentManagerHelper.attach(ProductsListClientFragment.class, bundle);

        Toolbar toolbar = view.findViewById(R.id.toolbarClientProducts);
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
