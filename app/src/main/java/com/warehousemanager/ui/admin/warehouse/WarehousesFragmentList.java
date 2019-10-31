package com.warehousemanager.ui.admin.warehouse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.JsonReader;

import java.util.Arrays;
import java.util.List;

public class WarehousesFragmentList extends Fragment {

  private RecyclerView warehousesList;

  JsonReader jsonReader;
  IFragmentManagerHelper fragmentManagerHelper;

  public WarehousesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_admin_warehouses_fragment_list, container, false);
    jsonReader = new JsonReader(getContext());
    warehousesList = view.findViewById(R.id.warehousesList);

    fragmentManagerHelper = new FragmentManagerHelper(
            getFragmentManager(), R.id.warehousesFragmentContainer);

    warehousesList.setLayoutManager(new LinearLayoutManager(getContext()));
    List<Warehouse> warehouses = jsonReader.getWarehouse();
    WarehousesListAdapter warehouseListAdapter = new WarehousesListAdapter(warehouses);

    warehousesList.setAdapter(warehouseListAdapter);

    return view;
  }

}
