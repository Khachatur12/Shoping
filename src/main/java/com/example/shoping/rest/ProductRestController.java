package com.example.shoping.rest;

import com.example.shoping.models.Product;
import com.example.shoping.models.Rate;
import com.example.shoping.security.SecurityUser;
import com.example.shoping.services.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> all = productService.getAll();
        return all;
    }

    @GetMapping("/name")
    public List<Product> getAllByName(@RequestParam("name") String name) {
        return productService.getAllByName(name);
    }

    @GetMapping("/filter")
    public List<Product> getAllPriceBetween(@RequestParam(value = "minPrice", required = false) Double minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        return productService.getAllAndPriceBetween(minPrice, maxPrice);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:permission')")
    public void create(@RequestBody Product product) {
        productService.create(product);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('admin:permission')")
    public void delete(@RequestBody Map<String, String> map) {
        Long productId = Long.parseLong(map.get("productId"));
        productService.delete(productId);
    }


    @PostMapping("/add-comment")
    @PreAuthorize("isAuthenticated()")
    public void commentProduct(@AuthenticationPrincipal SecurityUser user,
                               @RequestBody Map<String, String> map) {
        Long productId = Long.parseLong(map.get("productId"));
        String comment = map.get("comment");

        productService.addComment(user.getUsername(), productId, comment);
    }

    @DeleteMapping("/del-comment")
    @PreAuthorize("isAuthenticated()")
    public void deleteProductComment(@AuthenticationPrincipal SecurityUser user,
                                     @RequestBody Map<String, String> map) {
        Long commentId = Long.parseLong(map.get("commentId"));
        productService.deleteComment(user.getUsername(), commentId);
    }

    @PostMapping("/rate")
    @PreAuthorize("isAuthenticated()")
    public void rateProduct(@AuthenticationPrincipal SecurityUser user,
                            @RequestBody Map<String, String> map) {
        Rate.R r = Rate.R.valueOf(map.get("rate"));
        Long productId = Long.parseLong(map.get("productId"));
        productService.rateProduct(user.getUsername(), productId, r);
    }

    @DeleteMapping("/rate")
    @PreAuthorize("isAuthenticated()")
    public void deleteProductRate(@AuthenticationPrincipal SecurityUser user,
                                  @RequestBody Map<String, String> map) {
        Long productId = Long.parseLong(map.get("productId"));
        productService.deleteRate(user.getUsername(), productId);
    }

}
