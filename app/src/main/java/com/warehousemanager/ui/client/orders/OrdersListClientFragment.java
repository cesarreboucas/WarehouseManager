package com.warehousemanager.ui.client.orders;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersListClientFragment extends Fragment implements FragmentInteraction,
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView ordersListRecyclerView;
    private IFragmentManagerHelper fragmentManagerHelper;
    private FloatingActionButton floatingActionButton;
    private List<ClientOrder> clientOrders = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrdersListClientAdapter ordersListAdapter;
    private IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);
    private WarehouseDatabase warehouseDatabase;


    public OrdersListClientFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_orders_list, container, false);
        ordersListRecyclerView = view.findViewById(R.id.ordersListClientRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container_client_orders);
        swipeRefreshLayout.setOnRefreshListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.ordersFragmentContainer);
        warehouseDatabase = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());
        ordersListAdapter = new OrdersListClientAdapter(clientOrders, this);

        getData();
        ordersListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersListRecyclerView.setAdapter(ordersListAdapter);
        return view;
    }

    public void getData() {
        long userID = warehouseDatabase.userDao().getUser().getId();
        warehouseService.getOrdersByUser(userID).enqueue(new Callback<List<ClientOrder>>() {
            @Override
            public void onResponse(Call<List<ClientOrder>> call, Response<List<ClientOrder>> response) {
                if(response.isSuccessful()) {
                    clientOrders.clear();
                    clientOrders.addAll(response.body());
                    ordersListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ClientOrder>> call, Throwable t) {
            }
        });
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
    public void sendMessage(Message message) {

        ClientOrder clientOrder = (ClientOrder) message.obj;
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", clientOrder);
        fragmentManagerHelper.attach(OrderDetailClientFragment.class, bundle);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            getData();
        }
    }
}
