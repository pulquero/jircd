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

package jircd.irc_p10.commands;

import java.io.IOException;

import jircd.jIRCdMBean;
import jircd.irc.Connection;
import jircd.irc.ConnectedEntity;
import jircd.irc_p10.Util;

/**
 * @author markhale
 */
public class Connect extends jircd.irc.commands.Connect {
    public Connect(jIRCdMBean jircd) {
        super(jircd);
    }
    protected void sendPass(ConnectedEntity to, String password) {
        Util.sendPass(to, password);
    }
    protected void sendServer(ConnectedEntity to, jIRCdMBean jircd) {
        Util.sendServer(to, jircd);
    }
}
