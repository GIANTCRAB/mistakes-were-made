package controllers;

import entities.AtmCard;
import exceptions.IncorrectCredentialsException;
import services.AuthService;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class AtmAuthSessionBean implements AtmAuthSessionBeanLocal, AtmAuthSessionBeanRemote {
    @Inject
    AuthService authService;

    @Override
    public AtmCard insertAtmCard(String cardNumber, String cardPin) throws IncorrectCredentialsException {
        return this.authService.atmAuth(cardNumber, cardPin);
    }
}
