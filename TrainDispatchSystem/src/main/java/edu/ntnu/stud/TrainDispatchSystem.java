package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;



/**
 * This class creates a system for train departures.
 *
 * @version 1.6 2023-12-11
 *
 * */

public class TrainDispatchSystem {
  private final List<TrainDeparture> trainDepartures;

  public TrainDispatchSystem() {
    trainDepartures = new ArrayList<>();
  }
  /**
   * Adds a train departure. Train departure cannot be added if train number already exists.
   * The method will throw an exception if the train number already exists.
   *
   * @param trainDeparture the train departure details
   * @throws IllegalArgumentException if the train number already exists
   * */

  public void addTrainDeparture(TrainDeparture trainDeparture) {
    for (TrainDeparture trainDeparture1 : trainDepartures) {
      if (trainDeparture1.getTrn() == trainDeparture.getTrn()) {
        throw new IllegalArgumentException("Train number already exists");
      }
    }
    trainDepartures.add(trainDeparture);
  }

  /**
   * Removes a train departure by searching for the train number.
   * Returns true if the train departure was removed, and false if it was not found.
   *
   * @param trainNumber the train number
   * @return true if the train departure was removed, and false if it was not found
   */
  public boolean removeTrainDeparture(int trainNumber) {
    for (TrainDeparture td : trainDepartures) {
      if (td.getTrn() == trainNumber) {
        trainDepartures.remove(td);
        return true;
      }
    }
    return false;
  }

  /**
   * Rreturns the list of train departures sorted by departure time.
   *
   * @return the train departures sorted by departure time
   * */
  public TrainDeparture[] getTdtSortedByTime() {
    TrainDeparture[] tdt = new TrainDeparture[trainDepartures.size()];
    trainDepartures.toArray(tdt);
    Arrays.sort(tdt, Comparator.comparing(TrainDeparture::getTdt));
    return tdt;
  }


  /**
   * Returns the list of train departures sorted by delay.
   *
   * @return the train departures sorted by delay
   * */
  public TrainDeparture[] getTdtSortedByDel() {
    TrainDeparture[] tdt = new TrainDeparture[trainDepartures.size()];
    trainDepartures.toArray(tdt);
    Arrays.sort(tdt, Comparator.comparing(td -> td.getDel().toMinutes()));
    return tdt;
  }

  /**
   * Returns a train departure by train number.
   * Returns null if the train number is not found.
   *
   * @param trainNumber the train number
   * @return a train departure, or null if the train number is not found
   * */
  public TrainDeparture getTrainDepartureByTrn(int trainNumber) {
    for (TrainDeparture td : trainDepartures) {
      if (td.getTrn() == trainNumber) {
        return td;
      }
    }
    return null;
  }

