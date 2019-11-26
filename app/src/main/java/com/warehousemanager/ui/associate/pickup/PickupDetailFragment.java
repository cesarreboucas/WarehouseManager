package com.warehousemanager.ui.associate.pickup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

public class PickupDetailFragment 
{
    MovementOrder movementOrder = new MovementOrder();

    TextView txtOrderNumber;

    EditText editReason;

    IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    FragmentManagerHelper fragmentManagerHelper;
/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associate_pickup_report, container, false);

        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.pickupFragmentContainer);

        txtOrderNumber = view.findViewById(R.id.txtOrderNumber);

        editReason = view.findViewById(R.id.editReason);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        try{
            // pickup The movements endpoints
            //warehouseService.setReportType(spnReason.getSelectedItem().toString());
            //warehouseService.setProblem(editReason.getText().toString());

        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        // pickup The movements endpoints
        /*
        warehouseService.createMovementOrder(movementOrder).enqueue(new Callback<MovementOrder>() {
            @Override
            public void onResponse(Call<MovementOrder> call, Response<MovementOrder> response) {
                Toast.makeText(v.getContext(), "MovementOrder Created", Toast.LENGTH_SHORT).show();
                fragmentManagerHelper.goBack();
            }

            @Override
            public void onFailure(Call<MovementOrder> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error creating MovementOrder", Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
