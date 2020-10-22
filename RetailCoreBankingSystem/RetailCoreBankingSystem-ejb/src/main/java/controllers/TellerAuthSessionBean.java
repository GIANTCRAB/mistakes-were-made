package controllers;

import entities.Employee;
import exceptions.IncorrectCredentialsException;
import services.AuthService;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class TellerAuthSessionBean implements TellerAuthSessionBeanLocal, TellerAuthSessionBeanRemote {
    @Inject
    AuthService authService;

    @Override
    public Employee login(String username, String password) throws IncorrectCredentialsException {
        return this.authService.login(username, password);
    }
}