  /**
   * Returns train departures based on a given variable.
   * This was designed to be used with the user interface to make the system more flexible.
   *
   * @param variable the variable to look up (train number or destination)
   * @param value the value of the variable
   * @return an array of train departures, or null if the variable is not valid
   */
  public TrainDeparture[] getTrainDeparturesByVariable(String variable, String value) {
    List<TrainDeparture> result = new ArrayList<>();
    switch (variable) {
      /* This case is for train number, and it converts the value to an integer.
       * It first compares it to the train number of each train departure.
       * Train numbers are unique, so it breaks the loop as soon as a match is found.
       * */
      case "train-number":
        int trainNumber = Integer.parseInt(value);
        for (TrainDeparture td : trainDepartures) {
          if (td.getTrn() == trainNumber) {
            result.add(td);
            break; // exit the loop as soon as a match is found
          }
        }
        break;

      /* This case is for destination, and it compares the value to the destination of
      each train departure and sorts the result alphabetically by destination. */
      case "destination":
        for (TrainDeparture td : trainDepartures) {
          if (td.getDst().equals(value)) {
            result.add(td);
          }
        }
        result.sort(Comparator.comparing(td -> td.getDst().substring(0, 1)));
        break;

      /* This case is for departure time, and it compares the value to the departure time of
      each train departure and sorts the result by departure time. */
      case "departure-time":
        LocalTime departureTime = LocalTime.parse(value);
        for (TrainDeparture td : trainDepartures) {
          if (td.getTdt().equals(departureTime)) {
            result.add(td);
          }
        }
        result.sort(Comparator.comparing(TrainDeparture::getTdt));
        break;

      /* This case is for line, and it compares the value to the line of
      each train departure and sorts the result by the first character of the line. */
      case "line":
        for (TrainDeparture td : trainDepartures) {
          if (td.getLine().equals(value)) {
            result.add(td);
          }
        }
        result.sort(Comparator.comparing(td -> td.getLine().substring(0, 1)));
        break;

      /* This case is for track, and it compares the value to the track of
      each train departure and sorts the result by numbers. */
      case "track":
        for (TrainDeparture td : trainDepartures) {
          if (td.getTra().equals(value)) {
            result.add(td);
          }
        }
        result.sort(Comparator.comparingInt(TrainDeparture::getTrn));
        break;

      /* This case is for delay, and it compares the value to the delay of
      each train departure and sorts the result by the amount of delay. */
      case "delay":
        for (TrainDeparture td : trainDepartures) {
          if (td.getDel().toMinutes() > 0) {
            result.add(td);
          }
        }
        result.sort(Comparator.comparing(td -> td.getDel().toMinutes()));
        break;

      default:
        System.out.println("Invalid variable! Please enter one of the following: "
                            + "trainNumber, destination, departureTime, line, track, delay.");
        return new TrainDeparture[0];
    }
    TrainDeparture[] resultArray = new TrainDeparture[result.size()];
    result.toArray(resultArray);
    return resultArray;
  }

  /**
   * Assigns a track to a train departure.
   *
   * @param trainNumber the train number
   * @param track the track
   */
  public void assignTrackToTrainDeparture(int trainNumber, String track) {
    for (TrainDeparture trainDeparture : trainDepartures) {
      if (trainDeparture.getTrn() == trainNumber) {
        trainDeparture.setTra(track);
      }
    }
  }

  /**
   * Sets delay for a train departure. Method needs both amount of hours and minutes.
   * It also throws an exception if the minutes is negative.
   *
   * @param trainNumber the train number
   * @param minutes the amount of minutes to delay the train departure.
   * @throws IllegalArgumentException if the minutes is negative.
   * */
  public void setDelayForTrainDeparture(int trainNumber, Duration hours, Duration minutes) {
    for (TrainDeparture trainDeparture : trainDepartures) {
      if (trainDeparture.getTrn() == trainNumber) {
        if (minutes.isNegative()) {
          throw new IllegalArgumentException("Minutes cannot be negative");
        } else {
          trainDeparture.setDelay(hours, minutes);
        }
      }
    }
  }

  /**
   * Checks if a train has departed, and removes it from the list of train departures if it has.
   * The method is called every time the clock is updated.
   * */
  public void removeTrainDepartureIfDeparted(LocalTime currentTime) {
    trainDepartures.removeIf(trainDeparture -> trainDeparture.getTdt().isBefore(currentTime));
  }

  /**
   * Prints the all the departures in a list.
   *
   * @return the details of all train departures in a list
   * */
  public List<TrainDeparture> getTrainDepartures() {
    return trainDepartures;
  }

  /**
   * Method that updates a train departure with new information.
   * It takes in the train number, the type of information to change, and the new value.
   * The method has different cases for the different types of information to change.
   *
   * @param trainNumber the train number of the train departure to change.
   * @param typeToChange the type of information to change.
   * @param newValue the new value of the information to change.
   * */

  public void updateTrainDeparture(int trainNumber, String typeToChange, String newValue) {
    for (TrainDeparture trainDeparture : trainDepartures) {
      if (trainDeparture.getTrn() == trainNumber) {
        switch (typeToChange) {
          case "departure-time":
            trainDeparture.setTdt(LocalTime.parse(newValue));
            break;
          case "line":
            trainDeparture.setLine(newValue);
            break;
          case "destination":
            trainDeparture.setDst(newValue);
            break;
          case "track":
            trainDeparture.setTra(newValue);
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
   * Method that resets the list of train departures.
   * This is used for clearing the system of train departures.
   * */
  public void reset() {
    trainDepartures.clear();
  }
}



