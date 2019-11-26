package com.warehousemanager.ui.associate.pickup;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public static final int ROW_Pickup = 1;
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
    public void onClick(View c)
    {

    }

    private void onRowClick(View v){
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = pickupList.get(i);
        m.what = ROW_Pickup;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupListAdapter.PickupListViewHolder pickupListViewHolder, int i) {
        // TODO set row to onclick show details
        String orderNumber = String.valueOf(pickupList.get(i).getId());
        String clientID = String.valueOf(pickupList.get(i).getClientID());

        pickupListViewHolder.txtOrderNumber.setText(orderNumber);
        pickupListViewHolder.txtCustName.setText(clientID);

        pickupListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRowClick(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pickupList.size();
    }

    public class PickupListViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderNumber;
        TextView txtCustName;

        //Button btnScan;

        public PickupListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
            txtCustName = itemView.findViewById(R.id.txtCustName);

            //btnScan = itemView.findViewById(R.id.btnScan);
        }
    }
}
