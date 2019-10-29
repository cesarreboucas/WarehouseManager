package com.warehousemanager.ui.admin.summary;


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

public class SummariesFragmentList extends Fragment {

  IFragmentManagerHelper fragmentManagerHelper;

  RecyclerView summariesList;

  public SummariesFragmentList() { }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.summariesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_sumaries_fragment_list, container, false);

    summariesList = view.findViewById(R.id.summariesList);
    SummariesListAdapter summariesListAdapter = new SummariesListAdapter(
      Arrays.asList("Summary1", "Summary2", "...")
    );
    summariesList.setLayoutManager(new LinearLayoutManager(getContext()));
    summariesList.setAdapter(summariesListAdapter);
    return view;
  }

}
