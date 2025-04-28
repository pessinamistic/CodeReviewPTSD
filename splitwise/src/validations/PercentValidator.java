package validations;

import java.util.List;

public class PercentValidator implements ExpenseValidator {
  @Override
  public boolean validate(String payee, List<String> participants, double amount, int numberOfParticipants, List<Double> values) {

    if (!defaultValidations(payee, participants, amount, numberOfParticipants)) {
      return false;
    }

    if (values.size() != numberOfParticipants) {
      System.out.println("Number of values do not match");
      return false;
    }

    double total = 0;
    for (double value : values) {
      total += value;
    }

    if (total != 100) {
      System.out.println("Sum of Percentage should be 100");
      return false;
    }

    return true;
  }
}
