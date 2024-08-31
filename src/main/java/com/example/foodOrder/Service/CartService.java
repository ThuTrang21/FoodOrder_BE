package com.example.foodOrder.Service;

import com.example.foodOrder.model.Cart;
import com.example.foodOrder.model.CartItem;
import com.example.foodOrder.request.AddCartItemRequest;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    public Long calculateCartTotals(Cart cart)  throws Exception;
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(Long userId) throws Exception;
    public Cart clearCart(Long userId) throws Exception;
}
