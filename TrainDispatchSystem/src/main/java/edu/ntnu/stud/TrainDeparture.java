package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;


/**
 * This class represents a train departure.
 * The data types used in this class are:
 * LocalTime, String, int, Duration
 * These data types fit the variables because:
 * departureTime is a time, line is a string, trainNumber is an integer, destination is a string, track is an integer, delay is a duration.
 * @version 1.2 2023-12-07
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
     * @throws IllegalArgumentException if the departure time is null, above 23:59 or below 00:00
     * */
    public void setDepartureTime(LocalTime departureTime) {
        if (departureTime == null || departureTime.isAfter(LocalTime.of(23, 59) ) || departureTime.isBefore(LocalTime.of(0, 0))) {
            throw new IllegalArgumentException("Departure time must be between 00:00 and 23:59");
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
     * Sets the delay.
     * @param delay the delay
     * @throws IllegalArgumentException if the delay is null, or if the delay is negative.
     * */

    public void setDelay(Duration delay) {
        if (delay == null || delay.isNegative() || delay.isZero()) {
            throw new IllegalArgumentException("Delay must be a positive number");
        } else {
            this.delay = delay;
        }
    }

    /**
     * Checks if the train has departed.
     * @return true if the train has departed, false if not.
     * */

    public boolean hasDeparted(LocalTime currentTime) {
        return currentTime.isAfter(departureTime);
    }

    /**
     * Prints the details of the train departure.
     * @return the details of a given train departure
     * */

    public String printDetails() {
        return "TrainDeparture{" +
                "departureTime=" + departureTime +
                ", line='" + line + '\'' +
                ", trainNumber=" + trainNumber +
                ", destination='" + destination + '\'' +
                ", track=" + track +
                ", delay=" + delay +
                '}';
    }
}
