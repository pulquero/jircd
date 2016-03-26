package jircx.jms;

import java.io.Serializable;
import javax.jms.ObjectMessage;

public class ObjectMessageImpl extends MessageImpl implements ObjectMessage {
	private Serializable object;

	public ObjectMessageImpl() {}
	public ObjectMessageImpl(Serializable object) {
		this.object = object;
	}
	public Serializable getObject() {
		return object;
	}
	public void setObject(Serializable object) {
		this.object = object;
	}
	public String toString() {
		if(object != null)
			return object.toString();
		else
			return "null";
	}
	public void clearBody() {
		object = null;
	}
}
