package com.warehousemanager.ui.admin.warehouse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;

import java.util.Arrays;

public class WarehousesFragmentList extends Fragment {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView warehousesList;

  public WarehousesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.warehousesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_warehouses_fragment_list, container, false);

    warehousesList = view.findViewById(R.id.warehousesList);
    WarehousesListAdapter warehouseListAdapter = new WarehousesListAdapter(Arrays.asList("Warehouse1", "Warehouse2", "..."));
    warehousesList.setLayoutManager(new LinearLayoutManager(getContext()));
    warehousesList.setAdapter(warehouseListAdapter);
    return view;
  }

}
