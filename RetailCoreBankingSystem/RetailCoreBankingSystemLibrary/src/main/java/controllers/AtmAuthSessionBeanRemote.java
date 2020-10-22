package controllers;

import entities.AtmCard;
import exceptions.IncorrectCredentialsException;

import javax.ejb.Remote;

@Remote
public interface AtmAuthSessionBeanRemote {
    AtmCard insertAtmCard(String cardNumber, String cardPin) throws IncorrectCredentialsException;
}
