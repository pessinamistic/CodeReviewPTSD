package validations;

import repository.UserRepository;

import java.util.List;

public interface ExpenseValidator {
  public boolean validate(String payee,
                          List<String> participants,
                          double amount,
                          int numberOfParticipants,
                          List<Double> values);

  default boolean defaultValidations(
          String payee,
          List<String> participants,
          double amount,
          int numberOfParticipants) {

    if (payee == null || payee.isEmpty()) {
      System.out.println("Payee name cannot be null or empty");
      return false;
    }

    if (UserRepository.getUser(payee) == null) {
      System.out.println("User not found");
      return false;
    }

    if (participants == null || participants.isEmpty()) {
      System.out.println("Participants cannot be null or empty");
      return false;
    }



    if (amount <= 0) {
      System.out.println("Amount should be greater than 0");
      return false;
    }

    if (participants.size() != numberOfParticipants) {
      System.out.println("Number of participants do not match");
      return false;
    }
    return true;
  }
}
