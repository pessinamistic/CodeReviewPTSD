package splitwise.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Split;
import model.User;
import repository.UserRepository;
import service.EqualSplitCalculation;
import validations.EqualsValidator;
import validations.ExactValidator;
import validations.PercentValidator;
import validations.ShareValidator;

public class SplitWise {

  public static void main(String[] args) {
//    EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
//    EXPENSE u1 1250 2 u2 u3 EXACT 370 880
//    EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20

    Scanner scanner = new Scanner(System.in);
    initialiseSetup();

    while (true) {
      //String input = "EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL";
      String input = scanner.nextLine();

      if (input.equalsIgnoreCase("exit")) {
        break;
      }

      String[] userInput = input.split(" ");
      String command = userInput[0];

      switch (command.toUpperCase()) {
        case "EXPENSE" -> processExpenseCommand(userInput);
        case "SHOW" -> processShowCommand(userInput);
        default -> throw new IllegalStateException("Unexpected value: " + command.toUpperCase());
      }
    }

  }

  private static void initialiseSetup() {
    User u1 = new User("u1", "", "");
    UserRepository.addUser("u1", u1);
    User u2 = new User("u2", "", "");
    UserRepository.addUser("u2", u2);
    User u3 = new User("u3", "", "");
    UserRepository.addUser("u3", u3);
    User u4 = new User("u4", "", "");
    UserRepository.addUser("u4", u4);
  }

  private static void processShowCommand(String[] userInput) {
    String user = "";
    if (userInput.length == 2) {
      user = userInput[1];
    }

  }

  private static void processExpenseCommand(String[] userInput) {
    EqualsValidator equalsValidator = new EqualsValidator();
    EqualSplitCalculation equalSplitCalculation = new EqualSplitCalculation();
    ExactValidator exactValidator = new ExactValidator();
    PercentValidator percentValidator = new PercentValidator();
    ShareValidator shareValidator = new ShareValidator();

    String payee = userInput[1];
    double amount = Double.parseDouble(userInput[2]);
    int numberOfParticipant = Integer.parseInt(userInput[3]);
    List<String> participants = new ArrayList<>(numberOfParticipant);
    int i = 4;
    for (; i < 4 + numberOfParticipant; i++) {
      participants.add(userInput[i]);
    }
    Split splitType = Split.valueOf(userInput[i]);
    List<Double> values = new ArrayList<>(numberOfParticipant);
    switch (splitType) {
      case EQUAL -> {
        System.out.println("Equal Split");
        equalsValidator.validate(payee, participants, amount, numberOfParticipant, values);
        equalSplitCalculation.splitExpense(payee, participants, amount, numberOfParticipant, values);
      }
      case EXACT -> {
        System.out.println("Exact Split");
        extracted(i, userInput, values);
        exactValidator.validate(payee, participants, amount, numberOfParticipant, values);
      }
      case PERCENT -> {
        System.out.println("Percent Split");
        extracted(i, userInput, values);
        percentValidator.validate(payee, participants, amount, numberOfParticipant, values);
      }
      case SHARE -> {
        System.out.println("Share Split");
        extracted(i, userInput, values);
        shareValidator.validate(payee, participants, amount, numberOfParticipant, values);
      }
      default -> System.out.println("Invalid Split Type");

    }
  }

  private static void extracted(int i, String[] userInput, List<Double> values) {
    for (int j = i + 1; j < userInput.length; j++) {
      values.add(Double.parseDouble(userInput[j]));
    }
  }
}