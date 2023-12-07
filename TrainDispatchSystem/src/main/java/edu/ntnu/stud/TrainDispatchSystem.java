package edu.ntnu.stud;

import java.util.Arrays;
import java.time.Duration;

/**
 * This class creates a system for train departures.
 *
 * @version 1.0 2023-12-07
 *
 * */

public class TrainDispatchSystem {
    // Class that that declares variables, creates a constructor, creates methods for adding, removing and updating train departures, and creates a method for printing the train departures.

    private final TrainDeparture[] trainDepartures;
    private int numberOfDepartures;

    public TrainDispatchSystem() {
        trainDepartures = new TrainDeparture[100];
        numberOfDepartures = 0;
    }

    public void addTrainDeparture(TrainDeparture trainDeparture) {
        trainDepartures[numberOfDepartures] = trainDeparture;
        numberOfDepartures++;
    }

    public void removeTrainDeparture(int index) {
        for (int i = index; i < numberOfDepartures - 1; i++) {
            trainDepartures[i] = trainDepartures[i + 1];
        }
        numberOfDepartures--;
    }

    //Method that returns the list of train departures sorted by departure time.
    public TrainDeparture[] getTrainDeparturesSortedByDepartureTime() {
        TrainDeparture[] sortedTrainDepartures = Arrays.copyOf(trainDepartures, numberOfDepartures);
        Arrays.sort(sortedTrainDepartures, (trainDeparture1, trainDeparture2) -> trainDeparture1.getDepartureTime().compareTo(trainDeparture2.getDepartureTime()));
        return sortedTrainDepartures;
    }

    //Method that returns the list of train departures sorted by train number.
    public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
        for (int i = 0; i < numberOfDepartures; i++) {
            if (trainDepartures[i].getTrainNumber() == trainNumber) {
                return trainDepartures[i];
            }
        }
        return null;
    }

    //Method that returns the list of train departures sorted by destination.
    public TrainDeparture[] getTrainDeparturesByDestination(String destination) {
        TrainDeparture[] trainDeparturesByDestination = new TrainDeparture[numberOfDepartures];
        int numberOfTrainDeparturesByDestination = 0;
        for (int i = 0; i < numberOfDepartures; i++) {
            if (trainDepartures[i].getDestination().equals(destination)) {
                trainDeparturesByDestination[numberOfTrainDeparturesByDestination] = trainDepartures[i];
                numberOfTrainDeparturesByDestination++;
            }
        }
        return Arrays.copyOf(trainDeparturesByDestination, numberOfTrainDeparturesByDestination);
    }

    //Method to update train departure information.
    public void updateTrainDeparture(TrainDeparture trainDeparture) {
        for (int i = 0; i < numberOfDepartures; i++) {
            if (trainDepartures[i].getTrainNumber() == trainDeparture.getTrainNumber()) {
                trainDepartures[i] = trainDeparture;
            }
        }
    }

    //Method for assigning a track to a train departure.
    public void assignTrackToTrainDeparture(int trainNumber, int track) {
        for (int i = 0; i < numberOfDepartures; i++) {
            if (trainDepartures[i].getTrainNumber() == trainNumber) {
                trainDepartures[i].setTrack(track);
            }
        }
    }

    //Method to set delay for a train departure.
    public void setDelayForTrainDeparture(int trainNumber, Duration minutes) {
        for (int i = 0; i < numberOfDepartures; i++) {
            if (trainDepartures[i].getTrainNumber() == trainNumber) {
                trainDepartures[i].setDelay(minutes);
            }
        }
    }
}
