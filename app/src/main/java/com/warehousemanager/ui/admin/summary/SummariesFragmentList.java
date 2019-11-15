package com.warehousemanager.ui.admin.summary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Order;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummariesFragmentList extends Fragment {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView summariesList;

  public SummariesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.summariesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_sumaries_fragment_list, container, false);

    summariesList = view.findViewById(R.id.summariesList);

    final List<Order> ordersList = new ArrayList<>();
    final SummariesListAdapter summariesListAdapter = new SummariesListAdapter(ordersList);

    IWarehouseService warehouseService = new WarehouseService().getInstance().create(IWarehouseService.class);

    warehouseService.getAllOrders().enqueue(new Callback<List<Order>>() {
      @Override
      public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
        ordersList.addAll(response.body());
        summariesListAdapter.notifyDataSetChanged();
        Log.d("DBG", ordersList.size()+"");
      }

      @Override
      public void onFailure(Call<List<Order>> call, Throwable t) {

      }
    });

    summariesList.setLayoutManager(new LinearLayoutManager(getContext()));
    summariesList.setAdapter(summariesListAdapter);
    return view;
  }

}
