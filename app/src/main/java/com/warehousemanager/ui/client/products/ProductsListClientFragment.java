package com.warehousemanager.ui.client.products;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.product.AddProductsFragment;
import com.warehousemanager.ui.admin.product.ProductsListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListClientFragment extends Fragment implements FragmentInteraction {

    RecyclerView productsListRecyclerView;
    IFragmentManagerHelper fragmentManagerHelper;
    private FloatingActionButton floatingActionButton;
    List<Product> products = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_client_products_list, container, false);
        productsListRecyclerView = view.findViewById(R.id.prodcutsListClientRecyclerView);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsClientContainer);


        //JsonReader jsonReader = new JsonReader(getContext());
        //products = jsonReader.getProducts();

        productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ProductsListAdapter productsListAdapter = new ProductsListAdapter(products, this);
        productsListRecyclerView.setAdapter(productsListAdapter);

        IWarehouseService warehouseService = WarehouseService.getInstance()
                .create(IWarehouseService.class);

        warehouseService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products.addAll(response.body());
                productsListAdapter.notifyDataSetChanged();
                Log.d("DBG", "Data Changed");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void sendMessage(Message message) {
        Log.d("AAA", message.obj.toString());
    }

}
