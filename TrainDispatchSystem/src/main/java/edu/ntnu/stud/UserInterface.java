package edu.ntnu.stud;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;



/**
 * This class represents the user interface.
 *
 * @version 1.4 2023-12-11
 *
 * */

public class UserInterface {
  private final Clock clock;
  private final TrainDispatchSystem trainDispatchSystem;
  private final Scanner scanner;
  private final TrainSchedulePrinter tsp = new TrainSchedulePrinter();

  /**
   * Creates a user interface.
   * */
  public UserInterface() {
    clock = new Clock();
    trainDispatchSystem = new TrainDispatchSystem();
    scanner = new Scanner(System.in);
  }

  /**
   * Initializes the system with four train departures.
   * */
  public void init() {
    // Create four instances of the TrainDeparture class
    TrainDeparture td1 = new TrainDeparture(
          LocalTime.of(12, 0),
          "L1",
          -5,
          "Oslo",
          "1",
          Duration.ofMinutes(0));
    TrainDeparture td2 = new TrainDeparture(
          LocalTime.of(12, 30),
          "L2",
          -4,
          "Trondheim",
          "2",
          Duration.ofMinutes(0));
    TrainDeparture td3 = new TrainDeparture(
          LocalTime.of(13, 0),
          "L3",
          -3,
          "Bergen",
          "3",
          Duration.ofMinutes(0));
    TrainDeparture td4 = new TrainDeparture(
          LocalTime.of(13, 30),
          "L4",
          -2,
          "Stavanger",
          "4",
          Duration.ofMinutes(0));

    // Adds the four TrainDeparture objects to the trainDepartures list.
    trainDispatchSystem.addTrainDeparture(td1);
    trainDispatchSystem.addTrainDeparture(td2);
    trainDispatchSystem.addTrainDeparture(td3);
    trainDispatchSystem.addTrainDeparture(td4);

    // Check that the train departures are initialized correctly.
    Logger logger;
    logger = Logger.getLogger("edu.ntnu.stud");
    TrainDeparture[] trainDepartures = {td1, td2, td3, td4};
    for (TrainDeparture trainDeparture : trainDepartures) {
      logger.info(trainDeparture.printDetails());
    }

    System.out.println("Welcome to the Train Dispatch System!");
  }

  /**
   * Starts the user interface.
   * */
  public void start() {
    boolean running = true;
    while (running) {
      System.out.println("Please select an option:");
      System.out.println("1. Display all train departures");
      System.out.println("2. Add train departure");
      System.out.println("3. Remove train departure");
      System.out.println("4. Update train departure information");
      System.out.println("5. Search for train departures by "
            + "train number, line, destination or track");
      System.out.println("6. Display train departures sorted by departure time or delay");
      System.out.println("7. Update the clock");
      System.out.println("8. Show the current time");
      System.out.println("9. Reset the system, and move to a new day");
      System.out.println("10. Exit");
      System.out.println("Choose an option: ");
      int option = 0;
      try {
        option = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Invalid input");
        scanner.next();
      }
      String input;
      switch (option) {

        /* This case is for displaying all train departures using
           the printSchedule method from the TrainSchedulePrinter class. */
        case 1:
          String schedule = tsp.printSchedule(trainDispatchSystem.getTrainDepartures());
          System.out.println(schedule);
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        // This case is for adding train departures to the system by asking the user for input.
        case 2:
          try {
            /* This do-while loop is for validating the departure time input
               to ensure that the method does not throw an exception. */
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
                  System.out.println("Invalid input. Enter a valid time between 00:00 and 23:59.");
                  System.out.println("Enter departure time (hh:mm): ");
                }
              } else {
                System.out.println("Invalid input. Enter the time in the format hh:mm.");
                System.out.println("Enter departure time (hh:mm): ");
              }
            } while (true);

            /* This do-while loop is for validating the line
               input to satisfy the method requirement. */
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
            } while (true);

