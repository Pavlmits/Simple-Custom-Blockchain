package com.pavlmits.blockchain.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Block {

    private final int id;
    private final int magicNumber;
    private final int padding;
    private final long timestamp;
    private final String miner;
    private final String currentHash;
    private final String previousHash;
    private final List<String> messages;

    Block(int id, int magicNumber, String previousHash, int padding, long timestamp, String hash, String miner, List<String> messages) {
        this.id = id;
        this.magicNumber = magicNumber;
        this.previousHash = previousHash;
        this.padding = padding;
        this.timestamp = timestamp;
        this.currentHash = hash;
        this.miner = miner;
        this.messages = new ArrayList<>(messages);
    }

    int getId() {
        return id;
    }

    String getCurrentHash() {
        return currentHash;
    }

    String getPreviousHash() {
        return previousHash;
    }

    long getTimestamp() {
        return timestamp;
    }

    int getMagicNumber() {
        return magicNumber;
    }

    public String getMiner() {
        return miner;
    }

    public int getPadding() {
        return padding;
    }

    public List<String> getMessages() {
        return messages;
    }
}
