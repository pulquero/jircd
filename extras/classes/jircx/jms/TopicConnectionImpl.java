package jircx.jms;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.JMSException;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.IrcException;

public class TopicConnectionImpl extends PircBot implements TopicConnection {
	private final String host;
	private final int port;
	private final String password;
	private String clientID;
	private ExceptionListener listener;
	private final Set subscribers = Collections.synchronizedSet(new HashSet());

	public TopicConnectionImpl(String host, int port, String userName, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
		setName(userName);
		setLogin("JMS");
		setFinger("JMS Adapter");
	}

	void addSubscriber(TopicSubscriber subscriber) {
		subscribers.add(subscriber);
	}
	void removeSubscriber(TopicSubscriber subscriber) {
		subscribers.remove(subscriber);
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String text) {
		for(Iterator iter = subscribers.iterator(); iter.hasNext(); ) {
			TopicSubscriberImpl subscriber = (TopicSubscriberImpl) iter.next();
			TextMessageImpl message = new TextMessageImpl(text);
			message.setJMSDestination(new TopicImpl(channel));
			subscriber.setReceivedMessage(message);
			subscriber.getMessageListener().onMessage(message);
		}
	}

	public Session createSession(boolean transacted, int acknowledgeMode) {
		return createTopicSession(transacted, acknowledgeMode);
	}
	public TopicSession createTopicSession(boolean transacted, int acknowledgeMode) {
		return new TopicSessionImpl(this);
	}
	public ConnectionConsumer createConnectionConsumer(Destination destination, String msgSelector, ServerSessionPool pool, int maxMsgs) {
		throw new UnsupportedOperationException("Unsupported");
	}
	public ConnectionConsumer createConnectionConsumer(Topic topic, String msgSelector, ServerSessionPool pool, int maxMsgs) {
		throw new UnsupportedOperationException("Unsupported");
	}
	public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String name, String msgSelector, ServerSessionPool pool, int maxMsgs) {
		throw new UnsupportedOperationException("Unsupported");
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public ExceptionListener getExceptionListener() {
		return listener;
	}
	public void setExceptionListener(ExceptionListener listener) {
		this.listener = listener;
	}
	public ConnectionMetaData getMetaData() {
		return new ConnectionMetaDataImpl();
	}
	public void start() throws JMSException {
		try {
			connect(host, port, password);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		} catch(IrcException irce) {
			throw new JMSException(irce.getMessage());
		}
	}
	public void stop() {}
	public void close() {
		quitServer("JMS IRC adaptor");
	}
}
