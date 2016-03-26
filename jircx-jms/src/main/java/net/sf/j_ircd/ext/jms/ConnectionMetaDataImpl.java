package net.sf.j_ircd.ext.jms;

import java.util.Enumeration;
import java.util.Vector;
import javax.jms.ConnectionMetaData;

public class ConnectionMetaDataImpl implements ConnectionMetaData {
	public int getJMSMajorVersion() {
		return 1;
	}
	public int getJMSMinorVersion() {
		return 1;
	}
	public String getJMSProviderName() {
		return "jIRCd";
	}
	public String getJMSVersion() {
		return "1.1";
	}
	public Enumeration getJMSXPropertyNames() {
		return new Vector().elements();
	}
	public int getProviderMajorVersion() {
		return 1;
	}
	public int getProviderMinorVersion() {
		return 0;
	}
	public String getProviderVersion() {
		return "1.0";
	}
}
