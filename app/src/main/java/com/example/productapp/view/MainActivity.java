package com.example.productapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.productapp.R;
import com.example.productapp.utils.adapter.ProductItemAdapter;
import com.example.productapp.utils.model.ProductCart;
import com.example.productapp.utils.model.ProductItem;
import com.example.productapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductItemAdapter.ProductClickedListeners {

    private RecyclerView recyclerView;
    private List<ProductItem> productItemList;
    private ProductItemAdapter adapter;
    private CartViewModel viewModel;
    private CoordinatorLayout coordinatorLayout;
    private List<ProductCart> productCartList;
    private ImageView cartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setUpList();

        adapter.setProductItemList(productItemList);
        recyclerView.setAdapter(adapter);

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllCartItems().observe(this, new Observer<List<ProductCart>>() {
            @Override
            public void onChanged(List<ProductCart> productCarts) {
                productCartList.addAll(productCarts);
            }
        });
    }

    private void setUpList() {
        productItemList.add(new ProductItem("OnePlus 6 (Mirror Black 6GB RAM + 64GB memory)", R.drawable.phone_1, 34999, 4));
        productItemList.add(new ProductItem("Nokia 105 (Black)", R.drawable.phone_2, 999, 4));
        productItemList.add(new ProductItem("Samsung On7 Pro (Gold)", R.drawable.phone_3, 7990, 3));
        productItemList.add(new ProductItem("Moto G5s Plus (Lunar Grey, 64GB)", R.drawable.phone_4, 11999, 4));
        productItemList.add(new ProductItem("InFocus Vision 3 (Midnight Black, 18:9 FullVision Display)", R.drawable.phone_5, 7981, 2));
        productItemList.add(new ProductItem("OnePlus 6 (Red, 8GB RAM + 128GB Memory)", R.drawable.phone_6, 39999, 3));
        productItemList.add(new ProductItem("Redmi 6 Pro (Gold, 32GB)", R.drawable.phone_7, 10999, 3));
        productItemList.add(new ProductItem("Honor 7X (Blue, 4GB RAM + 32GB Memory)", R.drawable.phone_9, 11999, 4));


    }

    private void initializeVariables() {

        cartImageView = findViewById(R.id.cartIv);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        productCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        productItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductItemAdapter(this);
    }

    @Override
    public void onCardClicked(ProductItem product) {

        Intent i = new Intent(getApplicationContext(), DetailedActivity.class);
        i.putExtra("productItem", product);
        startActivity(i);
    }

    @Override
    public void onAddToCartBtnClicked(ProductItem productItem) {
        ProductCart productCart = new ProductCart();
        productCart.setProductName(productItem.getProductName());
        productCart.setProductPrice(productItem.getProductPrice());
        productCart.setRating(productItem.getRating());
        productCart.setProductImage(productItem.getProductImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!productCartList.isEmpty()) {
            for (int i = 0; i < productCartList.size(); i++) {
                if (productCart.getProductName().equals(productCartList.get(i).getProductName())) {
                    quantity[0] = productCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = productCartList.get(i).getId();
                }
            }
        }

        Log.d("TAG", "onAddToCartBtnClicked: " + quantity[0]);

        if (quantity[0] == 1) {
            productCart.setQuantity(quantity[0]);
            productCart.setTotalItemPrice(quantity[0] * productCart.getProductPrice());
            viewModel.insertCartItem(productCart);
        } else {
            viewModel.updateQuantity(id[0], quantity[0]);
            viewModel.updatePrice(id[0], quantity[0] * productCart.getProductPrice());
        }

        makeSnackBar("Item Added To Cart");
    }

    private void makeSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                    }
                }).show();
    }
}