package com.pavlmits.blockchain.chat;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final Blockchain blockchain = new Blockchain();
        Miner miner = new Miner(blockchain);



        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(miner, String.valueOf(i));
            thread.start();
            threads.add(thread);
        }

        //TODO create real chat
        for (int i = 0; i < 10 ; i++) {
            blockchain.getMessages().add("-Message" + i);
            Thread.sleep(10);
        }

        boolean allDead=false;
        while(! allDead){
            allDead=true;
            for (Thread thread : threads)
                if (thread.isAlive()) allDead = false;
            Thread.sleep(2000);

        }

        blockchain.printAll();

        System.out.println(blockchain.isValid());

        //TODO add stage 5
    }
}