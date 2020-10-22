package controllers;

import entities.AtmCard;
import entities.DepositAccount;
import exceptions.InvalidConstraintException;
import exceptions.InvalidEntityIdException;
import exceptions.NotAuthenticatedException;

import javax.ejb.Remote;

@Remote
public interface AtmSessionBeanRemote {
    AtmCard changePin(AtmCard atmCard, String newPin) throws NotAuthenticatedException, InvalidConstraintException;

    DepositAccount inquireDepositAccount(AtmCard atmCard, long depositAccountId) throws NotAuthenticatedException, InvalidEntityIdException;
}
