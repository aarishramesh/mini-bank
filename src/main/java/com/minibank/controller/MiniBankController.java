package com.minibank.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.minibank.exceptions.AccountCreationException;
import com.minibank.exceptions.InsufficientBalanceException;
import com.minibank.exceptions.InvalidAccountException;
import com.minibank.model.Account;
import com.minibank.model.Account.Currency;
import com.minibank.model.response.ApiResponse;
import com.minibank.model.response.Error;

public final class MiniBankController {

	//private static final Logger slf4Logger = new Logger(MiniBank.class);

	private static final Map<String, Account> accountNameMap = new ConcurrentHashMap<String, Account>();

	private static final MiniBankController INSTANCE = new MiniBankController();

	private void MiniBankController() {

	}

	public static MiniBankController getInstance() {
		return INSTANCE;
	}

	public Account getAccount(String accountName) {
		if (accountNameMap.get(accountName) != null) 
			return accountNameMap.get(accountName);
		return null;
	}

	public ApiResponse createAccount(final String accountName, final String address
			, final String currency) {

		ApiResponse response = new ApiResponse();
		try {
			String accountId = UUID.randomUUID().toString();
			Currency currencyObj = null;
			if (currency.equals("INR"))
				currencyObj = Currency.INR;
			Account account = new Account(accountId, accountName, currencyObj, address);

			synchronized(this) {
				if (accountNameMap.get(accountName) == null) {
					accountNameMap.put(accountName, account);
				} else {
					throw new AccountCreationException();
				}
			}
			response.setData("Account created successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}

	public ApiResponse depositMoney(final String accountName, final float money) throws InvalidAccountException {
		ApiResponse response = new ApiResponse();
		try {
			Account account = accountNameMap.get(accountName);
			if (account != null) {
				account.depositMoney(money);
				// Log
			} else {
				throw new InvalidAccountException();
			}
			response.setData("Money deposit successful for account " + accountName);
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}

	public ApiResponse withdrawMoney(final String accountName, final float money) throws InsufficientBalanceException, InvalidAccountException {
		ApiResponse response = new ApiResponse();
		try {
			Account account = accountNameMap.get(accountName);
			if (account != null) {
				synchronized(this) {
					float balance = account.getMoney();
					if (balance - money >= 0) {
						account.withdrawMoney(money);
						response.setData(balance - money);
					} else {
						throw new InsufficientBalanceException();
					}
				}
			} else {
				throw new InvalidAccountException();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}

	public ApiResponse checkBalance(final String accountName) throws InvalidAccountException {
		ApiResponse response = new ApiResponse();
		try {
			Account account = accountNameMap.get(accountName);
			if (account != null) {
				response.setData(account.getMoney());
			} else {
				throw new InvalidAccountException();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}

	public ApiResponse getTxnStatementForMonth(final String accountName, final int month) throws InvalidAccountException {
		ApiResponse response = new ApiResponse();
		try {
			Account account = accountNameMap.get(accountName);
			if (account != null) {
				response.setData(account.getTransactionStmtForMonth(month));
			} else {
				throw new InvalidAccountException();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}
	
	public ApiResponse closeAccount(final String accountName) throws InvalidAccountException {
		ApiResponse response = new ApiResponse();
		try {
			Account account = accountNameMap.get(accountName);
			if (account != null) {
				accountNameMap.remove(account);
				response.setData(accountName + " closed successfully");
			} else {
				throw new InvalidAccountException();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Error error = new Error("500", ex.getMessage());
			response.setError(error);
		}
		return response;
	}
	//public List<Transaction>
}
