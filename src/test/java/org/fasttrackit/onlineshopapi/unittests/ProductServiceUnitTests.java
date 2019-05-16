package org.fasttrackit.onlineshopapi.unittests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceUnitTests {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @Before
    public void setup(){
        productService = new ProductService(productRepository, new ObjectMapper());
    }

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProduct(){
        Product product = new Product();
        product.setId(1);
        product.setName("Test Phone");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        CreateProductRequest request = new CreateProductRequest();
        request.setName("Test Phone");
        request.setSku("orice");

        Product savedProduct = productService.createProduct(request);

        verify(productRepository).save(any(Product.class));

        assertThat(savedProduct, notNullValue());
        assertThat(savedProduct.getId(), CoreMatchers.is(product.getId()));
    }
}