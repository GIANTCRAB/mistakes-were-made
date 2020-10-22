package services;

import entities.Customer;
import exceptions.InvalidConstraintException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
        if(addressLine2 != null) {
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
}
