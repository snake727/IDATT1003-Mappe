package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class creates the framework for testing the TrainDispatchSystem class.
 * Remember to add @BeforeEach and possibly @BeforeAll if needed!
 *
 * @version 1.4 2023-12-11
 */

class TrainDispatchSystemTest {
  private TrainDispatchSystem trainDispatchSystem;

  @BeforeEach
  void setup() {
    trainDispatchSystem = new TrainDispatchSystem();
  }

  @Test
  @DisplayName("Check if the constructor works")
  void testConstructor() {
    assertNotNull(trainDispatchSystem);
  }

  @Test
  @DisplayName("Check if addTrainDeparture works")
  void testAddTrainDeparture() {
    TrainDeparture trainDeparture = new TrainDeparture(
          LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          "1",
          Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    assertEquals(trainDeparture,
          trainDispatchSystem.getTrainDeparturesByVariable("train-number", "1")[0]);
  }

  @Test
  @DisplayName("Check if addTrainDeparture throws "
        + "IllegalArgumentException when train number already exists")
  void testAddTrainDepartureThrowsIllegalArgumentExceptionWhenTrainNumberAlreadyExists() {
    TrainDeparture trainDeparture1 = new TrainDeparture(
          LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          "1",
          Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(
          LocalTime.of(12, 45),
          "L2",
          1,
          "Trondheim",
          "2",
          Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    assertThrows(IllegalArgumentException.class, () ->
          trainDispatchSystem.addTrainDeparture(trainDeparture2));
  }

  @Test
  @DisplayName("Check if removeTrainDeparture works")
  void testRemoveTrainDeparture() {
    TrainDeparture trainDeparture = new TrainDeparture(
          LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          null,
          Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    assertTrue(trainDispatchSystem.removeTrainDeparture(1));
  }

  @Test
  @DisplayName("Check if getTrainDeparturesSortedByDepartureTime works")
  void testGetTrainDeparturesSortedByDepartureTime() {
    // Add some train departures in an unsorted order
    trainDispatchSystem.addTrainDeparture(new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", "1", Duration.ofMinutes(0)));
    trainDispatchSystem.addTrainDeparture(new TrainDeparture(LocalTime.of(10, 30), "L2",
          2, "Bergen", "2", Duration.ofMinutes(0)));
    trainDispatchSystem.addTrainDeparture(new TrainDeparture(LocalTime.of(11, 30), "L3",
          3, "Trondheim", "3", Duration.ofMinutes(0)));

    // Get the train departures sorted by departure time
    TrainDeparture[] sortedTrainDepartures = trainDispatchSystem.getTdtSortedByTime();

    // Check that the train departures are sorted by departure time
    assertEquals(LocalTime.of(10, 30), sortedTrainDepartures[0].getTdt());
    assertEquals(LocalTime.of(11, 30), sortedTrainDepartures[1].getTdt());
    assertEquals(LocalTime.of(12, 30), sortedTrainDepartures[2].getTdt());
  }

  @Test
  @DisplayName("Check if getTrainDepartureByTrainNumber works")
  void testGetTrainDepartureByTrainNumber() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", "1", Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    assertEquals(trainDeparture,
          trainDispatchSystem.getTrainDeparturesByVariable("train-number", "1")[0]);
  }

  @Test
  @DisplayName("Check if getTrainDeparturesByDestination works")
  void testGetTrainDeparturesByDestination() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", "1", Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(12, 45), "L2",
          2, "Oslo", "2", Duration.ofMinutes(0));
    TrainDeparture trainDeparture3 = new TrainDeparture(LocalTime.of(13, 0), "L3",
          3, "Bergen", "3", Duration.ofMinutes(0));
    TrainDeparture trainDeparture4 = new TrainDeparture(LocalTime.of(13, 15), "L4",
          4, "Stavanger", "4", Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    trainDispatchSystem.addTrainDeparture(trainDeparture3);
    trainDispatchSystem.addTrainDeparture(trainDeparture4);
    TrainDeparture[] trainDepartures =
          trainDispatchSystem.getTrainDeparturesByVariable("destination", "Oslo");
    assertEquals(2, trainDepartures.length);
    assertEquals(trainDeparture1, trainDepartures[0]);
    assertEquals(trainDeparture2, trainDepartures[1]);
  }

  @Test
  @DisplayName("Check if the assignTrackToTrainDeparture method works")
  void testAssignTrackToTrainDeparture() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    trainDispatchSystem.assignTrackToTrainDeparture(1, "1");
    assertEquals("1", trainDeparture.getTra());
  }

  @Test
  @DisplayName("Check if the setDelayForTrainDeparture method works")
  void testSetDelayForTrainDeparture() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    trainDispatchSystem.setDelayForTrainDeparture(1, Duration.ofHours(1), Duration.ofMinutes(0));
    assertEquals(Duration.ofHours(1), trainDeparture.getDel());
  }

