package com.example.foodOrder.Service;

import com.example.foodOrder.model.Event;
import com.example.foodOrder.model.Food;
import com.example.foodOrder.model.IngredientsCategory;
import com.example.foodOrder.model.Restaurant;
import com.example.foodOrder.repository.EventRepository;
import com.example.foodOrder.request.CreateEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp implements EventService {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EventRepository eventRepository;


    @Override
    public Event createEvent(CreateEventRequest req, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        Event event = new Event();
        event.setRestaurant(restaurant);
        event.setName(req.getName());
        event.setImage(req.getImage());
        event.setLocation(req.getLocation());
        event.setEndsAt(req.getEndsAt());
        event.setStartedAt(req.getStartedAt());

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvent() throws Exception {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventByRestaurantId(Long restaurantId) throws Exception {
        return eventRepository.findByRestaurantId(restaurantId);

    }

    @Override
    public void deleteEvent(Long eventId) throws Exception {
        Event event=findEventById(eventId);
        event.setRestaurant(null);
        eventRepository.delete(event);
    }

    @Override
    public Event findEventById(Long eventId) throws Exception {
        Optional<Event> optionalEvent=eventRepository.findById(eventId);
        if(optionalEvent.isEmpty()){
            throw new Exception("Not found");
        }
        return optionalEvent.get();
    }
}
