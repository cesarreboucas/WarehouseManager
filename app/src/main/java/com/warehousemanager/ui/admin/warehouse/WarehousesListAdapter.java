package com.warehousemanager.ui.admin.warehouse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;

import java.util.List;

public class WarehousesListAdapter extends RecyclerView.Adapter<WarehousesListAdapter.WarehouseListViewHolder> {

  private List<String> warehouseList;

  public WarehousesListAdapter(List<String> warehouseList) {
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
    warehouseListViewHolder.textView.setText(warehouseList.get(i));
  }

  @Override
  public int getItemCount() {
    return warehouseList.size();
  }

  public class WarehouseListViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public WarehouseListViewHolder(@NonNull View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.textView);
    }
  }

}