            /* This do-while loop is for validating the train
               number input to satisfy the method requirement. */
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
              for (TrainDeparture trainDeparture : trainDispatchSystem.getTrainDepartures()) {
                if (trainDeparture.getTrn() == trainNumber) {
                  System.out.println("Train number already exists! Please enter a unique number.");
                  System.out.println("Enter train number: ");
                  trainNumberExists = true;
                  break;
                }
              }
            } while (trainNumberExists || trainNumber <= 0);

            // There is no need for validation of the destination input, as it can be any string.
            System.out.println("Enter destination: ");
            final String destination = scanner.next();

            /* This do-while loop is for validating the track input as it needs
               to be a number. This is done to satisfy the method requirement. */
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

            /* This do-while loop is for validating the delay input to
               ensure that the method does not throw an exception. */
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
                  System.out.println("Invalid input for delay! "
                        + "Please enter a valid delay in the format hh:mm.");
                  System.out.println("Enter delay (hh:mm): ");
                  validInput = false;
                }
              } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input for delay! "
                      + "Please enter a valid delay in the format hh:mm.");
                System.out.println("Enter delay (hh:mm): ");
                validInput = false;
              }
            } while (!validInput);

            Duration hours = Duration.ofHours(Long.parseLong(delaySplit[0]));
            Duration minutes = Duration.ofMinutes(Long.parseLong(delaySplit[1]));

            TrainDeparture trainDeparture = new TrainDeparture(
                  (parsedDepartureTime),
                  line,
                  trainNumber,
                  destination,
                  track,
                  hours.plus(minutes));
            trainDispatchSystem.addTrainDeparture(trainDeparture);
          } catch (DateTimeParseException
                   | NumberFormatException
                   | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input for departure time or delay!");
          }
          System.out.println("Train departure added!" + "\n");
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        /* This case is for removing train departures from the system
           by asking the user for what train number to remove. */
        case 3:
          int trainNumberToRemove = -1;
          do {
            System.out.println("Enter train number for the train departure you wish to remove: ");
            input = scanner.next();
            try {
              trainNumberToRemove = Integer.parseInt(input);
              if (trainDispatchSystem.getTrainDepartureByTrn(trainNumberToRemove) == null) {
                System.out.println("Train number not found! Please enter a valid train number.");
                trainNumberToRemove = -1;
              }
            } catch (NumberFormatException e) {
              System.out.println("Invalid input! Please enter a valid train number.");
            }
          } while (trainNumberToRemove == -1);

          if (trainDispatchSystem.removeTrainDeparture(trainNumberToRemove)) {
            System.out.println("Train departure removed!");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
          } else {
            System.out.println("Train number not found!");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
          }
          break;

        /* This case is for updating train departures in the system.
           Every aspect has its own case, and every case checks if the input is valid. */
        case 4:
          /* This do-while loop is for validating the
             train number input to satisfy the method requirement. */
          int trainNumberToUpdate = -1;
          do {
            System.out.println("Enter train number for the train departure you wish to update: ");
            input = scanner.next();
            try {
              trainNumberToUpdate = Integer.parseInt(input);
              if (trainDispatchSystem.getTrainDepartureByTrn(trainNumberToUpdate) == null) {
                System.out.println("Train number not found! Please enter a valid train number.");
                trainNumberToUpdate = -1;
              }
            } catch (NumberFormatException e) {
              System.out.println("Invalid input! Input must be a number.");
            }
          } while (trainNumberToUpdate == -1);
          System.out.println("Train departure found!");

          /* This do-while loop is for validating the type to change
             input to satisfy the method requirement. */
          String typeToChange;
          do {
            System.out.println("Enter what information you wish to change "
                  + "(departure-time, line, destination, track or delay): ");
            typeToChange = scanner.next();
            if (!Arrays.asList("departure-time", "line",
                  "destination", "track", "delay").contains(typeToChange)) {
              System.out.println("Invalid input! Please enter one of the following: "
                    + "departure-time, line, destination, track, delay.");
              typeToChange = null;
            }
          } while (typeToChange == null);

          /* This switch statement has cases for every type of information to change.
             Every case has its own do-while loop for validating the input. */
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
                System.out.println("Enter the new line "
                      + "(one uppercase letter followed by a number): ");
                newValue = scanner.next();
                if (!newValue.matches("^[A-Z]\\d")) {
                  System.out.println("Invalid line format! Please enter a line as one "
                        + "uppercase letter followed by a number!");
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
              scanner.nextLine();
              do {
                System.out.println("Enter the new destination: ");
                newValue = scanner.nextLine();
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
                    newValue = null;
                  }
                } catch (NumberFormatException e) {
                  System.out.println("Invalid track number! Please enter a number.");
                  newValue = null;
                }
              } while (newValue == null);
              break;
            case "delay":
              do {
                System.out.println("Enter the new delay (hh:mm): ");
                newValue = scanner.next();
                if (!newValue.matches("^(\\d?\\d|1\\d{2}):[0-5]\\d$")) {
                  System.out.println("Invalid delay format! "
                        + "Please enter delay in the format hh:mm.");
                  newValue = null;
                }
              } while (newValue == null);
              break;
            default:
              System.out.println("Invalid type to change!");
              return;
          }
          trainDispatchSystem.updateTrainDeparture(trainNumberToUpdate, typeToChange, newValue);
          System.out.println("Train departure information updated!" + "\n");
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        /* This case has a class specifically made for it that returns sorted lists with train
           departures that contain the information the user is searching for. */
        case 5:
          String variableToSearchFor;
          /* This do-while loop is for validating the variable
             to search for input to satisfy the method requirement. */
          do {
            System.out.println("Enter what information you wish to "
                  + "search for (train-number, line, destination or track): ");
            variableToSearchFor = scanner.next();
            if (!Arrays.asList("train-number", "line",
                  "destination", "track").contains(variableToSearchFor)) {
              System.out.println("Invalid input!");
              variableToSearchFor = null;
            }
          } while (variableToSearchFor == null);

          /* This switch statement has cases for every type of information to search for.
           Every case has its own do-while loop for validating the input. */
          String valueToSearchFor;
          switch (variableToSearchFor) {
            case "train-number":
              do {
                System.out.println("Enter the train number you wish to search for: ");
                valueToSearchFor = scanner.next();
                try {
                  Integer.parseInt(valueToSearchFor);
                } catch (NumberFormatException e) {
                  System.out.println("Invalid train number! Please enter a number.");
                  valueToSearchFor = null;
                }
              } while (valueToSearchFor == null);
              break;
            case "line":
              do {
                System.out.println("Enter the line you wish to search for "
                      + "(one uppercase letter followed by a number): ");
                valueToSearchFor = scanner.next();
                if (!valueToSearchFor.matches("^[A-Z]\\d")) {
                  System.out.println("Invalid line format! Please enter a line "
                        + "as one uppercase letter followed by a number!");
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
                    valueToSearchFor = null;
                  }
                } catch (NumberFormatException e) {
                  System.out.println("Invalid track number! Please enter a number.");
                  valueToSearchFor = null;
                }
              } while (valueToSearchFor == null);
              break;
            default:
              System.out.println("Invalid variable to search for!");
              return;
          }
          List<TrainDeparture> departures =
                List.of(trainDispatchSystem.getTrainDeparturesByVariable(variableToSearchFor,
                      valueToSearchFor));
          if (departures.isEmpty()) {
            System.out.println("No train departures found for the given criteria.");
          } else {
            System.out.println(tsp.printSchedule(departures));
          }
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        case 6:
          String departureTimeOrDelay;
          /* This do-while loop is for validating the variable to
             search for input to satisfy the method requirement. */
          do {
            System.out.println("Enter what information you wish to see "
                  + "(departure-time or delay): ");
            departureTimeOrDelay = scanner.next();
            if (!Arrays.asList("departure-time", "delay").contains(departureTimeOrDelay)) {
              System.out.println("Invalid input! Please enter one of the following: "
                    + "departure-time, delay.");
              departureTimeOrDelay = null;
            }
          } while (departureTimeOrDelay == null);

          if (Objects.equals(departureTimeOrDelay, "departure-time")) {
            System.out.println(tsp.printSchedule(List.of(
                  trainDispatchSystem.getTdtSortedByTime())));
          } else {
            System.out.println(tsp.printSchedule(List.of(trainDispatchSystem.getTdtSortedByDel())));
          }
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;
        case 7:
          /* This case is for updating the clock and removing
             any train departures that have already departed. */
          String newTime;
          do {
            System.out.println("Enter the new time (hh:mm): ");
            newTime = scanner.next();
            if (!newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
              System.out.println("Invalid time format! Please enter time in the format 00:00.");
              newTime = null;
            }
          } while (newTime == null);

          LocalTime newLocalTime = LocalTime.parse(newTime);
          if (newLocalTime.isAfter(clock.getCurrentTime())) {
            clock.setTime(newLocalTime);
            System.out.println("The clock has been updated!\n");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            scanner.nextLine();

            // Check and remove any train departures that have already departed
            trainDispatchSystem.removeTrainDepartureIfDeparted(newLocalTime);
          } else {
            System.out.println("The new time cannot be earlier than the current time!");
          }
          break;

        case 8:
          // This case is for showing the current time.
          System.out.println("The current time is: " + clock.getCurrentTime() + "\n");
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        case 9:
          // This case is for resetting the system, and moving to a new day.
          System.out.println("Resetting system...");
          trainDispatchSystem.reset();
          clock.resetTime();
          System.out.println("System reset!");
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;

        case 10:
          // This case is for exiting the program.
          System.out.println("Exiting program...");
          running = false;
          break;

        default:
          System.out.println("Please choose a menu option from 1 to 8.");
          System.out.println("Press enter to continue...");
          scanner.nextLine();
          scanner.nextLine();
          break;
      }
    }
  }
}
