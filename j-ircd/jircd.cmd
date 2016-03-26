@echo off
rem This script is intended for Windows systems,
rem but is easy to adapt to other operating systems.

set LOG_OPTS=-Dlog4j.configuration=file:log4j.xml
rem set JAAS_OPTS=-Djava.security.auth.login.config=jaas.config
rem set SSL_OPTS=-Djavax.net.ssl.keyStore=jircd.jks -Djavax.net.ssl.keyStorePassword=passphrase
rem set SECURITY_OPTS=-Djava.security.manager -Djava.security.policy=jircd.policy
set JIRCD_CLASSPATH=log4j.jar;jircd.jar;backport-util-concurrent.jar;GeoIP.jar;.
java %LOG_OPTS% %JAAS_OPTS% %SECURITY_OPTS% %SSL_OPTS% -cp %JIRCD_CLASSPATH% jircd.jIRCd file:jircd.properties
