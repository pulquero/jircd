package net.sf.j_ircd.ext.jms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import javax.jms.StreamMessage;
import javax.jms.JMSException;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotReadableException;
import javax.jms.MessageNotWriteableException;

public class StreamMessageImpl extends MessageImpl implements StreamMessage {
	private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private ObjectOutputStream dataOut;
	private ObjectInputStream dataIn;

	public StreamMessageImpl() throws JMSException {
		try {
			dataOut = new ObjectOutputStream(buffer);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public boolean readBoolean() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readBoolean();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public byte readByte() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readByte();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public int readBytes(byte[] value) throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.read(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public char readChar() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readChar();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public short readShort() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readShort();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public int readInt() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readInt();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public long readLong() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readLong();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public float readFloat() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readFloat();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public double readDouble() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readDouble();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public Object readObject() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readObject();
		} catch(ClassNotFoundException cnfe) {
			throw new JMSException(cnfe.getMessage());
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public String readString() throws JMSException {
		if(dataIn == null)
			throw new MessageNotWriteableException("Write-only");
		try {
			return dataIn.readUTF();
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}

	public void writeBoolean(boolean value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeBoolean(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeByte(byte value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeByte(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeBytes(byte[] value) throws JMSException {
		writeBytes(value, 0, value.length);
	}
	public void writeBytes(byte[] value, int offset, int length) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.write(value, offset, length);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeChar(char value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeChar(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeShort(short value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeShort(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeInt(int value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeInt(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeLong(long value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeLong(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeFloat(float value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeFloat(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeDouble(double value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeDouble(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public void writeObject(Object value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		if(value instanceof Boolean) {
			writeBoolean(((Boolean)value).booleanValue());
		} else if(value instanceof Byte) {
			writeByte(((Byte)value).byteValue());
		} else if(value instanceof Short) {
			writeShort(((Short)value).shortValue());
		} else if(value instanceof Character) {
			writeChar(((Character)value).charValue());
		} else if(value instanceof Integer) {
			writeInt(((Integer)value).intValue());
		} else if(value instanceof Long) {
			writeLong(((Long)value).longValue());
		} else if(value instanceof Float) {
			writeFloat(((Float)value).floatValue());
		} else if(value instanceof Double) {
			writeDouble(((Double)value).doubleValue());
		} else if(value instanceof String) {
			writeString((String)value);
		} else if(value instanceof byte[]) {
			writeBytes((byte[])value);
		} else if(value == null) {
			throw new NullPointerException("Cannot write null values");
		} else {
			throw new MessageFormatException("Cannot write objects of type "+value.getClass().getName());
		}
	}
	public void writeString(String value) throws JMSException {
		if(dataOut == null)
			throw new MessageNotReadableException("Read-only");
		try {
			dataOut.writeUTF(value);
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}

	public void reset() throws JMSException {
		try {
			dataIn = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
			dataOut = null;
		} catch(IOException ioe) {
			throw new JMSException(ioe.getMessage());
		}
	}
	public String toString() {
		return new String(buffer.toByteArray());
	}
}
