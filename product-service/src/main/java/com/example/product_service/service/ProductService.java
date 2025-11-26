package com.example.product_service.service;


import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepo productRepo;
    public Product createProduct(Product product){
        return productRepo.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("product not found"));
    }
}
