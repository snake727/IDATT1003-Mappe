package edu.ntnu.stud;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.time.Duration;
import java.time.LocalTime;


/**
 * This class creates a system for train departures.
 *
 * @version 1.4 2023-12-09
 *
 * */

public class TrainDispatchSystem {
    // Class that that declares variables, creates a constructor, creates methods for adding, removing and updating train departures, and creates a method for printing the train departures.

    private final List<TrainDeparture> trainDepartures;

    public TrainDispatchSystem() {
        trainDepartures = new ArrayList<>();
    }
    /**
     * Method that adds a train departure. Train departure cannot be added if train number already exists.
     * @param trainDeparture the train departure details
     * @throws IllegalArgumentException if the train number already exists
     * */
    public void addTrainDeparture(TrainDeparture trainDeparture) {
        for (TrainDeparture trainDeparture1 : trainDepartures) {
            if (trainDeparture1.getTrainNumber() == trainDeparture.getTrainNumber()) {
                throw new IllegalArgumentException("Train number already exists");
            }
        }
        trainDepartures.add(trainDeparture);
    }

    /**
     * Method that removes a train departure.
     * @param index the index of the train departure to be removed
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void removeTrainDeparture(int index) {
        trainDepartures.remove(index);
    }

    /**
     * Method that returns the list of train departures sorted by departure time.
     * @return the train departures
     * */
    public TrainDeparture[] getTrainDeparturesSortedByDepartureTime() {
        TrainDeparture[] trainDeparturesSortedByDepartureTime = new TrainDeparture[trainDepartures.size()];
        trainDepartures.toArray(trainDeparturesSortedByDepartureTime);
        Arrays.sort(trainDeparturesSortedByDepartureTime, Comparator.comparing(TrainDeparture::getDepartureTime));
        return trainDeparturesSortedByDepartureTime;
    }

    /**
     * Method that returns a train departure by train number.
     * @param trainNumber the train number
     * @return a train departure
     * */
    public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                return trainDeparture;
            }
        }
        return null;
    }

    /**
     * Method that returns the list of train departures sorted by destination.
     * @param destination the destination
     * @return the train departures
     * */
    public TrainDeparture[] getTrainDeparturesByDestination(String destination) {
        List<TrainDeparture> trainDeparturesByDestination = new ArrayList<>();
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getDestination().equals(destination)) {
                trainDeparturesByDestination.add(trainDeparture);
            }
        }
        TrainDeparture[] trainDeparturesByDestinationArray = new TrainDeparture[trainDeparturesByDestination.size()];
        trainDeparturesByDestination.toArray(trainDeparturesByDestinationArray);
        return trainDeparturesByDestinationArray;
    }

    /**
     * Method for assigning a track to a train departure.
     * @param trainNumber the train number
     * @param track the track
     */
    public void assignTrackToTrainDeparture(int trainNumber, String track) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                trainDeparture.setTrack(track);
            }
        }
    }

    /**
     * Method to set delay for a train departure. Method needs both amount of hours and minutes.
     * @param trainNumber the train number
     * @param minutes the amount of minutes to delay the train departure.
     * @throws IllegalArgumentException if the minutes is negative.
     * @throws IllegalStateException if the train departure has already departed.
     * */
    public void setDelayForTrainDeparture(int trainNumber, Duration hours, Duration minutes) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                if (minutes.isNegative()) {
                    throw new IllegalArgumentException("Minutes cannot be negative");
                } else if (trainDeparture.getDepartureTime().isBefore(LocalTime.now())) {
                    throw new IllegalStateException("Train departure has already departed");
                } else {
                    trainDeparture.setDelay(hours, minutes);
                }
            }
        }
    }

    /**
     * Method that checks if a train has departed, and removes it from the list of train departures if it has. The method is called every time the clock is updated. The method should use the Clock class to get the current time.
     * */
    public void removeTrainDepartureIfDeparted(LocalTime currentTime) {
        trainDepartures.removeIf(trainDeparture -> trainDeparture.getDepartureTime().isBefore(currentTime));
    }

    /**
     * Method that prints the all the departures in a list.
     * @return the details of all train departures in a list
     * */
    public List<TrainDeparture> getTrainDepartures() {
        return trainDepartures;
    }
}

