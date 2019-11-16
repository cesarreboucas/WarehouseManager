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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
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
    public void onBindViewHolder(@NonNull final ProductsViewHolder productsViewHolder, final int i) {
        productsViewHolder.name.setText(fitString(products.get(i).getName(),30));
        productsViewHolder.description.setText(fitString(products.get(i).getDescription(),30));
        productsViewHolder.cost.setText(String.format("%.2f",products.get(i).getCost()));
        productsViewHolder.picture.setImageBitmap(imageHelper.convertBase64ToBitmap(products.get(i).getPicture()));
        productsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = products.get(i);
                m.what = 1;
                ((FragmentInteraction)context).sendMessage(m);
                //IFragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);

            }
        });

        productsViewHolder.imgMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.what = 2;
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
        TextView cost;
        CircleImageView picture;
        ImageButton imgMove;
        View view;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            imgMove = itemView.findViewById(R.id.imgMove);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            picture = itemView.findViewById(R.id.profile_image);
        }
    }

}
