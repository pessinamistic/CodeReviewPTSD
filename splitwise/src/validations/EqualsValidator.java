package validations;

import java.util.List;

public class EqualsValidator implements ExpenseValidator {
  @Override
  public boolean validate(
      String payee,
      List<String> participants,
      double amount,
      int numberOfParticipants,
      List<Double> values) {
    return defaultValidations(payee, participants, amount, numberOfParticipants);
  }
}
