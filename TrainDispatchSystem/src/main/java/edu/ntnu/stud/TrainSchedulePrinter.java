package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

/**
 * This class prints the train schedule.
 *
 * @version 1.0 2023-12-09
 *
 * */

public class TrainSchedulePrinter {
    public String printSchedule(@NotNull List<TrainDeparture> trainDepartures) {
        // Sort the train departures by adjusted departure time
        List<TrainDeparture> sortedTrainDepartures = trainDepartures.stream()
                .sorted(Comparator.comparing(TrainDeparture::getDepartureTime))
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
