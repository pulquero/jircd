package net.sf.j_ircd.server.servlet;

import net.sf.j_ircd.server.irc.ConnectedEntity;
import net.sf.j_ircd.server.irc.Message;
import net.sf.j_ircd.server.irc.User;
import net.sf.j_ircd.server.irc_p10.Server_P10;
import net.sf.j_ircd.server.irc_p10.User_P10;
import net.sf.j_ircd.server.irc_p10.Util;
import net.sf.j_ircd.server.servlet.irc.IrcServlet;

/**
 * A servlet on a server.
 * @author markhale
 */
public class Servlet extends User_P10 {
	private final IrcServlet servlet;

	public Servlet(String nick, String name, IrcServlet servlet, Server_P10 server) {
		super(nick, 0, Util.randomUserToken(), "Servlet", "jIRCd", name, server);
		this.servlet = servlet;
	}
	protected String maskHost(String hostname) {
		return hostname;
	}
	public void send(Message message) {
		ConnectedEntity sender = message.getSender();
		if(sender instanceof User) {
			User user = (User) sender;
			String cmd = message.getCommand();
			String text = message.getParameter(message.getParameterCount()-1);
			IrcServletRequestImpl request = new IrcServletRequestImpl(user, cmd, text);
			IrcServletResponseImpl response = new IrcServletResponseImpl(this, user, cmd);
			try {
				servlet.service(request, response);
				if(!response.isCommitted())
					response.commit();
			} catch(Exception e) {
				servlet.log("An exception occured while trying to service a request", e);
			}
		}
	}
}
