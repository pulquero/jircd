package jircx.jms;

import java.util.Hashtable;
import java.util.Random;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.RefAddr;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import javax.jms.Connection;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

public class TopicConnectionFactoryImpl implements TopicConnectionFactory, Referenceable {
	private static final int DEFAULT_PORT = 6667;
	private static final Random random = new Random();
	private static final String REF_ADDR = "url";
	private final String url;

	public TopicConnectionFactoryImpl(String url) throws IllegalArgumentException {
		if(!url.startsWith("irc://"))
			throw new IllegalArgumentException("URL must begin irc://");
		this.url = url;
	}
	public Connection createConnection() {
		return createTopicConnection();
	}
	public Connection createConnection(String userName, String password) {
		return createTopicConnection(userName, password);
	}
	public TopicConnection createTopicConnection() {
		return createTopicConnection("JMSUser"+Math.abs(random.nextInt()), null);
	}
	public TopicConnection createTopicConnection(String userName, String password) {
		int startPos = 6; // "irc://"
		int portPos = url.indexOf(':', startPos);
		int pathPos = url.indexOf('/', startPos);
		if(pathPos == -1)
			pathPos = url.length();
		String host;
		int port;
		if(portPos != -1) {
			host = url.substring(startPos, portPos);
			port = Integer.parseInt(url.substring(portPos+1, pathPos));
		} else {
			host = url.substring(startPos, pathPos);
			port = DEFAULT_PORT;
		}
		return new TopicConnectionImpl(host, port, userName, password);
	}
	public Reference getReference() {
		return new Reference(getClass().getName(), new StringRefAddr(REF_ADDR, url), Factory.class.getName(), null);
	}

	public static class Factory implements ObjectFactory {
		public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws Exception {
			if(obj instanceof Reference) {
				Reference ref = (Reference) obj;
				if(ref.getClassName().equals(TopicConnectionFactoryImpl.class.getName())) {
					RefAddr addr = ref.get(REF_ADDR);
					if(addr != null)
						return new TopicConnectionFactoryImpl((String)addr.getContent());
				}
			}
			return null;
		}
	}
}
