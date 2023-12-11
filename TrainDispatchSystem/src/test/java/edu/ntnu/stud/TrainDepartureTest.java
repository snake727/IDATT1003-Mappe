package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



/**
 * This class creates the framework for testing the TrainDeparture class.
 *
 * @version 1.4 2023-12-11
 */
class TrainDepartureTest {
  TrainDeparture trainDeparture;

  @BeforeEach
  void setup() {
    trainDeparture = new TrainDeparture(LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          "1",
          Duration.ofMinutes(0));
  }


  @Test
  @DisplayName("Check if the constructor works")
  void testConstructor() {
    // Checks if the constructor works when delay is 0 minutes.
    assertEquals(LocalTime.of(12, 30), trainDeparture.getTdt());
    assertEquals("L1", trainDeparture.getLine());
    assertEquals(1, trainDeparture.getTrn());
    assertEquals("Oslo", trainDeparture.getDst());
    assertEquals("1", trainDeparture.getTra());
    assertEquals(Duration.ofHours(0).plusMinutes(0), trainDeparture.getDel());

    // Checks if the constructor works when delay is 1 minute.
    trainDeparture = new TrainDeparture(
          LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          "1",
          Duration.ofMinutes(1));
    assertEquals(LocalTime.of(12, 31), trainDeparture.getTdt());
    assertEquals("L1", trainDeparture.getLine());
    assertEquals(1, trainDeparture.getTrn());
    assertEquals("Oslo", trainDeparture.getDst());
    assertEquals("1", trainDeparture.getTra());
    assertEquals(Duration.ofHours(0).plusMinutes(1), trainDeparture.getDel());

    // Checks if the constructor works when delay is 1 hour and 30 minutes.
    trainDeparture = new TrainDeparture(
          LocalTime.of(12, 30),
          "L1",
          1,
          "Oslo",
          "1",
          Duration.ofHours(1).plusMinutes(30));
    assertEquals(LocalTime.of(14, 0), trainDeparture.getTdt());
    assertEquals("L1", trainDeparture.getLine());
    assertEquals(1, trainDeparture.getTrn());
    assertEquals("Oslo", trainDeparture.getDst());
    assertEquals("1", trainDeparture.getTra());
    assertEquals(Duration.ofHours(1).plusMinutes(30), trainDeparture.getDel());
  }

  @Test
  @DisplayName("Check if getDelay works")
  void testGetDelay() {
    // Checks if getDelay works when delay is 0 minutes.
    trainDeparture.setDelay(Duration.ofHours(0), Duration.ofMinutes(0));
    assertEquals(Duration.ofHours(0).plusMinutes(0), trainDeparture.getDel());

    // Checks if getDelay works when delay is 1 minute.
    trainDeparture.setDelay(Duration.ofHours(0), Duration.ofMinutes(1));
    assertEquals(Duration.ofHours(0).plusMinutes(1), trainDeparture.getDel());

    // Checks if getDelay works when delay is 1 hour and 30 minutes.
    trainDeparture.setDelay(Duration.ofHours(1), Duration.ofMinutes(30));
    assertEquals(Duration.ofHours(1).plusMinutes(30), trainDeparture.getDel());
  }

  @Test
  @DisplayName("Check if getTrack works")
  void testGetTrack() {
    // Checks if getTrack works when track is set to a single positive digit.
    trainDeparture.setTra("2");
    assertEquals("2", trainDeparture.getTra());

    // Checks if getTrack works when track is set to a single negative digit.
    trainDeparture.setTra("-2");
    assertEquals("-2", trainDeparture.getTra());
  }

  @Test
  @DisplayName("Check if getDestination works")
  void testGetDestination() {
    trainDeparture.setDst("Trondheim");
    assertEquals("Trondheim", trainDeparture.getDst());
  }

