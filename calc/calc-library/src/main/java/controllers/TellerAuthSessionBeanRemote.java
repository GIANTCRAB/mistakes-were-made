package controllers;

import entities.Employee;
import exceptions.IncorrectCredentialsException;

import javax.ejb.Remote;

@Remote
public interface TellerAuthSessionBeanRemote {
    Employee login(String username, String password) throws IncorrectCredentialsException;
}
