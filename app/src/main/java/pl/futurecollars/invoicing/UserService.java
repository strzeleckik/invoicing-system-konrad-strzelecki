package pl.futurecollars.invoicing;

public class UserService {

  public String capitalizeUserName(String name) {

    if (name == null) {
      return "";
    }

    return name.toUpperCase();
  }

}
