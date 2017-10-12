package com.minibank.exceptions;

public class InvalidAccountException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
		super("Invalid account");
	}
}
