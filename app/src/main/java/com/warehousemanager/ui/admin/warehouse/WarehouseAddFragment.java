package com.warehousemanager.ui.admin.warehouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseAddFragment extends Fragment implements View.OnClickListener {
    Warehouse warehouse = new Warehouse();

    TextView txtWarehouseName;
    TextView txtLocationGeo;
    TextView txtWorkerCount;
    TextView txtCapacity;

    FragmentManagerHelper fragmentManagerHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_warehouses_add, container, false);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);

        txtWarehouseName = view.findViewById(R.id.txtWarehouseName_add);
        txtLocationGeo = view.findViewById(R.id.txtWarehouseGeo_add);
        txtWorkerCount = view.findViewById(R.id.txtWorkerCount_add);
        txtCapacity = view.findViewById(R.id.txtCapacity_add);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        try{
            warehouse.setName(txtWarehouseName.getText().toString());
            warehouse.setLocation(txtLocationGeo.getText().toString());
            warehouse.setWorkerCount(Integer.parseInt(txtWorkerCount.getText().toString()));
            warehouse.setCapacity(Integer.parseInt(txtCapacity.getText().toString()));

        } catch (Exception e) {

        }


        IWarehouseService warehouseService = WarehouseService.getInstance()
                .create(IWarehouseService.class);
        warehouseService.createWarehouse(warehouse).enqueue(new Callback<Warehouse>() {
            @Override
            public void onResponse(Call<Warehouse> call, Response<Warehouse> response) {
                Toast.makeText(v.getContext(), "warehouse Created", Toast.LENGTH_SHORT).show();
                fragmentManagerHelper.goBack();
            }

            @Override
            public void onFailure(Call<Warehouse> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error creating warehouse", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }
