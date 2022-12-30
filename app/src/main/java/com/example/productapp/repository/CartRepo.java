package com.example.productapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.productapp.dao.CartDao;
import com.example.productapp.database.CartDatabase;
import com.example.productapp.utils.model.ProductCart;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDao cartDao;
    private LiveData<List<ProductCart>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<ProductCart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public CartRepo(Application application){
        cartDao = CartDatabase.getInstance(application).cartDao();
        allCartItemsLiveData = cartDao.getAllCartItems();
    }

    public void insertCartItem(ProductCart productCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.insertCartItem(productCart);
            }
        });
    }

    public void deleteCartItem(ProductCart productCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteCartItem(productCart);
            }
        });
    }

    public void updateQuantity(int id , int quantity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updateQuantity(id, quantity);
            }
        });
    }

    public void updatePrice(int id , double price){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updatePrice(id , price);
            }
        });
    }

    public void deleteAllCartItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteAllItems();
            }
        });
    }
}
