#!/usr/bin/env bash


GLASSFISH_HOME="/home/woohuiren/Programming/glassfish5/"

ROOT_DIR=$(cd `dirname  $0 `  && pwd )
ADMIN=$GLASSFISH_HOME/bin/asadmin
EJB_ARTIFACT=$ROOT_DIR/RetailCoreBankingSystem-ejb/target/RetailCoreBankingSystem-ejb.war
APP_NAME=RetailCoreBankingSystem-ejb

echo $ROOT_DIR

mvn -DskipTests=true clean install

$ADMIN undeploy $APP_NAME || echo "nothing to undeploy..."
$ADMIN deploy $EJB_ARTIFACT

cd $ROOT_DIR/TellerTerminalClient/
mvn clean verify exec:java

