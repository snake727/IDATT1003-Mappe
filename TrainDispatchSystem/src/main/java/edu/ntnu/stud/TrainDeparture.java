package edu.ntnu.stud;

import java.time.LocalTime;
import java.time.Duration;

/**
 * This class represents a train departure.
 * @version 1.0 2023-12-06
 * */

//Class the creates a system for train departures and only train departures. This class should only be used for train departures from one station.
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
}
