package service;

import java.util.List;

public class ShareSplitCalculation implements SplitService {
  @Override
  public void splitExpense(
      String payee,
      List<String> participants,
      double amount,
      int numberOfParticipants,
      List<Double> values) {}
}
