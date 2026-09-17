package solutions.pge.kiddieline.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final Sinks.Many<Event> eventSink = Sinks.many().multicast().onBackpressureBuffer();
    private final EventRepository eventRepository;


    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getAllEvents(){
        return eventRepository.findAllByOrderByTimestampDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventRepository.save(newEvent);
        eventSink.tryEmitNext(savedEvent);

        return savedEvent;
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> streamEvents() {
        return eventSink.asFlux();
    }


}
