package edu.ntnu.stud;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * This class creates the framework for testing the Clock class.
 * @version 1.0 2023-12-08
 * */

class ClockTest {
    @Test
    @DisplayName("Check if getCurrentTime works")
    void testGetCurrentTime() {
        // Create a clock
        Clock clock = new Clock();

        // Set the time to a known value
        LocalTime expectedTime = LocalTime.of(12, 0);
        clock.setTime(expectedTime);

        // Get the current time
        LocalTime actualTime = clock.getCurrentTime();

        // Check if the current time is the same as the expected time
        assertEquals(expectedTime, actualTime, "The current time should be the same as the expected time");
    }

    @Test
    @DisplayName("Check if setTime works")
    void testSetTime() {
        // Create a clock
        Clock clock = new Clock();

        // Set the time to a known value
        LocalTime expectedTime = LocalTime.of(12, 0);
        clock.setTime(expectedTime);

        // Get the current time
        LocalTime actualTime = clock.getCurrentTime();

        // Check if the current time is the same as the expected time
        assertEquals(expectedTime, actualTime, "The current time should be the same as the expected time");
    }

    /**
     * Side note for the following test method:
     * The LocalTime class has built-in logic that prevents the time from being set to a value that is not between 00:00 and 23:59.
     * Therefore, we do not need to test for this.
     * */
    @Test
    @DisplayName("Check if setTime throws IllegalArgumentException when time is null")
    void testSetTimeThrowsIllegalArgumentExceptionWhenTimeIsNull() {
        // Create a clock
        Clock clock = new Clock();

        // Check if IllegalArgumentException is thrown when time is set to null.
        assertThrows(IllegalArgumentException.class, () -> clock.setTime(null));
    }

    @Test
    @DisplayName("Check if the resetTime method works")
    void testResetTime() {
        // Create a clock
        Clock clock = new Clock();

        // Set the time to a known value
        LocalTime expectedTime = LocalTime.of(12, 0);
        clock.setTime(expectedTime);

        // Reset the time
        clock.resetTime();

        // Get the current time
        LocalTime actualTime = clock.getCurrentTime();

        // Check if the current time is the same as the expected time
        assertEquals(LocalTime.of(0, 0), actualTime, "The current time should be 00:00");
    }

}