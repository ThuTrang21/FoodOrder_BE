package com.example.foodOrder.Service;

import com.example.foodOrder.model.Category;
import com.example.foodOrder.model.Event;
import com.example.foodOrder.request.CreateEventRequest;

import java.util.List;

public interface EventService {
    public Event createEvent(CreateEventRequest req, Long restaurantId) throws Exception;
    public List<Event> getAllEvent() throws Exception;

    public List<Event> getEventByRestaurantId(Long restaurantId) throws Exception;
    public void deleteEvent(Long eventId) throws Exception ;
    public Event findEventById(Long eventId) throws Exception;

}
