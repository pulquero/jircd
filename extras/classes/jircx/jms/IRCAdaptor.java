package jircx.jms;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * A JMS adaptor for IRC servers.
 * @author markhale
 */
public class IRCAdaptor implements IRCAdaptorMBean {
	public static void main(String[] args) throws NamingException {
		IRCAdaptor adaptor = new IRCAdaptor();
		if(args.length >= 2) {
			adaptor.bindTopicConnectionFactory(args[0], args[1]);
			if(args.length >= 4)
				adaptor.bindTopic(args[2], args[3]);
		}
	}

	public IRCAdaptor() {}
	public IRCAdaptor(String bindName, String url) throws NamingException {
		bindTopicConnectionFactory(bindName, url);
	}
	/**
	 * Binds a new TopicConnectionFactory.
	 */
	public void bindTopicConnectionFactory(String bindName, String url) throws NamingException {
		Context ctx = new InitialContext();
		ctx.bind(bindName, new TopicConnectionFactoryImpl(url));
		ctx.close();
	}
	/**
	 * Binds a new Topic.
	 */
	public void bindTopic(String bindName, String topic) throws NamingException {
		Context ctx = new InitialContext();
		ctx.bind(bindName, new TopicImpl(topic));
		ctx.close();
	}
	/**
	 * Unbinds.
	 */
	public void unbind(String bindName) throws NamingException {
		Context ctx = new InitialContext();
		ctx.unbind(bindName);
		ctx.close();
	}
}
