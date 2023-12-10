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
 * @version 1.5 2023-12-09
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
     * Method that removes a train departure by searching for the train number. Returns true if the train departure was removed, and false if it was not found.
     * @param trainNumber the train number
     * @return true if the train departure was removed, and false if it was not found
     */
    public boolean removeTrainDeparture(int trainNumber) {
        for (TrainDeparture td : trainDepartures) {
            if (td.getTrainNumber() == trainNumber) {
                trainDepartures.remove(td);
                return true;
            }
        }
        return false;
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
     * Method that returns a train departure by train number. Returns null if the train number is not found.
     * @param trainNumber the train number
     * @return a train departure, or null if the train number is not found
     * */
    public TrainDeparture getTrainDepartureByTrainNumber(int trainNumber) {
        for (TrainDeparture td : trainDepartures) {
            if (td.getTrainNumber() == trainNumber) {
                return td;
            }
        }
        return null;
    }

    /**
     * Method that returns train departures based on a given variable. This was designed to be used with the user interface to make the system more flexible.
     * @param variable the variable to look up (train number or destination)
     * @param value the value of the variable
     * @return an array of train departures, or null if the variable is not valid
     */
    public TrainDeparture[] getTrainDeparturesByVariable(String variable, String value) {
        List<TrainDeparture> result = new ArrayList<>();
        switch (variable) {
            // This case is for train number, and it converts the value to an integer before comparing it to the train number of each train departure. It only returns one train departure, as there should only be one train departure with a given train number.
            case "trainNumber":
                int trainNumber = Integer.parseInt(value);
                for (TrainDeparture td : trainDepartures) {
                    if (td.getTrainNumber() == trainNumber) {
                        result.add(td);
                        break; // exit the loop as soon as a match is found
                    }
                }
                break;

            // This case is for destination, and it compares the value to the destination of each train departure and sorts the result alphabetically by destination.
            case "destination":
                for (TrainDeparture td : trainDepartures) {
                    if (td.getDestination().equals(value)) {
                        result.add(td);
                    }
                }
                result.sort(Comparator.comparing(td -> td.getDestination().substring(0, 1)));
                break;

            // This case is for departure time, and it compares the value to the departure time of each train departure and sorts the result by departure time.
            case "departureTime":
                LocalTime departureTime = LocalTime.parse(value);
                for (TrainDeparture td : trainDepartures) {
                    if (td.getDepartureTime().equals(departureTime)) {
                        result.add(td);
                    }
                }
                result.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
                break;

            // This case is for line, and it compares the value to the line of each train departure and sorts the result by the first character of the line.
            case "line":
                for (TrainDeparture td : trainDepartures) {
                    if (td.getLine().equals(value)) {
                        result.add(td);
                    }
                }
                result.sort(Comparator.comparing(td -> td.getLine().substring(0, 1)));
                break;

            // This case is for track, and it compares the value to the track of each train departure and sorts the result by numbers.
            case "track":
                for (TrainDeparture td : trainDepartures) {
                    if (td.getTrack().equals(value)) {
                        result.add(td);
                    }
                }
                result.sort(Comparator.comparingInt(TrainDeparture::getTrainNumber));
                break;

            // This case is for delay, and it compares the value to the delay of each train departure and sorts the result by the amount of delay.
            case "delay":
                for (TrainDeparture td : trainDepartures) {
                    if (td.getDelay().toMinutes() > 0) {
                        result.add(td);
                    }
                }
                result.sort(Comparator.comparing(td -> td.getDelay().toMinutes()));
                break;
            default:
                System.out.println("Invalid variable! Please enter one of the following: trainNumber, destination, departureTime, line, track, delay.");
                return new TrainDeparture[0];
        }
        TrainDeparture[] resultArray = new TrainDeparture[result.size()];
        result.toArray(resultArray);
        return resultArray;
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

    /**
     * Method that updates a train departure with new information. It takes in the train number, the type of information to change, and the new value. The method has different cases for the different types of information to change.
     * @param trainNumber the train number of the train departure to change.
     * @param typeToChange the type of information to change. Can be "departure-time", "line", "destination", "track" or "delay".
     * @param newValue the new value of the information to change.
     * */

    public void updateTrainDeparture(int trainNumber, String typeToChange, String newValue) {
        for (TrainDeparture trainDeparture : trainDepartures) {
            if (trainDeparture.getTrainNumber() == trainNumber) {
                switch (typeToChange) {
                    case "departure-time":
                        trainDeparture.setDepartureTime(LocalTime.parse(newValue));
                        break;
                    case "line":
                        trainDeparture.setLine(newValue);
                        break;
                    case "destination":
                        trainDeparture.setDestination(newValue);
                        break;
                    case "track":
                        trainDeparture.setTrack(newValue);
                        break;
                    case "delay":
                        String[] parts = newValue.split(":");
                        Duration hours = Duration.ofHours(Long.parseLong(parts[0]));
                        Duration minutes = Duration.ofMinutes(Long.parseLong(parts[1]));
                        trainDeparture.setDelay(hours, minutes);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid input");
                }
            }
        }
    }

    /**
     * Method that resets the list of train departures. This is used for clearing the system of train departures when the user wants to start a new day.
     * */
    public void reset() {
        trainDepartures.clear();
    }
}



