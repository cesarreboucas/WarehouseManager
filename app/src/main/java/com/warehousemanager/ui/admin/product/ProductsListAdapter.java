package com.warehousemanager.ui.admin.product;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsViewHolder> {

    List<Product> products;
    ImageHelper imageHelper = new ImageHelperImpl();
    private Fragment context;

    public ProductsListAdapter(List<Product> products, Fragment context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_admin_products_list_row, viewGroup, false);
        ProductsViewHolder productsViewHolder = new ProductsViewHolder(view);
        return productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, final int i) {
        productsViewHolder.name.setText(products.get(i).getName());
        productsViewHolder.description.setText(products.get(i).getDescription());
        productsViewHolder.cost.setText(String.format("%.2f",products.get(i).getCost()));
        productsViewHolder.picture.setImageBitmap(imageHelper.convertBase64ToBitmap(products.get(i).getPicture()));
        productsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAA", "AAA");
                Message m = new Message();
                m.obj = products.get(i).getName();
                ((FragmentInteraction)context).sendMessage(m);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView cost;
        CircleImageView picture;
        View view;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            picture = itemView.findViewById(R.id.profile_image);
        }
    }

}
