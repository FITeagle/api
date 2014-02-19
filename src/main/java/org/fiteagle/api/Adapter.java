package org.fiteagle.api;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public abstract class Adapter {
	public static final String STATUS_STARTED = "started";
	public static final String STATUS_STOPPED = "stopped";
	public static final String PROPERTY_STATUS = "status";
	public static final String PROPERTY_TYPE = "type";
	
	public static String toDebugString(TextMessage message) throws JMSException {
		String text = message.getText();
		String propStatus = message.getStringProperty(Adapter.PROPERTY_STATUS);
		return "Text: " + text + "; Propery 'status': " + propStatus;
	}
}
