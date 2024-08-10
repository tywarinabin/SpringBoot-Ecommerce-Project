package com.sheryians.major.service;

import com.sheryians.major.model.Product;
import com.sheryians.major.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }
    @Transactional
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(long id){
        productRepository.deleteById(id);
    }

    public List<Product> getProductByCategoryId(int id){
        return productRepository.findAllByCategory_Id(id);
    }
}
