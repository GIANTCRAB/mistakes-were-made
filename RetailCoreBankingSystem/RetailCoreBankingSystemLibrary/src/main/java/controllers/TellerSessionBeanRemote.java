package controllers;

import entities.AtmCard;
import entities.Customer;
import entities.DepositAccount;
import entities.Employee;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;
import exceptions.NotAuthenticatedException;

import javax.ejb.Remote;
import java.math.BigDecimal;

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

    DepositAccount openDepositAccount(Employee loggedInEmployee,
                                      Long customerId,
                                      String accountType,
                                      BigDecimal initialDeposit) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException;

    AtmCard issueAtmCard(Employee loggedInEmployee,
                         Long customerId,
                         String accountList,
                         String nameOnCard,
                         String pin) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException;

    AtmCard issueReplacementAtmCard(Employee loggedInEmployee, Long atmCardId) throws NotAuthenticatedException, InvalidEntityIdException, InvalidConstraintException;
}
