package com.warehousemanager.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.warehousemanager.data.db.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(Product product);

    @Query("SELECT * FROM product_table")
    List<Product> getProducts();

    @Update
    public void updateProduct(Product product);

    @Delete
    public void deleteProduct(Product product);
}
