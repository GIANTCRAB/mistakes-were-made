package controllers;

import entities.Customer;
import entities.Employee;
import exceptions.InvalidConstraintException;
import exceptions.NotAuthenticatedException;

import javax.ejb.Remote;

@Remote
public interface TellerSessionBeanRemote {
    Customer createCustomer(Employee loggedInEmployee,
                            String firstName,
                            String lastName,
                            String identificationNumber,
                            String contactNumber,
                            String addressLine1,
                            String addressLine2,
                            String postalCode) throws NotAuthenticatedException, InvalidConstraintException;

    void openDepositAccount();

    void issueAtmCard();

    void issueReplacementAtmCard();
}
