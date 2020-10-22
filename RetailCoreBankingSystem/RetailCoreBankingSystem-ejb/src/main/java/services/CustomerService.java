package services;

import entities.Customer;
import entities.DepositAccount;
import entities.DepositAccountType;
import exceptions.InvalidConstraintException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Stateless
@LocalBean
public class CustomerService {
    @PersistenceContext(unitName = "rcbs")
    private EntityManager em;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public Customer create(String firstName,
                           String lastName,
                           String identificationNumber,
                           String contactNumber,
                           String addressLine1,
                           String addressLine2,
                           String postalCode) throws InvalidConstraintException {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setIdentificationNumber(identificationNumber);
        newCustomer.setContactNumber(contactNumber);
        newCustomer.setAddressLine1(addressLine1);
        // Optional to have address line 2
        if (addressLine2 != null) {
            newCustomer.setAddressLine2(addressLine2);
        }
        newCustomer.setPostalCode(postalCode);

        Set<ConstraintViolation<Customer>> violations = validator.validate(newCustomer);
        // There are invalid data
        if (!violations.isEmpty()) {
            throw new InvalidConstraintException(violations.toString());
        }
        em.persist(newCustomer);
        em.flush();

        return newCustomer;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DepositAccount openDepositAccount(@NotNull Customer customer, DepositAccountType accountType, BigDecimal initialDeposit) throws InvalidConstraintException {
        DepositAccount depositAccount = new DepositAccount();
        final Random random = new Random();
        final String accountNumber = padZero(random.nextInt(1000), 3) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4) + "-";
        depositAccount.setAccountNumber(accountNumber);
        depositAccount.setCustomer(customer);
        depositAccount.setDepositAccountType(accountType);
        depositAccount.setAvailableBalance(initialDeposit);
        depositAccount.setLedgerBalance(initialDeposit);
        Set<ConstraintViolation<DepositAccount>> violations = validator.validate(depositAccount);
        // There are invalid data
        if (!violations.isEmpty()) {
            throw new InvalidConstraintException(violations.toString());
        }
        em.persist(depositAccount);
        em.flush();

        List<DepositAccount> depositAccountList = customer.getDepositAccountList();
        depositAccountList.add(depositAccount);
        customer.setDepositAccountList(depositAccountList);
        em.persist(customer);
        em.flush();

        return depositAccount;
    }

    private String padZero(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }
}
