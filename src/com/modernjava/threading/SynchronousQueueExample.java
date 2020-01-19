package com.modernjava.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        final String[] names = {"Mike", "Syed", "Jean", "Jenny", "Rajeev", "Henry"};
        final SynchronousQueue<String> queue = new SynchronousQueue<>();

        Runnable producer = () -> {
           for (String name: names){
               try {
                   queue.put(name);
                   System.out.println("Inserted: " + name + " in the queue");
                   TimeUnit.MILLISECONDS.sleep(1000);
               }catch (InterruptedException ie){
                   ie.printStackTrace();
               }
           }
        };

        Runnable consumer = () -> {
           while (true){
               try {
                   System.out.println("Retrieved: " + queue.take() + " from the queue");
                   TimeUnit.MILLISECONDS.sleep(10000);
               }catch (InterruptedException ie) {
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
