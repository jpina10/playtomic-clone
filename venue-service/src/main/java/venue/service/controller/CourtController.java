package venue.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import venue.service.dto.BookCourtDto;
import venue.service.dto.CreateCourtDto;
import venue.service.model.Court;
import venue.service.service.CourtService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courts")
public class CourtController {

    private final CourtService courtService;

    @PostMapping
    public Court createCourt(@RequestBody CreateCourtDto createCourtDto) {
        return courtService.createCourt(createCourtDto);
    }

    @GetMapping
    public List<Court> findAllCourts() {
        return courtService.findAllCourts();
    }

    @PostMapping("/book")
    public Court bookCourt(@RequestBody BookCourtDto bookCourtDto) {
        return courtService.bookCourt(bookCourtDto);
    }
}
