package com.minibank.exceptions;

public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException() {
		super("Insufficient balance in the account");
	}
}
