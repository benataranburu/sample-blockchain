package com.github.benataranburu.sampleblockchain;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.github.benataranburu.sampleblockchain.utils.SHA256;

/**
 * 
 * 
 */
public class Miner<T extends Tx> {

	List<T> transactionPool = new ArrayList<T>();
	SimpleBlockchain chain = null;

	public Miner(SimpleBlockchain chain) {
		this.chain = chain;
	}

	public void mine(T tx) {
		transactionPool.add(tx);
		if (transactionPool.size() > SimpleBlockchain.BLOCK_SIZE) {
			createBlockAndApplyToChain();
		}
	}

	private void createBlockAndApplyToChain() {

		Block block = chain.newBlock();
		// set previous hash with current hash
		block.setPreviousHash(chain.getHead().getHash());
		// set block hashes from POW
		// block
		block.setHash(proofOfWork(block));
		chain.addAndValidateBlock(block);
		// empty pool
		transactionPool = new ArrayList<T>();
	}

	private String proofOfWork(Block block) {

		String nonceKey = block.getNonce();
		long nonce = 0;
		boolean nonceFound = false;
		String nonceHash = "";

		Gson parser = new Gson();
		String serializedData = parser.toJson(transactionPool);
		String message = block.getTimeStamp() + block.getIndex() + block.getMerkleRoot() + serializedData
				+ block.getPreviousHash();

		while (!nonceFound) {

			nonceHash = SHA256.generateHash(message + nonce);
			nonceFound = nonceHash.substring(0, nonceKey.length()).equals(nonceKey);
			nonce++;

		}

		return nonceHash;

	}

}
