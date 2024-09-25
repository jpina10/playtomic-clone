package venue.service.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public record BookCourtDto (String courtId, LocalDate date, LocalTime requestedStartTime, Duration duration){

}
