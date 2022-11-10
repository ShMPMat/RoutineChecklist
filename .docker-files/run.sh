#!/bin/bash

sed -i s/\$RC_PG_PASSWORD/$1/g /usr/local/tomcat/conf/server.xml
catalina.sh run