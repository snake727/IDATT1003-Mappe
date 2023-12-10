package edu.ntnu.stud;

import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.time.LocalTime;
import java.time.Duration;
import java.util.logging.Logger;


/**
 * This class represents the user interface.
 *
 * @version 1.3 2023-12-09
 *
 * */

public class UserInterface {
    private final Clock clock;
    private final TrainDispatchSystem trainDispatchSystem;
    private final Scanner scanner;
    private final TrainSchedulePrinter tsp = new TrainSchedulePrinter();

    public UserInterface() {
        clock = new Clock();
        trainDispatchSystem = new TrainDispatchSystem();
        scanner = new Scanner(System.in);
    }

    public void start() {
        // Create four instances of the TrainDeparture class
        TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 0), "L1", -5, "Oslo", "1", Duration.ofMinutes(0));
        TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 30), "L2", -4, "Trondheim", "2", Duration.ofMinutes(0));
        TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(13, 0), "L3", -3, "Bergen", "3", Duration.ofMinutes(0));
        TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(13, 30), "L4", -2, "Stavanger", "4", Duration.ofMinutes(0));

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
            System.out.println("4. Update train departure information");
            System.out.println("5. Search for train departures");
            System.out.println("6. Update the clock");
            System.out.println("7. Show the current time");
            System.out.println("8. Reset the system, and move to a new day");
            System.out.println("9. Exit");
            System.out.println("Choose an option: ");
            int option = 0;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
            switch (option) {

                // This case is for displaying all train departures using the printSchedule method from the TrainSchedulePrinter class.
                case 1:
                    String schedule = tsp.printSchedule(trainDispatchSystem.getTrainDepartures());
                    System.out.println(schedule);
                    break;

                // This case is for adding train departures to the system by asking the user for input.
                case 2:
                    try {
                        // This do-while loop is for validating the departure time input to ensure that the method does not throw an exception.
                        System.out.println("Enter departure time (hh:mm): ");
                        LocalTime parsedDepartureTime;
                        do {
                            String departureTime = scanner.next();
                            if (departureTime.matches("\\d{2}:\\d{2}")) {
                                int hour = Integer.parseInt(departureTime.split(":")[0]);
                                int minute = Integer.parseInt(departureTime.split(":")[1]);
                                if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
                                    parsedDepartureTime = LocalTime.parse(departureTime);
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter a valid time between 00:00 and 23:59.");
                                    System.out.println("Enter departure time (hh:mm): ");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter the time in the format hh:mm.");
                                System.out.println("Enter departure time (hh:mm): ");
                            }
                        } while (true);

                        // This do-while loop is for validating the line input to satisfy the method requirement.
                        System.out.println("Enter line (one uppercase letter followed by a number): ");
                        String line;
                        do {
                            line = scanner.next();
                            if (line == null || !line.matches("[A-Z]\\d")) {
                                System.out.println("Line must be one uppercase letter followed by one number!");
                                System.out.println("Enter line: ");
                            } else {
                                break;
                            }
                        } while(true);

                        // This do-while loop is for validating the train number input to satisfy the method requirement.
                        System.out.println("Enter train number (positive number): ");
                        int trainNumber;
                        boolean trainNumberExists;
                        do {
                            trainNumberExists = false;
                            while (!scanner.hasNextInt()) {
                                System.out.println("That's not a number! Please enter a number above 0.");
                                System.out.println("Enter train number: ");
                                scanner.next(); // discard non-integer input
                            }
                            trainNumber = scanner.nextInt();
                            if (trainNumber <= 0) {
                                System.out.println("Invalid input. Please enter a number above 0.");
                                System.out.println("Enter train number: ");
                                continue;
                            }
                            for(TrainDeparture trainDeparture : trainDispatchSystem.getTrainDepartures()) {
                                if (trainDeparture.getTrainNumber() == trainNumber) {
                                    System.out.println("Train number already exists! Please enter a unique number.");
                                    System.out.println("Enter train number: ");
                                    trainNumberExists = true;
                                    break;
                                }
                            }
                        } while(trainNumberExists || trainNumber <= 0);

                        // There is no need for validation of the destination input, as it can be any string.
                        System.out.println("Enter destination: ");
                        String destination = scanner.next();

                        // This do-while loop is for validating the track input as it needs to be a number. This is done to satisfy the method requirement.
                        System.out.println("Enter track (positive number): ");
                        String track;
                        int trackNumber;
                        do {
                            track = scanner.next();
                            try {
                                trackNumber = Integer.parseInt(track);
                                if (trackNumber <= 0) {
                                    System.out.println("Invalid input. Please enter a number above 0.");
                                    System.out.println("Enter track: ");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("That's not a number! Please enter a positive number.");
                                System.out.println("Enter track: ");
                            }
                        } while (true);

                        // This do-while loop is for validating the delay input to ensure that the method does not throw an exception.
                        System.out.println("Enter delay (hh:mm): ");
                        String delay;
                        String[] delaySplit;
                        boolean validInput;
                        do {
                            validInput = true;
                            delay = scanner.next();
                            delaySplit = delay.split(":");
                            try {
                                if (Integer.parseInt(delaySplit[0]) > 23 || Integer.parseInt(delaySplit[1]) > 59) {
                                    System.out.println("Invalid input for delay! Please enter a valid delay in the format hh:mm.");
                                    System.out.println("Enter delay (hh:mm): ");
                                    validInput = false;
                                }
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                System.out.println("Invalid input for delay! Please enter a valid delay in the format hh:mm.");
                                System.out.println("Enter delay (hh:mm): ");
                                validInput = false;
                            }
                        } while (!validInput);

                        Duration hours = Duration.ofHours(Long.parseLong(delaySplit[0]));
                        Duration minutes = Duration.ofMinutes(Long.parseLong(delaySplit[1]));

                        TrainDeparture trainDeparture = new TrainDeparture((parsedDepartureTime), line, trainNumber, destination, track, hours.plus(minutes));
                        trainDispatchSystem.addTrainDeparture(trainDeparture);
                    } catch (DateTimeParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input for departure time or delay!");
                    }
                    System.out.println("Train departure added!");
                    break;

                // This case is for removing train departures from the system by asking the user for what train number to remove.
                case 3:
                    System.out.println("Enter train number for the train departure you wish to remove: ");
                    int trainNumberToRemove = scanner.nextInt();
                    boolean isRemoved = trainDispatchSystem.removeTrainDeparture(trainNumberToRemove);
                    if (isRemoved) {
                        System.out.println("Train departure removed!");
                    } else {
                        System.out.println("Train departure not found!\n");
                    }
                    break;

                // This case is for updating train departures in the system. Every aspect has its own case, and every case checks if the input is valid.
                case 4:                   
                    // This do-while loop is for validating the train number input to satisfy the method requirement.
                    int trainNumberToUpdate;
                    do {
                        System.out.println("Enter train number for the train departure you wish to update: ");
                        trainNumberToUpdate = scanner.nextInt();
                        if (trainDispatchSystem.getTrainDepartureByTrainNumber(trainNumberToUpdate) == null) {
                            System.out.println("Train number not found! Please enter a valid train number.");
                            trainNumberToUpdate = -1;
                        }
                    } while (trainNumberToUpdate == -1);
                    
                    // This do-while loop is for validating the type to change input to satisfy the method requirement.
                    String typeToChange;
                    do {
                        System.out.println("Enter what information you wish to change (departure-time, line, destination, track or delay): ");
                        typeToChange = scanner.next();
                        if (!Arrays.asList("departure-time", "line", "destination", "track", "delay").contains(typeToChange)) {
                            System.out.println("Invalid input! Please enter one of the following: departure-time, line, destination, track, delay.");
                            typeToChange = null;
                        }
                    } while (typeToChange == null);

                    // This switch statement has cases for every type of information to change. Every case has its own do-while loop for validating the input.
                    String newValue;
                    switch (typeToChange) {
                        case "departure-time":
                            do {
                                System.out.println("Enter the new departure time (hh:mm): ");
                                newValue = scanner.next();
                                if (!newValue.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                                    System.out.println("Invalid time format! Please enter time in the format 00:00.");
                                    newValue = null;
                                }
                            } while (newValue == null);
                            break;
                        case "line":
                            do {
                                System.out.println("Enter the new line (one uppercase letter followed by a number): ");
                                newValue = scanner.next();
                                if (!newValue.matches("^[A-Z]\\d")) {
                                    System.out.println("Invalid line format! Please enter a line as one uppercase letter followed by a number!");
                                    newValue = null;
                                } else {
                                    int numberPart = Integer.parseInt(newValue.substring(1));
                                    if (numberPart <= 0) {
                                        System.out.println("Invalid line number! Please enter a positive number.");
                                        newValue = null;
                                    }
                                }
                            } while (newValue == null);
                            break;
                        case "destination":
                            do {
                                System.out.println("Enter the new destination: ");
                                newValue = scanner.next();
                            } while (newValue == null);
                            break;
                        case "track":
                            do {
                                System.out.println("Enter the new track (positive number): ");
                                newValue = scanner.next();
                                try {
                                    int track = Integer.parseInt(newValue);
                                    if (track <= 0) {
                                        System.out.println("Invalid track number! Please enter a positive number.");
                                        System.out.println("Enter the new track (positive number): ");
                                        newValue = null;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid track number! Please enter a number.");
                                    System.out.println("Enter the new track (positive number): ");
                                    newValue = null;
                                }
                            } while (newValue == null);
                            break;
                        case "delay":
                            do {
                                System.out.println("Enter the new delay (hh:mm): ");
                                newValue = scanner.next();
                                if (!newValue.matches("^(\\d?\\d|1\\d{2}):[0-5]\\d$")) {
                                    System.out.println("Invalid delay format! Please enter delay in the format hh:mm.");
                                    newValue = null;
                                }
                            } while (newValue == null);
                            break;
                        default:
                            System.out.println("Invalid type to change!");
                            return;
                    }
                    System.out.println(trainNumberToUpdate);
                    System.out.println(typeToChange);
                    System.out.println(newValue);
                    trainDispatchSystem.updateTrainDeparture(trainNumberToUpdate, typeToChange, newValue);
                    System.out.println("Train departure information updated!" + "\n");
                    break;

                // This case has a class specifically made for it that returns sorted lists with train departures that contain the information the user is searching for.
                case 5:
                    String variableToSearchFor;

                    // This do-while loop is for validating the variable to search for input to satisfy the method requirement.
                    do {
                        System.out.println("Enter what information you wish to search for (departure-time, line, destination, track or delay): ");
                        variableToSearchFor = scanner.next();
                        if (!Arrays.asList("departure-time", "line", "destination", "track", "delay").contains(variableToSearchFor)) {
                            System.out.println("Invalid input! Please enter one of the following: departure-time, line, destination, track, delay.");
                            variableToSearchFor = null;
                        }
                    } while (variableToSearchFor == null);

                    // This switch statement has cases for every type of information to search for. Every case has its own do-while loop for validating the input.
                    String valueToSearchFor;
                    switch (variableToSearchFor) {
                        case "departure-time":
                            do {
                                System.out.println("Enter the departure time you wish to search for (hh:mm): ");
                                valueToSearchFor = scanner.next();
                                if (!valueToSearchFor.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                                    System.out.println("Invalid time format! Please enter time in the format 00:00.");
                                    valueToSearchFor = null;
                                }
                            } while (valueToSearchFor == null);
                            break;
                        case "line":
                            do {
                                System.out.println("Enter the line you wish to search for (one uppercase letter followed by a number): ");
                                valueToSearchFor = scanner.next();
                                if (!valueToSearchFor.matches("^[A-Z]\\d")) {
                                    System.out.println("Invalid line format! Please enter a line as one uppercase letter followed by a number!");
                                    valueToSearchFor = null;
                                } else {
                                    int numberPart = Integer.parseInt(valueToSearchFor.substring(1));
                                    if (numberPart <= 0) {
                                        System.out.println("Invalid line number! Please enter a positive number.");
                                        valueToSearchFor = null;
                                    }
                                }
                            } while (valueToSearchFor == null);
                            break;
                        case "destination":
                            do {
                                System.out.println("Enter the destination you wish to search for: ");
                                valueToSearchFor = scanner.next();
                            } while (valueToSearchFor == null);
                            break;
                        case "track":
                            do {
                                System.out.println("Enter the track you wish to search for (positive number): ");
                                valueToSearchFor = scanner.next();
                                try {
                                    int track = Integer.parseInt(valueToSearchFor);
                                    if (track <= 0) {
                                        System.out.println("Invalid track number! Please enter a positive number.");
                                        System.out.println("Enter the track you wish to search for (positive number): ");
                                        valueToSearchFor = null;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid track number! Please enter a number.");
                                    System.out.println("Enter the track you wish to search for (positive number): ");
                                    valueToSearchFor = null;
                                }
                            } while (valueToSearchFor == null);
                            break;
                        case "delay":
                            do {
                                System.out.println("Enter the delay you wish to search for (hh:mm): ");
                                valueToSearchFor = scanner.next();
                                if (!valueToSearchFor.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                                    System.out.println("Invalid delay format! Please enter delay in the format 00:00.");
                                    System.out.println("Enter the delay you wish to search for (hh:mm): ");
                                    valueToSearchFor = null;
                                }
                            } while (valueToSearchFor == null);
                            break;
                        default:
                            System.out.println("Invalid variable to search for!");
                            return;
                    }
                    System.out.println(tsp.printSchedule(Arrays.asList(trainDispatchSystem.getTrainDeparturesByVariable(variableToSearchFor, valueToSearchFor))));
                    break;
                case 6:
                    // This case is for updating the clock and removing any train departures that have already departed.
                    System.out.println("Enter the new time (hh:mm): ");
                    String newTime = scanner.next();
                    if (!newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                        System.out.println("Invalid time format! Please enter time in the format 00:00.");
                    } else {
                        LocalTime newLocalTime = LocalTime.parse(newTime);
                        if (newLocalTime.isAfter(clock.getCurrentTime())) {
                            clock.setTime(newLocalTime);
                            System.out.println("The clock has been updated!");

                            // Check and remove any train departures that have already departed
                            trainDispatchSystem.removeTrainDepartureIfDeparted(newLocalTime);
                        } else {
                            System.out.println("The new time cannot be earlier than the current time!");
                        }
                    }
                    break;
                case 7:
                    // This case is for showing the current time.
                    System.out.println("The current time is: " + clock.getCurrentTime() + "\n");
                    break;
                case 8:
                    // This case is for resetting the system, and moving to a new day.
                    System.out.println("Resetting system...");
                    trainDispatchSystem.reset();
                    clock.resetTime();
                    System.out.println("System reset!");
                    break;
                case 9:
                    // This case is for exiting the program.
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a menu option from 1 to 8.");
                    break;
            }
        }
    }
}
