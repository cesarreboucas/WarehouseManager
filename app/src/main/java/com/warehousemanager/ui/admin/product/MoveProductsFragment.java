package com.warehousemanager.ui.admin.product;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.db.entities.WarehouseHang;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveProductsFragment extends Fragment implements View.OnClickListener {

    IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    Product product;
    List<String> warehouseList = new ArrayList<>();

    ProgressBar progressBar;

    Spinner spnSenderWarehouse;
    ArrayAdapter<String> spnSenderWarehouseAdapter;
    List<String> senderWarehouseList = new ArrayList<>();

    Spinner spnReceiverWarehouse;
    ArrayAdapter<String> spnReceiverWarehouseAdapter;
    List<String> receiverWarehouseList = new ArrayList<>();

    Spinner spnAssociates;
    ArrayAdapter<String> spnAssociatesAdapter;
    List<String> associateList = new ArrayList<>();

    boolean warehousesFlag = false;
    boolean productsHangsFlag = false;

    FragmentManagerHelper fragmentManagerHelper;

    EditText Quantity;
    Button btnMove;

    TableLayout tableLayout;

    public MoveProductsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable("product");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_admin_move_products, container, false);

        spnSenderWarehouse = view.findViewById(R.id.spnSenderWarehouse);
        spnSenderWarehouseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, senderWarehouseList);
        spnSenderWarehouse.setAdapter(spnSenderWarehouseAdapter);

        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);

        spnReceiverWarehouse = view.findViewById(R.id.spnReceiverWarehouse);
        spnReceiverWarehouseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, receiverWarehouseList);
        spnReceiverWarehouse.setAdapter(spnReceiverWarehouseAdapter);
        spnReceiverWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                String warehouseReceiver = receiverWarehouseList.get(position);
                warehouseService.getAssociates(warehouseReceiver).enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()) {
                            for (User user : response.body()) {
                                associateList.add(user.getUsername());
                            }
                            spnAssociatesAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Couldn`t get the associates", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getContext(), "There was a problem connecting to the server", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAssociates = view.findViewById(R.id.spnAssociates);
        spnAssociatesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, associateList);
        spnAssociates.setAdapter(spnAssociatesAdapter);

        progressBar = view.findViewById(R.id.progressBar);

        tableLayout = view.findViewById(R.id.product_hangs_table);

        progressBar = new ProgressBar(getContext());

        progressBar.setVisibility(View.VISIBLE);
        warehouseService.getAllWarehouse().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if(response.isSuccessful()) {
                    List<String> strWarehouseList = new ArrayList<>();
                    for (Warehouse w : response.body()) {
                        strWarehouseList.add(w.getName());
                    }
                    warehouseList.addAll(strWarehouseList);
                    senderWarehouseList.add("");
                    senderWarehouseList.addAll(strWarehouseList);
                    receiverWarehouseList.addAll(strWarehouseList);
                    spnReceiverWarehouseAdapter.notifyDataSetChanged();
                    spnSenderWarehouseAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "There was a problem gathering the warehouses", Toast.LENGTH_SHORT).show();
                }
                warehousesFlag = true;
                if(warehousesFlag && productsHangsFlag) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getContext(), "There was a problem connecting to the server", Toast.LENGTH_SHORT).show();
                warehousesFlag = true;
                if(warehousesFlag && productsHangsFlag) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        warehouseService.getAllProductsHangs().enqueue(new Callback<List<ProductHang>>() {
            @Override
            public void onResponse(Call<List<ProductHang>> call, Response<List<ProductHang>> response) {
                if(response.isSuccessful()) {
                    ProductHang productHang = null;
                    for (ProductHang tmpProductHang : response.body()) {
                        if(product.getBarcode().equals(tmpProductHang.getBarcode())) {
                            productHang = tmpProductHang;
                            break;
                        }
                    }
                    if(productHang == null) {
                        tableLayout.setVisibility(View.INVISIBLE);
                        return;
                    }

                    for (WarehouseHang tmpWarehouseHang : productHang.getWarehouses()) {
                        LayoutInflater inflator = getLayoutInflater();
                        TableRow tableRow = (TableRow) inflator.inflate(R.layout.product_hangs_table_row, null);
                        for (int i = 0; i < tableRow.getChildCount(); i++) {
                            TextView textView = (TextView) tableRow.getChildAt(i);
                            textView.setTextColor(Color.BLACK);
                        }
                        TextView txtWarehouse = tableRow.findViewById(R.id.txtWarehouse);
                        TextView txtSold = tableRow.findViewById(R.id.txtSold);
                        TextView txtCompromised = tableRow.findViewById(R.id.txtCompromised);
                        TextView txtFuture = tableRow.findViewById(R.id.txtFuture);
                        TextView txtInStock = tableRow.findViewById(R.id.txtInStock);

                        txtWarehouse.setText(tmpWarehouseHang.getWarehouse_key());
                        txtSold.setText(tmpWarehouseHang.getQuantity_sold() + "");
                        txtCompromised.setText(tmpWarehouseHang.getQuantity_compromised() + "");
                        txtFuture.setText(tmpWarehouseHang.getQuantity_future_movs() + "");
                        txtInStock.setText(tmpWarehouseHang.getQuantity_in_stock() + "");
                        tableLayout.addView(tableRow);
                    }
                }
                productsHangsFlag = true;
                if(warehousesFlag && productsHangsFlag) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductHang>> call, Throwable t) {
                Toast.makeText(getContext(), "There was a problem connecting to the server", Toast.LENGTH_LONG).show();
                productsHangsFlag = true;
                if(warehousesFlag && productsHangsFlag) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
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

        MovementOrder movementOrder = new MovementOrder(receiverWarehouse, senderWarehouse,
                product.getBarcode(), product.getName(), quantity, false, false,
                new Date().getTime());

        progressBar.setVisibility(View.VISIBLE);
        warehouseService.createMovementOrder(movementOrder).enqueue(new Callback<MovementOrder>() {
            @Override
            public void onResponse(Call<MovementOrder> call, Response<MovementOrder> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), "Movement order create successfully", Toast.LENGTH_SHORT).show();
                    fragmentManagerHelper.goBack();
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
