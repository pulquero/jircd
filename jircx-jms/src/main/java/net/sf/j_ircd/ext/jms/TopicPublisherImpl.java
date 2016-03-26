package net.sf.j_ircd.ext.jms;

import javax.jms.Destination;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.JMSException;

public class TopicPublisherImpl implements TopicPublisher {
	private final TopicConnectionImpl connection;
	private final Topic topic;
	private int priority;
	private boolean msgIdDisabled;
	private boolean msgTimestampDisabled;
	private int deliveryMode = DeliveryMode.PERSISTENT;
	private long ttl;

	public TopicPublisherImpl(TopicConnectionImpl connection, Topic topic) {
		this.connection = connection;
		this.topic = topic;
		if(topic != null)
			connection.joinChannel(topic.toString());
	}
	public Destination getDestination() {
		return getTopic();
	}
	public Topic getTopic() {
		return topic;
	}
	public void send(Message message) throws JMSException {
		publish(message);
	}
	public void send(Message message, int deliveryMode, int priority, long ttl) throws JMSException {
		publish(message, deliveryMode, priority, ttl);
	}
	public void send(Destination destination, Message message) throws JMSException {
		publish((Topic) destination, message);
	}
	public void send(Destination destination, Message message, int deliveryMode, int priority, long ttl) throws JMSException {
		publish((Topic) destination, message, deliveryMode, priority, ttl);
	}
	public void publish(Message message) throws JMSException {
		publish(message, deliveryMode, priority, ttl);
	}
	public void publish(Message message, int deliveryMode, int priority, long ttl) throws JMSException {
		if(topic == null)
			throw new UnsupportedOperationException("Unidentified");
		if(!msgTimestampDisabled)
			message.setJMSTimestamp(System.currentTimeMillis());
		message.setJMSDestination(topic);
		connection.sendMessage(topic.toString(), message.toString());
	}
	public void publish(Topic topic, Message message) throws JMSException {
		publish(topic, message, deliveryMode, priority, ttl);
	}
	public void publish(Topic topic, Message message, int deliveryMode, int priority, long ttl) throws JMSException {
		if(topic != null)
			throw new UnsupportedOperationException("Identified");
		connection.joinChannel(topic.toString());
		if(!msgTimestampDisabled)
			message.setJMSTimestamp(System.currentTimeMillis());
		message.setJMSDestination(topic);
		connection.sendMessage(topic.toString(), message.toString());
	}
	public boolean getDisableMessageID() {
		return msgIdDisabled;
	}
	public void setDisableMessageID(boolean disabled) {
		msgIdDisabled = disabled;
	}
	public boolean getDisableMessageTimestamp() {
		return msgTimestampDisabled;
	}
	public void setDisableMessageTimestamp(boolean disabled) {
		msgTimestampDisabled = disabled;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public long getTimeToLive() {
		return ttl;
	}
	public void setTimeToLive(long ttl) {
		this.ttl = ttl;
	}
	public void close() {
		try {
			Thread.sleep((connection.getOutgoingQueueSize()+1)*connection.getMessageDelay());
		} catch(InterruptedException ie) {}
		if(topic != null)
			connection.partChannel(topic.toString());
	}
}
