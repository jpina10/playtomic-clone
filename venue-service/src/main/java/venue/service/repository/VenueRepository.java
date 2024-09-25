package venue.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import venue.service.model.Venue;

@Repository
public interface VenueRepository extends MongoRepository<Venue, String> {
}
