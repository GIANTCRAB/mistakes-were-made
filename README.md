# National University of Singapore IS2103

Java EE application using (Glassfish + MySQL).

## Instructions

 0. Make sure you have Glassfish and MySQL server setup. (See `jta-data-source` in `persistence.xml`)
 1. Change directory to RetailCoreBankingSystem using `cd RetailCoreBankingSystem`
 2. Set `GLASSFISH_HOME` in `deploy.sh` to your Glassfish home directory.
 3. Run `deploy.sh` file to build and deploy the EJB module.

### Run TellerTerminalClient

Command: `./run-tellerterminal.sh`

### Run AutomatedTellerMachineClient

Command: `./run-atm.sh`