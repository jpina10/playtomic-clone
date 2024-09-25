package venue.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import venue.service.model.Venue;
import venue.service.repository.VenueRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public Venue createVenue() {
        return venueRepository.save(new Venue());
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }
}
