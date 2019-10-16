package com.warehousemanager.data.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageHelperImpl implements ImageHelper {
  @Override
  public Bitmap convertBase64ToBitmap(String image) {
    byte[] decodedBytes = Base64.decode(
      image.substring(image.indexOf(",")  + 1),
      Base64.DEFAULT
    );

    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
  }

  @Override
  public String convertBitmapToBase64(Bitmap image) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
  }
}
