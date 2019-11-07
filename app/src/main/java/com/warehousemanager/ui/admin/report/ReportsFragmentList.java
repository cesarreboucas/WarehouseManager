package com.warehousemanager.ui.admin.report;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Report;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragmentList extends Fragment {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView reportsList;

  public ReportsFragmentList() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_admin_reports_fragment_list, container, false);
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.reportsFragmentContainer);

    IWarehouseService warehouseService = new WarehouseService().getInstance().create(IWarehouseService.class);
    reportsList = view.findViewById(R.id.reportsList);
    final List<Report> reports = new ArrayList<>();
    final ReportsListAdapter reportsListAdapter = new ReportsListAdapter(reports);

    warehouseService.getAllReports().enqueue(new Callback<List<Report>>() {
      @Override
      public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
        reports.addAll(response.body());
        reportsListAdapter.notifyDataSetChanged();

      }

      @Override
      public void onFailure(Call<List<Report>> call, Throwable t) {

      }
    });



    reportsList.setLayoutManager(new LinearLayoutManager(getContext()));
    reportsList.setAdapter(reportsListAdapter);
    return view;
  }
}
