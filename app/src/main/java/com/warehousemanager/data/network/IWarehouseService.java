package com.warehousemanager.data.network;


import com.squareup.okhttp.RequestBody;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.MovementOrder;

import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.Report;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.ui.scanner.BarcodeScannerFragment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IWarehouseService {

    // PRODUCTS ENDPOINTS
    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/hangs")
    Call<List<ProductHang>> getAllProductsHangs();

    @GET("products/hangs/{warehouse}/{barcode}")
    Call<ProductHang> getTodoProduct(@Path("warehouse") String warehouse, @Path("barcode") String barcode);

    @DELETE("products")
    Call<Product> deleteProduct(@Body String barcode);
     //=========================


    // WAREHOUSES ENDPOINTS
    @POST("warehouses")
    Call<Warehouse> createWarehouse(@Body Warehouse warehouse);

    @GET("warehouses")
    Call<List<Warehouse>> getAllWarehouse();

    @DELETE("warehouses")
    Call<Warehouse> deleteWarehouse(@Body String warehouseName);
    // =========================


    // USERS ENDPOINTS
    @POST("users/auth")
    Call<User> authenticate(@Body User user);
  
    @GET("users")
    Call<List<User>> getAllUsers();

    @POST("users")
    Call<User> createUser(@Body User user);
  
    @PUT("users")
    Call editUserRole(@Body User user);

    @PATCH("users")
    Call editUserPassword(@Body User user);

    @GET("users/{favouriteWarehouse}/associates")
    Call<List<User>> getAssociates(@Path("favouriteWarehouse") String favouriteWarehouse);

    @HTTP(method = "DELETE", path = "users", hasBody = true)
    Call<User> deleteUser(@Body User user);
    // =========================


    // ORDERS ENDPOINTS
    @POST("orders")
    Call<ClientOrder> createOrder(@Body ClientOrder clientOrder);

    @GET("orders")
    Call<List<ClientOrder>> getAllOrders();

    @GET("orders/user/{id}")
    Call<List<ClientOrder>> getOrdersByUser(@Path("id") long id);

    // REPORTS ENDPOINTS
    @GET("reports")
    Call<List<Report>> getAllReports();

    // =========================


    // MOVEMENTS ENDPOINTS
    @POST("movorders")
    Call<MovementOrder> createMovementOrder(@Body MovementOrder movementOrder);

    @GET("movorders")
    Call<List<MovementOrder>> getAllMovementOrders();

    @GET("movorders/{warehouse}/todo")
    Call<List<MovementOrder>> getTodoOrders(@Path("warehouse") String warehouse);

    @GET("movorders/{warehouse}/pending")
    Call<List<MovementOrder>> getPendingOrders(@Path("warehouse") String warehouse);

    @GET("movorders/{warehouse}/completed")
    Call<List<MovementOrder>> getCompletedOrders(@Path("warehouse") String warehouse);

    @PUT("movorders")
    Call<MovementOrder> editMovementOrder();

    @PATCH("movorders")
    Call<Void> updateMovementOrder(@Body BarcodeScannerFragment.UpdateMovementOrder body);
    // =========================

}
