package com.example.productapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.R;
import com.example.productapp.utils.model.ProductItem;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {

    private List<ProductItem> productItemList;
    private ProductClickedListeners productClickedListeners;
    public ProductItemAdapter(ProductClickedListeners productClickedListeners){
        this.productClickedListeners = productClickedListeners;
    }
    public void setProductItemList(List<ProductItem> productItemList){
        this.productItemList = productItemList;
    }
    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_product, parent, false);
        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        ProductItem productItem = productItemList.get(position);
        holder.productName.setText(productItem.getProductName());
        holder.productImageView.setImageResource(productItem.getProductImage());
        holder.productPrice.setText(String.valueOf(productItem.getProductPrice()));
        holder.productRating.setText(String.valueOf(productItem.getRating()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productClickedListeners.onCardClicked(productItem);
            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickedListeners.onAddToCartBtnClicked(productItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productItemList == null){
            return 0;
        }else {
            return productItemList.size();
        }
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImageView, addToCartBtn;
        private TextView productName, productPrice, productRating;
        private CardView cardView;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachProductCardView);
            addToCartBtn = itemView.findViewById(R.id.eachAddToCartBtn);
            productName =itemView.findViewById(R.id.eachProductName);
            productImageView = itemView.findViewById(R.id.eachProductImage);
            productPrice = itemView.findViewById(R.id.eachProductPrice);
            productRating = itemView.findViewById(R.id.eachCartRating);
        }
    }

    public interface ProductClickedListeners{
        void onCardClicked(ProductItem product);
        void onAddToCartBtnClicked(ProductItem productItem);

    }
}
