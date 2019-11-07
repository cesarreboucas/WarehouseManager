package com.warehousemanager.ui.client.products;

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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductListClientAdapter extends RecyclerView.Adapter<ProductListClientAdapter.ProductsViewHolder> {

    List<Product> products;
    ImageHelper imageHelper = new ImageHelperImpl();
    private Fragment context;

    public ProductListClientAdapter(List<Product> products, Fragment context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListClientAdapter.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_admin_products_list_row, viewGroup, false);
        ProductListClientAdapter.ProductsViewHolder productsViewHolder = new ProductListClientAdapter.ProductsViewHolder(view);
        return productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListClientAdapter.ProductsViewHolder productsViewHolder, final int i) {
        productsViewHolder.name.setText(products.get(i).getName());
        productsViewHolder.description.setText(products.get(i).getDescription());
        productsViewHolder.price.setText(String.format("$%.2f",products.get(i).getSalePrice()));
        productsViewHolder.picture.setImageBitmap(imageHelper.convertBase64ToBitmap(products.get(i).getPicture()));
        productsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = products.get(i);
                ((FragmentInteraction)context).sendMessage(m);
            }
        });

    }

    public String fitString(String text, int maxsize) {
        return text.length()>maxsize?text.substring(0,maxsize)+"...":
                text;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView price;
        CircleImageView picture;
        View view;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.cost);
            picture = itemView.findViewById(R.id.profile_image);
        }
    }
}
