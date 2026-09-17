package solutions.pge.kiddieline.events;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Document(collection = "events")
public record Event(

        @Id
        String id,
        String title,
        String description,
        String icon,
        Instant timestamp
) {
    public Event(String title, String description, String icon, Instant timestamp){
        this(null, title, description, icon, timestamp);
    }
}
