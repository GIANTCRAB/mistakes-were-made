package services;

import entities.AtmCard;
import entities.DepositAccount;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@LocalBean
@Stateless
public class AtmService {
    @PersistenceContext(unitName = "rcbs")
    private EntityManager em;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public AtmCard changePin(AtmCard atmCard, String newPin) throws InvalidConstraintException {
        atmCard.setPin(newPin);
        Set<ConstraintViolation<AtmCard>> violations = validator.validate(atmCard);
        // There are invalid data
        if (!violations.isEmpty()) {
            throw new InvalidConstraintException(violations.toString());
        }
        em.persist(atmCard);
        em.flush();

        return atmCard;
    }

    public DepositAccount inquireDepositAccount(AtmCard atmCard, long depositAccountId) throws InvalidEntityIdException {
        final TypedQuery<DepositAccount> searchQuery = this.em.createQuery("select da from DepositAccount da where da.depositAccountId =?1 and da.atmCard=?2", DepositAccount.class)
                .setParameter(1, depositAccountId)
                .setParameter(2, atmCard);

        try {
            return searchQuery.getSingleResult();
        } catch (NoResultException ignored) {
        }

        throw new InvalidEntityIdException();
    }
}