  @Test
  @DisplayName("Check if the setDelayForTrainDeparture method throws "
        + "IllegalArgumentException when minutes are negative")
  void testSetDelayForTrainDepartureThrowsIllegalArgumentExceptionWhenMinutesAreNegative() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    assertThrows(IllegalArgumentException.class, () ->
          trainDispatchSystem.setDelayForTrainDeparture(1, Duration.ofHours(1),
                Duration.ofMinutes(-1)));
  }

  @Test
  @DisplayName("Check if the removeTrainDepartureIfDeparted method works")
  void testRemoveTrainDepartureIfDeparted() {
    Clock clock = new Clock();
    clock.setTime(LocalTime.of(13, 0));
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30), "L2",
          2, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    trainDispatchSystem.removeTrainDepartureIfDeparted(clock.getCurrentTime());
    assertEquals(0, trainDispatchSystem.getTrainDeparturesByVariable("train-number", "1").length);
    assertEquals(1, trainDispatchSystem.getTrainDeparturesByVariable("train-number", "2").length);
  }

  @Test
  @DisplayName("Check if the getTrainDepartures method works")
  void testGetTrainDepartures() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30), "L2",
          2, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    List<TrainDeparture> trainDepartures = trainDispatchSystem.getTrainDepartures();
    assertEquals(2, trainDepartures.size());
    assertEquals(trainDeparture1, trainDepartures.get(0));
    assertEquals(trainDeparture2, trainDepartures.get(1));
  }

  @Test
  @DisplayName("Check if the updateTrainDeparture method works when departure time is changed")
  void testUpdateTrainDepartureWhenDepartureTimeIsChanged() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    trainDispatchSystem.updateTrainDeparture(1, "departure-time", "13:30");
    assertEquals(LocalTime.of(13, 30), trainDeparture.getTdt());
  }

  @Test
  @DisplayName("Check if the updateTrainDeparture method works "
        + "when the type of information to change is invalid")
  void testUpdateTrainDepartureWhenTypeOfInformationToChangeIsInvalid() {
    TrainDeparture trainDeparture = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture);
    assertThrows(IllegalArgumentException.class, () ->
          trainDispatchSystem.updateTrainDeparture(1, "invalid", "13:30"));
  }

  @Test
  @DisplayName("Check if the reset method works")
  void testReset() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30), "L2",
          2, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    trainDispatchSystem.reset();
    assertEquals(0, trainDispatchSystem.getTrainDepartures().size());
  }

  @Test
  @DisplayName("Check if the getTrainDeparturesByVariable method works")
  void testGetTrainDeparturesByVariable() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30), "L2",
          2, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    TrainDeparture[] trainDepartures =
          trainDispatchSystem.getTrainDeparturesByVariable("train-number", "1");
    assertEquals(1, trainDepartures.length);
    assertEquals(trainDeparture1, trainDepartures[0]);
  }

  @Test
  @DisplayName("Check if the getTrainDeparturesSortedByDepartureTime method works")
  void testGetTrainDeparturesSortedByDepartureTime2() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30),
          "L1", 1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30),
          "L2", 2, "Oslo", null, Duration.ofMinutes(0));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    TrainDeparture[] trainDepartures = trainDispatchSystem.getTdtSortedByTime();
    assertEquals(2, trainDepartures.length);
    assertEquals(trainDeparture1, trainDepartures[0]);
    assertEquals(trainDeparture2, trainDepartures[1]);
  }

  @Test
  @DisplayName("Check if the getTrainDeparturesSortedByDelay method works")
  void testGetTrainDeparturesSortedByDelay() {
    TrainDeparture trainDeparture1 = new TrainDeparture(LocalTime.of(12, 30), "L1",
          1, "Oslo", null, Duration.ofMinutes(0));
    TrainDeparture trainDeparture2 = new TrainDeparture(LocalTime.of(13, 30), "L2",
          2, "Oslo", null, Duration.ofMinutes(10));
    trainDispatchSystem.addTrainDeparture(trainDeparture1);
    trainDispatchSystem.addTrainDeparture(trainDeparture2);
    TrainDeparture[] trainDepartures = trainDispatchSystem.getTdtSortedByDel();
    assertEquals(2, trainDepartures.length);
    assertEquals(trainDeparture1, trainDepartures[0]);
    assertEquals(trainDeparture2, trainDepartures[1]);
  }
}