  @Test
  @DisplayName("Check if setDestination() works")
  void testSetDestination() {
    // Checks if setDestination works when destination is set to an arguably normal name.
    trainDeparture.setDst("Trondheim");
    assertEquals("Trondheim", trainDeparture.getDst());

    // Checks if setDestination works when destination is set to a name with spaces.
    trainDeparture.setDst("Trondheim Sentralstasjon");
    assertEquals("Trondheim Sentralstasjon", trainDeparture.getDst());

    /* Checks if setDestination works when destination is set
       to a name with spaces and special characters. */
    trainDeparture.setDst("Trondheim Sentralstasjon!");
    assertEquals("Trondheim Sentralstasjon!", trainDeparture.getDst());

    /* Checks if setDestination works when destination is set to
       a very long name with special characters, numbers and spaces. */
    trainDeparture.setDst("Trondheim Sentralstasjon "
          + "1234567890!@#$%^&*()_+|}{:?><,./;'[]=-`~");
    assertEquals("Trondheim Sentralstasjon "
          + "1234567890!@#$%^&*()_+|}{:?><,./;'[]=-`~", trainDeparture.getDst());

    // Checks if setDestination works when destination given is empty.
    trainDeparture.setDst("");
    assertEquals("", trainDeparture.getDst());

    // Checks if setDestination works when destination given is null.
    trainDeparture.setDst(null);

    // Checks if setDestination works when destination given is several spaces.
    trainDeparture.setDst("   ");
    assertEquals("   ", trainDeparture.getDst());
  }

