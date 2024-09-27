package venue.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import venue.service.dto.Booking;

public class CustomDeserializer implements Deserializer<Booking> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Booking deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Booking.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing Booking object", e);
        }
    }


}
