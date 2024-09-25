package venue.service.service;

import venue.service.model.Venue;

import java.util.List;

public interface VenueService {
    Venue createVenue();
    List<Venue> getAllVenues();
}
