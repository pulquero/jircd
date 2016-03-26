package net.sf.j_ircd.server.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;

import net.sf.j_ircd.server.irc.Connection;
import net.sf.j_ircd.server.irc.Message;
import net.sf.j_ircd.server.irc.User;
import net.sf.j_ircd.server.servlet.irc.IrcServletRequest;

/**
 * Implementation.
 */
public class IrcServletRequestImpl extends ServletRequestImpl implements IrcServletRequest {
	private final User user;
	private final String command;
	private final String message;

	public IrcServletRequestImpl(User user, String command, String message) {
		this.user = user;
		this.command = command;
		this.message = message;
		locales.add(user.getLocale());
	}
	public String getNick() {
		return user.getNick();
	}
	public String getCommand() {
		return command;
	}
	public String getText() {
		return message;
	}
	/**
	 * Returns -1 if unknown.
	 */
	public int getLocalPort() {
                if(user.isLocal())
			return user.getHandler().getConnection().getLocalPort();
		else
			return -1;
	}
	public String getLocalAddr() {
                if(user.isLocal())
			return user.getHandler().getConnection().getLocalAddress();
		else
			return null;
	}
	public String getLocalName() {
                if(user.isLocal())
			return user.getHandler().getConnection().getLocalHost();
		else
			return null;
	}
	/**
	 * Returns -1 if unknown.
	 */
	public int getRemotePort() {
                if(user.isLocal())
			return user.getHandler().getConnection().getRemotePort();
		else
			return -1;
	}
	public String getRemoteAddr() {
                if(user.isLocal())
			return user.getHandler().getConnection().getRemoteAddress();
		else
			return null;
	}
	public String getRemoteHost() {
                if(user.isLocal())
			return user.getHandler().getConnection().getRemoteHost();
		else
			return null;
	}
	public boolean isSecure() {
                if(user.isLocal())
			return user.getHandler().getConnection().isSecure();
		else
			return false;
	}
	public ServletInputStream getInputStream() {
		return new FilterServletInputStream(new ByteArrayInputStream(message.getBytes()));
	}
	public BufferedReader getReader() {
		return new BufferedReader(new StringReader(message));
	}
	public int getContentLength() {
		return message.length();
	}
	public String getContentType() {
		return null;
	}
	public String getProtocol() {
		return "IRC";
	}
	public String getScheme() {
		return "irc";
	}
	public String getServerName() {
		return getLocalName();
	}
	public int getServerPort() {
		return getLocalPort();
	}
	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

        /** @deprecated */
	public String getRealPath(String path) {
		return null;
	}
}
