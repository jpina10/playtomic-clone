package venue.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import venue.service.model.Venue;
import venue.service.service.VenueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/venue/")
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public Venue addVenue() {
        return venueService.createVenue();
    }

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

}
