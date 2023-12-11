package edu.ntnu.stud;

import java.util.Comparator;
import java.util.List;

/**
 * This class prints the train schedule.
 *
 * @version 1.1 2023-12-11
 *
 * */

public class TrainSchedulePrinter {
  /**
   * Prints the train schedule.
   * It takes in a list of train departures, and sorts them by adjusted departure time.
   * It then prints the details of each train departure.
   *
   * @param trainDepartures the list of train departures to be printed.
   * @return the train departures sorted by adjusted departure time.
   * */
  public String printSchedule(List<TrainDeparture> trainDepartures) {
    // Sort the train departures by adjusted departure time
    List<TrainDeparture> sortedTrainDepartures = trainDepartures.stream()
          .sorted(Comparator.comparing(TrainDeparture::getTdt))
          .toList();

    // Return the train departures sorted by adjusted departure time
    StringBuilder sb = new StringBuilder();
    for (TrainDeparture trainDeparture : sortedTrainDepartures) {
      sb.append(trainDeparture.printDetails());
      sb.append("\n");
    }
    return sb.toString();
  }
}
