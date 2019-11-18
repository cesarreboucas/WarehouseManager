package com.warehousemanager.ui.admin.product;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveProductsFragment extends Fragment implements View.OnClickListener {

    IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    Product product;
    List<String> warehouseList = new ArrayList<>();

    Spinner spnSenderWarehouse;
    ArrayAdapter<String> spnSenderWarehouseAdapter;
    List<String> senderWarehouseList = new ArrayList<>();

    Spinner spnReceiverWarehouse;
    ArrayAdapter<String> spnReceiverWarehouseAdapter;
    List<String> receiverWarehouseList = new ArrayList<>();

    ProgressBar progressBar;

    EditText Quantity;
    Button btnMove;

    public MoveProductsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_move_products, container, false);

        spnSenderWarehouse = view.findViewById(R.id.spnSenderWarehouse);
        spnSenderWarehouseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, senderWarehouseList);
        spnSenderWarehouse.setAdapter(spnSenderWarehouseAdapter);

        spnReceiverWarehouse = view.findViewById(R.id.spnReceiverWarehouse);
        spnReceiverWarehouseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, receiverWarehouseList);
        spnReceiverWarehouse.setAdapter(spnReceiverWarehouseAdapter);

        progressBar = new ProgressBar(getContext());

        warehouseService.getAllWarehouse().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Warehouse gathered", Toast.LENGTH_SHORT).show();
                    List<String> strWarehouseList = new ArrayList<>();
                    for (Warehouse w : response.body()) {
                        strWarehouseList.add(w.getName());
                    }
                    warehouseList.addAll(strWarehouseList);
                    senderWarehouseList.addAll(strWarehouseList);
                    receiverWarehouseList.addAll(strWarehouseList);
                    spnReceiverWarehouseAdapter.notifyDataSetChanged();
                    spnSenderWarehouseAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "There was a problem gathering the warehouses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getContext(), "There was a problem connecting to the server", Toast.LENGTH_SHORT).show();
            }
        });

        Quantity = view.findViewById(R.id.Quantity);
        btnMove = view.findViewById(R.id.btnMove);
        btnMove.setOnClickListener(this);

        return view;
    }

    private List<String> getNewWarehouseList(int position) {
        if(position > warehouseList.size()) return new ArrayList<>(warehouseList);
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < warehouseList.size(); i++) {
            if(i != position) {
                newList.add(warehouseList.get(i));
            }
        }
        return newList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMove:
                onBtnMoveClick(v);
                break;
        }
    }

    private void onBtnMoveClick(View v) {
        String senderWarehouse = spnSenderWarehouse.getSelectedItem().toString();
        String receiverWarehouse = spnReceiverWarehouse.getSelectedItem().toString();
        int quantity = Integer.parseInt(Quantity.getText().toString());

        MovementOrder movementOrder = new MovementOrder();
        movementOrder.setWarehouse_sender(senderWarehouse);
        movementOrder.setWarehouse_receiver(receiverWarehouse);
        movementOrder.setQuantity(quantity);
        movementOrder.setProductKey(product.getBarcode());
        movementOrder.setProductName(product.getName());
        movementOrder.setError(false);
        movementOrder.setSent(false);
        movementOrder.setUser("");

        progressBar.setVisibility(View.VISIBLE);
        warehouseService.createMovementOrder(movementOrder).enqueue(new Callback<MovementOrder>() {
            @Override
            public void onResponse(Call<MovementOrder> call, Response<MovementOrder> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Movement order create successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "There was a problem when  trying to create a movement order", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovementOrder> call, Throwable t) {
                Toast.makeText(getContext(), "There was a problem when trying to contact the server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
