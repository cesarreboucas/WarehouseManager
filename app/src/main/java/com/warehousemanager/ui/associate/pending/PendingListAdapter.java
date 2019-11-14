package com.warehousemanager.ui.associate.pending;

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
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.associate.completed.CompletedListAdapter;

import java.util.List;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.PendingListViewHolder> implements View.OnClickListener {

    private List<MovementOrder> pendingList;
    private Fragment fragment;

    public static final int VIEW_REPORT = 1;

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
        switch (v.getId()) {
            case R.id.btnViewReport:
                onBtnViewReport(v);
                break;
        }
    }

    public void onBtnViewReport(View v)
    {
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = pendingList.get(i);
        m.what = VIEW_REPORT;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingListViewHolder pendingListViewHolder, int i) {
        String transferType = pendingList.get(i).getTransferType();
        Product item = pendingList.get(i).getItem();
        String itemName = item.getName();
        int itemCount = item.getQuantity();
        String reportType = pendingList.get(i).getReportType();
        String user = pendingList.get(i).getUsername().toString();

        pendingListViewHolder.btnViewReport.setOnClickListener(this);
        pendingListViewHolder.btnViewReport.setTag(i);

        pendingListViewHolder.txtTransferType.setText(transferType);
        pendingListViewHolder.txtItemCount.setText(itemCount);
        pendingListViewHolder.txtItemName.setText(itemName);
        pendingListViewHolder.txtReportType.setText(reportType);
        pendingListViewHolder.txtUser.setText(user);
    }

    @Override
    public int getItemCount() {
            return pendingList.size();
            }

    public class PendingListViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransferType;
        TextView txtItemCount;
        TextView txtItemName;
        TextView txtReportType;
        TextView txtUser;

        Button btnViewReport;

        public PendingListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransferType = itemView.findViewById(R.id.txtTransferType);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);
            txtReportType = itemView.findViewById(R.id.txtReportType);
            txtUser = itemView.findViewById(R.id.txtUser);

            btnViewReport = itemView.findViewById(R.id.btnViewReport);
        }
    }
}


