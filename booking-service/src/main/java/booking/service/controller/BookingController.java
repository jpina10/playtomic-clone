package booking.service.controller;

import booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{message}")
    public String booking(@PathVariable String message) {
        bookingService.sendTestMessage(message);

        return "OK";
    }
}
