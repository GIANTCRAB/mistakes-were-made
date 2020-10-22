#!/usr/bin/env bash

ROOT_DIR=$(cd `dirname  $0 `  && pwd )
ADMIN=$GLASSFISH_HOME/bin/asadmin
EJB_ARTIFACT=$ROOT_DIR/calc-bean/target/calc-bean.war
APP_NAME=calc-bean

echo $ROOT_DIR

mvn -DskipTests=true clean install

$ADMIN undeploy $APP_NAME || echo "nothing to undeploy..."
$ADMIN deploy $EJB_ARTIFACT

cd $ROOT_DIR/calc-client/
mvn clean verify exec:java

