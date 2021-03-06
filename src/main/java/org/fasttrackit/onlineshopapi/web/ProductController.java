package org.fasttrackit.onlineshopapi.web;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    //@RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @GetMapping("/{id}")
    public ResponseEntity <Product> getProduct(@PathVariable("id") long id) throws ResourceNotFoundException {
        Product response = productService.getProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK); // pt. Get status.ok; pt POST status.created, pt DELETE status.noContent, pt PUT, oricare
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest request){
        Product response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct (@PathVariable("id") long id, @RequestBody @Valid UpdateProductRequest request) throws ResourceNotFoundException {
        productService.updateProduct(id, request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct (@PathVariable("id") long id){
        productService.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@Valid GetProductsRequest request, Pageable pageable){
        Page<ProductResponse> productResponse = productService.getProducts(request, pageable);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}