package com.warehousemanager.ui.admin.warehouse;

import android.app.Notification;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

public class WarehousesListAdapter extends RecyclerView.Adapter<WarehousesListAdapter.WarehouseListViewHolder> implements View.OnClickListener {

  private List<Warehouse> warehouseList;
  private Fragment fragment;

  public static final int EDIT_WAREHOUSE = 1;

  public WarehousesListAdapter(List<Warehouse> warehouseList, Fragment fragment) {
    this.warehouseList = warehouseList;
    this.fragment = fragment;
  }

  @NonNull
  @Override
  public WarehouseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_admin_warehouses_list_row, viewGroup, false);
    WarehouseListViewHolder warehouseListViewHolder = new WarehouseListViewHolder(view);
    return warehouseListViewHolder;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnEdit:
        onBtnEditClicked(v);
        break;
    }
  }

  private void onBtnEditClicked(View v){
    int i = (int) v.getTag();

    Message m = new Message();
    m.obj = warehouseList.get(i);
    m.what = EDIT_WAREHOUSE;
    ((FragmentInteraction)fragment).sendMessage(m);
  }

  @Override
  public void onBindViewHolder(@NonNull WarehouseListViewHolder warehouseListViewHolder, int i) {
    String name = warehouseList.get(i).getName();
    String location = warehouseList.get(i).getLocation();
    int workerCount = warehouseList.get(i).getWorkerCount();
    int capacity = warehouseList.get(i).getCapacity();

    warehouseListViewHolder.btnEdit.setOnClickListener(this);
    warehouseListViewHolder.btnEdit.setTag(i);

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
    Button btnEdit;

    public WarehouseListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtWarehouseName = itemView.findViewById(R.id.txtWarehouseName);
      txtLocationGeo = itemView.findViewById(R.id.txtLocationGeo);
      txtWorkerCount = itemView.findViewById(R.id.txtWorkerCount);
      txtCapacity = itemView.findViewById(R.id.txtCapacity_add);
      btnEdit = itemView.findViewById(R.id.btnEdit);
    }
  }

}
