package controllers;

import entities.Customer;
import entities.DepositAccount;
import entities.DepositAccountType;
import entities.Employee;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;
import exceptions.NotAuthenticatedException;
import services.AuthService;
import services.CustomerService;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.math.BigDecimal;

@Stateful
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
        if (this.authService.employeeNotExists(loggedInEmployee)) {
            throw new NotAuthenticatedException();
        }

        return this.customerService.create(firstName, lastName, identificationNumber, contactNumber, addressLine1, addressLine2, postalCode);
    }

    @Override
    public DepositAccount openDepositAccount(Employee loggedInEmployee,
                                             Long customerId,
                                             String accountType,
                                             BigDecimal initialDeposit) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException {
        if (this.authService.employeeNotExists(loggedInEmployee)) {
            throw new NotAuthenticatedException();
        }

        DepositAccountType depositAccountType;

        switch (accountType.toLowerCase()) {
            case "current":
                depositAccountType = DepositAccountType.CURRENT;
                break;
            default:
            case "savings":
                depositAccountType = DepositAccountType.SAVINGS;
                break;
        }

        return this.customerService.openDepositAccount(customerId, depositAccountType, initialDeposit);
    }


    @Override
    public void issueAtmCard() {

    }

    @Override
    public void issueReplacementAtmCard() {

    }
}
