package solutions.pge.kiddieline.events;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String title, String description, String icon, Instant timestamp) {
        Event newEvent = new Event(title, description, icon, timestamp);
        return eventRepository.save(newEvent);
    }
}
