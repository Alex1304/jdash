package com.github.alex1304.jdash.exceptions;

/**
 * Thrown when a GD API call fails
 * 
 * @author Alex1304
 */
public class GDAPIException extends Exception {

	private static final long serialVersionUID = 5480251003912770419L;
	
	private Exception underlyingException;
	
	public GDAPIException(Exception underlyingException) {
		this.underlyingException = underlyingException;
	}

	public GDAPIException() {
		super();
	}

	public GDAPIException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public GDAPIException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GDAPIException(String arg0) {
		super(arg0);
	}

	public GDAPIException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Gets the underlyingException
	 *
	 * @return Exception
	 */
	public Exception getUnderlyingException() {
		return underlyingException;
	}

}
