package com.warehousemanager.data.network;

import com.warehousemanager.data.db.entities.Product;

import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WarehouseService {


    final static String IPADDRESS = "http://140.161.82.15:8000";
    public static Retrofit retrofit;

    private static String username;
    private static String password;

    public static void setCredentials(String username, String password) {
        username = username;
        password = password;
    }

    public static void clearCredentials() {
        username = "";
        password = "";
    }

    public static Retrofit getInstance() {
        if(retrofit==null) {
            BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(username, password);
            OkHttpClient client = new OkHttpClient
              .Builder()
              .addInterceptor(basicAuthInterceptor)
              .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(IPADDRESS)
                    //.baseUrl("http://140.161.89.19:8000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
