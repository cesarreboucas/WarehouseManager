package com.warehousemanager.ui.client.shopping_cart;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends Fragment implements FragmentInteraction{

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;
    private WarehouseDatabase warehouseDatabase;
    private IWarehouseService warehouseService;
    private TextView txtTotals;
    private Spinner spinnerWarehouses;
    private ArrayAdapter<String> arrayAdapterWarehouses;
    private Button btnPlaceOrder;

    public ShoppingCartFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_shopping_cart, container, false);
        fragmentManagerHelper= new FragmentManagerHelper(getChildFragmentManager(), R.id.shoppingCartContainer);
        Bundle bundle = new Bundle();
        bundle.putString("test", "Testando arguments");
        fragmentManagerHelper.attach(ShoppingCartListFragment.class, bundle);
        warehouseDatabase  = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());
        txtTotals = view.findViewById(R.id.txtTotals);

        spinnerWarehouses = view.findViewById(R.id.spinnerWarehouses);
        final List<String> warehouseNames = new ArrayList<>();
        warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);
        warehouseService.getAllWarehouse().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if(response.code() == 200) {
                    List<Warehouse> warehousesList = response.body();
                    for (Warehouse w: warehousesList) {
                        warehouseNames.add(w.getName());
                    }
                    arrayAdapterWarehouses = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_item, warehouseNames);
                    spinnerWarehouses.setAdapter(arrayAdapterWarehouses);
                }
            }
            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
            }
        });


        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long userId = warehouseDatabase.userDao().getUser().getId();
                Log.d("Userid", String.valueOf(userId));
                String selectedWarehouse = spinnerWarehouses.getSelectedItem().toString();
                List<Product> productList = warehouseDatabase.productDao().getProducts();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000");
                String currentDateTime = dateFormat.format(new Date());

                ClientOrder clientOrder = new ClientOrder(userId, selectedWarehouse, productList, currentDateTime);
                warehouseService.createOrder(clientOrder).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext() , "Order placed", Toast.LENGTH_SHORT).show();
                            warehouseDatabase.productDao().deleteAllProducts();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    private void updateTotals() {
        List<Product> products = warehouseDatabase.productDao().getProducts();
        double total = 0;
        for(Product p : products) {
            total += (p.getQuantity() * p.getSalePrice());
        }
        txtTotals.setText(String.format("$%.2f", total)
                + "\n" + String.format("$%.2f", total * 0.12)
                + "\n" + String.format("$%.2f", total * 1.12));
    }

    @Override
    public void sendMessage(Message message) {
        if(message.obj.toString().equals("Update totals")) {
            updateTotals();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTotals();
    }
}
