package com.example.foodOrder.controller;

import com.example.foodOrder.Service.EventService;
import com.example.foodOrder.Service.RestaurantService;
import com.example.foodOrder.Service.UserService;
import com.example.foodOrder.model.Event;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.model.User;
import com.example.foodOrder.request.CreateEventRequest;
import com.example.foodOrder.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/admin/event")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        Event event=eventService.createEvent(req,restaurant.getId());
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvent( @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        List<Event> events=eventService.getAllEvent();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/admin/events/restaurant/{restaurantId}")
    public ResponseEntity<List<Event>> getRestaurantsEvent(@PathVariable Long restaurantId,
                                                           @RequestHeader("Authorization") String jwt) throws Exception{
        userService.findUserByJwtToken(jwt);
        List<Event> events=eventService.getEventByRestaurantId(restaurantId);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }

    @DeleteMapping("/admin/events/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(
            @PathVariable Long id,
                   @RequestHeader("Authorization") String jwt) throws  Exception{
        User user = userService.findUserByJwtToken(jwt);
        eventService.deleteEvent(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Event delete successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
