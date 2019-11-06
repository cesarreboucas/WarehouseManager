package com.warehousemanager.ui.associate.pending;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.ui.associate.completed.CompletedListAdapter;

import java.util.List;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.PendingListViewHolder> implements View.OnClickListener {

private List<MovementOrder> pendingList;
private Fragment fragment;

public static final int EDIT_COMPLETED = 1;

public PendingListAdapter(List<MovementOrder> pendingList, Fragment fragment) {
        this.pendingList = pendingList;
        this.fragment = fragment;
        }

@NonNull
@Override
public PendingListAdapter.PendingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_associate_pending_list_row, viewGroup, false);
    PendingListAdapter.PendingListViewHolder pendingListViewHolder = new PendingListAdapter.PendingListViewHolder(view);
        return pendingListViewHolder;
        }

@Override
public void onClick(View v) {

        }

@Override
public void onBindViewHolder(@NonNull PendingListViewHolder pendingListViewHolder, int i) {

        }

@Override
public int getItemCount() {
        return pendingList.size();
        }

    public class PendingListViewHolder extends RecyclerView.ViewHolder {

        public PendingListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}


