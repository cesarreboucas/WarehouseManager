package com.warehousemanager.data.network;

import com.warehousemanager.data.db.entities.Product;

import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WarehouseService {

    //final static String IPADDRESS = "http://140.161.215.16:8000";
    final static String IPADDRESS = "http://ec2-3-87-64-207.compute-1.amazonaws.com:8000"; // This IP address doesn't change

    public static Retrofit retrofit;

    private static String username;
    private static String password;

    public static void setCredentials(String newUsername, String newPassword) {
        username = newUsername;
        password = newPassword;
    }

    public static void clearCredentials() {
        username = "";
        password = "";
    }

    public static Retrofit getInstance() {

        BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(username, password);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(basicAuthInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(IPADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
