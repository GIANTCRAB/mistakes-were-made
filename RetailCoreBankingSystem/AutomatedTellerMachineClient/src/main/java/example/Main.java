package example;

import controllers.AtmAuthSessionBeanRemote;
import controllers.AtmSessionBeanRemote;

import javax.naming.InitialContext;

public class Main {
    public static void main(String a[]) throws Exception {
        InitialContext ic = new InitialContext();
        AtmAuthSessionBeanRemote atmAuthSessionBeanRemote = (AtmAuthSessionBeanRemote) ic.lookup(AtmAuthSessionBeanRemote.class.getName());
        AtmSessionBeanRemote atmSessionBeanRemote = (AtmSessionBeanRemote) ic.lookup(AtmSessionBeanRemote.class.getName());
        AtmClient atmClient = new AtmClient(System.in, System.out, atmAuthSessionBeanRemote, atmSessionBeanRemote);

        atmClient.runApp();
    }
}
