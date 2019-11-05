package com.warehousemanager.ui.admin.warehouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseDetailFragment extends Fragment
        implements View.OnClickListener {

    private IWarehouseService warehouseService =
            WarehouseService.getInstance().create(IWarehouseService.class);

    private FragmentManagerHelper fragmentManagerHelper;

    private EditText editName;
    private EditText editLocation;
    private EditText editCapacity;

    private Button btnEditWarehouse;
    private Button btnRemoveWarehouse;

    private Warehouse warehouse;

    public WarehouseDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if(getArguments().getSerializable("WAREHOUSE") != null) {
                this.warehouse = (Warehouse) getArguments().getSerializable("WAREHOUSE");
            }
        } catch (NullPointerException ex) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_warehouses_detail, container, false);

        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.warehousesFragmentContainer);

        btnEditWarehouse = view.findViewById(R.id.btnEditWarehouse);
        btnEditWarehouse.setOnClickListener(this);
        btnRemoveWarehouse = view.findViewById(R.id.btnRemoveWarehouse);
        btnRemoveWarehouse.setOnClickListener(this);

        editName = view.findViewById(R.id.editName);
        editLocation = view.findViewById(R.id.editLocation);
        editCapacity = view.findViewById(R.id.editCapacity);

        setWarehouseFields();

        return view;
    }

    private void setWarehouseFields() {

        editName.setText(warehouse.getName());
        editLocation.setText(warehouse.getLocation());
        editCapacity.setText(String.valueOf(warehouse.getCapacity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditWarehouse:
                onBtnEditWarehouseClicked(v);
                break;
            case R.id.btnRemoveWarehouse:
                onBtnRemoveWarehouseClicked(v);
                break;
        }
    }

    private void onBtnRemoveWarehouseClicked(View v) {
    }

    private void onBtnEditWarehouseClicked(View v) {
        try{
            warehouse.setName(editName.getText().toString());
            warehouse.setLocation(editLocation.getText().toString());
            warehouse.setWorkerCount(0);
            warehouse.setCapacity(Integer.parseInt(editCapacity.getText().toString()));

        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }


        IWarehouseService warehouseService = WarehouseService.getInstance()
                .create(IWarehouseService.class);
        warehouseService.createWarehouse(warehouse).enqueue(new Callback<Warehouse>() {
            @Override
            public void onResponse(Call<Warehouse> call, Response<Warehouse> response) {
                Toast.makeText(getContext(), "warehouse Created", Toast.LENGTH_SHORT).show();
                fragmentManagerHelper.goBack();
            }

            @Override
            public void onFailure(Call<Warehouse> call, Throwable t) {
                Toast.makeText(getContext(), "Error creating warehouse", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
