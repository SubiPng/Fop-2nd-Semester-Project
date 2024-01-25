import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class AtmMain {
    public static void main(String[] args) {
        System.out.println("Account Log-in (type \"exit\" to exit)");
        Scanner keyboard = new Scanner(System.in);

        boolean loop = true;
        String username;
        String password;
        float balance;

        while (loop == true) {

            System.out.println("Username (card insertion simulation): ");
            String usernameAttempt = keyboard.nextLine();

            if (usernameAttempt.equals("exit")) {
                loop = false;
            }

            else {
                // loop = accountChecker(loop, usernameAttempt, false);

                try {
                    File getAccount = new File(usernameAttempt + ".txt");
                    Scanner myReader = new Scanner(getAccount);

                    // Get Username
                    username = myReader.nextLine();
                    // System.out.println(username);

                    // get Password
                    password = myReader.nextLine();
                    // System.out.println(password);

                    // get balance
                    balance = myReader.nextFloat();
                    // System.out.println(balance);

                    myReader.close();

                    int passwordAttempt = 1;
                    boolean passwordValidity = true;

                    // PASSWORD ATTEMPTS AND VALIDITY
                    while (passwordValidity == true) {

                        System.out.println("Please enter your password " + "(Attempt: " + passwordAttempt + ')' + ':');
                        String passwordInput = keyboard.nextLine();

                        if (passwordInput.equals(password)) {
                            System.out.println("Welcome back, " + username + '!');
                            Menu(keyboard, balance, username, password);
                            passwordValidity = false;
                            loop = false;
                            break;
                        }

                        if (passwordAttempt == 3) {
                            System.out.println("Process cancelled");
                            passwordValidity = false;
                            loop = false;
                            break;
                        }

                        else {
                            passwordAttempt++;
                            System.out.println("Incorrect, Try again ");
                        }

                    }

                } catch (FileNotFoundException e) {
                    System.out.println("Account does not exist \n");
                }
            }
        }
    }

    // Method for ATM TERMINAL MENU
    private static void Menu(Scanner keyboard, float balance, String username, String password) {

        float withdraw;
        boolean loop = true;

        while (loop == true) {
            System.out.println("Menu (type either 1 or 2) \n1. Check Balance \n2. Wtihdraw \n3. Deposit \n4. Transfer"); //

            String menuInput = keyboard.nextLine();

            switch (menuInput) {

                case "1": // CHECK BALANCE
                    System.out.println("Balance: RM" + balance);
                    loop = false;
                    break;

                case "2": // WITHDRAW
                    System.out.println("How much do you want to withdraw?");
                    withdraw = keyboard.nextFloat();

                    balance = balance - withdraw;

                    deleteAccount(username);
                    createAccount(username, password, balance, false);
                    receiptGenerator(balance, withdraw);
                    
                    loop = false;
                    break;

                case "3": // DEPOSIT
                    System.out.println("How much do you want to deposit into your account?");
                    float deposit = keyboard.nextFloat();
                    balance = balance + deposit;

                    deleteAccount(username);

                    createAccount(username, password, balance, false);

                    loop = false;
                    break;

                case "4": // TRANSFER TO ANAOTHER ACCOUNT
                    boolean valid = false;

                    while (valid == false) {

                        boolean transferLoop = true;

                        while (transferLoop == true) {
                            System.out.println("Who do you want to transfer it to? \nUsername:");
                            String transferReceiver = keyboard.nextLine();

                            boolean checker = accountChecker(loop, transferReceiver, valid, false);

                            if (checker == true) {
                                break;
                            }

                            else {
                                transferLoop = false;
                            }

                            if (accountChecker(loop, transferReceiver, valid, false) == true) {
                                // System.out.println("valid 100%");

                                System.out.println("How much do you want to transfer?");
                                float transferAmount = keyboard.nextFloat();

                                if (transferAmount > balance) {
                                    receiptGenerator(balance, transferAmount);
                                    valid = true;
                                    loop = false;
                                    break;
                                }

                                else {
                                    try {

                                        String usernameTransfer;
                                        String passwordTransfer;
                                        float balanceTransfer;

                                        File getAccount = new File(transferReceiver + ".txt");
                                        Scanner myReader;
                                        myReader = new Scanner(getAccount);

                                        // Get Username
                                        usernameTransfer = myReader.nextLine();
                                        // System.out.println(username);

                                        // get Password
                                        passwordTransfer = myReader.nextLine();
                                        // System.out.println(password);

                                        // get balance
                                        balanceTransfer = myReader.nextFloat();
                                        // System.out.println(balance);

                                        myReader.close();

                                        // Delete and reCreate new account of the receiver
                                        balanceTransfer = balanceTransfer + transferAmount;

                                        deleteAccount(usernameTransfer);
                                        createAccount(usernameTransfer, passwordTransfer, balanceTransfer, false);

                                        balance = balance - transferAmount;

                                        deleteAccount(username);
                                        createAccount(username, password, balance, false);

                                        receiptGenerator(balance, transferAmount);
                                        valid = true;
                                        loop = false;

                                        return;

                                    } catch (Exception e) {

                                    }

                                }

                            }

                            else {
                                System.out.println("not valid");
                            }

                            valid = true;
                            loop = false;
                        }
                    }

                default:
                    if (loop == true) {
                        System.out.println("Invalid input. Please type either 1, 2, 3 or 4. \n");
                    }

                    else {

                    }
            }
        }
    }

    private static void receiptGenerator(float balance, float withdraw) {

        System.out.println("\nTransaction Receipt");

        System.out.print("Date and Time: ");
        Date now = new Date();
        System.out.println(now);

        // float withdraw = 10;
        // float balance = 50;
        if (withdraw > balance) {
            System.out.println("Error 101. Insuffclent balance");

            System.out.println("Thank you for using our service!");
        }

        else {
            System.out.println("Remaining balance = RM" + (balance));

            System.out.println("Thank you for using our service!");
        }
    }

    private static boolean accountChecker(boolean loop, String usernameAttempt, boolean accountValidity,
            boolean errorboolean) {
        try {
            File getAccount = new File(usernameAttempt + ".txt");
            Scanner myReader = new Scanner(getAccount);
            myReader.close();
            loop = false;
            return accountValidity = true;
        }

        catch (FileNotFoundException error) {
            if (errorboolean = true) {
                System.out.println("Account doesn't exist. Try again");
            }

            else {

            }
            // error.printStackTrace();
        }
        return loop;
    }

    private static void deleteAccount(String username) {
        // File Name
        String fileName = username + ".txt";
        File delFile = new File(fileName);
        delFile.delete();
    }

    private static void createAccount(String username, String password, float balance, boolean create) {
        // File Name
        String fileName = username + ".txt";

        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(username + "\n" + password + "\n" + balance); // File Content // 0 = default new account
                                                                         // balance
            myWriter.close();
            if (create == true) {
                System.out.println("Successfully created an account!");
            }
        } catch (IOException error) {
            System.out.println("An error occurred.");
            error.printStackTrace();
        }
    }

}