package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainDepartureTest {
    @Test
    @DisplayName("Check that the departure time is set correctly")
    void checkDepartureTimeNull() {
        TrainDeparture trainDeparture = new TrainDeparture(null, "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        assertNull(trainDeparture.getDepartureTime());
    }

    @Test
    void checkDepartureTimeIsAboveMax() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(24, 0), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        assertNull(trainDeparture.getDepartureTime());
    }

}