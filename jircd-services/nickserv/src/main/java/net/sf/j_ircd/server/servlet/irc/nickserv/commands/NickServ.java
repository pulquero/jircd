/*
 * jIRCd - Java Internet Relay Chat Daemon
 * Copyright 2003 Tyrel L. Haveman <tyrel@haveman.net>
 *
 * This file is part of jIRCd.
 *
 * jIRCd is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * jIRCd is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with jIRCd; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package net.sf.j_ircd.server.servlet.irc.nickserv.commands;

import net.sf.j_ircd.server.irc.*;

/**
 * @author markhale
 */
public class NickServ implements Command {
	public void invoke(RegisteredEntity src, String[] params) {
		User nickserv = src.getServer().getNetwork().getUser("NickServ");
		Message msg = new Message((User)src, "PRIVMSG", nickserv);
		msg.appendLastParameter(Util.join(params, ' ', 0));
		nickserv.send(msg);
	}
	public String getName() {
		return "NICKSERV";
	}
	public int getMinimumParameterCount() {
		return 1;
	}
}