package org.springboot.workorder.util.uuid;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * <b>uuid.hex</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32,
 * This string will consist of only hex digits. Optionally, 
 * the string may be generated with seperators between each 
 * component of the UUID.
 *
 * @author Gavin King
 */
@Component
public class UUIDHexGenerator extends UUIDGenerator{
	
	private String sep = "";
	
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}
	
	public Serializable generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) ).append(sep)
		.append( format( getJVM() ) ).append(sep)
		.append( format( getHiTime() ) ).append(sep)
		.append( format( getLoTime() ) ).append(sep)
		.append( format( getCount() ) )
		.toString();
	}
	
	public static void main( String[] args ) throws Exception {
		UUIDHexGenerator gen = new UUIDHexGenerator();
		UUIDHexGenerator gen2 = new UUIDHexGenerator();
		
		for ( int i=0; i<10; i++) {
			String id = (String) gen.generate();
			System.out.println( id + ": " +  id.length() );
			String id2 = (String) gen2.generate();
			System.out.println( id2 + ": " +  id2.length() );
		}
	}
	
}
