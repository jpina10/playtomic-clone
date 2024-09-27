package booking.service.service;

import booking.service.model.Booking;
import booking.service.model.BookingStatus;
import booking.service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final KafkaTemplate<String, Booking> kafkaTemplate;
    private final BookingRepository bookingRepository;

    @Override
    public void sendTestMessage(String message) {

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        Booking savedBooking = bookingRepository.save(booking);

        kafkaTemplate.send("test-topic", savedBooking);
    }

}
