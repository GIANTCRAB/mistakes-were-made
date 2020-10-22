package services;

import entities.Employee;
import exceptions.IncorrectCredentialsException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@LocalBean
@Stateless
public class AuthService {
    @PersistenceContext(unitName = "rcbs")
    private EntityManager em;
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public boolean checkEmployeeExists(Employee employee) {
        return this.em.find(Employee.class, employee) != null;
    }

    public Employee login(String username, String password) throws IncorrectCredentialsException {
        final Employee usernameFind = new Employee();
        usernameFind.setUsername(username);

        final Employee searchResult = this.em.find(Employee.class, usernameFind);

        if (searchResult != null) {
            if (this.passwordHash.verify(password.toCharArray(), searchResult.getPassword())) {
                return searchResult;
            }
        }

        throw new IncorrectCredentialsException();
    }
}
