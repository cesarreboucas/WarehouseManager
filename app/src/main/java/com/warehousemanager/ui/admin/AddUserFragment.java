package com.warehousemanager.ui.admin;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment implements IPickResult, View.OnClickListener {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private FragmentInteraction mListener;

  CircleImageView imageView;

  public AddUserFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
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

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Message message) {
    if (mListener != null) {
      mListener.sendMessage(message);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof FragmentInteraction) {
      mListener = (FragmentInteraction) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
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
