package com.example.productapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.productapp.R;
import com.example.productapp.utils.model.ProductCart;
import com.example.productapp.utils.model.ProductItem;
import com.example.productapp.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productName, productPrice, productRating;
    private AppCompatButton addToCartBtn;
    private ProductItem product;
    private CartViewModel viewModel;
    private List<ProductCart> productCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        product = getIntent().getParcelableExtra("productItem");
        initializeVariables();

        viewModel.getAllCartItems().observe(this, new Observer<List<ProductCart>>() {
            @Override
            public void onChanged(List<ProductCart> productCarts) {
                productCartList.addAll(productCarts);
            }
        });

        if (product != null) {
            setDataToWidgets();
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToRoom();
            }
        });


    }

    private void insertToRoom(){
        ProductCart productCart = new ProductCart();
        productCart.setProductName(product.getProductName());
        productCart.setProductPrice(product.getProductPrice());
        productCart.setRating(product.getRating());
        productCart.setProductImage(product.getProductImage());

    final int[] quantity = {1};
    final int[] id = new int[1];

        if (!productCartList.isEmpty()){
        for(int i=0;i<productCartList.size();i++){
            if (productCart.getProductName().equals(productCartList.get(i).getProductName())){
                quantity[0] = productCartList.get(i).getQuantity();
                quantity[0]++;
                id[0] = productCartList.get(i).getId();
            }
        }
    }

        if (quantity[0]==1){
        productCart.setQuantity(quantity[0]);
        productCart.setTotalItemPrice(quantity[0]*productCart.getProductPrice());
        viewModel.insertCartItem(productCart);
    }else{

        viewModel.updateQuantity(id[0] ,quantity[0]);
        viewModel.updatePrice(id[0] , quantity[0]*productCart.getProductPrice());
    }

    startActivity(new Intent(DetailedActivity.this , CartActivity.class));
}

    private void setDataToWidgets(){
        productName.setText(product.getProductName());
        productPrice.setText(String.valueOf(product.getProductPrice()));
        productRating.setText(String.valueOf(product.getRating()));
        productImageView.setImageResource(product.getProductImage());
    }


    private void initializeVariables(){
        productCartList = new ArrayList<>();
        productImageView = findViewById(R.id.detailActivityProductImage);
        productName = findViewById(R.id.detailActivityProductName);
        productPrice = findViewById(R.id.detailActivityProductPrice);
        productRating = findViewById(R.id.detailActivityProductRating);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }
}