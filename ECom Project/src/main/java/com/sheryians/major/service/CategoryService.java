package com.sheryians.major.service;

import com.sheryians.major.model.Category;
import com.sheryians.major.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCat(){
        return categoryRepository.findAll();
    }
    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }

    @Transactional
    public void addCategory(Category c){
        categoryRepository.save(c);
    }

    @Transactional
    public void deleteCatById(int id){
        categoryRepository.deleteById(id);
    }
}
