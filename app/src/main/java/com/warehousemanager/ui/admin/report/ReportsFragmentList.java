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
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;

import java.util.Arrays;


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

    reportsList = view.findViewById(R.id.reportsList);
    ReportsListAdapter reportsListAdapter = new ReportsListAdapter(
      Arrays.asList("Report1", "Report2", "...")
    );
    reportsList.setLayoutManager(new LinearLayoutManager(getContext()));
    reportsList.setAdapter(reportsListAdapter);
    return view;
  }
}
