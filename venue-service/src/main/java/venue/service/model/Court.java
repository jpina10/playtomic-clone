package venue.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "courts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Court {

    @Id
    private String id;

    private String venueId;

    private boolean isBooked; // just a draft to be able to add saga pattern

    private List<TimeSlot> timeSlots = new ArrayList<>();
}
