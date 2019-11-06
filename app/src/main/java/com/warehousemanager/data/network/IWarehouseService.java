package com.warehousemanager.data.network;

import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.db.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IWarehouseService {

    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @GET("products")
    Call<List<Product>> getAllProducts();
  
    @POST("warehouses")
    Call<Warehouse> createWarehouse(@Body Warehouse warehouse);

    @GET("warehouses")
    Call<List<Warehouse>> getAllWarehouse();

    @POST("users/auth")
    Call<User> authenticate(@Body User user);
  
    @GET("users")
    Call<List<User>> getAllUsers();

    @POST("users")
    Call<User> createUser(@Body User user);
  
    @PUT("users")
    Call<User> editUser(@Body User user);

    @HTTP(method = "DELETE", path = "users", hasBody = true)
    Call<User> deleteUser(@Body User user);
}
