package controllers;

import entities.*;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;
import exceptions.NotAuthenticatedException;
import services.AuthService;
import services.CustomerService;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public AtmCard issueAtmCard(Employee loggedInEmployee, Long customerId, String accountList, String nameOnCard, String pin) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException {
        if (this.authService.employeeNotExists(loggedInEmployee)) {
            throw new NotAuthenticatedException();
        }

        final List<Long> accountIds = new ArrayList<>();
        Arrays.stream(accountList.split(",")).mapToLong(Long::getLong).forEach(accountIds::add);

        return this.customerService.issueNewAtmCard(customerId, accountIds, nameOnCard, pin);
    }

    @Override
    public AtmCard issueReplacementAtmCard(Employee loggedInEmployee, Long atmCardId) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException {
        if (this.authService.employeeNotExists(loggedInEmployee)) {
            throw new NotAuthenticatedException();
        }

        return this.customerService.issueReplacementAtmCard(atmCardId);
    }
}
