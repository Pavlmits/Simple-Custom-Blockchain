package com.pavlmits.blockchain.chat;

import com.pavlmits.blockchain.chat.util.StringUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.Random;

public class Miner implements Runnable {

    private Blockchain blockchain;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        Pair<Integer, String> pair = mine();

        long endTime = System.nanoTime();
        blockchain.addBlock(pair.getRight(), pair.getLeft(), Thread.currentThread().getName(),(endTime - startTime) / 1_000_000_000);
    }

    private synchronized Pair<Integer,String> mine() {
        return calculateHash(blockchain.getLastBlocksHash());
    }


    private Pair<Integer,String> calculateHash(String previousHash){
        int magicNumber = new Random().nextInt(Integer.MAX_VALUE);
        String hash = calculateSHA(blockchain.getLastIdOfABlock() + 1, previousHash, magicNumber);

        while (!hash.startsWith("0".repeat(blockchain.getPadding()))) {
            magicNumber = new Random().nextInt(Integer.MAX_VALUE);
            hash = calculateSHA(blockchain.getLastIdOfABlock() + 1, previousHash, magicNumber);
        }

        return ImmutablePair.of(magicNumber, hash);
    }

    private String calculateSHA(int id, String previousHash,int magicNumber) {
        String input = id + previousHash + new Date().getTime() + magicNumber;
        return StringUtil.applySha256(input);
    }

}
