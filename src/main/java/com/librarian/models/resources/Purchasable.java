package com.librarian.models.resources;

public interface Purchasable {
    void purchase();

    boolean isAvailable();

    int getPurchasedCopiesCount();

    long getTotalPurchaseProfit();
}
