package net.sf.j_ircd.ext.webirc;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.jibble.pircbot.PircBot;

/**
 * Gateway JavaBean.
 */
public class Gateway extends PircBot implements HttpSessionBindingListener {
	private static final int TRANSCRIPT_SIZE = 100;
	private final List transcript = Collections.synchronizedList(new LinkedList());
	private final Map channelTopics = Collections.synchronizedMap(new HashMap());

	public void setNickName(String name) {
		setName(name);
	}
	public void setUserName(String name) {
		setLogin(name);
	}

	public void valueBound(HttpSessionBindingEvent event) {
	}
	public void valueUnbound(HttpSessionBindingEvent event) {
		quitServer("Session closed");
		dispose();
	}

	private void addTranscriptLine(String msg) {
		synchronized(transcript) {
		transcript.add(0, msg);
		if(transcript.size() > TRANSCRIPT_SIZE)
			transcript.remove(transcript.size()-1);
		}
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		addTranscriptLine(channel+" "+sender+"> "+message);
	}
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		addTranscriptLine(sender+"> "+message);
	}
	protected void onAction(String sender, String login, String hostname, String target, String action) {
		addTranscriptLine(target+" "+sender+" "+action);
	}
	protected void onNotice(String sender, String login, String hostname, String target, String notice) {
		addTranscriptLine(target+" "+sender+": "+notice);
	}
	protected void onJoin(String channel, String sender, String login, String hostname) {
		addTranscriptLine(channel+" "+sender+" has joined");
	}
	protected void onPart(String channel, String sender, String login, String hostname) {
		addTranscriptLine(channel+" "+sender+" has left");
	}
	protected void onQuit(String nick, String login, String hostname, String reason) {
		addTranscriptLine(nick+" has quit ("+reason+")");
	}
	protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
		addTranscriptLine(oldNick+" is now known as "+newNick);
	}
	protected void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		addTranscriptLine(sourceNick+" has invited you to "+channel);
	}
	protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		addTranscriptLine(channel+" "+recipientNick+" has been kicked by "+kickerNick+" ("+reason+")");
	}
	protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
		channelTopics.put(channel, topic);
		addTranscriptLine(channel+" "+setBy+" has set topic "+topic);
	}
	protected void onMode(String channel, String nick, String login, String hostname, String mode) {
		addTranscriptLine(nick+" has set mode "+channel+" "+mode);
	}
	protected void onUserMode(String target, String nick, String login, String hostname, String mode) {
		addTranscriptLine(nick+" has set mode "+target+" "+mode);
	}
	protected void onUnknown(String line) {
		addTranscriptLine("UNKNOWN: "+line);
	}
	public void send(String target, String msgType, String message) {
		if(message.charAt(0) == '/') {
			int endPos = message.indexOf(' ', 1);
			if(endPos != -1) {
				msgType = message.substring(1, endPos);
				message = message.substring(endPos+1);
			}
		}
		if("action".equalsIgnoreCase(msgType) || "me".equalsIgnoreCase(msgType)) {
			sendAction(target, message);
			onAction(getNick(), getLogin(), getInetAddress().getHostName(), target, message);
		} else if("notice".equalsIgnoreCase(msgType)) {
			sendNotice(target, message);
			onNotice(getNick(), getLogin(), getInetAddress().getHostName(), target, message);
		} else if("raw".equalsIgnoreCase(msgType)) {
			sendRawLineViaQueue(message);
			addTranscriptLine("RAW: "+message);
		} else {
			sendMessage(target, message);
			onMessage(target, getNick(), getLogin(), getInetAddress().getHostName(), message);
		}
	}
	public Map getUsers() {
		String[] channels = getChannels();
        Map channelUsers = new HashMap(channels.length);
		for(int i=0; i<channels.length; i++) {
			channelUsers.put(channels[i], getUsers(channels[i]));
		}
		return Collections.unmodifiableMap(channelUsers);
	}
	public Map getTopics() {
		return Collections.unmodifiableMap(channelTopics);
	}
	public List getTranscript() {
        return Collections.unmodifiableList(transcript);
	}
}
