package venue.service.service;

import venue.service.dto.BookCourtDto;
import venue.service.dto.CreateCourtDto;
import venue.service.model.Court;

import java.util.List;

public interface CourtService {
    Court createCourt(CreateCourtDto court);

    Court bookCourt(BookCourtDto bookCourtDto);

    List<Court> findAllCourts();
}
