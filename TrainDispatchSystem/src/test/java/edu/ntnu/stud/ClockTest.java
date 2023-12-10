package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * This class creates the framework for testing the Clock class.
 * @version 1.1 2023-12-10
 * */

class ClockTest {
    private Clock clock;

    @BeforeEach
    void setup() {
        clock = new Clock();
    }

    @Test
    @DisplayName("Check if getCurrentTime works")
    void testGetCurrentTime() {
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
        // Check if IllegalArgumentException is thrown when time is set to null.
        assertThrows(IllegalArgumentException.class, () -> clock.setTime(null));
    }

    @Test
    @DisplayName("Check if the resetTime method works")
    void testResetTime() {
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

    @Test
    @DisplayName("Check if resetTime works when time is already 00:00")
    void testResetTimeWhenTimeIsAlreadyMinimum() {
        // Set the time to the minimum possible value
        LocalTime minTime = LocalTime.of(0, 0);
        clock.setTime(minTime);

        // Reset the time
        clock.resetTime();

        // Check if the current time is still the minimum possible value
        assertEquals(minTime, clock.getCurrentTime(), "The current time should still be 00:00");
    }

    @Test
    @DisplayName("Check if setTime works with random values")
    void testSetTimeWithRandomValues() {
        // Generate a random hour and minute
        Random random = new Random();
        int randomHour = random.nextInt(24);
        int randomMinute = random.nextInt(60);
    
        // Set the time to a random value
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute);
        clock.setTime(randomTime);
        assertEquals(randomTime, clock.getCurrentTime(), "The current time should be the randomly set time");
    }
}