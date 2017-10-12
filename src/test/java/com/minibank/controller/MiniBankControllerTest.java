package com.minibank.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MiniBankControllerTest {
	private MiniBankController minibankController;
	
	@Before
	public void setUp() throws Exception {
		minibankController = MiniBankController.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAccountCreation() {
		String accountName = "aarish";
		String currency = "INR";
		String address = "Indira nagar";

		minibankController.createAccount(accountName, address, currency);
		
		assertNotNull("Account created successfully", minibankController);
	}

	@Test
	public void testDepositMoney() {
		String accountName = "aarish";
		float depositMoney = 10;
		float actualMoney = minibankController.getAccount(accountName).getMoney();
		float resultantMoney = actualMoney + depositMoney;
		try {
			minibankController.depositMoney(accountName, depositMoney);
		} catch (Exception ex) {
			// Should not occur
		}
		assertTrue("Money deposited ", minibankController.getAccount(accountName).getMoney()
				== resultantMoney);
	}
	
	@Test
	public void testWithdrawMoney() {
		String accountName = "aarish";
		float depositMoney = 10;
		try {
			minibankController.depositMoney(accountName, depositMoney);
		} catch (Exception ex) {
			// Should not occur
		}
		float withdrawMoney = 5;
		float actualMoney = minibankController.getAccount(accountName).getMoney();
		float resultantMoney = actualMoney - withdrawMoney;
		try {
			minibankController.withdrawMoney(accountName, withdrawMoney);
		} catch (Exception ex) {
			// Should not occur
		}
		assertTrue("Money deposited ", minibankController.getAccount(accountName).getMoney()
				== resultantMoney);
	}
	
	@Test
	public void testCloseAccount() {
		String accountName = "aarish";
		float depositMoney = 10;
		float actualMoney = minibankController.getAccount(accountName).getMoney();
		float resultantMoney = actualMoney + depositMoney;
		try {
			minibankController.depositMoney(accountName, depositMoney);
		} catch (Exception ex) {
			// Should not occur
		}
		assertTrue("Money deposited ", minibankController.getAccount(accountName).getMoney()
				== resultantMoney);
	}
}
