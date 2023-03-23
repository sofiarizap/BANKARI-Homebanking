package com.mindhub.homebanking.utils;

public final class CardUtils {
  private CardUtils(){}

  public static String getCardNumber() {
    String newCardNumber;
    newCardNumber = Integer.toString(getInt(0000, 9999));
    newCardNumber += " " + getInt(0000, 9999);
    newCardNumber += " " + getInt(0000, 9999);
    newCardNumber += " " + getInt(0000, 9999);
    return newCardNumber;
  }

  public static int getCVV() {
    return getInt(000, 999);
  }
  public static int getInt(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

}
