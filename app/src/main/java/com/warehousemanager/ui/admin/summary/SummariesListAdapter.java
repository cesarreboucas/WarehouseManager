package com.warehousemanager.ui.admin.summary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;

import java.util.List;

public class SummariesListAdapter extends RecyclerView.Adapter<SummariesListAdapter.SummariesListViewHolder> {

  List<String> summariesList;

  public SummariesListAdapter(List<String> summariesList) {
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
    summariesListViewHolder.textView.setText(summariesList.get(i));
  }

  @Override
  public int getItemCount() {
    return summariesList.size();
  }

  public class SummariesListViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public SummariesListViewHolder(@NonNull View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.txtWarehouseName);
    }
  }
}
