package com.example.productapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.R;
import com.example.productapp.utils.model.ProductCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<ProductCart> productCartList;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setProductCartList(List<ProductCart> productCartList) {
        this.productCartList = productCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ProductCart productCart = productCartList.get(position);
        holder.productImage.setImageResource(productCart.getProductImage());
        holder.productName.setText(productCart.getProductName());
        holder.productQuantity.setText(productCart.getQuantity() + "");
        holder.productPrice.setText(productCart.getTotalItemPrice() + "");


        holder.deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(productCart);
            }
        });


        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(productCart);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(productCart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productCartList == null) {
            return 0;
        } else {
            return productCartList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, productPrice, productRating, productQuantity;
        private ImageView deleteProductBtn;
        private ImageView productImage;
        private ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.eachCartItemName);
            productRating =itemView.findViewById(R.id.detailActivityProductRating);
            productPrice = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteProductBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            productImage = itemView.findViewById(R.id.eachCartItemIV);
            productQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }

    public interface CartClickedListeners {
        void onDeleteClicked(ProductCart productCart);

        void onPlusClicked(ProductCart productCart);

        void onMinusClicked(ProductCart productCart);
    }
}
