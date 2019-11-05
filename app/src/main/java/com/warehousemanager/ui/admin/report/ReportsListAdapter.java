package com.warehousemanager.ui.admin.report;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;

import java.util.List;

public class ReportsListAdapter extends RecyclerView.Adapter<ReportsListAdapter.ReportsListViewHolder> {

  List<String> reportsList;

  public ReportsListAdapter(List<String> reportsList) {
    this.reportsList = reportsList;
  }

  @NonNull
  @Override
  public ReportsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
      .inflate(R.layout.fragment_admin_reports_list_row, viewGroup, false);
    ReportsListViewHolder reportsListViewHolder = new ReportsListViewHolder(view);
    return reportsListViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull ReportsListViewHolder reportsListViewHolder, int i) {
    reportsListViewHolder.textView.setText(reportsList.get(i));
  }

  @Override
  public int getItemCount() {
    return reportsList.size();
  }

  public class ReportsListViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public ReportsListViewHolder(@NonNull View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.txtName);
    }
  }
}
