package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * This class creates the framework for testing the TrainDispatchSystem class.
 * @version 1.1 2023-12-09
 */

class TrainDispatchSystemTest {
    @Test
    @DisplayName("Check if addTrainDeparture works")
    void testAddTrainDeparture() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        assertEquals(trainDeparture, trainDispatchSystem.getTrainDepartureByTrainNumber(1));
    }

    @Test
    @DisplayName("Check if addTrainDeparture throws IllegalArgumentException when train number already exists")
    void testAddTrainDepartureThrowsIllegalArgumentExceptionWhenTrainNumberAlreadyExists() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2", 1, "Trondheim", "2", Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        assertThrows(IllegalArgumentException.class, () -> trainDispatchSystem.addTrainDeparture(trainDeparture2));
    }

    @Test
    @DisplayName("Check if removeTrainDeparture works")
    void testRemoveTrainDeparture() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        trainDispatchSystem.removeTrainDeparture(0);
        assertNull(trainDispatchSystem.getTrainDepartureByTrainNumber(1));
    }

    @Test
    @DisplayName("Check if getTrainDeparturesSortedByDepartureTime works")
    void testGetTrainDeparturesSortedByDepartureTime() {
        TrainDispatchSystem tds = new TrainDispatchSystem();

        // Add some train departures in an unsorted order
        tds.addTrainDeparture(new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0)));
        tds.addTrainDeparture(new TrainDeparture(LocalTime.of(10, 30), "L2", 2, "Bergen", "2", Duration.ofMinutes(0)));
        tds.addTrainDeparture(new TrainDeparture(LocalTime.of(11, 30), "L3", 3, "Trondheim", "3", Duration.ofMinutes(0)));

        // Get the train departures sorted by departure time
        TrainDeparture[] sortedTrainDepartures = tds.getTrainDeparturesSortedByDepartureTime();

        // Check that the train departures are sorted by departure time
        assertEquals(LocalTime.of(10, 30), sortedTrainDepartures[0].getDepartureTime());
        assertEquals(LocalTime.of(11, 30), sortedTrainDepartures[1].getDepartureTime());
        assertEquals(LocalTime.of(12, 30), sortedTrainDepartures[2].getDepartureTime());
    }

    @Test
    @DisplayName("Check if getTrainDepartureByTrainNumber works")
    void testGetTrainDepartureByTrainNumber() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        assertEquals(trainDeparture, trainDispatchSystem.getTrainDepartureByTrainNumber(1));
    }

    @Test
    @DisplayName("Check if getTrainDeparturesByDestination works")
    void testGetTrainDeparturesByDestination() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2", 2, "Oslo", "2", Duration.ofMinutes(0));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(13, 0), "L3", 3, "Bergen", "3", Duration.ofMinutes(0));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(13, 15), "L4", 4, "Stavanger", "4", Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        trainDispatchSystem.addTrainDeparture(trainDeparture2);
        trainDispatchSystem.addTrainDeparture(trainDeparture3);
        trainDispatchSystem.addTrainDeparture(trainDeparture4);
        TrainDeparture[] trainDepartures = trainDispatchSystem.getTrainDeparturesByDestination("Oslo");
        assertEquals(2, trainDepartures.length);
        assertEquals(trainDeparture1, trainDepartures[0]);
        assertEquals(trainDeparture2, trainDepartures[1]);
    }

    @Test
    @DisplayName("Check if the assignTrackToTrainDeparture method works")
    void testAssignTrackToTrainDeparture() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", null, Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        trainDispatchSystem.assignTrackToTrainDeparture(1, "1");
        assertEquals("1", trainDeparture.getTrack());
    }

    @Test
    @DisplayName("Check if the setDelayForTrainDeparture method works")
    void testSetDelayForTrainDeparture() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", null, Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        trainDispatchSystem.setDelayForTrainDeparture(1, Duration.ofHours(1), Duration.ofMinutes(5));
        assertEquals(Duration.ofHours(1).plusMinutes(5), trainDeparture.getDelay());
    }

    @Test
    @DisplayName("Check if the setDelayForTrainDeparture method throws IllegalArgumentException when minutes are negative")
    void testSetDelayForTrainDepartureThrowsIllegalArgumentExceptionWhenMinutesAreNegative() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", null, Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        assertThrows(IllegalArgumentException.class, () -> trainDispatchSystem.setDelayForTrainDeparture(1, Duration.ofHours(1), Duration.ofMinutes(-1)));
    }

    @Test
    @DisplayName("Check if the setDelayForTrainDeparture method throws IllegalStateException when the train has already departed")
    void testSetDelayForTrainDepartureThrowsIllegalStateExceptionWhenTheTrainHasAlreadyDeparted() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.now().minusHours(1), "L1", 1, "Oslo", null, Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture);
        assertThrows(IllegalStateException.class, () -> trainDispatchSystem.setDelayForTrainDeparture(1, Duration.ofHours(1), Duration.ofMinutes(0)));
    }

    @Test
    @DisplayName("Check if the removeTrainDepartureIfDeparted method works")
    void testRemoveTrainDepartureIfDeparted() {
        TrainDispatchSystem trainDispatchSystem = new TrainDispatchSystem();
        Clock clock = new Clock();
        clock.setTime(LocalTime.of(13, 0));
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12,30), "L1", 1, "Oslo", null, Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13,30), "L2", 2, "Oslo", null, Duration.ofMinutes(0));
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        trainDispatchSystem.addTrainDeparture(trainDeparture2);
        trainDispatchSystem.removeTrainDepartureIfDeparted(clock.getCurrentTime());
        assertNull(trainDispatchSystem.getTrainDepartureByTrainNumber(1));
        assertNotNull(trainDispatchSystem.getTrainDepartureByTrainNumber(2));
    }



}