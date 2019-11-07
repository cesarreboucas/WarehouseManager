package com.warehousemanager.ui.client.shopping_cart;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    List<Product> products;
    ImageHelper imageHelper = new ImageHelperImpl();
    private Fragment fragment;

    public ShoppingCartAdapter(List<Product> products, Fragment context) {
        this.products = products;
        this.fragment = context;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_client_shopping_cart_products_row, viewGroup, false);
        ShoppingCartAdapter.ShoppingCartViewHolder shoppingCartViewHolder = new ShoppingCartAdapter.ShoppingCartViewHolder(view);
        return shoppingCartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ShoppingCartViewHolder shoppingCartViewHolder, final int i) {
        shoppingCartViewHolder.name.setText(products.get(i).getName());
        shoppingCartViewHolder.total.setText(String.format("$%.2f",products.get(i).getTotal()));
        shoppingCartViewHolder.quantity.setText(String.valueOf(products.get(i).getQuantity()));
        shoppingCartViewHolder.picture.setImageBitmap(imageHelper.convertBase64ToBitmap(products.get(i).getPicture()));
        shoppingCartViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message m = new Message();
                m.obj = products.get(i);
                ((FragmentInteraction)fragment).sendMessage(m);
            }
        });

        shoppingCartViewHolder.btnProdInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = products.get(i);
                int quantity = products.get(i).getQuantity();
                product.setQuantity(quantity + 1);

                Message m = new Message();
                m.obj = product;
                ((FragmentInteraction)fragment).sendMessage(m);
            }
        });

        shoppingCartViewHolder.btnProdDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = products.get(i);
                int quantity = products.get(i).getQuantity();
                if(quantity > 1) {
                    product.setQuantity(quantity - 1);

                    Message m = new Message();
                    m.obj = product;
                    ((FragmentInteraction)fragment).sendMessage(m);
                }
            }
        });

        shoppingCartViewHolder.btnProdDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = products.get(i);

                Message m = new Message();
                m.obj = product;
                ((FragmentInteraction)fragment).sendMessage(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView total;
        EditText quantity;
        CircleImageView picture;
        ImageButton btnProdInc;
        ImageButton btnProdDec;
        ImageButton btnProdDel;
        View view;

        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);
            quantity = itemView.findViewById(R.id.editTextQty);
            picture = itemView.findViewById(R.id.profile_image);
            btnProdInc = itemView.findViewById(R.id.btnProductIncrease);
            btnProdDec = itemView.findViewById(R.id.btnProductDecrease);
            btnProdDel = itemView.findViewById(R.id.btnProductDelete);
        }
    }
}
