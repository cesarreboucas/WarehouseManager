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
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.client.products.ProductListClientAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderProductListDetailClientAdapter extends RecyclerView.Adapter<OrderProductListDetailClientAdapter.OrderProductsViewHolder> {

    List<Product> products;
    private Fragment context;

    public OrderProductListDetailClientAdapter(List<Product> products, Fragment context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderProductListDetailClientAdapter.OrderProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_client_order_product_list_row, viewGroup, false);
        OrderProductListDetailClientAdapter.OrderProductsViewHolder productsViewHolder = new OrderProductListDetailClientAdapter.OrderProductsViewHolder(view);
        return productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductListDetailClientAdapter.OrderProductsViewHolder productsViewHolder, final int i) {
        productsViewHolder.name.setText(products.get(i).getName());
        productsViewHolder.qty.setText("Quantity: " + products.get(i).getQuantity());
        productsViewHolder.price.setText(String.format("$%.2f",products.get(i).getSalePrice()));
        productsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = products.get(i);
                ((FragmentInteraction)context).sendMessage(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class OrderProductsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView qty;
        TextView price;
        View view;

        public OrderProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            qty = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.cost);
        }
    }
}
