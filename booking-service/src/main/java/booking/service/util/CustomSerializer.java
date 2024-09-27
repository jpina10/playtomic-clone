package booking.service.util;

import booking.service.model.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerializer implements Serializer<Booking> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Booking booking) {
        try {
            return objectMapper.writeValueAsBytes(booking);
        } catch (Exception e) {
            throw new SerializationException("Error serializing Booking object", e);
        }
    }


}
