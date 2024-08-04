package com.librarian.models.resources;

public class PurchasableBook extends Resource implements Purchasable {
    private String publisher, publicationYear;
    private int copiesCount, purchasedCopiesCount, price, discountPercentage;
    private long totalPurchaseProfit;

    public PurchasableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, int copiesCount, int purchasedCopiesCount, int price, int discountPercentage, long totalPurchaseProfit) {
        super(id, libraryId, categoryId, title, author);
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.copiesCount = copiesCount;
        this.purchasedCopiesCount = purchasedCopiesCount;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.totalPurchaseProfit = totalPurchaseProfit;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getCopiesCount() {
        return copiesCount;
    }

    public void setCopiesCount(int copiesCount) {
        this.copiesCount = copiesCount;
    }

    @Override
    public int getPurchasedCopiesCount() {
        return purchasedCopiesCount;
    }

    public void setPurchasedCopiesCount(int purchasedCopiesCount) {
        this.purchasedCopiesCount = purchasedCopiesCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getFinalPrice() {
        return price * (100 - discountPercentage) / 100;
    }

    @Override
    public void purchase()
    {
        purchasedCopiesCount++;
        copiesCount--;
        totalPurchaseProfit += getFinalPrice();
    }

    @Override
    public boolean isAvailable() {
        return copiesCount > 0;
    }

    @Override
    public long getTotalPurchaseProfit() {
        return totalPurchaseProfit;
    }
}
