package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * This class represents a clock, and its sole purpose is to keep track of time.
 * It also provides methods for dealing with time relevant for the program.
 *
 * @version 1.3 2023-12-11
 *
 * */

public class Clock {
  private LocalTime time;

  /**
   * Creates a clock with the time set to 00:00.
   * */
  public Clock() {
    time = LocalTime.of(0, 0);
  }

  /**
   * Returns the current time of the clock.
   *
   * @return the time
   * */
  public LocalTime getCurrentTime() {
    return time;
  }

  /**
   * Sets the time. Time cannot be before current time.
   * Added logic to make sure time is between 00:00 and 23:59.
   * Also check that it is not before current time, as this is a requirement for its task.
   *
   * @param time the time to be set.
   * @throws IllegalArgumentException if the time is null, above 23:59 or below 00:00
   * @throws IllegalStateException if the time is before current time
   * */
  public void setTime(LocalTime time) {
    if (time == null || time.isAfter(LocalTime.of(23, 59)) || time.isBefore(LocalTime.of(0, 0))) {
      throw new IllegalArgumentException("Time must be between 00:00 and 23:59");
    } else if (time.isBefore(this.time)) {
      throw new IllegalStateException("Time cannot be before current time");
    } else {
      this.time = time;
    }
  }

  /**
   * Resets the time back to 00:00.
   * This is needed for resetting the clock in the UI.
   * This is because the clock cannot be set back by any other means.
   * */
  public void resetTime() {
    time = LocalTime.of(0, 0);
  }
}
