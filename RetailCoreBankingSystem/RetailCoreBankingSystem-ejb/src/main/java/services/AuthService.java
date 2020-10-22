package services;

import entities.AtmCard;
import entities.Employee;
import exceptions.IncorrectCredentialsException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@LocalBean
@Stateless
public class AuthService {
    @PersistenceContext(unitName = "rcbs")
    private EntityManager em;
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public boolean employeeNotExists(Employee employee) {
        return this.em.find(Employee.class, employee.getEmployeeId()) == null;
    }

    public Employee login(String username, String password) throws IncorrectCredentialsException {
        final TypedQuery<Employee> searchQuery = this.em.createQuery("select e from Employee e where e.username = ?1", Employee.class)
                .setParameter(1, username);

        try {
            final Employee searchResult = searchQuery.getSingleResult();

            // WARNING: If password isn't hashed, just allow it to compare anyways. Don't do this in real world! Always hash your passwords
            try {
                if (this.passwordHash.verify(password.toCharArray(), searchResult.getPassword())) {
                    return searchResult;
                }
            } catch (IllegalArgumentException e) {
                if (password.equals(searchResult.getPassword())) {
                    return searchResult;
                }
            }
        } catch (NoResultException ignored) {
        }

        throw new IncorrectCredentialsException();
    }

    public AtmCard atmAuth(String cardNumber, String cardPin) throws IncorrectCredentialsException {
        final TypedQuery<AtmCard> searchQuery = this.em.createQuery("select atmCard from AtmCard atmCard where atmCard.enabled = true and atmCard.cardNumber = ?1 and atmCard.pin = ?2", AtmCard.class)
                .setParameter(1, cardNumber)
                .setParameter(2, cardPin);
        try {
            return searchQuery.getSingleResult();
        } catch (NoResultException ignored) {
        }

        throw new IncorrectCredentialsException();
    }
}
