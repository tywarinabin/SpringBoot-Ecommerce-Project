package com.sheryians.major.controller;

import com.sheryians.major.dto.ProductDTO;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String getAdminPage(){
        return "adminHome";
    }
    @GetMapping("admin/categories")
    public String getCate(Model model){
        model.addAttribute("categories",categoryService.getAllCat());
        return "categories";
    }

    @GetMapping("admin/categories/add")
    public String getCategoryAdd(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }
    @PostMapping("admin/categories/add")
    public String postCategoryAdd(@ModelAttribute("category")Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("admin/categories/delete/{id}")
    public String updateCategoryAdd(@PathVariable int id){
        categoryService.deleteCatById(id);
        return "redirect:/admin/categories";
    }
    @GetMapping("admin/categories/update/{id}")
    public String postCategoryAdd(@PathVariable int id,Model model){
        Optional<Category> opt = categoryService.getCategoryById(id);
        if(opt.isPresent()){
            model.addAttribute("category",opt.get());
            return "categoriesAdd";
        }
        else {
            return "404";
        }
    }


//    Product Section

    @GetMapping("/admin/products")
    public String getProd(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }
    @GetMapping("/admin/products/add")
    public String addProduct(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCat());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO")ProductDTO productDTO, @RequestParam("productImage") MultipartFile file , @RequestParam("imgName") String imgName) throws IOException {

//        Converting Product DTO to Product Image
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imgUUID;
        if(!file.isEmpty()){
            imgUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imgUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else{
            imgUUID = imgName;
        }
        product.setImageName(imgUUID);

//        Saving the product to the Database using Product Service
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
      productService.deleteProductById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("admin/product/update/{id}")
    public String updateProduct(@PathVariable long id,Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setWeight(product.getWeight());

        model.addAttribute("categories",categoryService.getAllCat());
        model.addAttribute("productDTO",productDTO);

        return "productsAdd";
    }
}
