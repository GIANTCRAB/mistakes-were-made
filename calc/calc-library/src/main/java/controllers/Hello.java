package controllers;

import javax.ejb.Remote;

@Remote
public interface Hello {
    String hello(String name);
}
