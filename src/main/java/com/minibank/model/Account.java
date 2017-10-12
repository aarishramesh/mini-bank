package com.minibank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.minibank.model.Transaction.TxnType;
import com.minibank.util.MiniBankUtil;

import lombok.Data;

/**
 * Account model. A reentrant lock is used to ensure concurrency
 * 
 * @author aarishramesh
 *
 */
@Data
public class Account {

	private final ReentrantLock lock = new ReentrantLock(true);
	private final String accountId;
	private final String accountName;
	private final String address;
	private float money;
	private final Currency currency;
	private final Map<Integer, List<Transaction>> monthVsTxns = 
			new ConcurrentHashMap<Integer, List<Transaction>>();

	public Account(String accountId, String accountName, Currency currency, String address) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.currency = currency;
		this.address = address;
	}

	public void depositMoney(final float money) {
		lock.lock();
		try {
			this.money += money ;
			addTransaction(System.currentTimeMillis(), TxnType.DEPOSIT);
		} finally {
			lock.unlock();
		}
	}

	public void withdrawMoney(final float money) {
		lock.lock();
		try {
			this.money -= money;
			addTransaction(System.currentTimeMillis(), TxnType.WITHDRAW);
		} finally {
			lock.unlock();
		}
	}
	
	public void addTransaction(long time, TxnType txn) {
		lock.lock();
		try {
			int month = MiniBankUtil.getMonth();
			if (this.getMonthVsTxns().get(month) != null)
				this.getMonthVsTxns().get(month)
					.add(new Transaction(System.currentTimeMillis(), txn));
			else 
				this.getMonthVsTxns().put(month, new ArrayList<Transaction>());
		} finally {
			lock.unlock();
		}
	}
	
	public List<Transaction> getTransactionStmtForMonth(int month) {
		return monthVsTxns.get(month);
	}
	
	public static enum Country {
		IND;
	}

	public static enum Currency {
		INR;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (currency != other.currency)
			return false;
		if (Float.floatToIntBits(money) != Float.floatToIntBits(other.money))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + Float.floatToIntBits(money);
		return result;
	}
}
