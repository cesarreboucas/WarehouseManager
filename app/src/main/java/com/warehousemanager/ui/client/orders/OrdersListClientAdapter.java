package com.warehousemanager.ui.client.orders;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersListClientAdapter extends RecyclerView.Adapter<OrdersListClientAdapter.OrdersViewHolder> {

    private List<ClientOrder> clientOrders;
    private Fragment context;

    public OrdersListClientAdapter(List<ClientOrder> clientOrders, Fragment context) {
        this.clientOrders = clientOrders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersListClientAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_client_orders_list_row, viewGroup, false);
        OrdersListClientAdapter.OrdersViewHolder ordersViewHolder = new OrdersListClientAdapter.OrdersViewHolder(view);
        return ordersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder ordersViewHolder, final int i) {
        ordersViewHolder.number.setText("Order # " + clientOrders.get(i).getId());

        List<Product> products = clientOrders.get(i).getProducts();
        double total = 0;
        for (Product p: products) {
            total += p.getTotal();
        }
        ordersViewHolder.total.setText(String.format("$%.2f", total));


        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000");
        String inputDate = clientOrders.get(i).getOrdertime();
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = outputFormat.format(date);
        ordersViewHolder.date.setText(outputDate);

        int done = clientOrders.get(i).getDone();
        int ready = clientOrders.get(i).getReady();
        if(done == 1) {
            ordersViewHolder.status.setText("Completed");
        } else if(ready == 1 && done == 0) {
            ordersViewHolder.status.setText("Available to pickup");
        } else {
            ordersViewHolder.status.setText("Preparing order");
        }

        ordersViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = clientOrders.get(i);
                ((FragmentInteraction)context).sendMessage(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientOrders.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView total;
        TextView date;
        TextView status;
        View view;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            number = itemView.findViewById(R.id.txtOrderNumberClient);
            total = itemView.findViewById(R.id.txtOrderPriceClient);
            date = itemView.findViewById(R.id.txtOrderDateClient);
            status = itemView.findViewById(R.id.txtOrderStatusClient);
        }
    }
}
