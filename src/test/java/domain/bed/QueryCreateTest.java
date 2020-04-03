package domain.bed;

import static org.junit.jupiter.api.Assertions.*;

import application.Query;
import domain.bed.exception.*;
import domain.booking.exception.InvalidArrivalDateException;
import domain.booking.exception.InvalidNumberOfNightsException;
import domain.geolocation.exception.InvalidZipCodeException;
import domain.geolocation.exception.NonExistingZipCodeException;
import org.junit.jupiter.api.Test;

public class QueryCreateTest {
  @Test
  void createQuery_withInvalidArrivalDate_shouldThrow() {
    assertThrows(
        InvalidArrivalDateException.class,
        () ->
            new Query(
                "empty",
                "empty",
                "empty",
                "empty",
                "50",
                "empty",
                "12-05-2020",
                "empty",
                "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsString_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "50", "empty", "empty", "test", "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsBelow1_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "50", "empty", "empty", "0", "empty", "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsBigger90_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "50", "empty", "empty", "91", "empty",
                "empty"));
  }

  @Test
  void createQuery_withArrivalDateInPast_shouldThrow() {
    assertThrows(
        ArrivalDateInPastException.class,
        () ->
            new Query(
                "empty",
                "empty",
                "empty",
                "empty",
                "50",
                "empty",
                "1990-05-06",
                "empty",
                "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidDateWithoutMinCapacity_shouldThrow() {
    assertThrows(
        InvalidDateWithoutMinCapacityException.class,
        () ->
            new Query(
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "2021-05-06",
                "empty",
                "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsWithoutMinCapacity_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsWithoutMinCapacityException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "empty", "empty", "empty", "14", "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidMinCapacityFloat_shouldThrow() {
    assertThrows(
        InvalidMinCapacityException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "4.0", "empty", "empty", "empty", "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidMinCapacity0_shouldThrow() {
    assertThrows(
        InvalidMinCapacityException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "0", "empty", "empty", "empty", "empty",
                "empty"));
  }

  @Test
  void createQuery_withInvalidZipCode_shouldThrow() {
    assertThrows(
        InvalidZipCodeException.class,
        () ->
            new Query(
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "empty",
                "1234567890",
                "empty"));
  }

  @Test
  void createQuery_withNonExistingZipCode_shouldThrow() {
    assertThrows(
        NonExistingZipCodeException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "99999",
                "empty"));
  }

  @Test
  void createQuery_withInvalidMaxDistanceString_shouldThrow() {
    assertThrows(
        InvalidMaxDistanceException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "12345",
                "test"));
  }

  @Test
  void createQuery_withInvalidMaxDistance0_shouldThrow() {
    assertThrows(
        InvalidMaxDistanceException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "12345",
                "0"));
  }

  @Test
  void createQuery_withMaxDistanceWithoutOrigin_shouldThrow() {
    assertThrows(
        InvalidMaxDistanceWithoutOriginException.class,
        () ->
            new Query(
                "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty",
                "15.4"));
  }
}
