package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.Duration;
import java.time.LocalTime;

/**
 * This class creates the framework for testing the TrainSchedulePrinter class.
 * @version 1.0 2023-12-09
 */

class TrainSchedulePrinterTest {
    TrainSchedulePrinter tsp;
    TrainDispatchSystem trainDispatchSystem;
    // Using the @BeforeEach annotation to make sure that the tsp and trainDispatchSystem objects are created before each test, so that we don't have to create them in each test.
    @BeforeEach
    void setup() {
        tsp = new TrainSchedulePrinter();
        trainDispatchSystem = new TrainDispatchSystem();
    }

    @Test
    @DisplayName("Check if printSchecule method works")
    void testPrintSchedule() {
        // Creating new TrainDeparture objects
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(17, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2", 2, "Trondheim", "2", Duration.ofMinutes(0));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(20, 0), "L3", 3, "Bergen", "3", Duration.ofMinutes(0));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(14, 15), "L4", 4, "Stavanger", "4", Duration.ofMinutes(0));
        TrainDeparture trainDeparture5 = new TrainDeparture(LocalTime.of(10, 30), "L5", 5, "Kristiansand", "5", Duration.ofMinutes(0));
        TrainDeparture trainDeparture6 = new TrainDeparture(LocalTime.of(3, 45), "L6", 6, "Bodø", "6", Duration.ofMinutes(0));

        // Adding the TrainDeparture objects to the TrainDispatchSystem object
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        trainDispatchSystem.addTrainDeparture(trainDeparture2);
        trainDispatchSystem.addTrainDeparture(trainDeparture3);
        trainDispatchSystem.addTrainDeparture(trainDeparture4);
        trainDispatchSystem.addTrainDeparture(trainDeparture5);
        trainDispatchSystem.addTrainDeparture(trainDeparture6);

        // Creating a comparison string with the train departures in the correct time order.
        String comparisonString = trainDeparture6.printDetails() + "\n" + trainDeparture5.printDetails() + "\n" + trainDeparture2.printDetails() + "\n" + trainDeparture4.printDetails() + "\n" + trainDeparture1.printDetails() + "\n" + trainDeparture3.printDetails() + "\n";

        // Checking if the printSchedule method returns the correct string.
        assertEquals(comparisonString, tsp.printSchedule(trainDispatchSystem.getTrainDepartures()));
    }

    @Test
    @DisplayName("Check if printSchecule method works when there are train departures with delays")
    void testPrintScheduleWithDelay() {
        // Creating new TrainDeparture objects
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(17, 30), "L1", 1, "Oslo", "1", Duration.ofHours(0).plusMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2", 2, "Trondheim", "2", Duration.ofMinutes(40));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(20, 0), "L3", 3, "Bergen", "3", Duration.ofHours(3).plusMinutes(2));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(14, 15), "L4", 4, "Stavanger", "4", Duration.ofHours(4));
        TrainDeparture trainDeparture5 = new TrainDeparture(LocalTime.of(10, 30), "L5", 5, "Kristiansand", "5", Duration.ofHours(2).plusMinutes(15));
        TrainDeparture trainDeparture6 = new TrainDeparture(LocalTime.of(3, 45), "L6", 6, "Bodø", "6", Duration.ofMinutes(10));

        // Adding the TrainDeparture objects to the TrainDispatchSystem object
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        trainDispatchSystem.addTrainDeparture(trainDeparture2);
        trainDispatchSystem.addTrainDeparture(trainDeparture3);
        trainDispatchSystem.addTrainDeparture(trainDeparture4);
        trainDispatchSystem.addTrainDeparture(trainDeparture5);
        trainDispatchSystem.addTrainDeparture(trainDeparture6);

        // Creating a comparison string with the train departures in the correct time order.
        String comparisonString = trainDeparture6.printDetails() + "\n" + trainDeparture5.printDetails() + "\n" + trainDeparture2.printDetails() + "\n" + trainDeparture1.printDetails() + "\n" + trainDeparture4.printDetails() + "\n" + trainDeparture3.printDetails() + "\n";

        // Checking if the printSchedule method returns the correct string.
        assertEquals(comparisonString, tsp.printSchedule(trainDispatchSystem.getTrainDepartures()));
    }
}