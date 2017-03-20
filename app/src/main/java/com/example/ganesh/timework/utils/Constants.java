package com.example.ganesh.timework.utils;

import android.util.Log;

/**
 * Created by Ganesh Prasad on 15-07-2016.
 */

public class Constants {

  public static final class Days {

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
  }

  public static int getIntWeekday(String day) {
    switch (day) {
      case "Monday":
        return 1;
      case "Tuesday":
        return 2;
      case "Wednesday":
        return 3;
      case "Thursday":
        return 4;
      case "Friday":
        return 5;
      case "Saturday":
        return 6;
      case "Sunday":
        return 7;
      default:
        return -1;
    }
  }

  public static final class RoutineTypes {

    public static final String PERSONAL = "personal";
    public static final String WORK = "work";
    public static final String HOBBY = "hobby";

  }

  public static final class TimeSizes {

    public static final String LESS = "less";
    public static final String MORE = "more";
    public static final String ALOT = "a lot";
  }

  public static final class RoutineBoolean {

    public static final int TRUE = 1;
    public static final int FALSE = 0;

  }

  public static final class DayGroups {

    public static final int WEEKDAYS = 1;
    public static final int ALLDAYS = 2;
    public static final int CUSTOM = 3;

  }

  public static int[] getDaysArray(int days, boolean[] custom) {

    int[] array;

    if (days == DayGroups.WEEKDAYS) {
      array = new int[]{1, 1, 1, 1, 1, 0, 0};
    } else if (days == DayGroups.ALLDAYS) {
      array = new int[]{1, 1, 1, 1, 1, 1, 1};
    } else {
      array = new int[7];
      for (int i = 0; i < custom.length; i++) {
        array[i] = Constants.booleanToInt(custom[i]);
      }
    }
    return array;
  }

  public static String getTypeOfRoutine(int typeSelected) {

    switch (typeSelected) {

      case 0:
        return RoutineTypes.HOBBY;
      case 1:
        return RoutineTypes.PERSONAL;
      case 2:
        return RoutineTypes.WORK;
      default:
        return null;
    }
  }

  public static int getIntForTypeOfRoutine(String type) {
    if (type.equals(RoutineTypes.HOBBY)) {
      return 0;
    } else if (type.equals(RoutineTypes.PERSONAL)) {
      return 1;
    } else if (type.equals(RoutineTypes.WORK)) {
      return 2;
    } else {
      return -1;
    }
  }

  /**
   * return methods for timesize
   */
  public static String getTimeSize(int timesize) {
    switch (timesize) {
      case 0:
        return TimeSizes.LESS;
      case 1:
        return TimeSizes.MORE;
      case 2:
        return TimeSizes.ALOT;
      default:
        return null;
    }
  }

  public static int getIntForTimeSize(String time_size) {
    if (time_size.equals(TimeSizes.LESS)) {
      return 0;
    } else if (time_size.equals(TimeSizes.MORE)) {
      return 1;
    } else if (time_size.equals(TimeSizes.ALOT)) {
      return 2;
    } else {
      return -1;
    }
  }

  //    bool to int method
  public static int booleanToInt(boolean bool) {

    if (bool) {
      return 1;
    } else {
      return 0;
    }

  }

}
