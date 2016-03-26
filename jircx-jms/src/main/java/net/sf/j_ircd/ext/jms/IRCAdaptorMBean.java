package net.sf.j_ircd.ext.jms;

import javax.naming.NamingException;

public interface IRCAdaptorMBean {
	void bindTopicConnectionFactory(String bindName, String url) throws NamingException;
	void bindTopic(String bindName, String topic) throws NamingException;
	void unbind(String bindName) throws NamingException;
}
