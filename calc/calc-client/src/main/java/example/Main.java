package example;

import controllers.TellerAuthSessionBeanRemote;
import controllers.TellerSessionBeanRemote;

import javax.naming.InitialContext;

public class Main {
    public static void main(String a[]) throws Exception {
        InitialContext ic = new InitialContext();
        TellerAuthSessionBeanRemote tellerAuthSessionBeanRemote = (TellerAuthSessionBeanRemote) ic.lookup(TellerAuthSessionBeanRemote.class.getName());
        TellerSessionBeanRemote tellerSessionBeanRemote = (TellerSessionBeanRemote) ic.lookup(TellerSessionBeanRemote.class.getName());
        TerminalClient terminalClient = new TerminalClient(System.in, System.out, tellerAuthSessionBeanRemote, tellerSessionBeanRemote);

        terminalClient.runApp();
    }
}
