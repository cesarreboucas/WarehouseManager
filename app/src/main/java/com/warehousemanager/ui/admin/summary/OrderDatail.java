package com.warehousemanager.ui.admin.summary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;

public class OrderDatail extends Fragment {

    ClientOrder clientOrder;

    public OrderDatail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientOrder = (ClientOrder) getArguments().getSerializable("clientOrder");
        Log.d("DBX", clientOrder.getId()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_order_datail, container, false);
    }
}
