package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * This class represents a clock, and its sole purpose is to keep track of time, and provide methods for dealing with time.
 *
 * @version 1.1 2023-12-08
 *
 * */

public class Clock {
    private LocalTime time;

    /**
     * Constructor that creates a clock with the time set to 00:00.
     * */
    public Clock() {
        time = LocalTime.of(0, 0);
    }

    /**
     * Gets the time.
     * @return the time
     * */
    public LocalTime getCurrentTime() {
        return time;
    }

    /**
     * Sets the time. Time cannot be before current time.
     * @param time the time
     * @throws IllegalArgumentException if the time is null, above 23:59 or below 00:00
     * @throws IllegalStateException if the time is before current time
     * */
    public void setTime(LocalTime time) {
        if (time == null || time.isAfter(LocalTime.of(23, 59) ) || time.isBefore(LocalTime.of(0, 0))) {
            throw new IllegalArgumentException("Time must be between 00:00 and 23:59");
        } else if (time.isBefore(this.time)) {
            throw new IllegalStateException("Time cannot be before current time");
        } else {
            this.time = time;
        }
    }

    /**
     * Resets the time back to 00:00.
     * */
    public void resetTime() {
        time = LocalTime.of(0, 0);
    }
}
