package controllers;

import entities.AtmCard;
import exceptions.InvalidConstraintException;
import exceptions.NotAuthenticatedException;

import javax.ejb.Remote;

@Remote
public interface AtmSessionBeanRemote {
    AtmCard changePin(AtmCard atmCard, String newPin) throws NotAuthenticatedException, InvalidConstraintException;
}
