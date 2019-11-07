package com.warehousemanager.ui.admin.report;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Report;

import java.util.List;

public class ReportsListAdapter extends RecyclerView.Adapter<ReportsListAdapter.ReportsListViewHolder> {

  List<Report> reportsList;

  public ReportsListAdapter(List<Report> reportsList) {
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
    reportsListViewHolder.txtWarehouseName.setText(reportsList.get(i).getWarehouseName());
    reportsListViewHolder.txtAssociateName.setText(reportsList.get(i).getAssociateName());
    reportsListViewHolder.txtProductName.setText(reportsList.get(i).getProductName());
  }

  @Override
  public int getItemCount() {
    return reportsList.size();
  }

  public class ReportsListViewHolder extends RecyclerView.ViewHolder {
    TextView txtWarehouseName;
    TextView txtAssociateName;
    TextView txtProductName;

    public ReportsListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtWarehouseName = itemView.findViewById(R.id.txtWarehouseName);
      txtAssociateName = itemView.findViewById(R.id.txtAssociateName);
      txtProductName = itemView.findViewById(R.id.txtProductName);
    }
  }
}
