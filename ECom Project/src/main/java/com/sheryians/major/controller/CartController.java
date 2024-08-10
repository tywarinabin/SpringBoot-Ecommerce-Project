package com.sheryians.major.controller;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
@Autowired
ProductService productService;
@GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id)
    {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public String goToCart(Model model){
    model.addAttribute("cartCount",GlobalData.cart.size());
    model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
    model.addAttribute("cart",GlobalData.cart);
    return "cart";
    }
    @GetMapping("/cart/removeItem/{id}")
    public String removeItemFromCart(@PathVariable int id){
    GlobalData.cart.remove(id);
    return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String goToCheckOut(Model model){
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
}
