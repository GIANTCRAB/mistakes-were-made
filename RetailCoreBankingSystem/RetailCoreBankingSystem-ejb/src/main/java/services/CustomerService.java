package services;

import entities.AtmCard;
import entities.Customer;
import entities.DepositAccount;
import entities.DepositAccountType;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    public DepositAccount openDepositAccount(Long customerId, DepositAccountType accountType, BigDecimal initialDeposit) throws InvalidEntityIdException, InvalidConstraintException {
        final Customer customer = this.em.find(Customer.class, customerId);
        if (customer == null) {
            throw new InvalidEntityIdException();
        }

        DepositAccount depositAccount = new DepositAccount();
        final Random random = new Random();
        final String accountNumber = padZero(random.nextInt(1000), 3) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4) + "-"
                + padZero(random.nextInt(10000), 4);
        depositAccount.setAccountNumber(accountNumber);
        depositAccount.setCustomer(customer);
        depositAccount.setDepositAccountType(accountType);
        // TODO: have a initial transaction to set balance?
        depositAccount.setAvailableBalance(initialDeposit);
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AtmCard issueNewAtmCard(Long customerId, List<Long> accountIdList, String nameOnCard, String pin) throws InvalidEntityIdException, InvalidConstraintException {
        final Customer customer = this.em.find(Customer.class, customerId);
        if (customer == null) {
            throw new InvalidEntityIdException();
        }

        List<DepositAccount> depositAccountList = new ArrayList<>();

        for (Long id : accountIdList) {
            DepositAccount depositAccount = this.em.find(DepositAccount.class, id);
            if (depositAccount == null) {
                throw new InvalidEntityIdException();
            }

            depositAccountList.add(depositAccount);
        }

        AtmCard atmCard = new AtmCard();
        atmCard.setCardNumber("00000000");
        atmCard.setCustomer(customer);
        atmCard.setDepositAccountList(depositAccountList);
        atmCard.setNameOnCard(nameOnCard);
        atmCard.setPin(pin);
        Set<ConstraintViolation<AtmCard>> violations = validator.validate(atmCard);
        // There are invalid data
        if (!violations.isEmpty()) {
            throw new InvalidConstraintException(violations.toString());
        }
        em.persist(atmCard);
        em.flush();

        for (DepositAccount depositAccount : depositAccountList) {
            depositAccount.setAtmCard(atmCard);
            em.persist(depositAccount);
        }
        customer.setAtmCard(atmCard);
        em.persist(customer);
        em.flush();

        return atmCard;
    }

    private String padZero(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }
}
