import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AtmRegistration {
  public static void main(String[] args) {

    boolean passwordCreation = true;
    boolean passLenght = false;

    System.out.println("Account Registration (type \"exit\" to exit)");
    Scanner keyboard = new Scanner(System.in);

    // Username
    System.out.println("Username:");
    String username = keyboard.nextLine();
    username = username.trim();

    if (username.equals("exit")) {
      passLenght = true;
      passwordCreation = false;
    }

    // Password Creation Loop-the-loop
    while (passwordCreation == true) {
      // Choosing a Password
      while (passLenght == false) {
        System.out.println("Enter a password: ");
        String tempPassword = keyboard.nextLine();

        if (!tempPassword.equals("exit")) {
          if (tempPassword.length() >= 8) {
            passLenght = true;
            reEnterPass(passwordCreation, keyboard, username, tempPassword); // Re-entering the password
            passwordCreation = false;
          }

          else {
            System.out.println("Your password must contains at least 8 characters");
          }
        }

        else {
          passLenght = true;
          passwordCreation = false;
        }
      }
    }
  }

  private static void reEnterPass(boolean loop, Scanner keyboard, String username, String temp) {
    String password;
    boolean reEnterLoop = true;

    // Re-enter password
    while (reEnterLoop == true) {
      System.out.println("Re-enter the password:");
      String tempInput = keyboard.nextLine();

      if (tempInput.equals("exit")) {
        reEnterLoop = false;
        // passwordCreation = false;
      }

      else { // (!tempInput.equals("exit"))
        if (tempInput.equals(temp)) {
          password = temp;
          loop = false;
          reEnterLoop = false;
          createAccount(username, password, 0);
        }

        else {
          System.out.println("The password does not match! Try again");
        }
      }
    }
  }

  private static void createAccount(String username, String password, float balance) {
    // File Name
    String fileName = username + ".txt";

    try {
      FileWriter myWriter = new FileWriter(fileName);
      myWriter.write(username + "\n" + password + "\n" + balance); // File Content // 0 = default new account balance
      myWriter.close();
      System.out.println("Successfully created an account!");
    } catch (IOException error) {
      System.out.println("An error occurred.");
      error.printStackTrace();
    }
  }
}