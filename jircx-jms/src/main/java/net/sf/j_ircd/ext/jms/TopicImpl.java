package net.sf.j_ircd.ext.jms;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.RefAddr;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import javax.jms.Topic;

public class TopicImpl implements Topic, Referenceable {
	private static final String REF_ADDR = "channel";
	private final String channel;

	public TopicImpl(String channel) {
		this.channel = channel;
	}
	public String getTopicName() {
		return channel;
	}
	public String toString() {
		return channel;
	}
	public Reference getReference() {
		return new Reference(getClass().getName(), new StringRefAddr(REF_ADDR, channel), Factory.class.getName(), null);
	}

	public static class Factory implements ObjectFactory {
		public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws Exception {
			if(obj instanceof Reference) {
				Reference ref = (Reference) obj;
				if(ref.getClassName().equals(TopicImpl.class.getName())) {
					RefAddr addr = ref.get(REF_ADDR);
					if(addr != null)
						return new TopicImpl((String)addr.getContent());
				}
			}
			return null;
		}
	}
}
