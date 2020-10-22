package controllers;

import entities.Customer;
import entities.Employee;
import exceptions.InvalidConstraintException;
import exceptions.NotAuthenticatedException;
import services.AuthService;
import services.CustomerService;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful(name = "Teller")
public class TellerSessionBean implements TellerSessionBeanLocal, TellerSessionBeanRemote {
    // This is a managed bean, prefer CDI over EJB annotation
    @Inject
    CustomerService customerService;
    @Inject
    AuthService authService;

    @Override
    public Customer createCustomer(Employee loggedInEmployee,
                                   String firstName,
                                   String lastName,
                                   String identificationNumber,
                                   String contactNumber,
                                   String addressLine1,
                                   String addressLine2,
                                   String postalCode) throws NotAuthenticatedException, InvalidConstraintException {
        // Make sure employee is logged in
        if (this.authService.checkEmployeeExists(loggedInEmployee)) {
            throw new NotAuthenticatedException();
        }

        return this.customerService.create(firstName, lastName, identificationNumber, contactNumber, addressLine1, addressLine2, postalCode);
    }

    @Override
    public void openDepositAccount() {

    }

    @Override
    public void issueAtmCard() {

    }

    @Override
    public void issueReplacementAtmCard() {

    }
}
