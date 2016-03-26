@echo off
rem This script is intended for Windows systems,
rem but is easy to adapt to other operating systems.

set JMX_OPTS=-Dcom.sun.management.jmxremote
set LOG_OPTS=-Dlog4j.configuration=file:log4j.xml
set NBPROFILER_LIB=c:\progra~1\netbeans-5.0\profiler1\lib
set DEBUG_OPTS=-agentpath:%NBPROFILER_LIB%\deployed\jdk15\windows\profilerinterface.dll=%NBPROFILER_LIB%,5140
set DEBUG_OPTS=
rem set JAAS_OPTS=-Djava.security.auth.login.config=jaas.config
rem set SSL_OPTS=-Djavax.net.ssl.keyStore=jircd.jks -Djavax.net.ssl.keyStorePassword=passphrase
rem set SECURITY_OPTS=-Djava.security.manager -Djava.security.policy=jircd.policy
set JIRCD_CLASSPATH=log4j.jar;jircd-server.jar;GeoIP.jar;AgentMBean.jar;.
java %JMX_OPTS% %LOG_OPTS% %DEBUG_OPTS% %SSL_OPTS% -cp %JIRCD_CLASSPATH% net.sf.mbeans.agent.AgentMBean net.sf.mbeans.TaskScheduler file:agent.start file:agent.stop
