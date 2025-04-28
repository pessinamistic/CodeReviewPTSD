package service;

import java.util.List;

public class EqualSplitCalculation implements SplitService{
  @Override
  public void splitExpense(String payee, List<String> participants, double amount, int numberOfParticipants, List<Double> values) {
    double splitAmount = amount / numberOfParticipants;
    for (String participant : participants) {
      if (participant.equalsIgnoreCase(payee)){
        continue;
      }
      System.out.println(participant + " owes " + payee + ": " + splitAmount);
    }
  }
}
