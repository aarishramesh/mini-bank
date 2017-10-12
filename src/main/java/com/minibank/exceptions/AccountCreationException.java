package com.minibank.exceptions;

public class AccountCreationException extends Exception {
	private static final long serialVersionUID = 1L;

	public AccountCreationException() {
		super("Account already exists");
	}
}
