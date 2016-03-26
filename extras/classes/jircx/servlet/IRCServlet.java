package jircx.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jibble.pircbot.IrcException;

public class IRCServlet extends HttpServlet {
	private static final String LOGIN_URL = "/";
	private static final String CHAT_URL = "/chat.jspx";
	private static final String LOGIN_ACTION = "login";
	private static final String JOIN_ACTION = "join";
	private static final String SEND_ACTION = "send";
	private static final String CHANGE_ACTION = "change";
	private static final String LOGOFF_ACTION = "logoff";
	private static final String CHANNEL_PARAM = "channel";
	private static final String QUERY_NICK_PARAM = "query";
	private static final String MESSAGE_PARAM = "message";
	private static final String MESSAGE_TYPE_PARAM = "messageType";
	private static final String NICK_PARAM = "nick";
	private static final String USER_PARAM = "user";
	private static final String HOST_PARAM = "host";
	private static final String GATEWAY_ATTR = "gateway";

	public void init() throws ServletException {
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getQueryString();
		if(action == null) {
			response.sendError(response.SC_FORBIDDEN);
		} else if(action.startsWith(LOGIN_ACTION)) {
			doLogin(request, response);
		} else if(action.startsWith(CHANNEL_PARAM) || action.startsWith(QUERY_NICK_PARAM)) {
			// update/change current view
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} else {
			response.sendError(response.SC_FORBIDDEN);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getQueryString();
		if(action == null) {
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} else if(action.equals(LOGIN_ACTION)) {
			doLogin(request, response);
		} else if(action.equals(JOIN_ACTION)) {
			String channel = request.getParameter(CHANNEL_PARAM);
			if(channel != null && channel.length() > 0) {
				HttpSession session = request.getSession();
				Gateway ircbot = (Gateway) session.getAttribute(GATEWAY_ATTR);
				ircbot.joinChannel("#"+channel);
			}
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} else if(action.equals(SEND_ACTION)) {
			String channel = request.getParameter(CHANNEL_PARAM);
			String queryNick = request.getParameter(QUERY_NICK_PARAM);
			String message = request.getParameter(MESSAGE_PARAM);
			String msgType = request.getParameter(MESSAGE_TYPE_PARAM);
			if(message != null) {
				HttpSession session = request.getSession();
				Gateway ircbot = (Gateway) session.getAttribute(GATEWAY_ATTR);
				if(channel != null && channel.length() > 0)
					ircbot.send("#"+channel, msgType, message);
				else if(queryNick != null && queryNick.length() > 0)
					ircbot.send(queryNick, msgType, message);
			}
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} else if(action.equals(CHANGE_ACTION)) {
			String nick = request.getParameter(NICK_PARAM);
			if(nick != null && nick.length() > 0) {
				HttpSession session = request.getSession();
				Gateway ircbot = (Gateway) session.getAttribute(GATEWAY_ATTR);
				ircbot.changeNick(nick);
			}
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} else if(action.equals(LOGOFF_ACTION)) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			request.getRequestDispatcher(response.encodeURL(LOGIN_URL)).forward(request, response);
		} else {
			response.sendError(response.SC_FORBIDDEN);
		}
	}
	protected void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nick = request.getParameter(NICK_PARAM);
		if(nick == null) {
			response.sendError(response.SC_BAD_REQUEST, "No nick specified.");
			return;
		}
		String host = request.getParameter(HOST_PARAM);
		if(host == null) {
			response.sendError(response.SC_BAD_REQUEST, "No host specified.");
			return;
		}
		String user = request.getParameter(USER_PARAM);
		String channel = request.getParameter(CHANNEL_PARAM);
		Gateway ircbot = new Gateway();
		ircbot.setNickName(nick);
		if(user != null)
			ircbot.setUserName(user);
		ircbot.setVerbose(true);
		try {
			ircbot.connect(host);
			if(channel != null && channel.length() > 0)
				ircbot.joinChannel("#"+channel);
			HttpSession session = request.getSession();
			session.setAttribute(GATEWAY_ATTR, ircbot);
			request.getRequestDispatcher(response.encodeURL(CHAT_URL)).forward(request, response);
		} catch(IrcException irce) {
			response.sendError(response.SC_BAD_GATEWAY, irce.getMessage());
		}
	}
	public void destroy() {
	}
}
