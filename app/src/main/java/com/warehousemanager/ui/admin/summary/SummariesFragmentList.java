package com.warehousemanager.ui.admin.summary;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.WarehouseHang;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.product.AddProductsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummariesFragmentList extends Fragment implements FragmentInteraction {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView summariesList;
  final List<ClientOrder> ordersList = new ArrayList<>();
  final SummariesListAdapter summariesListAdapter = new SummariesListAdapter(ordersList, this);
  final List<ProductHang> productsHangs = new ArrayList<>();
  boolean hangsDone = false;

  public SummariesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.summariesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_sumaries_fragment_list, container, false);

    summariesList = view.findViewById(R.id.summariesList);



    IWarehouseService warehouseService = new WarehouseService().getInstance().create(IWarehouseService.class);

    warehouseService.getAllOrders().enqueue(new Callback<List<ClientOrder>>() {
      @Override
      public void onResponse(Call<List<ClientOrder>> call, Response<List<ClientOrder>> response) {
        ordersList.clear();
        ordersList.addAll(response.body());
        summariesListAdapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Call<List<ClientOrder>> call, Throwable t) {
          Log.d("DBX", t.getMessage());
      }
    });

    summariesList.setLayoutManager(new LinearLayoutManager(getContext()));
    summariesList.setAdapter(summariesListAdapter);
    return view;
  }


  @Override
  public void sendMessage(Message message) {
    ClientOrder clientOrder = (ClientOrder) message.obj;
    Bundle bundle = new Bundle();
    bundle.putSerializable("clientOrder", clientOrder);
    switch (message.what) {
      case 1: // Add/Edit Product
        fragmentManagerHelper.attach(OrderDatail.class, bundle);
        break;
    }
  }
}
