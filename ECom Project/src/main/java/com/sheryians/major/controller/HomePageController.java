package com.sheryians.major.controller;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomePageController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;


   @GetMapping({"/", "/home", "/index", "/ghar","start","welcome"})
    public String goToHomePage(Model model){
       model.addAttribute("cartCount",GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String goToShop(Model model){
       model.addAttribute("categories",categoryService.getAllCat());
        model.addAttribute("cartCount", GlobalData.cart.size());
       model.addAttribute("products",productService.getAllProduct());
       return "shop";
    }
    @GetMapping("/shop/category/{id}")
    public String getProductByCat(@PathVariable int id, Model model){
        model.addAttribute("categories",categoryService.getAllCat());
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("products",productService.getProductByCategoryId(id));
        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model){
       model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "viewProduct";
    }
}
