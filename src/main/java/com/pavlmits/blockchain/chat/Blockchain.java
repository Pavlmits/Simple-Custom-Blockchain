package com.pavlmits.blockchain.chat;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private volatile int lastIdOfABlock;
    private volatile int padding;
    private volatile List<Block> blocks;
    private volatile long previousTimer;
    private volatile List<String> messages;

    Blockchain() {
        this.lastIdOfABlock = 1;
        this.padding = 1;
        this.previousTimer = 0;
        blocks = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public boolean isValid() {
        for (int i = 0; i < blocks.size() - 1; i++) {
            if (!blocks.get(i + 1).getPreviousHash().equals(blocks.get(i).getCurrentHash()))
                return false;
        }
        return true;
    }

    void printAll() {
        for (Block block : blocks) {
            print(block);
        }
    }

    private void print(Block block) {
        System.out.println("Block: ");
        System.out.println("Created by miner #" + block.getMiner());
        System.out.println(block.getMiner() + "gets 100 VC");
        System.out.println("Id: " + block.getId());
        System.out.println("Timestamp: " + block.getTimestamp());
        System.out.println("Magic number: " + block.getMagicNumber());
        System.out.println("Hash of the previous block: \n" + block.getPreviousHash());
        System.out.println("Hash of the block: \n" + block.getCurrentHash());
        if (block.getMessages().isEmpty())
            System.out.println("Block data: no messages");
        else {
            System.out.println("Block data:");
            for (String message : block.getMessages()) {
                System.out.println(message);
            }
        }
        System.out.println("N was increased to " + block.getPadding());
        System.out.println();
    }

    public synchronized int getIdOfAnewBlock() {
        return lastIdOfABlock++;
    }

    public int getLastIdOfABlock() {
        return lastIdOfABlock;
    }

    public synchronized String getLastBlocksHash() {
        if (blocks.isEmpty())
            return "0";
        return blocks.get(blocks.size() - 1).getCurrentHash();
    }

    public synchronized void addBlock(String hash, int magicNumber, String minerName, long time) {
        Block block = new Block(getIdOfAnewBlock(), magicNumber, getLastBlocksHash(), padding, time, hash, minerName, messages);
        blocks.add(block);
        calculatePadding(time);
    }

    //TODO find effective way to calculate padding
    private synchronized void calculatePadding(long time) {
        if (time <= previousTimer && padding < 5) {
            previousTimer = time;
            padding++;
            messages.clear();
        } else {
            previousTimer = time;
            padding--;
        }
    }

    public int getPadding() {
        return padding;
    }

    public List<String> getMessages() {
        return messages;
    }
}
