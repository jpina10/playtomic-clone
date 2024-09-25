package venue.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import venue.service.model.Court;

@Repository
public interface CourtRepository extends MongoRepository<Court, String> {
}
