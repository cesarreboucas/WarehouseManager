package com.warehousemanager.ui.admin.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;
import com.warehousemanager.R;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment implements IPickResult, View.OnClickListener {

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
    View view = inflater.inflate(R.layout.fragment_admin_add_user, container, false);
    Button button = view.findViewById(R.id.btnTakePicture);
    button.setOnClickListener(this);

    imageView = view.findViewById(R.id.imgPicture);
    return view;
  }

  @Override
  public void onPickResult(PickResult r) {
    Toast.makeText(this.getContext(), "Picture taken", Toast.LENGTH_LONG).show();
    imageView.setImageBitmap(r.getBitmap());
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnTakePicture:
        onButtonTakePictureClick(v);
        break;
    }
  }

  private void onButtonTakePictureClick(View v) {
    PickImageDialog.build(new PickSetup()).setOnPickResult(this).show(getChildFragmentManager());
  }
}
