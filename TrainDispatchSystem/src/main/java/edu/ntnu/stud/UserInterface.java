package edu.ntnu.stud;

import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalTime;
import java.time.Duration;
import java.util.logging.Logger;


/**
 * This class represents the user interface.
 *
 * @version 1.1 2023-12-07
 *
 * */

public class UserInterface {
    Logger logger = Logger.getLogger(UserInterface.class.getName());
    public UserInterface(TrainDispatchSystem dispatchSystem) {
    }


    // To start with, the start method should only test that the TrainDeparture class works by creating 4 instances of TrainDeparture and printing them to the console.
    public void start() {
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2", 2, "Trondheim", "2", Duration.ofMinutes(0));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(13, 00), "L3", 3, "Bergen", "3", Duration.ofMinutes(0));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(13, 15), "L4", 4, "Stavanger", "4", Duration.ofMinutes(0));
        for (TrainDeparture trainDeparture : Arrays.asList(trainDeparture1, trainDeparture2, trainDeparture3, trainDeparture4)) {
            logger.info(trainDeparture.printDetails());
        }


    }

    // Method that initializes the necessary user interface components.
    public void init() {

    }
}
