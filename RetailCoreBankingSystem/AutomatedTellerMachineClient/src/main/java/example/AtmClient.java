package example;

import controllers.AtmAuthSessionBeanRemote;
import controllers.AtmSessionBeanRemote;
import entities.AtmCard;
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
public class AtmClient {
    @NonNull
    private final InputStream inputStream;
    @NonNull
    private final OutputStream outputStream;
    @NonNull
    private final AtmAuthSessionBeanRemote atmAuthSessionBeanRemote;
    @NonNull
    private final AtmSessionBeanRemote atmSessionBeanRemote;

    @Setter(AccessLevel.PRIVATE)
    private Scanner scanner;
    @Setter(AccessLevel.PRIVATE)
    private OutputStreamWriter outputStreamWriter;
    @Setter(AccessLevel.PRIVATE)
    private AtmCard authenticatedAtmCard;

    public void runApp() throws IOException {
        this.scanner = new Scanner(inputStream);
        this.outputStreamWriter = new OutputStreamWriter(outputStream);

        boolean loop = true;

        while (loop) {
            this.outputStreamWriter.write("*** Atm Client ***\n");
            this.outputStreamWriter.write("1: Insert ATM Card\n");
            this.outputStreamWriter.write("2: Exit\n");
            this.outputStreamWriter.flush();

            final int option = scanner.nextInt();

            if (option == 1) {
                doAtmLogin();

                // Display menu after login
                displayCustomerMenu();
            } else {
                loop = false;
            }
        }

        this.inputStream.close();
        this.outputStream.close();
    }

    private void doAtmLogin() throws IOException {
        // Get credit card number and pin input
        boolean loginLoop = true;
        while (loginLoop) {
            this.outputStreamWriter.write("Enter Credit Card Number:\n");
            this.outputStreamWriter.flush();
            final String ccNumber = scanner.next();
            this.outputStreamWriter.write("Enter Pin:\n");
            this.outputStreamWriter.flush();
            final String pin = scanner.next();
            try {
                this.authenticatedAtmCard = this.atmAuthSessionBeanRemote.insertAtmCard(ccNumber, pin);

                this.outputStreamWriter.write("Authenticated as " + this.authenticatedAtmCard.getCustomer().getFirstName() + " (" + this.authenticatedAtmCard.getCustomer().getCustomerId() + ")" + ".\n");
                loginLoop = false;
            } catch (IncorrectCredentialsException e) {
                this.outputStreamWriter.write("Incorrect credentials! Try again!\n");
            }

            this.outputStreamWriter.flush();
        }
    }

    private void displayCustomerMenu() throws IOException {
        boolean loggedIn = true;
        while (loggedIn) {
            this.outputStreamWriter.write("*** Atm Client ***\n");
            this.outputStreamWriter.write("1: Change PIN\n");
            this.outputStreamWriter.write("2: Inquire Balance\n");
            this.outputStreamWriter.write("3: Logout\n");
            this.outputStreamWriter.flush();

            final int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayChangePinMenu();
                    break;
                case 3:
                default:
                    loggedIn = false;
                    this.authenticatedAtmCard = null;
                    break;
            }
        }
    }

    private void displayChangePinMenu() throws IOException {
        boolean loop = true;
        while (loop) {
            this.outputStreamWriter.write("Enter New PIN:\n");
            this.outputStreamWriter.flush();
            String newPin = scanner.next();

            try {
                this.atmSessionBeanRemote.changePin(this.authenticatedAtmCard, newPin);
                this.outputStreamWriter.write("PIN changed successfully.\n");
                this.outputStreamWriter.flush();
                loop = false;
            } catch (NotAuthenticatedException e) {
                this.displayNotAuthenticatedMessage();
                loop = false;
            } catch (InvalidConstraintException e) {
                this.displayConstraintErrorMessage(e);
            }
        }
    }

    private void displayNotAuthenticatedMessage() throws IOException {
        this.outputStreamWriter.write("You are not logged in! Restart and try again.\n");
        this.outputStreamWriter.flush();
    }

    private void displayConstraintErrorMessage(InvalidConstraintException invalidConstraintException) throws IOException {
        this.outputStreamWriter.write("There were some validation errors!\n");
        this.outputStreamWriter.write(invalidConstraintException.toString() + "\n");
        this.outputStreamWriter.flush();
    }
}
