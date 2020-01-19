package com.modernjava.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

        Runnable producer =  () ->{
            int i=0;
            while (true){
                try {
                    arrayBlockingQueue.put(++i);
                    System.out.println("Added: " + i);
                    TimeUnit.MILLISECONDS.sleep(1000);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }

            }
        };

        Runnable consumer = () -> {
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(10000);
                    Integer poll;
                    poll = arrayBlockingQueue.take();
                    System.out.println("Polled: " + poll);
                    TimeUnit.MILLISECONDS.sleep(20);
                }catch (Exception ie){
                    ie.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.shutdown();

    }
}
