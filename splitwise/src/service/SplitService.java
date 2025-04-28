package service;

import java.util.List;

public interface SplitService {
  public void splitExpense(String payee,
                              List<String> participants,
                              double amount,
                              int numberOfParticipants,
                              List<Double> values);
}
