package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;


/**
 * This class represents a train departure.
 *
 * @version 1.0 2023-12-06
 *
 * */

public class TrainDeparture {
    private LocalTime departureTime;
    private String line;
    private int trainNumber;
    private String destination;
    private int track;
    private Duration delay;

    public TrainDeparture(LocalTime departureTime, String line, int trainNumber, String destination, int track, Duration delay) {
        this.departureTime = departureTime;
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.track = track;
        this.delay = delay;
    }
    // Getters and setters for the variables

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public Duration getDelay() {
        return delay;
    }

    public void setDelay(Duration delay) {
        this.delay = delay;
    }

    public void assignTrack(int track) {
        this.track = track;
    }

    public boolean hasDeparted(LocalTime currentTime) {
        return currentTime.isAfter(departureTime);
    }

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
