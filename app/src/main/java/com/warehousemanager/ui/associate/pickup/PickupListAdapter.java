package com.warehousemanager.ui.associate.pickup;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupListAdapter extends RecyclerView.Adapter<PickupListAdapter.PickupListViewHolder> implements View.OnClickListener {

    private IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    private List<ClientOrder> pickupList;

    private Fragment fragment;

    public static final int REPORT_pickup = 1;
    public static final int COMPLETE_pickup = 2;

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
                Log.d("BtnReportClicked", "Clicked");
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

    @Override
    public void onBindViewHolder(@NonNull PickupListAdapter.PickupListViewHolder pickupListViewHolder, final int i) {
        int orderNum = pickupList.get(i).getId();
        long custName = pickupList.get(i).getClientID();

        pickupListViewHolder.txtOrderNumber.setText(String.valueOf(orderNum));
        pickupListViewHolder.txtCustName.setText(String.valueOf(custName));
        pickupListViewHolder.btnDetailedReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = pickupList.get(i);
                m.what = REPORT_pickup;
                ((FragmentInteraction)fragment).sendMessage(m);
            }
        });
        pickupListViewHolder.btnCompleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickupList.get(i).setDone(1);
                warehouseService.setOrderComplete(pickupList.get(i)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(fragment.getContext(), "Pickup Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        pickupListViewHolder.itemView.setBackgroundColor(Color.CYAN);

    }

    @Override
    public int getItemCount() {
        return pickupList.size();
    }

    public class PickupListViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderNumber;
        TextView txtCustName;

        ImageButton btnDetailedReport;
        ImageButton btnCompleteTransaction;

        public PickupListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
            txtCustName = itemView.findViewById(R.id.txtCustName);

            btnDetailedReport = itemView.findViewById(R.id.btnDetailedReport);
            btnCompleteTransaction = itemView.findViewById(R.id.btnCompleteTransaction);
        }
    }
}
