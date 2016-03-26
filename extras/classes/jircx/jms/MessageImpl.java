package jircx.jms;

import java.util.Enumeration;
import java.util.Properties;
import javax.jms.Destination;
import javax.jms.Message;

public class MessageImpl implements Message {
	private Destination replyTo;
	private Destination destination;
	private String id;
	private long timestamp;
	private String correlationID;
	private String type;
	private int deliveryMode;
	private long expiration;
	private int priority;
	private boolean redelivered;
	private final Properties properties = new Properties();

	public Destination getJMSReplyTo() {
		return replyTo;
	}
	public void setJMSReplyTo(Destination replyTo) {
		this.replyTo = replyTo;
	}
	public Destination getJMSDestination() {
		return destination;
	}
	public void setJMSDestination(Destination destination) {
		this.destination = destination;
	}
	public String getJMSMessageID() {
		return id;
	}
	public void setJMSMessageID(String msgID) {
		id = msgID;
	}
	public long getJMSTimestamp() {
		return timestamp;
	}
	public void setJMSTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getJMSCorrelationID() {
		return correlationID;
	}
	public void setJMSCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public byte[] getJMSCorrelationIDAsBytes() {
		throw new UnsupportedOperationException();
	}
	public void setJMSCorrelationIDAsBytes(byte[] correlationID) {
		throw new UnsupportedOperationException();
	}
	public String getJMSType() {
		return type;
	}
	public void setJMSType(String type) {
		this.type = type;
	}
	public int getJMSDeliveryMode() {
		return deliveryMode;
	}
	public void setJMSDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public long getJMSExpiration() {
		return expiration;
	}
	public void setJMSExpiration(long expiration) {
		this.expiration = expiration;
	}
	public int getJMSPriority() {
		return priority;
	}
	public void setJMSPriority(int priority) {
		this.priority = priority;
	}
	public boolean getJMSRedelivered() {
		return redelivered;
	}
	public void setJMSRedelivered(boolean redelivered) {
		this.redelivered = redelivered;
	}

	public Enumeration getPropertyNames() {
		return properties.propertyNames();
	}
	public boolean propertyExists(String name) {
		return false;
	}
	public void clearProperties() {
		properties.clear();
	}

	public boolean getBooleanProperty(String name) {
		return Boolean.valueOf(properties.getProperty(name)).booleanValue();
	}
	public void setBooleanProperty(String name, boolean value) {
		properties.put(name, Boolean.toString(value));
	}
	public byte getByteProperty(String name) {
		return Byte.valueOf(properties.getProperty(name)).byteValue();
	}
	public void setByteProperty(String name, byte value) {
		properties.put(name, Byte.toString(value));
	}
	public short getShortProperty(String name) {
		return Short.valueOf(properties.getProperty(name)).shortValue();
	}
	public void setShortProperty(String name, short value) {
		properties.put(name, Short.toString(value));
	}
	public int getIntProperty(String name) {
		return Integer.valueOf(properties.getProperty(name)).intValue();
	}
	public void setIntProperty(String name, int value) {
		properties.put(name, Integer.toString(value));
	}
	public long getLongProperty(String name) {
		return Long.valueOf(properties.getProperty(name)).longValue();
	}
	public void setLongProperty(String name, long value) {
		properties.put(name, Long.toString(value));
	}
	public float getFloatProperty(String name) {
		return Float.valueOf(properties.getProperty(name)).floatValue();
	}
	public void setFloatProperty(String name, float value) {
		properties.put(name, Float.toString(value));
	}
	public double getDoubleProperty(String name) {
		return Double.valueOf(properties.getProperty(name)).doubleValue();
	}
	public void setDoubleProperty(String name, double value) {
		properties.put(name, Double.toString(value));
	}
	public String getStringProperty(String name) {
		return properties.getProperty(name);
	}
	public void setStringProperty(String name, String value) {
		properties.put(name, value);
	}
	public Object getObjectProperty(String name) {
		return properties.getProperty(name);
	}
	public void setObjectProperty(String name, Object value) {
		properties.put(name, value);
	}
	public void acknowledge() {}
	public void clearBody() {}
}
