package com.ecommerce.order.services;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;


    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
      /// Look for product in DB


        //Check if product exists
//        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
//        if(productOpt.isEmpty())
//            return  false;
//
//        //Check if product has enough stock
//        Product product = productOpt.get();
//        if(product.getStockQuantity() < cartItemRequest.getQuantity())
//            return false;
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if(existingCartItem != null){
            //Update the Quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
//            existingCartItem.setPrice(productId.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())) );
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;

    }


    public boolean deleteItemFromCart(String userId, String productId) {

//        Optional<Product> productOpt = productRepository.findById(productId);
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

//        if(productOpt.isPresent() && userOpt.isPresent()){
//            cartItemRepository.deleteByUserIdAndProductId(userOpt.get(),productOpt.get());
//            return true;
//        }
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;


    }

//    public  List<CartItem> getCartItems(String userId) {
//      return userRepository.findById(Long.valueOf(userId))
//              .map(cartItemRepository:: findByUser)
//              .orElse(List.of());
//    }
    public  List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

//    public void clearCart(String userId) {
//        userRepository.findById(Long.valueOf(userId))
//                .ifPresent(cartItemRepository::deleteByUser);
//    }

    public void clearCart(String userId) {
     cartItemRepository.deleteByUserId(userId);
    }
}
