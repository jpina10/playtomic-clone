package venue.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import venue.service.dto.BookCourtDto;
import venue.service.dto.CreateCourtDto;
import venue.service.model.Court;
import venue.service.model.Venue;
import venue.service.repository.CourtRepository;
import venue.service.repository.VenueRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final VenueRepository venueRepository;

    @Override
    public Court createCourt(CreateCourtDto courtDto) {
        Venue venue = venueRepository.findById(courtDto.venueId()).orElseThrow(() -> new RuntimeException("Venue doesn't exist"));

        Court court = new Court();
        court.setVenueId(venue.getId());

        Court savedCourt = courtRepository.save(court);

        venue.getCourts().add(savedCourt);

        venueRepository.save(venue);

        return savedCourt;
    }

    // Method to handle court booking with fixed time slots
    @Override
    public Court bookCourt(BookCourtDto bookCourtDto) {
        Court court = courtRepository.findById(bookCourtDto.courtId()).orElseThrow(() -> new RuntimeException("Court doesn't exist"));

        court.setBooked(true);

        return courtRepository.save(court);
    }

    @Override
    public List<Court> findAllCourts() {
        return courtRepository.findAll();
    }

}

