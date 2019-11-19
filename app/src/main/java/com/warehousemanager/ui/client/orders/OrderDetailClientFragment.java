package com.warehousemanager.ui.client.orders;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailClientFragment extends Fragment {

    private ClientOrder clientOrder;
    private TextView ordernumber;
    private TextView warehouse;
    private TextView total;
    private TextView date;
    private TextView status;
    private List<Product> productList;

    private IFragmentManagerHelper fragmentManagerHelper;
    private RecyclerView productsListRecyclerView;
    private OrderProductListDetailClientAdapter orderProductListDetailClientAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientOrder = (ClientOrder)getArguments().getSerializable("order");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_order_detail, container, false);
        fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.ordersFragmentContainer);
        fragmentManagerHelper.attach(OrderDetailClientFragment.class);

        ordernumber = view.findViewById(R.id.txtOrderNumberDetail);
        warehouse = view.findViewById(R.id.txtWarehouseOrderDetail);
        total = view.findViewById(R.id.txtTotalOrderDetail);
        date = view.findViewById(R.id.txtDateOrderDetail);
        status = view.findViewById(R.id.txtStatusOrderDetail);

        productsListRecyclerView = view.findViewById(R.id.productsOrderDetailList);
        productList = clientOrder.getProducts();
        orderProductListDetailClientAdapter = new OrderProductListDetailClientAdapter(productList, this);
        productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productsListRecyclerView.setAdapter(orderProductListDetailClientAdapter);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(clientOrder.getWarehouseKey()!= "") {

            List<Product> products = clientOrder.getProducts();

            ordernumber.setText("Order # " + clientOrder.getId());
            warehouse.setText("Warehouse: " + clientOrder.getWarehouseKey());

            double totalPrice = 0;
            for (Product p: products) {
                totalPrice += p.getTotal();
            }
            total.setText(String.format("$%.2f", totalPrice));

            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd - HH:mm");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000");
            String inputDate = clientOrder.getOrdertime();
            Date dateTime = null;
            try {
                dateTime = inputFormat.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDate = outputFormat.format(dateTime);
            date.setText(outputDate);

            int done = clientOrder.isReady();
            int ready = clientOrder.isDone();
            if(done == 1) {
                status.setText("Status: Completed");
            } else if(ready == 1 && done == 0) {
                status.setText("Status: Available to pickup");
            } else {
                status.setText("Status: Preparing order");
            }
        }

    }
}
