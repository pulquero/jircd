package net.sf.j_ircd.ext.jms;

import java.io.Serializable;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.TemporaryTopic;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.JMSException;
import javax.jms.IllegalStateException;

public class TopicSessionImpl implements TopicSession {
	private final TopicConnectionImpl connection;

	public TopicSessionImpl(TopicConnectionImpl connection) {
		this.connection = connection;
	}
	public TopicSubscriber createDurableSubscriber(Topic topic, String name) {
		return null;
	}
	public TopicSubscriber createDurableSubscriber(Topic topic, String name, String msgSelector, boolean noLocal) {
		return null;
	}
	public TopicPublisher createPublisher(Topic topic) {
		return new TopicPublisherImpl(connection, topic);
	}
	public TopicSubscriber createSubscriber(Topic topic) {
		return createSubscriber(topic, null, false);
	}
	public TopicSubscriber createSubscriber(Topic topic, String msgSelector, boolean noLocal) {
		return new TopicSubscriberImpl(connection, topic, msgSelector, noLocal);
	}
	public MessageProducer createProducer(Destination destination) {
		return createPublisher((Topic)destination);
	}
	public MessageConsumer createConsumer(Destination destination) {
		return createSubscriber((Topic)destination);
	}
	public MessageConsumer createConsumer(Destination destination, String msgSelector) {
		return createSubscriber((Topic)destination, msgSelector, false);
	}
	public MessageConsumer createConsumer(Destination destination, String msgSelector, boolean noLocal) {
		return createSubscriber((Topic)destination, msgSelector, noLocal);
	}
	public TemporaryTopic createTemporaryTopic() {
		return null;
	}
	public Topic createTopic(String name) {
		return new TopicImpl(name);
	}
	public void unsubscribe(String name) {}

	public BytesMessage createBytesMessage() {
		return new BytesMessageImpl();
	}
	public ObjectMessage createObjectMessage() {
		return new ObjectMessageImpl();
	}
	public ObjectMessage createObjectMessage(Serializable object) {
		return new ObjectMessageImpl(object);
	}
	public StreamMessage createStreamMessage() throws JMSException {
		return new StreamMessageImpl();
	}
	public TextMessage createTextMessage() {
		return new TextMessageImpl();
	}
	public TextMessage createTextMessage(String text) {
		return new TextMessageImpl(text);
	}
	public MapMessage createMapMessage() {
		return null;
	}
	public Message createMessage() {
		return new MessageImpl();
	}
	public QueueBrowser createBrowser(Queue queue) throws IllegalStateException {
		throw new IllegalStateException("Not a QueueSession");
	}
	public QueueBrowser createBrowser(Queue queue, String msgSelector) throws IllegalStateException {
		throw new IllegalStateException("Not a QueueSession");
	}
	public Queue createQueue() throws IllegalStateException {
		throw new IllegalStateException("Not a QueueSession");
	}
	public Queue createQueue(String name) throws IllegalStateException {
		throw new IllegalStateException("Not a QueueSession");
	}
	public TemporaryQueue createTemporaryQueue() throws IllegalStateException {
		throw new IllegalStateException("Not a QueueSession");
	}
	public void commit() {}
	public void recover() {}
	public void rollback() {}
	public void run() {}
	public void close() {}
	public boolean getTransacted() {
		return false;
	}
	public int getAcknowledgeMode() {
		return AUTO_ACKNOWLEDGE;
	}
	public void setMessageListener(MessageListener listener) throws IllegalStateException {
		throw new IllegalStateException("Unsupported");
	}
	public MessageListener getMessageListener() throws IllegalStateException {
		throw new IllegalStateException("Unsupported");
	}
}
