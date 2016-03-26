package jircx.jms;

import javax.jms.TextMessage;

public class TextMessageImpl extends MessageImpl implements TextMessage {
	private String text;

	public TextMessageImpl() {}
	public TextMessageImpl(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String toString() {
		return getText();
	}
	public void clearBody() {
		text = null;
	}
}
