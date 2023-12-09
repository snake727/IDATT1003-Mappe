package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalTime;


/**
 * This class represents a train departure.
 * The data types used in this class are:
 * LocalTime, String, int, Duration
 * These data types fit the variables because:
 * departureTime is a time, line is a string, trainNumber is an integer, destination is a string, track is an integer, delay is a duration.
 * @version 1.4 2023-12-09
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
     * This method returns the departure time, plain and simple. It also adds the delay to the departure time, to ensure the correct departure time is returned in order for methods to work correctly.
     * @return the departure time + the delay
     * */
    public LocalTime getDepartureTime() {
        return departureTime.plus(delay);
    }

    /**
     * Method that sets the departure time. It utilizes the built-in checks in the LocalTime class to ensure the time is between 00:00 and 23:59. It also throws a custom exception if the departure time is null.
     * @param departureTime the departure time for a train departure.
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
     * Simple method that returns the line in a string format.
     * @return the line
     * */
    public String getLine() {
        return line;
    }

    /**
     * Method that sets the line. The line is assigned as a String, but converted to a char before being assigned to the line variable, this is to ensure that the line is only one character followed by one digit. It also throws a custom exception if the line is null or if the line does not meet the requirements.
     * @param line the line the train is supposed to follow.
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
     * Simple method that returns the train number as an integer.
     * @return the train number
     * */
    public int getTrainNumber() {
        return trainNumber;
    }

    /**
     * Method that sets the train number. It also throws a custom exception if the train number is below 1. This is to make the class more robust, as it should not be possible to set a train number below 1.
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
     * Simple method that returns the destination as a string.
     * @return the destination of the train
     * */
    public String getDestination() {
        return destination;
    }

    /**
     * Simple method that sets the destination. It is kept simple as destinations can be anything, and there is no need to check for anything.
     * @param destination the trains destination
     * */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Simple method that returns the track as a string.
     * @return the track that the train is assigned to.
     * */
    public String getTrack() {
        return track;
    }

    /**
     * Method that sets the track. The track is assigned as a String, but converted to an integer before being assigned to the track variable. This is to ensure that the track is a number by checking if it contains letters. If the track is null or empty, it is set to -1 as per the task requirement. It also throws an exception if the track is not a number.
     * @param track the train track
     * @throws IllegalArgumentException if the track is a string containing letters, as it should be a number.
     * */
    public void setTrack(String track) {
        if (track == null || track.isEmpty()) {
            this.track = "-1";
        } else if (!track.matches("-?\\d+")) {
            throw new IllegalArgumentException("Track must be a number");
        } else {
            this.track = String.valueOf(Integer.parseInt(track));
        }
    }

    /**
     * Simple method that returns the delay as the data type Duration.
     * @return the delay of a train departure.
     */

    public Duration getDelay() {
        return delay;
    }

    /**
     * Method that sets the delay. Requires both hours and minutes to work. Seconds are not included here on purpose, as any amount of seconds (before it amounts to minutes) of delay are virtually meaningless in infrastructure, hours and minutes are not. It also throws a custom exception if the delay is null or if the delay is negative.
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
            this.delay = hours.plus(minutes);
        }
    }

    /**
     * Method that checks if the train has departed by comparing the current time to the departure time plus the delay.
     * @return true if the train has departed, false if not.
     * */

    public boolean hasDeparted(@NotNull LocalTime currentTime) {
        return currentTime.isAfter(departureTime.plus(delay));
    }

    /**
     * Prints the details of the train departure. If the train has no delay, the delay is set to "No delay". Otherwise, the delay is printed in hours and minutes.
     * @return the details of the train departure.
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
