package venue.service.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import venue.service.dto.Booking;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = "test-topic", groupId = "group-1")
    public void receive(Booking message) {
        log.info(message.toString());
    }
}
