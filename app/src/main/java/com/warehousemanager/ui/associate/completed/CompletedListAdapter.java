package com.warehousemanager.ui.associate.completed;

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
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.warehouse.WarehousesListAdapter;

import java.util.List;

public class CompletedListAdapter extends RecyclerView.Adapter<CompletedListAdapter.CompletedListViewHolder> implements View.OnClickListener {

    private List<MovementOrder> completedList;
    private Fragment fragment;

    WarehouseDatabase warehouseDatabase;

    public static final int EDIT_COMPLETED = 1;

    public CompletedListAdapter(List<MovementOrder> completedList, Fragment fragment) {
        this.completedList = completedList;
        this.fragment = fragment;

        warehouseDatabase = WarehouseDatabase.getAppDatabase(fragment.getActivity().getApplicationContext());
    }

    @NonNull
    @Override
    public CompletedListAdapter.CompletedListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_associate_completed_list_row, viewGroup, false);
        CompletedListAdapter.CompletedListViewHolder completedListViewHolder = new CompletedListAdapter.CompletedListViewHolder(view);
        return completedListViewHolder;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBindViewHolder(@NonNull CompletedListViewHolder completedListViewHolder, int i) {
        String transferType = "";
        if(completedList.get(i).getWarehouseSender().isEmpty()) {
            transferType = "Fulfillment";
        } else {
            transferType = "Transference";
        }
        String id = completedList.get(i).getId();
        String itemName = completedList.get(i).getProductName();
        if(itemName.length() > 10) {
            itemName = itemName.substring(0, 9) + "...";
        }
        int itemCount = completedList.get(i).getQuantity();
        User user = warehouseDatabase.userDao().getUser();

        completedListViewHolder.txtTransferType.setText(transferType);
        completedListViewHolder.txtOrderNumber.setText(id);
        completedListViewHolder.txtItemCount.setText(itemCount + "");
        completedListViewHolder.txtItemName.setText(itemName);
        completedListViewHolder.txtUser.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return completedList.size();
    }

    public class CompletedListViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransferType;
        TextView txtItemCount;
        TextView txtItemName;
        TextView txtOrderNumber;
        TextView txtUser;

        public CompletedListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransferType = itemView.findViewById(R.id.txtTransferType);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
        }
    }
}
