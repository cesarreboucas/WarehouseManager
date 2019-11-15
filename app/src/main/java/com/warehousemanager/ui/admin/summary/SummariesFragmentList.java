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
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.WarehouseHang;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummariesFragmentList extends Fragment {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView summariesList;
  final List<ClientOrder> ordersList = new ArrayList<>();
  final SummariesListAdapter summariesListAdapter = new SummariesListAdapter(ordersList);
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


    warehouseService.getAllProductsHangs().enqueue(new Callback<List<ProductHang>>() {
      @Override
      public void onResponse(Call<List<ProductHang>> call, Response<List<ProductHang>> response) {
        productsHangs.clear();
        productsHangs.addAll(response.body());
        hangsDone = true;
        SetHangsInOrders();

      }

      @Override
      public void onFailure(Call<List<ProductHang>> call, Throwable t) {
        Log.d("DBX", t.getMessage());
      }
    });

    warehouseService.getAllOrders().enqueue(new Callback<List<ClientOrder>>() {
      @Override
      public void onResponse(Call<List<ClientOrder>> call, Response<List<ClientOrder>> response) {
        ordersList.clear();
        ordersList.addAll(response.body());
        SetHangsInOrders();

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

  public void SetHangsInOrders() {
    // Execute only when hangs are Done
    if(ordersList.size() > 0 && productsHangs.size() > 0) {
      for(int x=0; x < ordersList.size(); ++x) {
        boolean outOfStock = false;
        for(Product p : ordersList.get(x).getProducts()) {
          Log.d("DBX", "Prod: "+p.getBarcode() + " WH: "+ordersList.get(x).getWarehouseKey());
          for(ProductHang pHang : productsHangs) {
            // Same Product
            if(pHang.getBarcode().equals(p.getBarcode())) {
              for(WarehouseHang whhang : pHang.getWarehouses()) {
                // Same Product and Same Warehouse
                if(whhang.getWarehouse_key().equals(ordersList.get(x).getWarehouseKey())) {
                  if(whhang.getFreeQuantity() < p.getQuantity()) {
                    ordersList.get(x).setOutOfStock(true);
                    Log.d("DBX", "Set OOS to Order "+ordersList.get(x).getId());
                    outOfStock = true;
                    break;
                  }
                }
              }
              if(outOfStock) {
                break;
              }
            }
          }
          if(outOfStock) {
            break;
          }
        }

      }
      summariesListAdapter.notifyDataSetChanged();
    } else if(ordersList.size() > 0 && hangsDone) { // hangs gotten but empty (no problems)
      for(int x=0; x < ordersList.size(); ++x) {
        ordersList.get(x).setOutOfStock(false);
      }
      summariesListAdapter.notifyDataSetChanged();
    }
  }

}
