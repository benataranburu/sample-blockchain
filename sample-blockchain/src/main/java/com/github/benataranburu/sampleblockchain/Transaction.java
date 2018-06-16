package com.github.benataranburu.sampleblockchain;

import com.github.benataranburu.sampleblockchain.utils.SHA256;

public class Transaction implements Tx {
	
	private String hash;
	private String value;
	
	public String hash() { return hash; }
	
	public Transaction(String value) {
		this.hash = SHA256.generateHash(value);
		this.setValue(value);		
	}

	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.hash = SHA256.generateHash(value);	
		this.value = value;
	}
	
	public String toString() { 
		return this.hash + " : "+this.getValue();		
	}
	

}
