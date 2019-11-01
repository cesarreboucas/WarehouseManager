package com.warehousemanager.ui.admin.warehouse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.type.LatLng;
import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;

import java.util.List;

public class WarehousesListAdapter extends RecyclerView.Adapter<WarehousesListAdapter.WarehouseListViewHolder> {

  private List<Warehouse> warehouseList;

  public WarehousesListAdapter(List<Warehouse> warehouseList) {
    this.warehouseList = warehouseList;
  }

  @NonNull
  @Override
  public WarehouseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_admin_warehouses_list_row, viewGroup, false);
    WarehouseListViewHolder warehouseListViewHolder = new WarehouseListViewHolder(view);
    return warehouseListViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull WarehouseListViewHolder warehouseListViewHolder, int i) {
    String name = warehouseList.get(i).getName();
    String location = warehouseList.get(i).getLocation();
    int workerCount = warehouseList.get(i).getWorkerCount();
    int capacity = warehouseList.get(i).getCapacity();

    warehouseListViewHolder.txtWarehouseName.setText(name);
    warehouseListViewHolder.txtLocationGeo.setText(location);
    warehouseListViewHolder.txtWorkerCount.setText(String.valueOf(workerCount));
    warehouseListViewHolder.txtCapacity.setText(String.valueOf(capacity));
  }

  @Override
  public int getItemCount() {
    return warehouseList.size();
  }

  public class WarehouseListViewHolder extends RecyclerView.ViewHolder {
    TextView txtWarehouseName;
    TextView txtLocationGeo;
    TextView txtWorkerCount;
    TextView txtCapacity;

    public WarehouseListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtWarehouseName = itemView.findViewById(R.id.txtWarehouseName);
      txtLocationGeo = itemView.findViewById(R.id.txtLocationGeo);
      txtWorkerCount = itemView.findViewById(R.id.txtWorkerCount);
      txtCapacity = itemView.findViewById(R.id.txtCapacity);
    }
  }

}
