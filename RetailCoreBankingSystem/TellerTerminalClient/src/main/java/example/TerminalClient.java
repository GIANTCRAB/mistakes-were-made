package example;

import controllers.TellerAuthSessionBeanRemote;
import controllers.TellerSessionBeanRemote;
import entities.Employee;
import exceptions.IncorrectCredentialsException;
import exceptions.InvalidConstraintException;
import exceptions.NotAuthenticatedException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

@RequiredArgsConstructor
public class TerminalClient {
    @NonNull
    private final InputStream inputStream;
    @NonNull
    private final OutputStream outputStream;
    @NonNull
    private final TellerAuthSessionBeanRemote tellerAuthSessionBeanRemote;
    @NonNull
    private final TellerSessionBeanRemote tellerSessionBeanRemote;

    @Setter(AccessLevel.PRIVATE)
    private Scanner scanner;
    @Setter(AccessLevel.PRIVATE)
    private OutputStreamWriter outputStreamWriter;
    @Setter(AccessLevel.PRIVATE)
    private Employee authenticatedEmployee;

    public void runApp() throws IOException {
        this.scanner = new Scanner(inputStream);
        this.outputStreamWriter = new OutputStreamWriter(outputStream);

        boolean loop = true;

        while (loop) {
            this.outputStreamWriter.write("*** Bank Teller Terminal Client ***\n");
            this.outputStreamWriter.write("1: Login\n");
            this.outputStreamWriter.write("2: Exit\n");
            this.outputStreamWriter.flush();

            final int option = scanner.nextInt();

            if (option == 1) {
                doEmployeeLogin();

                // Display menu after login
                displayEmployeeMenu();
            } else {
                loop = false;
            }
        }
    }

    private void doEmployeeLogin() throws IOException {
        // Get username and password input
        boolean loginLoop = true;
        while (loginLoop) {
            this.outputStreamWriter.write("Enter username:\n");
            this.outputStreamWriter.flush();
            final String username = scanner.next();
            this.outputStreamWriter.write("Enter password:\n");
            this.outputStreamWriter.flush();
            final String password = scanner.next();
            try {
                this.authenticatedEmployee = this.tellerAuthSessionBeanRemote.login(username, password);

                this.outputStreamWriter.write("Authenticated as " + authenticatedEmployee.getFirstName() + ".\n");
                loginLoop = false;
            } catch (IncorrectCredentialsException e) {
                this.outputStreamWriter.write("Incorrect credentials! Try again!\n");
            }

            this.outputStreamWriter.flush();
        }
    }

    private void displayEmployeeMenu() throws IOException {
        boolean loggedIn = true;
        while (loggedIn) {
            this.outputStreamWriter.write("*** Bank Teller Terminal Client ***\n");
            this.outputStreamWriter.write("1: Create Customer\n");
            this.outputStreamWriter.write("2: Open Deposit Account\n");
            this.outputStreamWriter.write("3: Issue ATM Card\n");
            this.outputStreamWriter.write("4: Issue Replacement ATM Card\n");
            this.outputStreamWriter.write("5: Logout\n");
            this.outputStreamWriter.flush();

            final int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayCustomerCreationMenu();
                case 5:
                default:
                    loggedIn = false;
                    this.authenticatedEmployee = null;
                    break;
            }
        }
    }

    private void displayCustomerCreationMenu() throws IOException {
        boolean loop = true;
        while (loop) {
            this.outputStreamWriter.write("Enter first name:\n");
            this.outputStreamWriter.flush();
            final String firstName = scanner.next();
            this.outputStreamWriter.write("Enter last name:\n");
            this.outputStreamWriter.flush();
            final String lastName = scanner.next();
            this.outputStreamWriter.write("Enter identification number:\n");
            this.outputStreamWriter.flush();
            final String identificationNumber = scanner.next();
            this.outputStreamWriter.write("Enter contact number:\n");
            this.outputStreamWriter.flush();
            final String contactNumber = scanner.next();
            this.outputStreamWriter.write("Enter address line 1:\n");
            this.outputStreamWriter.flush();
            final String addressLine1 = scanner.next();
            this.outputStreamWriter.write("Enter address line 2: (Optional)\n");
            this.outputStreamWriter.flush();
            final String addressLine2Input = scanner.next();
            String addressLine2 = null;
            if (!addressLine2Input.trim().equals("")) {
                addressLine2 = addressLine2Input;
            }
            this.outputStreamWriter.write("Enter postal code:\n");
            this.outputStreamWriter.flush();
            final String postalCode = scanner.next();

            try {
                this.tellerSessionBeanRemote.createCustomer(this.authenticatedEmployee, firstName, lastName, identificationNumber, contactNumber, addressLine1, addressLine2, postalCode);
                this.outputStreamWriter.write("Customer created successfully\n");
                this.outputStreamWriter.flush();
                loop = false;
            } catch (NotAuthenticatedException | InvalidConstraintException e) {
                if (e instanceof NotAuthenticatedException) {
                    this.outputStreamWriter.write("You are not logged in! Restart and try again.\n");
                    this.outputStreamWriter.flush();
                    loop = false;
                }

                if (e instanceof InvalidConstraintException) {
                    final InvalidConstraintException invalidConstraintException = (InvalidConstraintException) e;
                    this.outputStreamWriter.write("There were some validation errors!\n");
                    this.outputStreamWriter.write(invalidConstraintException.toString() + "\n");
                    this.outputStreamWriter.flush();
                }
            }
        }
    }
}