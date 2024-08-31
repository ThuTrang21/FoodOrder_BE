package com.example.foodOrder.Service;

import com.example.foodOrder.model.Cart;
import com.example.foodOrder.model.CartItem;
import com.example.foodOrder.model.Food;
import com.example.foodOrder.model.User;
import com.example.foodOrder.repository.CartItemRepository;
import com.example.foodOrder.repository.CartRepository;
import com.example.foodOrder.repository.FoodRepository;
import com.example.foodOrder.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodById(req.getFoodId());

        Cart cart=cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem=new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setFood(food);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());


        CartItem savedCartItem=cartItemRepository.save(newCartItem);

        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
        if(opt.isEmpty())
            throw new Exception("cart item not found");
        CartItem item=opt.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
        if(opt.isEmpty())
            throw new Exception("cart item not found");

        CartItem item=opt.get();
        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total=0L;
        for(CartItem cartItem:cart.getItem()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> opt=cartRepository.findById(id);
        if(opt.isEmpty())
            throw new Exception("cart not found");

        return opt.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart=cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart=findCartById(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
