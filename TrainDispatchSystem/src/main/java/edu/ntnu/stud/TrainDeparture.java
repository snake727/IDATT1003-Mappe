package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;


/**
 * This class represents a train departure.
 * The data types used in this class are:
 * LocalTime, String, int, Duration
 * These data types fit the variables because:
 * departureTime is a time, line is a string, trainNumber is an integer, destination is a string, track is an integer, delay is a duration.
 * @version 1.3 2023-12-08
 * */

public class TrainDeparture {

    private LocalTime departureTime;
    private String line;
    private int trainNumber;
    private String destination;
    private String track;
    private Duration delay;

    public TrainDeparture(LocalTime departureTime, String line, int trainNumber, String destination, String track, Duration delay) {
        this.departureTime = departureTime;
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.track = track;
        this.delay = delay;
    }

    /**
     * Gets the departure time.
     * @return the departure time
     * */
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     * @param departureTime the departure time
     * @throws IllegalArgumentException if the departure time is null. The class LocalTime has built-in checks for time being between 00:00 and 23:59.
     * */
    public void setDepartureTime(LocalTime departureTime) {
        if (departureTime== null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        } else {
            this.departureTime = departureTime;
        }
    }

    /**
     * Gets the line.
     * @return the line
     * */
    public String getLine() {
        return line;
    }

    /**
     * Sets the line.
     * @param line the line
     * @throws IllegalArgumentException if the line is null, or if the string is not one char followed by one digit.
     * */
    public void setLine(String line) {
        if (line == null || !line.matches("[A-Z]\\d")) {
            throw new IllegalArgumentException("Line must be one char followed by one digit");
        } else {
            this.line = line;
        }
    }

    /**
     * Gets the train number.
     * @return the train number
     * */
    public int getTrainNumber() {
        return trainNumber;
    }

    /**
     * Sets the train number.
     * @param trainNumber the train number
     * @throws IllegalArgumentException if the train number is below 1.
     * */
    public void setTrainNumber(int trainNumber) {
        if (trainNumber < 1) {
            throw new IllegalArgumentException("Train number must be above 0");
        } else {
            this.trainNumber = trainNumber;
        }
    }

    /**
     * Gets the destination.
     * @return the destination
     * */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination.
     * @param destination the destination
     * */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the track.
     * @return the track
     * */
    public String getTrack() {
        return track;
    }

    /**
     * Sets the track. The track is assigned as a String, but converted to an integer before being assigned to the track variable.
     * @param track the track
     * @throws IllegalArgumentException if the track is a string containing letters, as it should be a number.
     * */

    public void setTrack(String track) {
        if (track == null || !track.matches("\\d+")) {
            throw new IllegalArgumentException("Track must be a positive number");
        } else {
            this.track = String.valueOf(Integer.parseInt(track));
        }
    }

    /**
     * Gets the delay.
     * @return the delay
     */

    public Duration getDelay() {
        return delay;
    }

    /**
     * Sets the delay. Requires both hours and minutes to work.
     * @param hours the amount of hours to delay the train departure.
     * @param minutes the amount of minutes to delay the train departure.
     * @throws IllegalArgumentException if the delay is null, or if the delay is negative.
     */

    public void setDelay(Duration hours, Duration minutes) {
        if (hours == null || minutes == null) {
            throw new IllegalArgumentException("Delay cannot be null");
        } else if (hours.isNegative() || minutes.isNegative()) {
            throw new IllegalArgumentException("Delay cannot be negative");
        } else {
            this.delay = hours.plus(minutes);
        }
    }

    /**
     * Checks if the train has departed by comparing the current time to the departure time and delay.
     * @return true if the train has departed, false if not.
     * */

    public boolean hasDeparted(LocalTime currentTime) {
        return currentTime.isAfter(departureTime.plus(delay));
    }

    /**
     * Prints the details of the train departure. If the train has no delay, the delay is set to "No delay". Otherwise, the delay is printed in hours and minutes.
     * @return the details of a given train departure
     * */

    public String printDetails() {
        long hours = delay.toHoursPart();
        long minutes = delay.toMinutesPart();
        String delayString = "";

        // Checks if there is a delay. If there is a delay, checks if the delay is in hours, minutes or both.
        if (hours > 0) {
            delayString = String.format("%d hours %d minutes", hours, minutes);
        } else if (minutes > 0) {
            delayString = String.format("%d minutes", minutes);
        } else if (minutes == 0) {
            delayString = "No delay";
        }

        return "Train departure details: " +
                "departure-time = " + departureTime +
                ", line = " + line +
                ", train-number = " + trainNumber +
                ", destination = " + destination +
                ", track = " + track +
                ", delay = " + delayString;
    }
}
