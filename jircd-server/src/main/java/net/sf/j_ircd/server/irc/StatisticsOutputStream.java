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

package net.sf.j_ircd.server.irc;

import java.io.*;

/**
 *
 * @author Mark
 */
public final class StatisticsOutputStream extends FilterOutputStream {
    private long bytesWritten;
    
    /** Creates a new instance of StatisticsOutputStream */
    public StatisticsOutputStream(OutputStream out) {
        super(out);
    }
    public long getBytesWritten() {
        return bytesWritten;
    }
    public void write(int b) throws IOException {
        out.write(b);
        bytesWritten++;
    }
    public void write(byte[] b, int offset, int len) throws IOException {
        out.write(b, offset, len);
        bytesWritten += len;
    }
}
