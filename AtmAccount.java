import java.util.Scanner;

public class AtmAccount {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        boolean loop = true;

        while (loop == true) {

            System.out.println("Account (Type either 1, 2 or exit.)\n1. Log-in \n2. Register");
            String accMode = keyboard.nextLine();

            if (accMode.equals("exit")) {
                loop = false;
            }

            if (accMode.equals("1")) {
                AtmMain.main(args);
                loop = false;
            }

            if (accMode.equals("2")) {
                AtmRegistration.main(args);
            }

            else {
                System.out.println("Invalid input. Please type either 1 or 2.");
            }
        }
        keyboard.close();
    }
}
