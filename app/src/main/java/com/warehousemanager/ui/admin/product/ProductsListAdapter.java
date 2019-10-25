package com.warehousemanager.ui.admin.product;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsViewHolder> {

    List<Product> products;
    ImageHelper imageHelper = new ImageHelperImpl();

    public ProductsListAdapter(List<Product> products) {
        this.products = products;
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
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        productsViewHolder.name.setText(products.get(i).getName());
        productsViewHolder.description.setText(products.get(i).getDescription());
        productsViewHolder.cost.setText(String.format("%.2f",products.get(i).getCost()));
        productsViewHolder.picture.setImageBitmap(imageHelper.convertBase64ToBitmap(products.get(i).getPicture()));

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
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            picture = itemView.findViewById(R.id.profile_image);

        }
    }

}