  @Test
  @DisplayName("Check if setTrack works")
  void testSetTrack() {
    // Checks if setTrack works when track is set to a single positive digit.
    trainDeparture.setTra("2");
    assertEquals("2", trainDeparture.getTra());

    // Checks if setTrack works when track is set to a single negative digit.
    trainDeparture.setTra("-2");
    assertEquals("-2", trainDeparture.getTra());

    /* Checks if setTrack throws IllegalArgumentException
       when track is a letter or the - character. */
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTra("A"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTra("-"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTra("a"));
  }

  @Test
  @DisplayName("Check if setTrack throws IllegalArgumentException "
        + "when track is a letter or the - character")
  void testSetTrackThrowsIllegalArgumentExceptionWhenTrackIs() {
    // Checks if IllegalArgumentException is thrown when track is set to a single letter.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTra("A"));

    // Checks if IllegalArgumentException is thrown when track is set to the - character.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTra("-"));
  }

  @Test
  @DisplayName("Check if setDelay works")
  void testSetDelay() {
    // Checks if setDelay works when delay is set to 0 minutes.
    trainDeparture.setDelay(Duration.ofHours(1), Duration.ofMinutes(30));

    // Checks if setDelay works when delay is set to 1 minute.
    assertEquals(Duration.ofHours(1).plusMinutes(30), trainDeparture.getDel());

    // Checks if setDelay works when delay is set to 1 hour and 30 minutes.
    assertNotEquals(Duration.ofHours(1).plusMinutes(29), trainDeparture.getDel());

    // Checks if setDelay works when delay is set to 1 hour and 30 minutes.
    assertNotEquals(Duration.ofHours(1).plusMinutes(31), trainDeparture.getDel());
  }

  @Test
  @DisplayName("Check if setLine works")
  void testSetLine() {
    /* Checks if setLine works when line is set to a
       single letter followed by a single positive digit. */
    trainDeparture.setLine("L1");
    assertEquals("L1", trainDeparture.getLine());

    /* Checks if setLine throws IllegalArgumentException when
       line is not set to a single letter followed by a single negative digit */
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("L-1"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("L11"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("LA"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("A"));
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("1"));
  }

  @Test
  @DisplayName("Check if setLine throws IllegalArgumentException when line is null")
  void testSetLineThrowsIllegalArgumentExceptionWhenLineIsNull() {
    // Checks if IllegalArgumentException is thrown when line is set to null.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine(null));
  }

  @Test
  @DisplayName("Check if setLine throws IllegalArgumentException when "
        + "line is not one char followed by one digit")
  void testSetLineThrowsIllegalArgumentExceptionWhenLineIsNotOneCharFollowedByOneDigit() {
    // Checks if IllegalArgumentException is thrown when line is set to a single letter.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("A"));

    // Checks if IllegalArgumentException is thrown when line is set to a single digit.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("1"));

    /* Checks if IllegalArgumentException is thrown when
       line is set to a single letter followed by two digits. */
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("L11"));

    /* Checks if IllegalArgumentException is thrown
       when line is set to a single letter followed by a letter. */
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("LA"));

    /* Checks if IllegalArgumentException is thrown
    when line is set to a single letter followed by the - character. */
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setLine("L-"));
  }

  @Test
  @DisplayName("Check if setTrainNumber works")
  void testSetTrainNumber() {
    // Checks if setTrainNumber works when train number is set to 1.
    trainDeparture.setTrn(1);
    assertEquals(1, trainDeparture.getTrn());

    // Checks if setTrainNumber works when train number is set to 100.
    trainDeparture.setTrn(100);
    assertEquals(100, trainDeparture.getTrn());
  }

  @Test
  @DisplayName("Check if setTrainNumber throws "
        + "IllegalArgumentException when train number is below 1")
  void testSetTrainNumberThrowsIllegalArgumentExceptionWhenTrainNumberIsBelow1() {
    // Checks if IllegalArgumentException is thrown when train number is set to 0.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrn(0));

    // Checks if IllegalArgumentException is thrown when train number is set to -1.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTrn(-1));
  }

  @Test
  @DisplayName("Check if setDepartureTime works")
  void testSetDepartureTime() {
    // Checks if setDepartureTime works when departure time is set to 00:00.
    trainDeparture.setTdt(LocalTime.of(0, 0));
    assertEquals(LocalTime.of(0, 0), trainDeparture.getTdt());

    // Checks if setDepartureTime works when departure time is set to 23:59.
    trainDeparture.setTdt(LocalTime.of(23, 59));
    assertEquals(LocalTime.of(23, 59), trainDeparture.getTdt());
  }

  @Test
  @DisplayName("Check if hasDeparted works")
  void testHasDeparted() {
    // Checks if hasDeparted works when departure time is set to 12:29.
    assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 29)));

    // Checks if hasDeparted works when departure time is set to 12:30.
    assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 30)));

    // Checks if hasDeparted works when departure time is set to 12:31.
    assertTrue(trainDeparture.hasDeparted(LocalTime.of(12, 31)));
  }

  @Test
  @DisplayName("Check if hasDeparted works with delay")
  void testHasDepartedWithDelay() {
    /* Checks if hasDeparted works. The departure time is 12:30,
       and the delay is 1 minute. This means that the train will depart at 12:31. */
    trainDeparture.setDelay(Duration.ofHours(0), Duration.ofMinutes(1));
    assertFalse(trainDeparture.hasDeparted(LocalTime.of(12, 29)));
    assertTrue(trainDeparture.hasDeparted(LocalTime.of(12, 32)));

    /* Checks if hasDeparted works. The departure time is 12:30,
       and the delay is 1 hour and 1 minute. This means that the train will depart at 13:31. */
    trainDeparture.setDelay(Duration.ofHours(1), Duration.ofMinutes(1));
    assertFalse(trainDeparture.hasDeparted(LocalTime.of(13, 30)));
    assertTrue(trainDeparture.hasDeparted(LocalTime.of(13, 32)));
  }


  @Test
  @DisplayName("Check if setDepartureTime throws "
        + "IllegalArgumentException when departure time is null")
  void testSetDepartureTimeThrowsIllegalArgumentExceptionIfInvalidInput() {
    // Checks if IllegalArgumentException is thrown when departure time is set to null.
    assertThrows(IllegalArgumentException.class, () -> trainDeparture.setTdt(null));
  }

  @Test
  @DisplayName("Check if printDetails works. PT0S is a standard representation of "
        + "duration of time in the ISO-8601 format, where P indicates the period (duration) and T"
        + " indicates the time. The number before the S indicates the number of seconds.")
  void testPrintDetails() {
    // Checks if printDetails() works when delay is 0 minutes.
    trainDeparture.setDelay(Duration.ofHours(0), Duration.ofMinutes(0));
    assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 1, "
          + "destination = Oslo, track = 1, delay = No delay", trainDeparture.printDetails());

    // Checks if printDetails() works when delay is 1 minute.
    trainDeparture.setDelay(Duration.ofHours(0), Duration.ofMinutes(1));
    assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 1, "
          + "destination = Oslo, track = 1, delay = 1 minutes", trainDeparture.printDetails());

    // Checks if printDetails() works when delay is 1 hour and 30 minutes.
    trainDeparture.setDelay(Duration.ofHours(1), Duration.ofMinutes(30));
    assertEquals("Train departure details: departure-time = 12:30, line = L1, train-number = 1, "
          + "destination = Oslo, track = 1, "
          + "delay = 1 hours 30 minutes", trainDeparture.printDetails());
  }
}