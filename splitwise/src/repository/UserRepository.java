package repository;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
  static final Map<String, User> USER_MAP = new HashMap<>();

  public static User getUser(String name) {
    if (USER_MAP.containsKey(name)) {
      return USER_MAP.get(name);
    }
    System.out.println("User not found");
    return null;
  }
  public static void addUser(String name, User user) {
    USER_MAP.put(name, user);
    System.out.println(name + " registered successfully!");
  }
}
