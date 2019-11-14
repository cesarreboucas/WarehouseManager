package com.warehousemanager.data.network;

import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IMovementService {

    @POST("movementOrder")
    Call<MovementOrder> createMovementOrder(@Body MovementOrder movementOrder);

    @GET("movementOrder")
    Call<List<MovementOrder>> getAllMovementOrders();

    @DELETE("movementOrder")
    Call<MovementOrder> deleteMovementOrder(@Body int key);



}
