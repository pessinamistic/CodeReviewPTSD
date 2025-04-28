package validations;

import java.util.List;

public class ShareValidator implements ExpenseValidator{
  @Override
  public boolean validate(String payee, List<String> participants, double amount, int numberOfParticipants, List<Double> values) {
    if (!defaultValidations(payee, participants, amount, numberOfParticipants)) {
      return false;
    }
    if (values.size() != numberOfParticipants) {
      System.out.println("Shares should be equal to the no of participants");
      return false;
    }
    return true;
  }
}
