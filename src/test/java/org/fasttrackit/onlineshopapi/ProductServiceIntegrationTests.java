package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId(){ //metodele pt test intotdeauna VOID
        Product product = createProduct();

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
    }

    private Product createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("asdfghjkl");

        return productService.createProduct(request);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void testGetProduct_whenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        productService.getProduct(0);
    }

    @Test
    public void testUpdateProduct_whenValidRequestWithAllFields_thenReturnUpdatedProduct() throws ResourceNotFoundException { //ORICE TEST ESTE INDEPENDENT
        Product createdProduct = createProduct();

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(createdProduct.getName() + " Edited");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 5);
        request.setSku(createdProduct.getSku() + "Edited");

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));

        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));

        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
    }
    // todo: Implement negative tests for update and tests for update with some fields only.

    @Test (expected = ResourceNotFoundException.class)
    public void testDeleteProduct_whenExistingId_thenProductIsDeleted() throws ResourceNotFoundException {
     Product createdProduct = createProduct();

     productService.deleteProduct(createdProduct.getId());

     productService.getProduct(createdProduct.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGeroducts_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults(){
        Product createdProduct = createProduct();

        GetProductsRequest request = new GetProductsRequest();
        request.setPartialName("top");
        request.setMinimumPrice(9.9);
        request.setMaximumPrice(10.1);
        request.setMinimumQuantity(1);

        Page<Product> products = productService.getProducts(request, PageRequest.of(0, 10));

        assertThat(products.getTotalElements(), greaterThanOrEqualTo(1L));

        // todo: for each product from the response assert that all criteria are matched
    }
}