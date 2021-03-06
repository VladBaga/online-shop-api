package org.fasttrackit.onlineshopapi.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private long id;
                                        //eager - aduce toate informatiile, cart+customer
    @OneToOne(fetch = FetchType.LAZY) //lazy - info doar despre cart, daca ii cer suplimentar imi aduce si despre customer
    @MapsId
    private Customer customer;

    @ManyToMany(cascade = CascadeType.MERGE) //cascade = ce fel de actiuni sa transmita intre entitati
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product){
        // ensuring received product is added to the current cart
        products.add(product);
        // ensuring that the current cart is present in the carts set of the received product
        product.getCarts().add(this);
    }

    public void removeProduct(Product product){
        products.remove(product);
        product.getCarts().remove(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id &&
                customer.equals(cart.customer) &&
                products.equals(cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products);
    }
}
