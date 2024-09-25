package venue.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "venues")
@NoArgsConstructor
public class Venue {

    @Id
    private String id;

    private String name; //name of the club

    private List<Court> courts = new ArrayList<>();
}
