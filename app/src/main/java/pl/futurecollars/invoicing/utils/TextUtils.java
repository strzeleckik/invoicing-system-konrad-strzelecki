package pl.futurecollars.invoicing.utils;

public class TextUtils {

  public static String capitalizeText(String text) {
    if (text == null) {
      return "";
    }
    return text.toUpperCase();
  }

}
