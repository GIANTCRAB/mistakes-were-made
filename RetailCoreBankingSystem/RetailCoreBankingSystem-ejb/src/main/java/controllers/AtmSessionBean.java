package controllers;

import entities.AtmCard;
import entities.DepositAccount;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;
import exceptions.NotAuthenticatedException;
import services.AtmService;
import services.AuthService;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class AtmSessionBean implements AtmSessionBeanLocal, AtmSessionBeanRemote {
    @Inject
    AuthService authService;
    @Inject
    AtmService atmService;

    @Override
    public AtmCard changePin(AtmCard atmCard, String newPin) throws NotAuthenticatedException, InvalidConstraintException {
        final AtmCard managedAtmCard = this.authService.retrieveManagedAtmCard(atmCard);

        return this.atmService.changePin(managedAtmCard, newPin);
    }

    @Override
    public DepositAccount inquireDepositAccount(AtmCard atmCard, long depositAccountId) throws NotAuthenticatedException, InvalidEntityIdException {
        final AtmCard managedAtmCard = this.authService.retrieveManagedAtmCard(atmCard);

        return this.atmService.inquireDepositAccount(managedAtmCard, depositAccountId);
    }
}
