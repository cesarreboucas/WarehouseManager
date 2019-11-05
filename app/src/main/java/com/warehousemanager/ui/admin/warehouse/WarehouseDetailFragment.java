package com.warehousemanager.ui.admin.warehouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

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
        try {if(getArguments().getSerializable("WAREHOUSE") != null) {
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
        editCapacity.setText(warehouse.getCapacity());
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
    }
}
