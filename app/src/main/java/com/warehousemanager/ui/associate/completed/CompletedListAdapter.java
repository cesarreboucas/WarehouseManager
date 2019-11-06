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
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.warehouse.WarehousesListAdapter;

import java.util.List;

public class CompletedListAdapter extends RecyclerView.Adapter<CompletedListAdapter.CompletedListViewHolder> implements View.OnClickListener {

    private List<MovementOrder> completedList;
    private Fragment fragment;

    public static final int EDIT_COMPLETED = 1;

    public CompletedListAdapter(List<MovementOrder> completedList, Fragment fragment) {
        this.completedList = completedList;
        this.fragment = fragment;
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

    }

    @Override
    public int getItemCount() {
        return completedList.size();
    }

    public class CompletedListViewHolder extends RecyclerView.ViewHolder {

        public CompletedListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
