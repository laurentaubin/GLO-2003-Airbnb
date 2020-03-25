package domain.bed;

import static org.junit.jupiter.api.Assertions.*;

import application.Query;
import domain.bed.exception.ArrivalDateInPastException;
import domain.bed.exception.InvalidDateWithoutMinCapacityException;
import domain.bed.exception.InvalidMinCapacityException;
import domain.bed.exception.InvalidNumberOfNightsWithoutMinCapacityException;
import domain.booking.exception.InvalidArrivalDateException;
import domain.booking.exception.InvalidNumberOfNightsException;
import org.junit.jupiter.api.Test;

public class QueryCreateTest {
  @Test
  void createQuery_withInvalidArrivalDate_shouldThrow() {
    assertThrows(
        InvalidArrivalDateException.class,
        () -> new Query("empty", "empty", "empty", "empty", "50", "empty", "12-05-2020", "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsString_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> new Query("empty", "empty", "empty", "empty", "50", "empty", "empty", "test"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsBelow1_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> new Query("empty", "empty", "empty", "empty", "50", "empty", "empty", "0"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsBigger90_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> new Query("empty", "empty", "empty", "empty", "50", "empty", "empty", "91"));
  }

  @Test
  void createQuery_withArrivalDateInPast_shouldThrow() {
    assertThrows(
        ArrivalDateInPastException.class,
        () -> new Query("empty", "empty", "empty", "empty", "50", "empty", "1990-05-06", "empty"));
  }

  @Test
  void createQuery_withInvalidDateWithoutMinCapacity_shouldThrow() {
    assertThrows(
        InvalidDateWithoutMinCapacityException.class,
        () ->
            new Query("empty", "empty", "empty", "empty", "empty", "empty", "2021-05-06", "empty"));
  }

  @Test
  void createQuery_withInvalidNumberOfNightsWithoutMinCapacity_shouldThrow() {
    assertThrows(
        InvalidNumberOfNightsWithoutMinCapacityException.class,
        () -> new Query("empty", "empty", "empty", "empty", "empty", "empty", "empty", "14"));
  }

  @Test
  void createQuery_withInvalidMinCapacityFloat_shouldThrow() {
    assertThrows(
        InvalidMinCapacityException.class,
        () -> new Query("empty", "empty", "empty", "empty", "4.0", "empty", "empty", "empty"));
  }

  @Test
  void createQuery_withInvalidMinCapacity0_shouldThrow() {
    assertThrows(
        InvalidMinCapacityException.class,
        () -> new Query("empty", "empty", "empty", "empty", "0", "empty", "empty", "empty"));
  }
}
