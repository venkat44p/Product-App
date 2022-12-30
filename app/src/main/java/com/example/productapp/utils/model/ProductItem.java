package com.example.productapp.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductItem implements Parcelable {

    private String productName;
    private int productImage;
    private double productPrice, rating;

    public ProductItem(String productName, int productImage, double productPrice, double rating) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.rating = rating;
    }

    protected ProductItem(Parcel in) {
        productName = in.readString();
        productImage = in.readInt();
        productPrice = in.readDouble();
        rating = in.readDouble();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeInt(productImage);
        dest.writeDouble(productPrice);
        dest.writeDouble(rating);
    }
}
