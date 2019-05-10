package org.fasttrackit.onlineshopapi.transfer.cart;

import java.util.Set;

public class SaveCartRequest {

    private long customerId;
    private Set<Long> productsId;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(Set<Long> productsId) {
        this.productsId = productsId;
    }

    @Override
    public String toString() {
        return "SaveCartRequest{" +
                "customerId=" + customerId +
                ", productsId=" + productsId +
                '}';
    }
}