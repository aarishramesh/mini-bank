package com.minibank.model;

import lombok.Data;

@Data
public class Transaction {
	private long timeOfTransaction;
	private TxnType txnType;
	
	public Transaction(long time, TxnType type) {
		this.timeOfTransaction = time;
		this.txnType = type;
	}
	
	public static enum TxnType {
		DEPOSIT, WITHDRAW;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (timeOfTransaction != other.timeOfTransaction)
			return false;
		if (txnType != other.txnType)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timeOfTransaction ^ (timeOfTransaction >>> 32));
		result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
		return result;
	}
}
