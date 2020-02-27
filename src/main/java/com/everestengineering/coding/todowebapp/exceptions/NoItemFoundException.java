/**
 * 
 */
package com.everestengineering.coding.todowebapp.exceptions;

/**
 * @author Srinivas.Pakala
 *
 */
public class NoItemFoundException extends Exception {
	
	private static final long serialVersionUID = -1190319181817521691L;

	public NoItemFoundException(String exception){
		super(exception);
	}
}
