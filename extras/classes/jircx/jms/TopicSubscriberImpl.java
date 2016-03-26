package jircx.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class TopicSubscriberImpl implements TopicSubscriber {
	private final TopicConnectionImpl connection;
	private final Topic topic;
	private final String selector;
	private final boolean noLocal;
	private MessageListener listener;
	private Message receivedMessage;

	public TopicSubscriberImpl(TopicConnectionImpl connection, Topic topic, String selector, boolean noLocal) {
		this.connection = connection;
		this.topic = topic;
		this.selector = selector;
		this.noLocal = noLocal;
		connection.addSubscriber(this);
		connection.joinChannel(topic.toString());
	}
	synchronized void setReceivedMessage(Message message) {
		receivedMessage = message;
		notify();
	}
	public Topic getTopic() {
		return topic;
	}
	public String getMessageSelector() {
		return selector;
	}
	public boolean getNoLocal() {
		return noLocal;
	}
	public MessageListener getMessageListener() {
		return listener;
	}
	public void setMessageListener(MessageListener listener) {
		this.listener = listener;
	}
	public synchronized Message receive() {
		return receive(0);
	}
	public synchronized Message receive(long timeout) {
		try {
			wait(timeout);
		} catch(InterruptedException ie) {}
		return receiveNoWait();
	}
	public synchronized Message receiveNoWait() {
		Message msg = receivedMessage;
		receivedMessage = null;
		return msg;
	}
	public void close() {
		connection.removeSubscriber(this);
		connection.partChannel(topic.toString());
	}
}
