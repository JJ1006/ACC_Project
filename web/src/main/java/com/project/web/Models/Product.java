package com.project.web.Models;

public class Product {

    String productName;

    String productImage;
    String productUrl;

    String productRegularPrice;

    public String getProductSalePrice() {
        return productSalePrice;
    }

    public void setProductSalePrice(String productSalePrice) {
        this.productSalePrice = productSalePrice;
    }

    String productSalePrice;
    String productRatingStars;
    String productRatingCounts;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductRegularPrice() {
        return productRegularPrice;
    }

    public void setProductRegularPrice(String productRegularPrice) {
        this.productRegularPrice = productRegularPrice;
    }

    public String getProductRatingStars() {
        return productRatingStars;
    }

    public void setProductRatingStars(String productRatingStars) {
        this.productRatingStars = productRatingStars;
    }

    public String getProductRatingCounts() {
        return productRatingCounts;
    }

    public void setProductRatingCounts(String productRatingCounts) {
        this.productRatingCounts = productRatingCounts;
    }
}
