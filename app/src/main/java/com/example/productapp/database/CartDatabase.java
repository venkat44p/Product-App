package com.example.productapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.productapp.dao.CartDao;
import com.example.productapp.utils.model.ProductCart;

@Database(entities = {ProductCart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDao cartDao();
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , CartDatabase.class , "ProductCartDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
