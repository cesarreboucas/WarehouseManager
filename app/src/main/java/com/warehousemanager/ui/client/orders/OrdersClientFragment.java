package com.warehousemanager.ui.client.orders;


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

public class OrdersClientFragment extends Fragment implements FragmentInteraction {

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;

    public OrdersClientFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_orders, container, false);
        fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.ordersFragmentContainer);
        fragmentManagerHelper.attach(OrdersListClientFragment.class);

        Toolbar toolbar = view.findViewById(R.id.toolbarClientOrders);
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
    public void sendMessage(Message message) {

    }
}
