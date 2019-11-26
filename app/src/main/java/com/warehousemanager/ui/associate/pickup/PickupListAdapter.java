package com.warehousemanager.ui.associate.pickup;

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
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

public class PickupListAdapter extends RecyclerView.Adapter<PickupListAdapter.PickupListViewHolder> implements View.OnClickListener {

    private List<ClientOrder> pickupList;

    private Fragment fragment;

    public static final int REPORT_pickup = 1;
    public static final int SCAN_pickup = 2;

    public PickupListAdapter(List<ClientOrder> pickupList, Fragment fragment) {
        this.pickupList = pickupList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PickupListAdapter.PickupListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_associate_pickup_list_row, viewGroup, false);
        PickupListAdapter.PickupListViewHolder pickupListViewHolder = new PickupListAdapter.PickupListViewHolder(view);
        return pickupListViewHolder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDetailedReport:
                onBtnReportClicked(v);
                break;
            case R.id.btnCompleteTransaction:
                onBtnCompleteTransactionClicked(v);
                break;
        }
    }

    private void onBtnReportClicked(View v){
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = pickupList.get(i);
        m.what = REPORT_pickup;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

    private void onBtnCompleteTransactionClicked(View v){
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = pickupList.get(i);
        m.what = SCAN_pickup;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupListAdapter.PickupListViewHolder pickupListViewHolder, int i) {
        // pickup How do we get the associate's assigned warehouse to compare with warehouse_receiver? 
        //  String transferType = pickupList.get(i).getTransferType();
/*
        String item = pickupList.get(i).getProductName();
        int quantity = pickupList.get(i).getQuantity();

        pickupListViewHolder.btnScan.setOnClickListener(this);
        pickupListViewHolder.btnScan.setTag(i);
        pickupListViewHolder.btnReport.setOnClickListener(this);
        pickupListViewHolder.btnReport.setTag(i);

        //pickupListViewHolder.txtTransferType.setText(transferType);
        pickupListViewHolder.txtItemCount.setText(quantity);
        pickupListViewHolder.txtItemName.setText(item);
*/


    }

    @Override
    public int getItemCount() {
        return pickupList.size();
    }

    public class PickupListViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransferType;
        TextView txtItemCount;
        TextView txtItemName;

        Button btnReport;
        Button btnScan;

        public PickupListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransferType = itemView.findViewById(R.id.txtTransferType);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);

            btnReport = itemView.findViewById(R.id.btnDetailedReport);
            btnScan = itemView.findViewById(R.id.btnScan);
        }
    }
}
