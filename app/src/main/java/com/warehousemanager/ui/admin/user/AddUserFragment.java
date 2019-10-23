package com.warehousemanager.ui.admin.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.warehousemanager.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment implements View.OnClickListener {

  CircleImageView imageView;

  public AddUserFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_admin_users_add, container, false);
    Button button = view.findViewById(R.id.btnCreateUser);
    button.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnCreateUser:
        onButtonCreateUser(v);
        break;
    }
  }

  private void onButtonCreateUser(View view) {

  }
}
