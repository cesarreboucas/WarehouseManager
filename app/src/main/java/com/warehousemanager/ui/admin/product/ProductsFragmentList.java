package com.warehousemanager.ui.admin.product;

import android.content.Context;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.JsonReader;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragmentList extends Fragment implements FragmentInteraction {

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
        final View view = inflater.inflate(R.layout.fragment_admin_products_list, container, false);
        productsListRecyclerView = view.findViewById(R.id.productsList);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManagerHelper.attach(AddProductsFragment.class);
            }
        });


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