package com.warehousemanager.ui.admin.summary;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;

import java.util.List;

public class SummariesListAdapter extends RecyclerView.Adapter<SummariesListAdapter.SummariesListViewHolder> {

  List<ClientOrder> summariesList;

  public SummariesListAdapter(List<ClientOrder> summariesList) {
    this.summariesList = summariesList;
  }

  @NonNull
  @Override
  public SummariesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
      .inflate(R.layout.fragment_admin_summaries_list_row, viewGroup, false);

    SummariesListViewHolder summariesListViewHolder = new SummariesListViewHolder(view);
    return summariesListViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull SummariesListViewHolder summariesListViewHolder, int i) {
    summariesList.get(i).updateTotals();
    summariesListViewHolder.txtWhKey.setText(summariesList.get(i).getWarehouseKey());
    if(summariesList.get(i).isOutOfStock()) {
      summariesListViewHolder.txtOutofstock.setText("Out of Stock");
      summariesListViewHolder.txtOutofstock.setBackgroundColor(Color.RED);
    } else {
      summariesListViewHolder.txtOutofstock.setText("Ready to be set");
      summariesListViewHolder.txtOutofstock.setBackgroundColor(Color.TRANSPARENT);
    }
    summariesListViewHolder.txtOrderTotal.setText(String.format("$ %.2f",summariesList.get(i).getTotal()));
    summariesListViewHolder.txtProfit.setText(String.format("$ %.2f",summariesList.get(i).getProfit()));
  }

  @Override
  public int getItemCount() {
    return summariesList.size();
  }

  public class SummariesListViewHolder extends RecyclerView.ViewHolder {
    TextView txtWhKey;
    TextView txtOutofstock;
    TextView txtOrderTotal;
    TextView txtProfit;

    public SummariesListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtWhKey = itemView.findViewById(R.id.txtWhkey);
      txtOutofstock = itemView.findViewById(R.id.txtOutofstock);
      txtOrderTotal = itemView.findViewById(R.id.txtOrderTotal);
      txtProfit = itemView.findViewById(R.id.txtProfit);
    }
  }
}
