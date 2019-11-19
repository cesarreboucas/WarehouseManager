package com.warehousemanager.ui.admin.summary;

import android.graphics.Color;
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
import com.warehousemanager.ui.admin.FragmentInteraction;
import org.w3c.dom.Text;

import java.util.List;

public class SummariesListAdapter extends RecyclerView.Adapter<SummariesListAdapter.SummariesListViewHolder> {

  List<ClientOrder> summariesList;
  Fragment context;

  public SummariesListAdapter(List<ClientOrder> summariesList, Fragment context) {
    this.summariesList = summariesList;
    this.context = context;
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
  public void onBindViewHolder(@NonNull SummariesListViewHolder summariesListViewHolder, final int i) {
    summariesList.get(i).updateTotals();
    summariesListViewHolder.txtWhKey.setText(summariesList.get(i).getWarehouseKey());
    if(summariesList.get(i).getReady()==1) {
      summariesListViewHolder.txtReady.setText("Ready");
    } else {
      summariesListViewHolder.txtReady.setText("Not Ready");
      summariesListViewHolder.txtReady.setBackgroundColor(Color.parseColor("#e77fd5"));
    }
    summariesListViewHolder.txtOrderTotal.setText(String.format("$ %.2f",summariesList.get(i).getTotal()));
    summariesListViewHolder.txtProfit.setText(String.format("$ %.2f",summariesList.get(i).getProfit()));

    summariesListViewHolder.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Message m = new Message();
          m.obj = summariesList.get(i);
          m.what = 1;
          ((FragmentInteraction) context).sendMessage(m);
      }
    });

  }

  @Override
  public int getItemCount() {
    return summariesList.size();
  }

  public class SummariesListViewHolder extends RecyclerView.ViewHolder {
    TextView txtWhKey;
    TextView txtOrderTotal;
    TextView txtOrderTime;
    TextView txtProfit;
    TextView txtReady;
    View view;

    public SummariesListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtWhKey = itemView.findViewById(R.id.txtWhkey);
      txtOrderTime = itemView.findViewById(R.id.txtOrderTime);
      txtOrderTotal = itemView.findViewById(R.id.txtOrderTotal);
      txtReady = itemView.findViewById(R.id.txtReady);
      txtProfit = itemView.findViewById(R.id.txtProfit);
      view = itemView;
    }
  }
}
