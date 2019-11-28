package com.warehousemanager.ui.client.shopping_cart;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.What;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartListFragment extends Fragment implements FragmentInteraction,
        SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView shoppingCartRecyclerView;
    private IFragmentManagerHelper fragmentManagerHelper;
    private List<Product> products = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private final ShoppingCartAdapter shoppingCartAdapter = new ShoppingCartAdapter(products, this);
    private WarehouseDatabase warehouseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_client_shopping_cart_list, container, false);
        shoppingCartRecyclerView = view.findViewById(R.id.shoppingCartListRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container_client_shopping_cart);
        swipeRefreshLayout.setOnRefreshListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.shoppingCartContainer);
        warehouseDatabase = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());

        getData();
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
        return view;
    }

    public void getData() {
        products.clear();
        products.addAll(warehouseDatabase.productDao().getProducts());
        shoppingCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendMessage(Message message) {
        Product product = (Product)message.obj;
        switch (message.what) {
            case What.CREATE:
                warehouseDatabase.productDao().insertProduct(product);
                shoppingCartAdapter.notifyDataSetChanged();
                break;
            case What.UPDATE:
                warehouseDatabase.productDao().updateProduct(product);
                shoppingCartAdapter.notifyDataSetChanged();
                break;
            case What.REMOVE:
                warehouseDatabase.productDao().deleteProduct(product);
                shoppingCartAdapter.notifyDataSetChanged();
                break;
        }
        Message m = new Message();
        m.obj = "Update totals";
        ((FragmentInteraction)getParentFragment()).sendMessage(m);
    }

    @Override
    public void onRefresh() {
        getData();
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000); // Delay in millis
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            getData();
        }
    }
}
