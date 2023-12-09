package edu.ntnu.stud;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalTime;
import java.time.Duration;
import java.util.logging.Logger;


/**
 * This class represents the user interface.
 *
 * @version 1.2 2023-12-07
 *
 * */

public class UserInterface {
    private Clock clock;
    private TrainDispatchSystem trainDispatchSystem;
    private Scanner scanner;

    public UserInterface() {
        clock = new Clock();
        trainDispatchSystem = new TrainDispatchSystem();
        scanner = new Scanner(System.in);
    }

    public void start() {
        // Create four instances of the TrainDeparture class
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 0), "L1", 1, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 30), "L2", 2, "Trondheim", "2", Duration.ofMinutes(0));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(13, 0), "L3", 3, "Bergen", "3", Duration.ofMinutes(0));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(13, 30), "L4", 4, "Stavanger", "4", Duration.ofMinutes(0));

        // Create an instance of the TrainDispatchSystem class and add the four TrainDeparture instances to it
        trainDispatchSystem.addTrainDeparture(trainDeparture1);
        trainDispatchSystem.addTrainDeparture(trainDeparture2);
        trainDispatchSystem.addTrainDeparture(trainDeparture3);
        trainDispatchSystem.addTrainDeparture(trainDeparture4);

        // Check if the TrainDeparture instances work as intended by using a logger to print all the details in a loop
        Logger logger;
        logger = Logger.getLogger("edu.ntnu.stud");
        TrainDeparture[] trainDepartures = {trainDeparture1, trainDeparture2, trainDeparture3, trainDeparture4};
        for (TrainDeparture trainDeparture : trainDepartures) {
            logger.info(trainDeparture.printDetails());
        }

        System.out.println("Welcome to the Train Dispatch System!");
    }

    // Method that initializes the necessary user interface components.
    public void init() {
        boolean running = true;
        while (running) {
            System.out.println("Please select an option:");
            System.out.println("1. Display all train departures");
            System.out.println("2. Add train departure");
            System.out.println("3. Remove train departure");
            System.out.println("4. Update train departure");
            System.out.println("5. Assign a track to a train departure");
            System.out.println("6. Add delay to a train departure");
            System.out.println("7. Search for a train departure using train number");
            System.out.println("8. Search for train departures using destination");
            System.out.println("9. Update the clock");
            System.out.println("10. Exit");
            System.out.println("Choose an option: ");
            int option = 0;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
            switch (option) {
                case 1:
                    // This case is for displaying all train departures using the printSchedule method from the TrainSchedulePrinter class.
                    TrainSchedulePrinter tsp = new TrainSchedulePrinter();
                    tsp.printSchedule(trainDispatchSystem.getTrainDepartures());
                    break;
                case 2:
                    // This case is for adding train departures to the system.
                    break;
                case 3:
                    // This case is for removing train departures from the system.
                    break;
                case 4:
                    // This case is for updating train departures in the system.
                    break;
                case 5:
                    // This case is for assigning a track to a train departure.
                    break;
                case 6:
                    // This case is for adding delay to a train departure.
                    break;
                case 7:
                    // This case is for searching for a train departure using train number.
                    break;
                case 8:
                    // This case is for searching for train departures using destination.
                    break;
                case 9:
                    // This case is for updating the clock.
                    break;
                case 10:
                    // This case is for exiting the program.
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
