package com.modernjava.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        final String[] names = {"Mike", "Syed", "Jean", "Jenny", "Rajeev", "Henry"};
        final PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        Runnable producer = () -> {
            for (String name:names)
                queue.put(name);
        };

        Runnable consumer = () -> {
            while (true){
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.shutdown();

    }
}
