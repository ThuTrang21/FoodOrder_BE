package com.example.foodOrder.Service;

import com.example.foodOrder.dto.RestaurantDTO;
import com.example.foodOrder.model.Address;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.model.User;
import com.example.foodOrder.repository.AddressRepository;
import com.example.foodOrder.repository.RestaurantRepository;
import com.example.foodOrder.repository.UserRepository;
import com.example.foodOrder.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepository.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setCustomer(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        // Lấy nhà hàng từ cơ sở dữ liệu theo restaurantId
        Restaurant restaurant = findRestaurantById(restaurantId);

        // Cập nhật thông tin nhà hàng từ request nếu có giá trị mới
        if (updateRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if (updateRestaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if (updateRestaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
        }
        if (updateRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(updateRestaurant.getContactInformation());
        }
        if (updateRestaurant.getAddress() != null) {
            restaurant.setAddress(updateRestaurant.getAddress());
        }
        if (updateRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updateRestaurant.getOpeningHours());
        }
        if (updateRestaurant.getImages() != null) {
            restaurant.setImages(updateRestaurant.getImages());
        }

        // Lưu nhà hàng đã cập nhật vào cơ sở dữ liệu
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
       Restaurant restaurant=findRestaurantById(restaurantId);
       restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("restaurant not found with id");
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByCustomerId(userId);
        if(restaurant==null){
            throw new Exception("restaurant not found with owner id "+ userId);

        }
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        RestaurantDTO dto=new RestaurantDTO();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDTO> favorites=user.getFavorites();
        for(RestaurantDTO favorite:favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorited=true;
                break;
            }
        }
        if(isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));

        }
        else
            favorites.add(dto);

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
       Restaurant restaurant=findRestaurantById(id);
       restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
