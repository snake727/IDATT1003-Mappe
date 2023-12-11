package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a train departure.
 * The data types used in this class are:
 * LocalTime, String, int, Duration.
 * These data types fit the variables because:
 * departureTime is a LocalTime type, as its class is well suited for the program criteria.
 * line is a string as it should be one letter and one number.
 * trainNumber is an integer, as it should be a number.
 * destination is a string as it can be anything.
 * track is a String, as strings are easier to work with than integers.
 * In retrospect, I'm not sure why I didn't use an integer here.
 * delay is a Duration type, as its class is well suited for the program criteria.
 * trainNumber is an integer, this is kept simple as there is no logic required for this variable,
 *
 * @version 1.6 2023-12-11
 * */

public class TrainDeparture {

  // This variable is for storing the train departure time.
  private LocalTime tdt;

  // This variable is for storing the line of a train departure.
  private String line;

  // This variable is for storing the train number of a train departure.
  private int trn;

  // This variable is for storing the destination of a train departure.
  private String dst;

  // This variable is for storing the track of a train departure.
  private String tra;

  // This variable is for storing the delay of a train departure.
  private Duration del;

  /**
   * Creates a train departure.
   * It also throws a custom exception if one of the parameters is null.
   *
   * @param tdt the departure time for a train departure.
   * @param line the line the train is supposed to follow.
   * @param trn the train number
   * @param dst the trains destination
   * @param tra the train track
   * @param del the delay of a train departure.
   * @throws IllegalArgumentException if one of the parameters is null.
   * */
  public TrainDeparture(LocalTime tdt, String line, int trn, String dst, String tra, Duration del) {
    this.tdt = tdt;
    this.line = line;
    this.trn = trn;
    this.dst = dst;
    this.tra = tra;
    this.del = del;
  }

  // Simple method that returns the departure time.
  public LocalTime getTdt() {
    return tdt.plus(del);
  }

  /**
   * Sets the departure time.
   * Uses the built-in checks in the LocalTime class to ensure the time is between 00:00 and 23:59.
   * It also throws a custom exception if the departure time is null.
   *
   * @param tdt the departure time for a train departure.
   * @throws IllegalArgumentException if the departure time is null.
   * */
  public void setTdt(LocalTime tdt) {
    if (tdt == null) {
      throw new IllegalArgumentException("Departure time cannot be null");
    } else {
      this.tdt = tdt;
    }
  }

  /**
   * Returns the line in a string format.
   *
   * @return the line
   * */
  public String getLine() {
    return line;
  }

  /**
   * Sets the line. The line is assigned as a String.
   * Then it is converted to an uppercase char before being assigned to the line variable.
   * This is to ensure that the line is only one character followed by one digit.
   *
   * @param line the line the train is supposed to follow.
   * @throws IllegalArgumentException if the line is null, or if the input is invalid.
   * */
  public void setLine(String line) {
    if (line == null || !line.matches("[A-Z]\\d")) {
      throw new IllegalArgumentException("Line must be one char followed by one digit");
    } else {
      this.line = line;
    }
  }

  /**
   * Returns the train number as an integer.
   *
   * @return the train number
   * */
  public int getTrn() {
    return trn;
  }

  /**
   * Sets the train number.
   * It also throws a custom exception if the train number is below 1.
   * This is to make the class more robust.
   *
   * @param trn the train number
   * @throws IllegalArgumentException if the train number is below 1.
   * */
  public void setTrn(int trn) {
    if (trn < 1) {
      throw new IllegalArgumentException("Train number must be above 0");
    } else {
      this.trn = trn;
    }
  }

  /**
   * Returns the destination as a string.
   *
   * @return the destination of the train
   * */
  public String getDst() {
    return dst;
  }

  /**
   * Sets the destination.
   * It is kept simple as destinations can be anything, and there is no need to check for anything.
   *
   * @param dst the trains destination
   * */
  public void setDst(String dst) {
    this.dst = dst;
  }

  /**
   * Returns the track as a string.
   *
   * @return the track that the train is assigned to.
   * */
  public String getTra() {
    return tra;
  }

  /**
   * Sets the track. The track is assigned as a String.
   * Then it is converted to an integer before being assigned to the track variable.
   * This is to ensure that the track is a number by checking if it contains letters.
   * If the track is null or empty, it is set to -1 as per the task requirement.
   * It also throws an exception if the track is not a number.
   *
   * @param tra the train track
   * @throws IllegalArgumentException if the track is a string containing letters.
   * */
  public void setTra(String tra) {
    if (tra == null || tra.isEmpty()) {
      this.tra = "-1";
    } else if (!tra.matches("-?\\d+")) {
      throw new IllegalArgumentException("Track must be a number");
    } else {
      this.tra = String.valueOf(Integer.parseInt(tra));
    }
  }

  /**
   * Returns the delay as the data type Duration.
   *
   * @return the delay of a train departure.
   */

  public Duration getDel() {
    return del;
  }

  /**
   * Sets the delay. Requires both hours and minutes to work.
   * Seconds are not included here on purpose.
   * Seconds of delay are virtually meaningless in infrastructure, hours and minutes are not.
   * It also throws a custom exception if the delay is null or if the delay is negative.
   *
   * @param hours the amount of hours the train departure is delayed.
   * @param minutes the amount of minutes the train departure is delayed.
   * @throws IllegalArgumentException if the delay is null, or if the delay is negative.
   */

  public void setDelay(Duration hours, Duration minutes) {
    if (hours == null || minutes == null) {
      throw new IllegalArgumentException("Delay cannot be null");
    } else if (hours.isNegative() || minutes.isNegative()) {
      throw new IllegalArgumentException("Delay cannot be negative");
    } else {
      this.del = hours.plus(minutes);
    }
  }

  /**
   * Checks if the train has departed.
   * It does so by comparing the current time to the departure time plus the delay.
   *
   * @return true if the train has departed, false if not.
   * */

  public boolean hasDeparted(@NotNull LocalTime currentTime) {
    return currentTime.isAfter(tdt.plus(del));
  }

  /**
   * Prints the details of the train departure.
   * If the train has no delay, the delay is set to "No delay".
   * Otherwise, the delay is printed in hours and minutes.
   *
   * @return the details of the train departure.
   * */

  public String printDetails() {
    long hours = del.toHoursPart();
    long minutes = del.toMinutesPart();
    String delayString = "";

    // Checks if there is a delay.
    // If there is a delay, checks if the delay is in hours, minutes or both.
    if (hours > 0) {
      delayString = String.format("%d hours %d minutes", hours, minutes);
    } else if (minutes > 0) {
      delayString = String.format("%d minutes", minutes);
    } else if (minutes == 0) {
      delayString = "No delay";
    }

    return "Train departure details: "
          +
      "departure-time = " + tdt
          +
      ", line = " + line
          +
      ", train-number = " + trn
          +
      ", destination = " + dst
          +
      ", track = " + tra
          +
      ", delay = " + delayString;
  }
}
