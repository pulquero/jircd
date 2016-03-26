package jircx.log4j;

import java.io.IOException;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.IrcException;

public class IRCAppender extends AppenderSkeleton {
      private final IRCBot ircbot = new IRCBot();
      private String host = null;
	private int port = 6667;
      private String nick = "IRCAppender";
      private String channel = "#log";
	private long messageDelay = ircbot.getMessageDelay();

	public IRCAppender() {}
	public void activateOptions() {
		if(host == null) {
			LogLog.error("Host option not set for appender ["+name+"].");
			return;
		}
                try {
                        ircbot.setNick(nick);
                        ircbot.setMessageDelay(messageDelay);
                        ircbot.connect(host, port);
                        ircbot.joinChannel(channel);
                        super.activateOptions();
                } catch(IOException ioe) {
                        LogLog.error("Could not activate options for appender ["+name+"]", ioe);
                } catch(IrcException irce) {
                        LogLog.error("Could not activate options for appender ["+name+"]", irce);
                }
        }
        protected void append(LoggingEvent event) {
		if(closed) {
			LogLog.warn("Not allowed to write to a closed appender.");
			return;
		}
		if(layout == null) {
			errorHandler.error("No layout set for the appender named ["+name+"].");
			return;
		}
                ircbot.sendNotice(channel, layout.format(event));
                if(layout.ignoresThrowable()) {
                        String[] s = event.getThrowableStrRep();
                        if(s != null) {
                                int len = s.length;
                                for(int i=0; i<len; i++) {
                                        ircbot.sendNotice(channel, s[i]);
                                        ircbot.sendNotice(channel, Layout.LINE_SEP);
                                }
                        }
                }
        }
        public boolean requiresLayout() {
                return true;
        }
        public void close() {
                if(closed)
                        return;
                closed = true;
                ircbot.disconnect();
                ircbot.dispose();
        }

	/**
	 * IRC server hostname (<strong>Host</strong>).
	 * Required.
	 */
        public void setHost(String host) {
                this.host = host;
        }
        public String getHost() {
                return host;
        }
	/**
	 * IRC server port (<strong>Port</strong>).
	 */
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	/**
	 * Required (<strong>Nick</strong>).
	 */
        public void setNick(String nick) {
                this.nick = nick;
        }
        public String getNick() {
                return nick;
        }
	/**
	 * Required (<strong>Channel</strong>).
	 */
        public void setChannel(String channel) {
                this.channel = channel;
        }
        public String getChannel() {
                return channel;
        }
	/**
	 * Sets message delay (<strong>MessageDelay</strong>).
	 */
        public void setMessageDelay(long delay) {
                this.messageDelay = messageDelay;
        }
        public long getMessageDelay() {
                return messageDelay;
        }

        protected class IRCBot extends PircBot {
                public IRCBot() {
                        setLogin("IRCAppender");
                        setVersion("IRCAppender");
                }
                protected void setNick(String nick) {
                        setName(nick);
                }
        }
}
