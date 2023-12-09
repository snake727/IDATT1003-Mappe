package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class creates the framework for testing the TrainDeparture class.
 * @version 1.1 2023-12-07
 */

class TrainDepartureTest {
    @Test
    @DisplayName("Check if setDestination() works")
    void testSetDestination() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDeparture.setDestination("Trondheim");
        assertEquals("Trondheim", trainDeparture.getDestination());
    }

    @Test
    @DisplayName("Check if setTrack works")
    void testSetTrack() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDeparture.setTrack("2");
        assertEquals("2", trainDeparture.getTrack());

    }

    @Test
    @DisplayName("Check if setTrack throws IllegalArgumentException when track is not a positive number")
    void testSetTrackThrowsIllegalArgumentExceptionWhenTrackIs() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));

        // Checks if IllegalArgumentException is thrown when track is set to null.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrack(null));

        // Checks if IllegalArgumentException is thrown when track is set to a string.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrack("a"));

        // Checks if IllegalArgumentException is thrown when track is set to a negative number.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrack("-1"));
    }

    @Test
    @DisplayName("Check if setDelay works")
    void testSetDelay() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", null);
        trainDeparture.setDelay(Duration.ofHours(1),Duration.ofMinutes(30));
        assertEquals(Duration.ofHours(1).plusMinutes(30), trainDeparture.getDelay());
        assertNotEquals(Duration.ofHours(1).plusMinutes(29), trainDeparture.getDelay());
        assertNotEquals(Duration.ofHours(1).plusMinutes(31), trainDeparture.getDelay());
    }

    @Test
    @DisplayName("Check if setLine works")
    void testSetLine() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDeparture.setLine("L2");
        assertEquals("L2", trainDeparture.getLine());
    }

    @Test
    @DisplayName("Check if setLine throws IllegalArgumentException when line is null")
    void testSetLineThrowsIllegalArgumentExceptionWhenLineIsNull() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine(null));
    }

    @Test
    @DisplayName("Check if setLine throws IllegalArgumentException when line is not one char followed by one digit")
    void testSetLineThrowsIllegalArgumentExceptionWhenLineIsNotOneCharFollowedByOneDigit() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));

        // Checks if IllegalArgumentException is thrown when line is set to a single letter.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("L"));

        // Checks if IllegalArgumentException is thrown when line is set to a single positive digit.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("1"));

        // Checks if IllegalArgumentException is thrown when line is set to a single negative digit.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("-1"));
    }

    @Test
    @DisplayName("Check if setTrainNumber works")
    void testSetTrainNumber() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDeparture.setTrainNumber(2);
        assertEquals(2, trainDeparture.getTrainNumber());
    }

    @Test
    @DisplayName("Check if setTrainNumber throws IllegalArgumentException when train number is below 1")
    void testSetTrainNumberThrowsIllegalArgumentExceptionWhenTrainNumberIsBelow1() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));

        // Checks if IllegalArgumentException is thrown when train number is set to 0.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrainNumber(0));

        // Checks if IllegalArgumentException is thrown when train number is set to a negative number.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrainNumber(-1));
    }

    @Test
    @DisplayName("Check if setDepartureTime works")
    void testSetDepartureTime() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        trainDeparture.setDepartureTime(LocalTime.of(12, 45));
        assertEquals(LocalTime.of(12, 45), trainDeparture.getDepartureTime());
    }

    @Test
    @DisplayName("Check if hasDeparted works")
    void testHasDeparted() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofSeconds(0));
        assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 29)));
        assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 30)));
        assertTrue(trainDeparture.hasDeparted(LocalTime.of(12, 31)));
    }
    
    @Test
    @DisplayName("Check if hasDeparted works with delay")
    void testHasDepartedWithDelay() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofSeconds(30));
        assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 29)));
        assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 30)));
        assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 30, 30)));
        assertTrue(trainDeparture.hasDeparted(LocalTime.of(12, 31)));
    }


    @Test
    @DisplayName("Check if setDepartureTime throws IllegalArgumentException when departure time is null")
    void testSetDepartureTimeThrowsIllegalArgumentExceptionIfInvalidInput() {
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));

        // Checks if IllegalArgumentException is thrown when departure time is set to null.
        assertThrows(IllegalArgumentException.class, () -> trainDeparture.setDepartureTime(null));
    }

    @Test
    @DisplayName("Check if printDetails works. PT0S is a standard representation of duration of time in the ISO-8601 format, where P indicates the period (duration) and T indicates the time. The number before the S indicates the number of seconds.")
    void testPrintDetails() {

        // Checks if printDetails() works when delay is 0 minutes.
        TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1", 123, "Oslo", "1", Duration.ofMinutes(0));
        assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 123, destination = Oslo, track = 1, delay = No delay", trainDeparture.printDetails());

        // Checks if printDetails() works when delay is 1 minute.
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 30), "L1", 123, "Oslo", "1", Duration.ofMinutes(1));
        assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 123, destination = Oslo, track = 1, delay = 1 minutes", trainDeparture2.printDetails());

        // Checks if printDetails() works when delay is 1 hour and 30 minutes.
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(12, 30), "L1", 123, "Oslo", "1", Duration.ofHours(1).plusMinutes(30));
        assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 123, destination = Oslo, track = 1, delay = 1 hours 30 minutes", trainDeparture3.printDetails());
    }



}