package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader ("X-User-ID")String userId, @RequestBody CartItemRequest cartItemRequest){
         if(!cartService.addToCart(userId,cartItemRequest)){
             return  ResponseEntity.badRequest().body("Product out of stock or User not found or Product not found");
         }
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader ("X-User-ID")String userId,@PathVariable String productId) {
        boolean result = cartService.deleteItemFromCart(userId, productId);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCartItem(@RequestHeader ("X-User-ID")String userId) {
       return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
