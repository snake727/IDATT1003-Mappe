package edu.ntnu.stud;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.time.Duration;

/**
 * This class creates a system for train departures.
 *
 * @version 1.1 2023-12-07
 *
 * */

public class TrainDispatchSystem {
    // Class that that declares variables, creates a constructor, creates methods for adding, removing and updating train departures, and creates a method for printing the train departures.

    private final List<TrainDeparture> trainDepartures;

    public TrainDispatchSystem() {
        trainDepartures = new ArrayList<>();
    }

    public void addTrainDeparture(TrainDeparture trainDeparture) {
        trainDepartures.add(trainDeparture);
    }

    public void removeTrainDeparture(int index) {
        trainDepartures.remove(index);
    }

    //Method that returns the list of train departures sorted by departure time.
    public TrainDeparture[] getTrainDeparturesSortedByDepartureTime() {
        TrainDeparture[] trainDeparturesSortedByDepartureTime = new TrainDeparture[trainDepartures.size()];
        trainDepartures.toArray(trainDeparturesSortedByDepartureTime);
        Arrays.sort(trainDeparturesSortedByDepartureTime);
        return trainDeparturesSortedByDepartureTime;
    }

    //Method that returns the list of train departures sorted by train number.
    public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                return trainDeparture;
            }
        }
        return null;
    }

    //Method that returns the list of train departures sorted by destination.
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

    //Method to update train departure information.
    public void updateTrainDeparture(TrainDeparture trainDeparture) {
        for (int i = 0; i < trainDepartures.size(); i++) {
            if (trainDepartures.get(i).getTrainNumber() == trainDeparture.getTrainNumber()) {
                trainDepartures.set(i, trainDeparture);
            }
        }
    }

    //Method for assigning a track to a train departure.
    public void assignTrackToTrainDeparture(int trainNumber, String track) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                trainDeparture.setTrack(track);
            }
        }
    }

    //Method to set delay for a train departure.
    public void setDelayForTrainDeparture(int trainNumber, Duration minutes) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                trainDeparture.setDelay(minutes);
            }
        }
    }
}
